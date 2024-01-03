public class GraphNode {

    private  boolean mark;
    private  int name;
//    initialize private instance variables

    public GraphNode(int name){
        this.name = name;
        mark = false;
//        initialize graph node with mark set to false by default and int name as a number
    }
    public void mark(boolean mark){
        this.mark = mark;
    }


    public int getName() {
        return name;
    }

    public boolean isMarked() {
        return mark;

    }
    // marking mark to mark, return mark which is either true or false and return getName as name
}
