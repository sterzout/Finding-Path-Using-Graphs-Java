import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public class Maze {
    private int widthOfMaze;
    private int lengthOfMaze;
    private int numberOfCoins;
    private GraphNode entrance;
    private GraphNode exit;
    private Graph mazeGraph;
    private Stack<GraphNode> stack;


    // initialize private instance variables needed to be called in helper methods such as coins, stack, exit, entrance
    // and mazeGraph. width and length used to set equal to file lines 2 and 3
    public Maze(String inputFile) throws MazeException {
        try (BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
            String mazeFirstLine = in.readLine();
            String mazeSecondLine = in.readLine();
            widthOfMaze = Integer.parseInt(mazeSecondLine);
            String mazeThirdLine = in.readLine();
            lengthOfMaze = Integer.parseInt(mazeThirdLine);
            String mazeFourthLine = in.readLine();
            //read first four lines to get rid of them before we start reading the actual maze at the fifth line
            numberOfCoins = Integer.parseInt(mazeFourthLine);
            int totalWidth = (2 * widthOfMaze) - 1;
            int totalLength = (2 * lengthOfMaze) - 1;
            // set total length to width given times 2 minus 1 to get length of file
            // same for width to get length of a line

            char[][] array = new char[totalLength][totalWidth];
            for (int i = 0; i < totalLength; i++) {
                String lineToRead = in.readLine();
                if (lineToRead == null) {
                    throw new MazeException("Line is null");
                    // if the fifth line is null then we retunr null
                    // if not null then we can start filling our 2d Array
                }
                for (int j = 0; j < totalWidth; j++) {
                    array[i][j] = lineToRead.charAt(j);
                    // throw all characters into an array

                }
            }
            int numberOfNodes = widthOfMaze * lengthOfMaze;
            this.mazeGraph = new Graph(numberOfNodes);
            // initialize maze graph to length times width from line 2 and line 3 of file

            for (int i = 0; i < totalLength; i++) {
                for (int j = 0; j < totalWidth; j++) {
                    if (i % 2 == 0 && j % 2 == 0) {
                        // only check every other row since these are where the rooms, entrance and exit are
                        if (array[i][j] == 's' || array[i][j] == 'x' || array[i][j] == 'o') {
                            if (array[i][j] == 's') {
                                entrance = mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2));
                                //setting entrance if s with formula for current node you are at
                            }
                            if (array[i][j] == 'x') {
                                exit = mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2));
                                // setting exit if x
                            }
                            if (j + 2 < totalWidth) {
                                // check if horizontal direction goes out of bounds
                                if ((array[i][j + 2] == 's' || array[i][j + 2] == 'x' || array[i][j + 2] == 'o')) {
                                    if (array[i][j + 1] == 'c') {
                                        // if the char in between is c we add edge with 0 coins. horizontally checker
                                        mazeGraph.insertEdge(mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2)), mazeGraph.getNode((j / 2 + widthOfMaze * (i / 2)) + 1), 0, "corridor");
                                        // get current node and current node + 1 in the nodes order
                                    } else if (array[i][j + 1] >= '0' && array[i][j + 1] <= '9') {
                                        // if the char in between is a number we add edge with that char value coins
                                        // no check for wall since we do nothing for wall we only stop for c or a number else it keeps going to vertical check
                                        int doorPrice = Character.getNumericValue(array[i][j + 1]);
                                        mazeGraph.insertEdge(mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2)), mazeGraph.getNode((j / 2 + widthOfMaze * (i / 2)) + 1), doorPrice, "door");
                                        // get current node and current node + 1 in the nodes order
                                    }
                                }
                            }
                            if (i + 2 < totalLength) {
                                // check if vertical direction goes out of bounds
                                if (array[i + 2][j] == 's' || array[i + 2][j] == 'x' || array[i + 2][j] == 'o') {
                                    if (array[i + 1][j] == 'c') {
                                        // if the char in between is c we add edge with 0 coins. vertical checker
                                        mazeGraph.insertEdge(mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2)), mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2) + widthOfMaze), 0, "corridor");
                                        // get current node and current node + width in the nodes order (node directly below it)
                                    } else if (array[i + 1][j] >= '0' && array[i + 1][j] <= '9') {
                                        // if the char in between is a number we add edge with that char value coins
                                        // no check for wall since we do nothing for wall we only stop for c or a number else it keeps going to next line with i%2=0 and j%2=0
                                        int doorPrice = Character.getNumericValue(array[i + 1][j]);
                                        mazeGraph.insertEdge(mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2)), mazeGraph.getNode(j / 2 + widthOfMaze * (i / 2) + widthOfMaze), doorPrice, "door");
                                        // get current node and current node + width in the nodes order (node directly below it)
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (GraphException | IOException e) {
            throw new RuntimeException(e);
            // else if file null we catch graph and IO exception
        }

    }

    public Graph getGraph() throws MazeException {
        if (mazeGraph == null) {
            throw new MazeException("Graph null");
            // get graph as long as not null
        }
        return mazeGraph;
    }

    public Iterator solve() throws GraphException {
        stack = new Stack<>();
        if (path(entrance, exit, numberOfCoins)){
            return stack.iterator();
        }
        // return our stack iterator which has the order of the path taken with our path from the helper method if the
        // path returns true if the condition is not met then it means helper method returned false and we return
        // null as no path found
        return null;
    }

    private boolean path(GraphNode current, GraphNode exit, int numberOfCoins) throws GraphException {
        //create stack to return nodes
        stack.push(current);
        current.mark(true);
        // mark and push the first node
        if (current.getName() == exit.getName()) {
            return true;
            //we return true if the name becomes equal to the exit name which means we are at the end of our path
        } else {
            // else we keep getting the incident edges of that node
            Iterator<GraphEdge> it = mazeGraph.incidentEdges(current);
            while (it.hasNext()) {
                GraphEdge currentEdge = it.next();
                GraphNode u = currentEdge.secondEndpoint();
                if (u.getName() == current.getName()){
                    u = currentEdge.firstEndpoint();
                }
                // there was a bug here where the current node would end up being the second end point which would then
                //be popped since it is already true in the stack. So to fix this, we made sure that if a case ever came
                // up like this we would swap the end points and put our current node back to first endpoint and put the
                // node from the new edge we are iterating through as the second end point.
                if (u.isMarked() == false && numberOfCoins- currentEdge.getType() >= 0) {
                    if (path(u, exit, numberOfCoins - currentEdge.getType()) == true){
                        return true;
                        // if false and numCoins - door/corridor cost is > 0 then we proceed and we check each recursive
                        //call to see if it returns true then we return true for the helper method
                    }
                }
            }
            current.mark(false);
            stack.pop();
            // if the condition above is not met, we change the mark back to false and remove it from our stack
            // as this will not be part of the path we take
        }
        return false;
        // return false if true is not met in the above conditions
    }
}
