package ex1;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


/**
 * This class implements the weighted_graph_algorithms interface.
 * allowing to perform Graph Theory operations on an undirected weighted graph.
 *
 * @author Ido Guzi
 */
public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;

    public WGraph_Algo(){
        graph = new WGraph_DS();
    }

    public WGraph_Algo(weighted_graph g){
        graph=g;
    }


    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g - the graph to initialize to this graph_algo.
     */
    @Override
    public void init(weighted_graph g) {
        graph=g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return - a weighted_graph object.
     */
    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return - deep copy of the weighted graph in this object.
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(graph);
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node.
     * solving this problem by running dijksta algorithm,
     * and checking that all dijksta initialization of the distance had changed.
     * @return - true if the graph is connected, else false.
     */
    @Override
    public boolean isConnected() {
        Collection<node_info> col = graph.getV();
        if (col.size()==0 || col.size()==1) return true;
        Iterator<node_info> it = col.iterator();
        int a=it.next().getKey(),b=it.next().getKey();
        Dijksta(a,b);
        it = graph.getV().iterator();
        while(it.hasNext()){
            node_info n = it.next();
            if (n.getTag()==Double.MAX_VALUE) return false;
        }
        return true;
    }

    /**
     * returns the length of the shortest path between src to dest
     * if no such path --> returns -1
     * getting the distance by using dijksta algorithm.
     * @param src  - start node
     * @param dest - end (target) node
     * @return - the distance from src to dest with dijksta, -1 if path doesn't exist.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        Dijksta(src, dest);
        double dist = graph.getNode(dest).getTag();
        if (dist==Double.MAX_VALUE) return -1;
        return dist;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * if path doesn't exist returning null.
     * This method sovle the shortest Path problem by using dijksta algorithm.
     * @param src  - start node
     * @param dest - end (target) node
     * @return - returns the the shortest path between src to dest with dijksta, null if none.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        HashMap<node_info,node_info> parents = Dijksta(src, dest);
        List<node_info> path = new LinkedList<>();
        node_info tmp = graph.getNode(dest);
        while (tmp!=null){
            path.add(0,tmp);
            tmp = parents.get(tmp);
        }
        if (path.get(0).getKey()!=src || path.get(path.size()-1).getKey()!=dest) {
            return null;
        }
        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            // Writing to a file
            FileWriter fw = new FileWriter(file);
            fw.write(toJsonGraph());
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            Scanner scanner = new Scanner(new File(file));
            String jsonString = scanner.useDelimiter("\\A").next();
            JSONObject jp = new JSONObject(jsonString);
            fromJsonGraph(jp);
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String toJsonGraph(){
        JSONObject jo = new JSONObject();
        JSONArray no = new JSONArray();
        JSONArray eo = new JSONArray();
        jo.put("nodeSize", graph.nodeSize());
        jo.put("edgeSize", graph.edgeSize());
        jo.put("modeCount",graph.getMC());
        Iterator<node_info> itr = graph.getV().iterator();
        while (itr.hasNext()){
            node_info n = itr.next();
            no.put(toJsonNode(n));
            Iterator<node_info> itr2 = graph.getV(n.getKey()).iterator();
            while (itr2.hasNext()){
                node_info v = itr2.next();
                eo.put(toJsonEdge(n.getKey(),v.getKey(),graph.getEdge(n.getKey(),v.getKey())));
            }
        }
        jo.put("nodes",no);
        jo.put("edges",eo);
        return jo.toString();
    }

    private void fromJsonGraph(JSONObject g){
        this.graph = new WGraph_DS();
        JSONArray ja = g.getJSONArray("nodes");
        int size = ja.length();
        for (int i=0;i<size;i++){
            fromJsonNode(ja.getJSONObject(i));
        }
        ja = g.getJSONArray("edges");
        size = ja.length();
        for (int i=0;i<size;i++){
            fromJsonEdge(ja.getJSONObject(i));
        }
    }

    private JSONObject toJsonEdge(int n1, int n2, double w){
        JSONObject jo = new JSONObject();
        jo.put("node1",n1);
        jo.put("node2",n2);
        jo.put("weight",w);
        return jo;
    }

    private void fromJsonEdge(JSONObject edge){
        int n1=edge.getInt("node1");
        int n2=edge.getInt("node2");
        double w = edge.getDouble("weight");
        graph.connect(n1,n2,w);
    }

    private JSONObject toJsonNode(node_info n){
        JSONObject jo = new JSONObject();
        jo.put("key",n.getKey());
        jo.put("info",n.getInfo());
        jo.put("tag",n.getTag());
        return jo;
    }

    private void fromJsonNode(JSONObject node){
        int key = node.getInt("key");
        graph.addNode(key);
        graph.getNode(key).setInfo(node.getString("info"));
        graph.getNode(key).setTag(node.getDouble("tag"));

    }



    private void clearTags(){
        Iterator<node_info> itr = graph.getV().iterator();
        while (itr.hasNext()){
            node_info n = itr.next();
            n.setTag(Double.MAX_VALUE);
            n.setInfo("");
        }
    }


    private HashMap<node_info,node_info> Dijksta(int src, int dest){
        clearTags();
        HashMap<node_info,node_info> prev = new HashMap<>();
        graph.getNode(src).setTag(0);
        PriorityQueue<node_info> queue = new PriorityQueue<>(6, new NodeComperator());
        queue.add(graph.getNode(src));
        prev.put(graph.getNode(src),null);
        while (!queue.isEmpty()){
            node_info n = queue.poll();
            Iterator<node_info> itr = graph.getV(n.getKey()).iterator();
            while (itr.hasNext()){
                node_info v = itr.next();
                if (v.getTag()>n.getTag()+graph.getEdge(n.getKey(),v.getKey())) {
                    v.setTag(n.getTag()+graph.getEdge(n.getKey(),v.getKey()));
                    prev.put(v,n);
                    queue.add(v);
                }
            }
        }
        return prev;

    }

    private class NodeComperator implements Comparator<node_info> {
        private final Double EPS = 0.00001;

        /**
         * Note: this comparator imposes orderings that are inconsistent with equals.
         * <p>
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * ensuring {@code sgn(compare(x, y)) ==
         * -sgn(compare(y, x))} for all {@code x} and {@code y}.  (This
         * implies that {@code compare(x, y)} must throw an exception if and only
         * if {@code compare(y, x)} throws an exception.)<p>
         * <p>
         * compare is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * ensuring that {@code compare(x, y)==0}
         * implies that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all
         * {@code z}.<p>
         * <p>
         * <i>not</i> supporting {@code (compare(x, y)==0) == (x.equals(y))}.
         * <p>
         * In the foregoing description, the notation
         * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
         * <i>signum</i> function, which is defined to return one of {@code -1},
         * {@code 0}, or {@code 1} according to whether the value of
         * <i>expression</i> is negative, zero, or positive, respectively.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         */
        @Override
        public int compare(node_info o1, node_info o2) {
            if (o1.getTag()-o2.getTag()<-EPS) return -1;
            if (o1.getTag()-o2.getTag()<EPS) return 0;
            if (o1.getTag()-o2.getTag()>EPS) return 1;
            throw new RuntimeException("Error: no condition met");
        }
    }
}
