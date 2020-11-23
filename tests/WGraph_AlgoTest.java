package ex1.tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.*;


class WGraph_AlgoTest {
    weighted_graph graph = new WGraph_DS();
    weighted_graph_algorithms algo_graph = new WGraph_Algo();

    @Test
    void isConnected() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        Assertions.assertTrue(ag0.isConnected());
  
        g0 = WGraph_DSTest.graph_creator(1,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        Assertions.assertTrue(ag0.isConnected());

         g0 = WGraph_DSTest.graph_creator(2,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        Assertions.assertFalse(ag0.isConnected());
        
         g0 = WGraph_DSTest.graph_creator(2,1,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        Assertions.assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        Assertions.assertTrue(b);
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        Assertions.assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0,10);
        assertEquals(d, 5.1);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,10);
        //double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
        int[] checkKey = {0, 1, 5, 7, 10};
        int i = 0;
        for(node_info n: sp) {
        	//assertEquals(n.getTag(), checkTag[i]);
        	assertEquals(n.getKey(), checkKey[i]);
        	i++;
        }
    }
    
    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.load(str);

        assertEquals(g0,g1);
        g0.removeNode(0);
        Assertions.assertNotEquals(g0,g1);
    }

    private weighted_graph small_graph() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(11,0,1);
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,3);

        g0.connect(1,4,17);
        g0.connect(1,5,1);
        g0.connect(2,4,1);
        g0.connect(3, 5,10);
        g0.connect(3,6,100);
        g0.connect(5,7,1.1);
        g0.connect(6,7,10);
        g0.connect(7,10,2);
        g0.connect(6,8,30);
        g0.connect(8,10,10);
        g0.connect(4,10,30);
        g0.connect(3,9,10);
        g0.connect(8,10,10);

        return g0;
    }
    @Test
    void copy2() {
        graph.connect(0, 1, 2);
        graph.connect(1, 2, 1);
        graph.connect(1, 4, 6);
        graph.connect(2, 3, 2);
        graph.connect(3, 4, 1);
        algo_graph.init(graph);
        weighted_graph copy = algo_graph.copy();
        Assertions.assertEquals(graph, copy);
        assertNotSame(graph, copy);
        graph.removeNode(3);
        Assertions.assertNotEquals(graph, copy);
        assertNotSame(graph, copy);
        copy.removeNode(3);
        Assertions.assertEquals(graph, copy);
    }

    @Test
    void copy() {
        weighted_graph g = WGraph_DSTest.graph_creator(20,30,6);
        weighted_graph wg1 = new WGraph_DS();
        weighted_graph_algorithms wg_algo = new WGraph_Algo();
        assertNotEquals(wg1, null);
        wg_algo.init(wg1);
        assertNotEquals(wg1, wg_algo.copy());
        wg_algo.init(g);
        assertNotEquals(g, wg_algo.copy());
    }

}