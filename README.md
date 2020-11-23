# Ex1-OOP
## the second assignment in Java OOP course.
This project represents an un-directional weighted graph and the uses of searching algorithms on it.
The src folder contains three interfaces and two classes which implements the interfaces.
The test's folder contains two junit test classes which tests the implemented classes.

## NodeInfo:
NodeInfo is the inner class that implements the node_data interface which represents a single node in the graph.<br>
with each node having a unique key, info and a tag . <br>
The tag and info contains data which is used by the algorithm class.<br>

## WGraph_DS:
WGraph_DS is the class that implements the weighted_graph interface which represents a weighted graph.<br>
every edge in the graph has a weight that represents the distance from another node therefor it can only be bigger or equal to 0.<br>

The class contains the methods:<br>
getNode(int key); return the node associated with the key, if no node return null.<br>
hasEdge(int node1, int node2); return true if there's an edge between nodes else false.<br>
getEdge(int node1, int node2); return the weight of the edge of the two nodes, if no edge return -1.<br>
addNode(int key); adds new node with the associated key to the graph. if the key is already there, do nothing.<br>
connect(int node1, int node2, double w); connect two nodes with an edge with the given weight.<br>
getV(); return a collection of all the nodes in the graph.<br>
getV(int node_id); return a collection of all the "neighbours" of the node_id node.<br>
removeNode(int key); removes the node with the associated key from the graph and deletes all the edges connected to it.<br>
removeEdge(int node1, int node2); removes the edge connected with node1 and node2.<br>
nodeSize(); return node size in the graph.<br>
edgeSize(); return edge size in the graph.<br>
getMC(); return the number of changes made in the graph.<br>
<br>
## WGraph_Algo:<br>
WGraph_Algo is a class that implements the graph_algorithms interface which contains algorithms that can be used on a weighted graph.<br>
<br>
The class consists the methods:<br>
init(weighted_graph g); Initiate the weighted graph with the WGraph_Algo object.<br>
getGraph(); Returns the graph associated with the WGraph_Algo object.<br>
copy(); Returns a deep copie of a given graph.<br>
isConnected(); Return true if all the nodes in the graph are connected, else returns false.<br>
shortestPathDist(int src, int dest); Returns the sum of the weights in the shortest path.<br>
shortestPath(int src, int dest); Returns a list of the shortest path.<br>
save(String file); Saves the graph associated with the WGraph_Algo object as a stream of byts.<br>
load(String file); Loads an object which is a stream of byts and casts it as a graph.<br>

## Data Structures implementation:
each graph has the following HashMaps:<br>
<br>
private HashMap<Integer, node_info>  nodeMap;<br>
private HashMap<Integer, HashMap<Integer, node_info>> neighbourMap;<br>
private HashMap<Integer, HashMap<Integer, Double>> edgesWeightMap;<br>
<br>    
and with more detail:   
nodeMap for <node index,node> pairing to keep track of the nodes in the graph.<br>
neighbourMap for <node index,<neighbour index,neighbour node>> in order to keep track of the neighbours.<br>
edgesWeightMap for <node index,<neighbour index,connection Weight>> in order to keep track of the edges weight.<br>

