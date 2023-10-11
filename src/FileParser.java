package src;

import java.util.ArrayList;


public class FileParser {
    private FileManager fileManager;
    private int size;
    
    public FileParser(){
        this.size = 0;
    }
    /**
     * This method will read the file and save the data in a list
     * @return a list with the data of the file
     */
    public double[][] parse(String address){
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
                    //we are going to check that the node is not repeated
                    for(int j = 0; j < data.size(); j++){
                        String[] line2 = data.get(j).trim().split("\s+");
                        if(line[0].equals(line2[0]) && line[1].equals(line2[1]) && line[2].equals(line2[2]) && i != j){
                            System.out.println("data: "+data.get(i)+" is repeated");
                            data.remove(i);
                            i--;
                            break;
                        }
                    }
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
        this.size = data.size()+1;
        //printGraph();
        // we create the distance map
        double[][] distanceMap = new double[this.size][this.size];
        // we will set all the nodes in 0
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                distanceMap[i][j] = 0;
            }
        }
        // we fill the distance map
        //System.out.println("size: "+distanceMap.length);
        for(int i = 1; i < distanceMap.length; i++){
            // this is the start node
            String[] line = data.get(i-1).trim().split("\s+");
            int node1x = Integer.parseInt(line[1]);
            int node1y = Integer.parseInt(line[2]);
            for(int j = 1; j < distanceMap[i].length; j++){
                // this is the next node
                String[] line2 = data.get(j-1).trim().split("\s+");
                int node2x = Integer.parseInt(line2[1]);
                int node2y = Integer.parseInt(line2[2]);
                distanceMap[i][j] = Math.sqrt(Math.pow(node2x-node1x, 2)+Math.pow(node2y-node1y, 2));
            }


        }
        //printGraph(distanceMap);
        return distanceMap;
    }

    // function for printing the file information
    public void printFile(ArrayList<String> data){
        for (String string : data) {
            System.out.println(string);
        }
    }

    // function for printing the map
    public void printGraph(double[][] distanceMap){
        double distance = 0;
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size-1; j++){
                // we are going to round the number to 2 decimals
                distance = Math.round(distanceMap[i][j]*100.0)/100.0;
                System.out.println("Distance from node "+i+" to node "+j+" is: "+distance);
            }
            System.out.println();
        }
    }

    public int getSize(){
        return this.size;
    }
}
