package unitest;

import classes.WGraph_Algo;
import classes.WGraph_DS;
import ex1.node_info;
import ex1.weighted_graph;
import ex1.weighted_graph_algorithms;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the WGraph_Algo class representing undirectional
 * weighted graph theory algorithms.
 */
class WGraph_AlgoTest {
    Random rand = new Random();

    /**
     * test function for the init method.
     * assumptions: getGraph working.
     */
    @Test
    void init() {
        for (int i=0;i<10;i++) {
            int n = random(10, 30);
            weighted_graph g = factory(n, n);
            weighted_graph_algorithms ga = new WGraph_Algo();
            assertNull(ga.getGraph().getNode(0));
            ga.init(g);
            assertNotNull(ga.getGraph().getNode(0));
        }

    }

    /**
     * test function for the getGraph method.
     * assumptions: none.
     */
    @Test
    void getGraph() {
        for (int i=0;i<100;i++) {
            weighted_graph_algorithms ga = new WGraph_Algo(factory(random(10,30),0));
            assertNotNull(ga.getGraph());
        }
    }

    /**
     * test function for the getGraph method.
     * assumptions: getGraph working.
     */
    @Test
    void copy() {
        for (int i=0;i<10;i++) {
            int num = random(30, 75);
            weighted_graph_algorithms ga = new WGraph_Algo(factory(num, num));
            weighted_graph g = ga.copy();
            assertEquals(true, g.nodeSize()==ga.getGraph().nodeSize());
            assertEquals(true, g.edgeSize()==ga.getGraph().edgeSize());
            for (int j=0;j<num;j++){
                g.removeNode(j);
                assertNull(g.getNode(j));
                assertNotNull(ga.getGraph().getNode(j));
                assertNotEquals(true, g.nodeSize()==ga.getGraph().nodeSize());
                assertNotEquals(true, g.getMC()==ga.getGraph().getMC());
            }
        }
    }

    @Test
    void isConnected() {
        weighted_graph_algorithms ga = new WGraph_Algo(graph1());
        assertEquals(true,ga.isConnected());
        ga.getGraph().addNode(30);
        assertEquals(false,ga.isConnected());
        ga.init(graph2());
        assertEquals(true,ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph_algorithms ga = new WGraph_Algo(graph1());
        assertEquals(7.3,ga.shortestPathDist(0,5));
        assertEquals(9.6,ga.shortestPathDist(2,5));
        ga.init(graph2());
        assertEquals(7.2,ga.shortestPathDist(3,0));
        assertEquals(4.5,ga.shortestPathDist(4,2));
    }

    @Test
    void shortestPath() {
        weighted_graph_algorithms ga = new WGraph_Algo(graph1());
        List<node_info> path= ga.shortestPath(0,5);
        assertNotNull(path);
        assertEquals(4,path.size());
        assertEquals(0,path.get(0).getKey());
        assertEquals(1,path.get(1).getKey());
        assertEquals(4,path.get(2).getKey());
        assertEquals(5,path.get(3).getKey());
        path = ga.shortestPath(2,5);
        assertNotNull(path);
        assertEquals(5,path.size());
        assertEquals(2,path.get(0).getKey());
        assertEquals(0,path.get(1).getKey());
        assertEquals(1,path.get(2).getKey());
        assertEquals(4,path.get(3).getKey());
        assertEquals(5,path.get(4).getKey());
        ga.init(graph2());
        path = ga.shortestPath(3,0);
        assertNotNull(path);
        assertEquals(5,path.size());
        assertEquals(3,path.get(0).getKey());
        assertEquals(5,path.get(1).getKey());
        assertEquals(7,path.get(2).getKey());
        assertEquals(2,path.get(3).getKey());
        assertEquals(0,path.get(4).getKey());
        path = ga.shortestPath(4,2);
        assertNotNull(path);
        assertEquals(4,path.size());
        assertEquals(4,path.get(0).getKey());
        assertEquals(6,path.get(1).getKey());
        assertEquals(7,path.get(2).getKey());
        assertEquals(2,path.get(3).getKey());


    }

    @Test
    void save() {
        weighted_graph_algorithms ga = new WGraph_Algo();
        weighted_graph g = ga.getGraph();
        for (int i=0;i<30;i++){
            g.addNode(i);
        }
        g.connect(0,29,1.2);
        g.connect(3,7,4.5);
        g.connect(11,23,3.23);
        g.connect(24,18,5.7);
        ga.init(g);
        assertEquals(true, ga.save("graph.json"));
        assertEquals(true,ga.load("graph.json"));
        for (int i=0;i<30;i++){
            assertNotNull(ga.getGraph().getNode(i));
        }
        assertEquals(true, ga.getGraph().hasEdge(0,29));
        assertEquals(true, ga.getGraph().hasEdge(3,7));
        assertEquals(true, ga.getGraph().hasEdge(11,23));
        assertEquals(true, ga.getGraph().hasEdge(24,18));
    }

    @Test
    void load() {
        weighted_graph_algorithms ga = new WGraph_Algo();
        weighted_graph g = ga.getGraph();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0,2,3.1);
        g.connect(3,1,1.7);
        g.connect(2,4,5.3);
        g.connect(0,3,2.8);
        assertEquals(true,ga.save("graph.json"));
        assertEquals(true,ga.load("graph.json"));
        for (int i=0;i<5;i++){
            assertNotNull(ga.getGraph().getNode(i));
        }
        assertEquals(true, ga.getGraph().hasEdge(0,2));
        assertEquals(true, ga.getGraph().hasEdge(3,1));
        assertEquals(true, ga.getGraph().hasEdge(4,2));
        assertEquals(true, ga.getGraph().hasEdge(0,3));
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


    private weighted_graph graph1(){
        weighted_graph g = new WGraph_DS();
        for (int i=0;i<6;i++){
            g.addNode(i);
        }
        g.connect(0,1,1.1);
        g.connect(1,4,4.5);
        g.connect(0,2,2.3);
        g.connect(2,3,3.7);
        g.connect(4,5,1.7);
        g.connect(3,5,7.2);
        return g;
    }

    private weighted_graph graph2(){
        weighted_graph g = new WGraph_DS();
        for (int i=0;i<8;i++){
            g.addNode(i);
        }
        g.connect(0,1,1.2);
        g.connect(0,2,2.2);
        g.connect(0,6,3.0);
        g.connect(1,4,2.6);
        g.connect(2,7,2.1);
        g.connect(3,4,4.1);
        g.connect(3,5,2.5);
        g.connect(3,6,4.9);
        g.connect(4,6,0.7);
        g.connect(5,7,0.4);
        g.connect(6,7,1.7);
        return g;
    }
}