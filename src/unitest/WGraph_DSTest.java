package unitest;

import classes.WGraph_DS;
import ex1.node_info;
import ex1.weighted_graph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test class for the WGraph_DS class representing undirectional
 * weighted graph.
 */
class WGraph_DSTest {
    Random rand = new Random();


    @Test
    @Timeout(value = 10,unit = TimeUnit.SECONDS)
    void graphBuilder(){
        int nodes = 1000000;
        int edges = 1000000;
        weighted_graph g = new WGraph_DS();
        for (int i=0;i<nodes;i++){
            g.addNode(i);
            System.out.println(i);
        }
        while (g.edgeSize()<edges){
            int a = random(0,nodes);
            int b = random(0,nodes);
            double w = rand.nextDouble()*10;
            if (a==b || g.hasEdge(a,b)) continue;
            g.connect(a,b,w);

        }
        assertEquals(true,g.nodeSize()>999999);
        assertEquals(true,g.edgeSize()>999999);


    }


    /**
     * Test function for the getNode method.
     * assumptions: factory(addNode) working.
     */
    @Test
    void getNode() {
        int n=random(100,300);
        weighted_graph g = factory(n,0);
        for (int i=0;i<n;i++){
            node_info node = g.getNode(i);
            assertEquals(i,node.getKey());
        }
    }

    /**
     * Test function for the hasEdge method.
     * assumptions: factory(addNode,connect), getV, getV(int) working.
     */
    @Test
    void hasEdge() {
        int n=random(100,300);
        int e=random(n*2,n*3);
        weighted_graph g = factory(n,e);
        Iterator<node_info> it1 = g.getV().iterator();
        while (it1.hasNext()){
            node_info node = it1.next();
            Iterator<node_info> it2 = g.getV(node.getKey()).iterator();
            while (it2.hasNext()){
                assertEquals(true, g.hasEdge(node.getKey(),it2.next().getKey()));
            }
        }
    }


    /**
     * Test function for the getEdge method.
     * assumptions: factory(addNode,connect), getV, hasEdge working.
     */
    @Test
    void getEdge() {
        int n=random(100,300);
        int e=random(n*2,n*3);
        weighted_graph g = factory(n,e);
        Iterator<node_info> it1 = g.getV().iterator();
        while (it1.hasNext()){
            node_info node = it1.next();
            Iterator<node_info> it2 = g.getV().iterator();
            while (it2.hasNext()){
                node_info node2 = it2.next();
                if (g.hasEdge(node.getKey(),node2.getKey())){
                    assertEquals(true,g.getEdge(node.getKey(),node2.getKey())>=0);
                }else{
                    assertEquals(-1,g.getEdge(node.getKey(),node2.getKey()));
                }
            }
        }
    }

    /**
     * Test function for the addNode method.
     * assumptions: nodeSize working.
     */
    @Test
    void addNode() {
        int n=random(100,300);
        weighted_graph g = new WGraph_DS();
        for (int i=0;i<n;i++){
            g.addNode(i);
            assertEquals(i+1,g.nodeSize());
        }
    }

    /**
     * Test function for the connect method.
     * assumptions: factory(addNode), edgeSize, nodeSize, hasEdge working.
     */
    @Test
    void connect() {
        int n=random(100,300);
        weighted_graph g = factory(n,0);
        while (g.edgeSize()<n*2){
            int a = random(0,g.nodeSize());
            int b = random(0,g.nodeSize());
            double w = rand.nextDouble()*10;
            if (g.hasEdge(a,b)) continue;
            g.connect(a,b,w);
            assertEquals(w,g.getEdge(a,b));
        }

    }

    /**
     * Test function for the getV method.
     * assumptions: factory(addNode), getNode working.
     */
    @Test
    void getV() {
        int n=random(100,300);
        weighted_graph g = factory(n,0);
        Iterator<node_info> it = g.getV().iterator();
        while (it.hasNext()){
            assertNotNull(g.getNode(it.next().getKey()));
        }
    }

    /**
     * Test function for the getV(int) method.
     * assumptions: factory(addNode,connect), getV, hasEdge working.
     */
    @Test
    void testGetV() {
        int n=random(100,300);
        weighted_graph g = factory(n,n*3);
        Iterator<node_info> it = g.getV().iterator();
        while (it.hasNext()){
            node_info node = it.next();
            Iterator<node_info> itr = g.getV(node.getKey()).iterator();
            while (itr.hasNext()){
                assertEquals(true,g.hasEdge(node.getKey(),itr.next().getKey()));
            }
        }
    }

