public class GraphEdge {
    private GraphNode u;
    // first endpoint
    private GraphNode v;
    //second endpoint
    private int type;
    // door, corridor, wall price... (corridor 0, door #, wall nothing)
    private String label;
    // type of edge it is door, wall, corridor

    public GraphEdge(GraphNode u, GraphNode v, int type, String label){
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
        // initialize edge with the type and name of it

    }
    public GraphNode firstEndpoint(){
        return u;
    }
    // return first node of that edge
    public GraphNode secondEndpoint(){
        return v;
    }
    //return end node of the edge
    public int getType(){
        return type;
    }
    // return type
    public void setType(int newType){
        this.type = newType;
    }
    // set type
    public String getLabel(){
        return label;
    }
    //get label
    public void setLabel(String newLabel){
        this.label = newLabel;
    }
//    set label
}
