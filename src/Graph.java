import java.util.*;

public class Graph implements GraphADT {
    private int n;
    private GraphNode[] nodes;
    private GraphEdge[][] edges;
    //initialize nodes and edges arrays


    public Graph(int n) {
        this.n = n;
        this.nodes = new GraphNode[n];
        this.edges = new GraphEdge[n][n];

        for (int i = 0; i < n; i++) {
            nodes[i] = new GraphNode(i);
            // initialize the graph with new nodes from our node array
        }
    }

    @Override
    public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
        if (nodeu.getName() > n-1 || nodeu.getName() < 0 || nodev.getName() > n-1 || nodev.getName() < 0) {
            throw new GraphException("One of the nodes is not in the list of nodes");
            // check if the node name is valid
        } else {
            for (int i = 0; i < n; i++) {
                if ((edges[nodeu.getName()][nodev.getName()] != null && edges[nodeu.getName()][nodev.getName()].equals(edges[nodeu.getName()][i])) || (edges[nodev.getName()][nodeu.getName()] != null && edges[nodev.getName()][nodev.getName()].equals(edges[nodev.getName()][i]))) {
                    throw new GraphException("Edge already exists");
                }
                //check to see if the node exists
            }
            GraphEdge newNodeEdge = new GraphEdge(nodeu, nodev, type, label);
            int indexNodeU = nodeu.getName();
            int indexNodeV = nodev.getName();
            if (indexNodeU >= 0 && indexNodeU <= n - 1 && indexNodeV >= 0 && indexNodeV <= n - 1) {
                edges[indexNodeU][indexNodeV] = newNodeEdge;
                edges[indexNodeV][indexNodeU] = newNodeEdge;
                //if not then insert in the edges array in both positions
            } else {
                throw new GraphException("Out of bounds error in edges array");
                // else throw exception
            }

        }
    }

    @Override
    public GraphNode getNode(int u) throws GraphException {
        if (u > n-1 || u < 0) {
            throw new GraphException("Node not in list of nodes");
        } else {
            return nodes[u];
            // get node if valid index
        }
    }

    @Override
    public Iterator incidentEdges(GraphNode u) throws GraphException {
        if (u.getName() > n-1 || u.getName() < 0) {
            throw new GraphException("The node is not in matrix");
            // if index is invalid then not in matrix and has no edges
        } else {
            Stack<GraphEdge> stack = new Stack<>();
            for (int i = 0; i < n; i++) {
                if (edges[u.getName()][i] != null) {
                    stack.push(edges[u.getName()][i]);
                    // else push all the edges in that position to a stack
                }
            }
            if (!stack.iterator().hasNext()) {
                return null;
                // if iterator is null return null
            }else{
                return stack.iterator();
                //else return that stack iterator which will return sorted stack
            }
        }
    }

    @Override
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        if (u.getName() > n - 1 || u.getName() < 0 || v.getName() > n - 1 || v.getName() < 0) {
            throw new GraphException("At least one of the nodes is not in the node array");
            // same check as before
        } else {
            for (int i = 0; i < n; i++) {
                if ((edges[u.getName()][v.getName()] != null && edges[u.getName()][i] != null && edges[u.getName()][i].equals(edges[u.getName()][v.getName()]))) {
                    return edges[u.getName()][(i)];
                    // return the exact edge
                }
            }
            throw new GraphException("The nodes do not have an edge");
            // throw exception if the node has none
        }
    }

    @Override
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        boolean valid = false;
        if (u.getName() > n - 1 || u.getName() < 0 || v.getName() > n - 1 || v.getName() < 0) {
            throw new GraphException("At least one of the nodes is not in the node array");
            // same validity check
        }else {
            if (edges[u.getName()][v.getName()] != null) {
                for (int i = 0; i < n; i++) {
                    if (edges[u.getName()][i] != null) {
                        if (edges[u.getName()][i].equals(edges[u.getName()][v.getName()])) {
                            valid = true;
                            // if they are adjacent set valid to true since the two nodes contain an edge
                        }
                    }
                }
            }
        }
        return valid;
        // return valid which will either stay false or change to true
    }
}
