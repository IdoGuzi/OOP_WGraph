package classes;

import ex1.node_info;
import ex1.weighted_graph;
import util.Pair;

import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph{
    private HashMap<Integer, node_info> nodes;
    private HashMap<Pair<Integer>, Double> edges;
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
        Pair<Integer> p = new Pair<>(node1,node2);
        if (edges.get(p)>=0) return true;
        return false;
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
        Pair<Integer> p = new Pair<>(node1,node2);
        Double w = edges.get(p);
        if (w==null) w = edges.get(p.swap());
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

    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     *
     * @return Collection<node_data> of the nodes in the graph.
     */
    @Override
    public Collection<node_info> getV() {
        return null;
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
        return null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @param key - the key of the node to remove.
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        return null;
    }

    /**
     * Delete the edge from the graph.
     * @param node1 - key of the node in one side of the edge.
     * @param node2 - key of the node in the other side of the edge.
     */
    @Override
    public void removeEdge(int node1, int node2) {

    }

    /**
     * return the number of vertices (nodes) in the graph.
     * @return the number of nodes in the graph.
     */
    @Override
    public int nodeSize() {
        return 0;
    }

    /**
     * return the number of edges (undirectional graph).
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return 0;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return the number of changes in the graph.
     */
    @Override
    public int getMC() {
        return 0;
    }
}
