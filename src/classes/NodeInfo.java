package classes;

import ex1.node_info;

/**
 * This class is an implementation of the interface node_info.
 * representing a vertex of a graph.
 * @author Ido Guzi
 */
public class NodeInfo implements node_info {
    private int key;
    private double tag;
    private String info;
    private static int keyCount=0;

    public NodeInfo(){
        key=keyCount;
        tag=0;
        info="";
        keyCount++;
    }

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
