package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph, Serializable {
    private int mc_count;
    private int edge_count;
    private HashMap<Integer, node_info>  nodeMap;

    private HashMap<Integer, HashMap<Integer, node_info>> neighbourMap;
    private HashMap<Integer, HashMap<Integer, Double>> edgesWeightMap;


    /**
     * Constructor for WGraph_DS class initializing it with
     * nodeMap for <node index,node> pairing
     * neighbourMap for <node index,<neighbour index,neighbour node>>
     * edgesWeightMap for <node index,<neighbour index,connection Weight>>
     */
public WGraph_DS () {
    nodeMap = new HashMap<Integer, node_info>() ;
    neighbourMap = new HashMap<Integer, HashMap<Integer, node_info>> ();
    edgesWeightMap = new  HashMap<Integer, HashMap<Integer, Double>> ();
    this.mc_count = 0;
    this.edge_count = 0;
}

    // Graph implementation
    /**
     * return the node_data by the node_id,
     * checks to see if it is in the nodeMap
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
    if (nodeMap.containsKey(key)) {
        return nodeMap.get(key);
    }
        return null;
    }


    /**
     * returns true iff (if and only if) there is an edge between node1 and node2.
     * Note: this method runs in O(1) time.
     * checks to see if neighbourMap contains both nodes and if they are in
     * each others neighbourMap.
     * @param node1
     * @param node2
     * @return true if there is a connection between the two nodes, false if not.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (node1 != node2 &&
                neighbourMap.containsKey(node1) &&
                neighbourMap.containsKey(node2) &&
                neighbourMap.get(node1).containsKey(node2) &&
                neighbourMap.get(node2).containsKey(node1))
                return true;

        return false;
    }


    /**
     * returns the weight of the edge (node1, node1) if it exists.
     * In the case that there is no such edge - returns -1
     * Note: this method runs in O(1) time.
     * @param node1
     * @param node2
     * @return the weight of the edge between the nodes (double).
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            return edgesWeightMap.get(node1).get(node2);
        }
        return -1;

    }


    /**
     * checks to see it the graph already contains the given key node(key>=0).
     * if not it adds a new node to nodeMap, the graph HashMap, with the given key.
     * and also adds the node to the NeighbourMap and EdgesWeightMap.
     * after that it increases the mc_count
     * Note: if there is already a node with such a key -> no action is performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
    if (!nodeMap.containsKey(key) && key >= 0){
        node_info newNode = new NodeInfo(key);
        nodeMap.put(key,newNode);
        neighbourMap.put(key,new HashMap<Integer, node_info>());
        edgesWeightMap.put(key, new HashMap<Integer, Double>());
        mc_count++;

    }}


    /**
     * Connects an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method runs in O(1) time.
     * Note2: if the edge node1-node2 already exists in edgesWeightMap -
     *  the method simply updates the weight of the edge and increases the mc_count.
     * Note3: if the edge doesn't exist we add the given nodes to each other in
     *  neighbourMap and edgeWeightMap with the given weight
     *  and increase the graph mc_count and edge_count.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(node1 == node2)  return;
        if (w<0) return;
        if (!nodeMap.containsKey(node1) ||!nodeMap.containsKey(node2) ) return;

        if(hasEdge(node1, node2)){
            if(getEdge(node1, node2) != w){
                edgesWeightMap.get(node1).put(node2,w);
                edgesWeightMap.get(node2).put(node1,w);
                mc_count++;
            }}

        else{
            edgesWeightMap.get(node1).put(node2,w);
            edgesWeightMap.get(node2).put(node1,w);
            neighbourMap.get(node1).put(node2,nodeMap.get(node2));
            neighbourMap.get(node2).put(node1,nodeMap.get(node1));
            mc_count++;
            edge_count++;
        }
    }


    /**
     * This method returns a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method runs in O(1) time
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return nodeMap.values();
    }


    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * checks to see if the graph neighbourMap contains this specific node_id and returns it.
     * else return null.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(neighbourMap.containsKey(node_id) && node_id >= 0){
            return neighbourMap.get(node_id).values();
        }
        return null;
    }


    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method runs in O(n), |V|=n, as all the edges should be removed.
     * checks to see if null if not it iterates over the nodes neighbours
     * and delete our node from their neighbourMap and edgesWeightMap and decreses the edge_count.
     * at the end removes the node itself from edgesWeightMap,neighbourMap,nodeMap
     * and increases the mc_count;
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        if (nodeMap.containsKey(key)){
//checks to see null if not it iterates over the nodes neighbours and delete our node from their neighbourMap and edgesWeightMap
            node_info del_null_check;
            if( (del_null_check = nodeMap.get(key)) != null){
                for(node_info node : neighbourMap.get(key).values()){
                    if(hasEdge(node.getKey(), key)){
                        neighbourMap.get(node.getKey()).remove(key);
                        edgesWeightMap.get(node.getKey()).remove(key);
                        edge_count--;
            }}}
            edgesWeightMap.remove(key);
            neighbourMap.remove(key);
            mc_count++;
            return nodeMap.remove(key);}
        return null;
    }


    /**
     * Deletes the edge from the graph,
     * removes the nodes from each other neighbourMap and edgesWeightMap.
     * increase the mc_count and decrease the edge_count
     * Note: this method runs in O(1) time.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1, node2)) {
            neighbourMap.get(node1).remove(node2,getNode(node2));
            neighbourMap.get(node2).remove(node1,getNode(node1));
            edgesWeightMap.get(node1).remove(node2);
            edgesWeightMap.get(node2).remove(node1);
            mc_count++;
            edge_count--;
        }
    }

    @Override
    public int nodeSize() {
        if (nodeMap != null){
            return this.nodeMap.size();
        }
        return 0;
    }


    @Override
    public int edgeSize() {
        return edge_count;
    }

    @Override
    public int getMC() {
        return mc_count;
    }


// implementing the node class for the graph
private class NodeInfo implements node_info, Serializable{

    private int key;
    private double tag;
    private String info;
    private int nextID = 0;

    // Default Constructor for NodeInfo
    public NodeInfo(){
       this.tag = 0 ;
        this.info = "";
        this.key = nextID++;
    }

    // Constructor for NodeInfo with specific key
    public NodeInfo(int key){
        this.tag = 0;
        this.info = "";
        this.nextID = key;
        this.key = nextID++;
    }


    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public double getTag() {
        return this.tag;
    }

    @Override
    public void setTag(double t) {
        this.tag = t;
    }

// returns a String of the nodes inner data.
    @Override
    public String toString() {
        return "NodeInfo{" +
                "key = " + key +
                ", tag = " + tag +
                ", info = '" + info + '\'' + '}';
    }
}

}