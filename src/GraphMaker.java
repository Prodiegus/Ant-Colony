package src;

import java.util.ArrayList;


public class GraphMaker {
    private Graph graph;
    private FileManager fileManager;
    private int size;
    
    public GraphMaker(){
        this.size = 0;
    }
    /**
     * This method will read the file and save the data in a list
     * @return a list with the data of the file
     */
    public Graph makeGraph(String address){
        // we create the file manager
        this.fileManager = new FileManager(address);
        // we read the file
        ArrayList<String> data = this.fileManager.readFile();
        /**
         * As the documentation of the TSP file says information about the graph we will read the file
         * and then cut the lines without the format "number number number" and then we will take the size
         */
        for(int i = 0; i < data.size(); i++){
            String[] line = data.get(i).trim().split("\s+");
            // we check that the information splited is int int int
            if(line.length == 3){
                try {
                    Integer.parseInt(line[0]);
                    Integer.parseInt(line[1]);
                    Integer.parseInt(line[2]);
                } catch (Exception e) {
                    //System.out.println("data: "+data.get(i)+" is not int int int");
                    data.remove(i);
                    i--;
                }
            }else{
                //System.out.println("data: "+data.get(i)+" is not 3 arguments");
                data.remove(i);
                i--;
            }
        }
        //printFile(data);
        // we get the size of the graph
        this.size = data.size();
        // we create the graph
        this.graph = new Graph(this.size);
        // we add the nodes to the graph
        for(int i = 0; i < data.size()-1; i++){
            String[] node = data.get(i).trim().split("\s+");
            this.graph.addNode(Integer.parseInt(node[0]), Integer.parseInt(node[1]), Integer.parseInt(node[2]));
        }
        //printGraph();
        return this.graph;
    }

    // function for printing the file information
    public void printFile(ArrayList<String> data){
        for (String string : data) {
            System.out.println(string);
        }
    }

    public void printGraph(){
        System.out.println(this.graph.toString());
    }
    // getters
    public Graph getGraph(){
        return this.graph;
    }
    public int getSize(){
        return this.size;
    }
}
