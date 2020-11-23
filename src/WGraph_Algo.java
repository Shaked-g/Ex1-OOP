package ex1.src;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WGraph_Algo implements  weighted_graph_algorithms {

    private weighted_graph graph;
    private LinkedList<node_info> queue;


    /**
     * default constructor for Graph_Algo that creates a new empty graph.
     */
    public WGraph_Algo() {
        this.graph = new WGraph_DS();
    }


    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }


    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }


    /**
     * Compute a deep copy of this weighted graph.
     * copy's all the nodes in the graph and their neighbours
     * including the
     * @return
     */
    @Override
    public weighted_graph copy() {

        weighted_graph copyGraph = new WGraph_DS();

        for (node_info node : graph.getV()) {
            node_info newNode = graph.getNode(node.getKey());
            copyGraph.addNode(newNode.getKey());
            copyGraph.getNode(newNode.getKey()).setTag(node.getTag());
            copyGraph.getNode(newNode.getKey()).setInfo(node.getInfo());
        }

        for (node_info node : graph.getV()) {
            for (node_info oldNi : graph.getV(node.getKey())) {
                copyGraph.connect(node.getKey(), oldNi.getKey(), graph.getEdge(node.getKey(), oldNi.getKey()));
            }
        }
        return copyGraph;
    }


    /**
     * Returns true if and only if (iff) there is a valid path from EVERY node to each
     * other node. NOTE: assume unidirectional graph.
     * if there is 0 or 1 node in the graph returns connected(true)
     * if not than the Dijkstra's algorithm starts with the first vertex in the graph.
     * the node tag is updated during the algorithm and after that we go over all the nodes to check if there is an unvisited node(tag=0)
     * which will mean that the graph is not connected(returns false).
     * @return true iff all the nodes in the graph are connected, false if not.
     */
    @Override
    public boolean isConnected() {
        if(graph == null)return true;
        if (graph.nodeSize() <= 1) return true;
        tagReset(graph); // tagResets
        boolean bool_result = true;
        node_info src = null;

        for (node_info node : graph.getV()){
            src = node;
            break;
        }
        queue = new LinkedList<node_info>();
        queue.add(src);
        src.setTag(-1);
        while (queue.size() != 0) {
            node_info polled = queue.poll();
            for (node_info node : graph.getV(polled.getKey())) {
                if (node.getTag() >= 0) {
                    queue.add(node);
                    node.setTag(-1);
                } } }

        for (node_info node : graph.getV()) {
            if(node.getTag() == 0 ){
                bool_result = false;
                break;
            }
        }
        tagReset(graph);
        return bool_result;



    }


    // resets the tag to 0 and the info to ""(empty).
    private void tagReset(weighted_graph graph){
        for (node_info node : graph.getV()) {
            node.setTag(0);
            node.setInfo("");
        }
    }


    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * if the nodes are the same we return 0.
     * we check to see if the nodes exists in the graph.
     * if the do we run Dijkstra's Algorithm while while updating the tags with the dist.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if((graph.getNode(src) == null || graph.getNode(dest) == null)){
            return -1;
        }
        if (src == dest && graph.getNode(src) != null) return 0;
        // sets all the Tags with "infinity" value.
        for (node_info n : graph.getV()){
            n.setTag(Integer.MAX_VALUE);
        }
        node_info src2, dest2;
        if ((src2 = graph.getNode(src)) != null && (dest2 = graph.getNode(dest)) != null) {
            for (node_info n : graph.getV()){
                n.setTag(Integer.MAX_VALUE);
            }

            queue = new LinkedList<node_info>();
            queue.add(src2);
            src2.setTag(0);
            while (queue.size() != 0) {
                node_info polled = queue.poll();
                for (node_info node : graph.getV(polled.getKey())) {
                    if (node.getTag() == Integer.MAX_VALUE ){
                        queue.add(node);
                    }
                    if (node.getTag() >= (polled.getTag() + graph.getEdge(polled.getKey(),node.getKey()))) {
                        node.setTag(polled.getTag() + graph.getEdge(polled.getKey(),node.getKey()));
                    } } } }
        else{
            return -1;
        }
        if(dest2.getTag() == Integer.MAX_VALUE){
            return -1;
        }
        return graph.getNode(dest).getTag();
    }


    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * first we check if the shortestPathDist is 0 (one node) or -1 (not connected).
     * we start Dijkstra's Algorithm on the graph and update the tag with the length
     * of the path from the source node to the destination node.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        double dist = shortestPathDist(src,dest);
        queue = new LinkedList<node_info>();

        if(dist == 0){
            queue.addFirst(graph.getNode(src));
            return queue;}
        else if(dist == -1){
            return queue;}

        else{
            node_info temp_node = graph.getNode(dest);
            do {
                for (node_info temp_neighbour : graph.getV(temp_node.getKey())){
                    if(temp_neighbour.getTag() + graph.getEdge(temp_neighbour.getKey(), temp_node.getKey()) == temp_node.getTag() ){
                        queue.addFirst(temp_node);
                        temp_node = temp_neighbour;
                    } } }
            while (temp_node.getTag() != 0);
            queue.addFirst(temp_node);

        }
        return queue;
    }


    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream file1 = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(file1);
            out.writeObject(graph);

            out.close();
            file1.close();
            return true;

        }
        catch ( IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try{
            FileInputStream file1 = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(file1);
            graph = (weighted_graph) in.readObject();
            in.close();
            file1.close();
            return true;
        }
        catch(IOException | ClassNotFoundException e ){
            return false;
        }

}

}
