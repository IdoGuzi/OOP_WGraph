package unitest;

import classes.NodeInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a unit test for the NodeInfo class.
 * @author Ido Guzi
 */
class NodeInfoTest {


    /**
     * Test function for the getKey method.
     */
    @Test
    void getKey() {
        ArrayList<NodeInfo> nodes = factory(100);
        for (int i=0;i<100;i++){
            assertEquals(i,nodes.get(i).getKey());
        }
    }

    /**
     * Test function for the getInfo method.
     * assumptions: setInfo works.
     */
    @Test
    void getInfo() {
        ArrayList<NodeInfo> nodes = factory(100);
        for (int i=0;i<100;i++){
            assertEquals("", nodes.get(i).getInfo());
            nodes.get(i).setInfo(Integer.toString(i));
        }
        for (int i=0;i<100;i++){
            assertEquals(Integer.toString(i), nodes.get(i).getInfo());
        }
    }

    /**
     * Test function for the setInfo method.
     * assumptions: getInfo works.
     */
    @Test
    void setInfo() {
        ArrayList<NodeInfo> nodes = factory(100);
        for (int i=0;i<100;i++){
            String old = nodes.get(i).getInfo();
            nodes.get(i).setInfo(Integer.toString(i));
            assertNotEquals(old, nodes.get(i).getInfo());
        }
    }

    /**
     * Test function for the getTag method.
     * assumptions: setTag works.
     */
    @Test
    void getTag() {
        ArrayList<NodeInfo> nodes = factory(100);
        for (int i=0;i<100;i++){
            assertEquals(0.0, nodes.get(i).getTag());
            nodes.get(i).setTag(i);
        }
        for (int i=0;i<100;i++){
            assertEquals(i, nodes.get(i).getTag());
        }
    }

    /**
     * Test function for the setTag method.
     * assumptions: getTag works.
     */
    @Test
    void setTag() {
        ArrayList<NodeInfo> nodes = factory(100);
        for (int i=0;i<100;i++){
            double old = nodes.get(i).getTag();
            nodes.get(i).setTag(i);
            if (i==0) continue;
            assertNotEquals(old, nodes.get(i).getTag());
        }
    }

    private ArrayList<NodeInfo> factory(int nodes){
        ArrayList<NodeInfo> arr = new ArrayList<>();
        for (int i=0;i<nodes;i++) {
            arr.add(i,new NodeInfo());
        }
        return arr;
    }
}