package classes;

import ex1.node_info;
import ex1.weighted_graph;

import java.util.*;

public class WGraph_DS implements weighted_graph{
    private HashMap<Integer, node_info> nodes;
    private HashMap<Integer,HashMap<Integer,Double>> edges;
    private int nodeCount, edgeCount, modeCount;

    public WGraph_DS(){
        nodes = new HashMap<>();
        edges = new HashMap<>();
        nodeCount = 0;
        edgeCount = 0;
        modeCount = 0;
    }


    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return nodes.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     *
     * @param node1 - id of first node.
     * @param node2 - id of second node.
     * @return true if the nodes are connected with an edge.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (nodes.get(node1)==null || nodes.get(node2)==null) return false;
        if (node1==node2) return true;
        HashMap<Integer,Double> nei = edges.get(node1);
        if (nei==null) return false;
        Double w = nei.get(node2);
        if (w==null) return false;
        return true;
    }

    /**
     * return the weight of the edge (node1, node2).
     * if edge doesn't exist -1
     * @param node1 - id of the first node.
     * @param node2 - id of the second node.
     * @return the weight of the edge, -1 if doesn't exist.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) return -1;
        if (node1==node2) return 0;
        Double w = edges.get(node1).get(node2);
        if (w==null) return -1;
        return w;
    }

    /**
     * add a new node to the graph with the given key.
     * if node with given key exist, doing nothing.
     *
     * @param key - key for the new node.
     */
    @Override
    public void addNode(int key) {
        nodes.putIfAbsent(key,new NodeInfo(key));
        edges.put(key,new HashMap<>());
        nodeCount++;
        modeCount++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * if edge exist, update weight.
     *
     * @param node1 - key of the first node.
     * @param node2 - key of the second node.
     * @param w - weight for the edge;
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (nodes.get(node1)==null || nodes.get(node2)==null) return;
        if (w<0) throw new IllegalArgumentException("Error: weight should be bigger or equal than 0");
        edges.get(node1).put(node2,w);
        edges.get(node2).put(node1,w);
        edgeCount++;
        modeCount++;
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     *
     * @return Collection<node_data> of the nodes in the graph.
     */
    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     *
     * @param node_id - id of the node
     * @return Collection<node_data> of the nodes connected to node_id
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (getNode(node_id)==null) return null;
        Collection<node_info> nei = new ArrayList<>();
        Iterator<Integer> it = edges.get(node_id).keySet().iterator();
        while (it.hasNext()){
            nei.add(nodes.get(it.next()));
        }
        return nei;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @param key - the key of the node to remove.
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        node_info n = nodes.get(key);
        if (n==null) return null;
        Iterator<node_info> it = getV(key).iterator();
        while (it.hasNext()){
            removeEdge(key,it.next().getKey());
        }
        edges.remove(key);
        nodes.remove(key);
        nodeCount--;
        modeCount++;
        return n;
    }

    /**
     * Delete the edge from the graph.
     * @param node1 - key of the node in one side of the edge.
     * @param node2 - key of the node in the other side of the edge.
     */
    @Override
    public void removeEdge(int node1, int node2) {
        node_info n1 = nodes.get(node1), n2 = nodes.get(node2);
        if (n1==null || n2==null) return;
        edges.get(node1).remove(node2);
        edges.get(node2).remove(node1);
        edgeCount--;
        modeCount++;
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * @return the number of nodes in the graph.
     */
    @Override
    public int nodeSize() {
        return nodeCount;
    }

    /**
     * return the number of edges (undirectional graph).
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return edgeCount;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return the number of changes in the graph.
     */
    @Override
    public int getMC() {
        return modeCount;
    }




    /**
     * This class is an implementation of the interface node_info.
     * representing a vertex of a graph.
     */
    private class NodeInfo implements node_info {
        private int key;
        private double tag;
        private String info;


        public NodeInfo(int k){
            key=k;
            tag=0;
            info="";
        }

        /**
         * return the key (ID) associated with this node.
         * @return the key of the node.
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * return information about the node.
         * @return a string containing information about this node.
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * Change the current information of this node to string (s).
         * @param s the new information of the node.
         */
        @Override
        public void setInfo(String s) {
            info=s;
        }

        /**
         * return data that was saved for any reason.
         * @return a real number containing as tag.
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * set a real number as temporal data that can be used for any reason.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag=t;
        }
    }
}