    /**
     * Test function for the removeNode method.
     * assumptions: factory(addNode,connect), getV, hasEdge, getNode working.
     */
    @Test
    void removeNode() {
        int n=random(100,300);
        weighted_graph g = factory(n,n*3);
        for (int i=0;i<n;i++) {
            g.removeNode(i);
            Iterator<node_info> it = g.getV().iterator();
            while (it.hasNext()) {
                node_info node = it.next();
                assertEquals(false, g.hasEdge(node.getKey(),i));
                assertNull(g.getNode(i));
            }
        }
    }

    /**
     * Test function for the removeEdge method.
     * assumptions: factory(addNode,connect)
     */
    @Test
    void removeEdge() {
        int n=random(100,300);
        weighted_graph g = factory(n,n*3);
        while (g.edgeSize()>=n*2) {
            int a = random(0, g.nodeSize());
            int b = random(0, g.nodeSize());
            boolean before = g.hasEdge(a,b);
            if (!before) continue;
            g.removeEdge(a,b);
            if (a!=b) {
                assertEquals(false, g.hasEdge(a, b));
                assertEquals(false, g.hasEdge(b, a));
            }
        }
    }

    @Test
    void nodeSize() {
        int n=random(100,300);
        for (int i=0;i<n;i++) {
            weighted_graph g = factory(i, 0);
            assertEquals(i,g.nodeSize());
        }
    }

    @Test
    void edgeSize() {
        int n=random(100,300), counter=0;
        weighted_graph g = factory(n, 0);
        while (g.edgeSize()<n){
            int a = random(0,n);
            int b = random(0,n);
            if (g.hasEdge(a,b)) continue;
            double w = rand.nextDouble()*10;
            g.connect(a,b,w);
            assertEquals(++counter,g.edgeSize());
        }
    }

    @Test
    void getMC() {
        int n=random(100,300);
        for (int i=0;i<n;i++) {
            weighted_graph g = factory(i, 0);
            assertEquals(i,g.getMC());
        }
    }






    /**
     * Test function for the getKey method.
     * assumptions: addNode, getNode working.
     */
    @Test
    void getKey() {
        weighted_graph g = new WGraph_DS();
        for (int i=0;i<100;i++){
            g.addNode(i);
            assertEquals(i, g.getNode(i).getKey());
        }
    }

    /**
     * Test function for the getInfo method.
     * assumptions: getKey, setInfo works.
     */
    @Test
    void getInfo() {
        Collection<node_info> nodes = factory(100, 0).getV();
        for (node_info n : nodes) {
            assertEquals("", n.getInfo());
            n.setInfo(Integer.toString(n.getKey()));
            assertEquals(Integer.toString(n.getKey()),n.getInfo());
        }
    }

    /**
     * Test function for the setInfo method.
     * assumptions: getKey, getInfo works.
     */
    @Test
    void setInfo() {
        Collection<node_info> nodes = factory(100, 0).getV();
        for (node_info n : nodes) {
            String old = n.getInfo();
            n.setInfo(Integer.toString(n.getKey()));
            assertNotEquals(old, n.getInfo());
        }
    }

    /**
     * Test function for the getTag method.
     * assumptions: setTag works.
     */
    @Test
    void getTag() {
        Collection<node_info> nodes = factory(100, 0).getV();
        for (node_info n : nodes) {
            assertEquals(0.0, n.getTag());
            double nt = random(5, 10);
            n.setTag(nt);
            assertEquals(nt,n.getTag());
        }
    }

    /**
     * Test function for the setTag method.
     * assumptions: getTag works.
     */
    @Test
    void setTag() {
        Collection<node_info> nodes = factory(100,0).getV();
        for (node_info n : nodes){
            double old = n.getTag();
            double nt = random(5,10);
            n.setTag(nt);
            if (nt==0) continue;
            assertNotEquals(old, n.getTag());
            assertEquals(nt,n.getTag());
        }
    }



    private weighted_graph factory(int nodes, int edges){
        weighted_graph g = new WGraph_DS();
        for (int i=0;i<nodes;i++){
            g.addNode(i);
        }
        while (g.edgeSize()<edges){
            int a = random(0,nodes);
            int b = random(0,nodes);
            double w = rand.nextDouble()*10;
            g.connect(a,b,w);
        }
        return g;
    }

    private int random(int min, int max){
        return rand.nextInt(max-min)+min;
    }

}