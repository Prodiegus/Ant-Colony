// Author: Diego Fernandez

import src.*;

public class Main {
    /**
     * The main method is the entry point of the Ant Colony Optimization program.
     * It prints a message to the console indicating that a solution for the Ant Colony Optimization problem is being developed.
     *
     * @param args an array of command-line arguments for the program
     */
    public static void main(String[] args) {
        // we take from the command line the number of ants (-ant <Integer>), the times we will run the ACO (-times <integer>) and the name of file (-file name.tsp) in the graph
        int ants = 5; // default number of ants
        int times = 5000; // default number of times we will run the ACO
        Graph graph;
        String file = "TSP/"; // file folder from src
        /**
         * We will take the arguments from the command line
         * -ant <integer> will be the number of ants
         * -times <integer> will be the number of times we will run the ACO
         * -file <name.tsp> will be the name of the file in the folder TSP
         * -help will show the help
         *  We will validate the arguments make sure that the number of ants and times are integers
         * If the arguments are not valid we will show the help and exit the program and if we dont have arguments we will show the help and exit the program
         */
        if(args.length > 0){
            for(int i = 0; i < args.length; i++){
                if(args[i].equals("-ant")){
                    try{
                        ants = Integer.parseInt(args[i+1]);
                        if(ants < 1){
                            System.out.println("The number of ants must be greater than 0");
                            System.exit(0);
                        }
                    }catch(Exception e){
                        System.out.println("The number of ants must be an integer");
                        System.exit(0);
                    }
                }
                if(args[i].equals("-times")){
                    try{
                        times = Integer.parseInt(args[i+1]);
                        if(times < 1){
                            System.out.println("The number of times must be greater than 0");
                            System.exit(0);
                        }
                    }catch(Exception e){
                        System.out.println("The number of times must be an integer");
                        System.exit(0);
                    }
                }
                if(args[i].equals("-file")){
                    file += args[i+1];
                    if(!file.contains(".tsp")){
                        System.out.println("The file must be a .tsp file");
                        System.exit(0);
                    }
                    try{
                        FileManager fileManager = new FileManager(file);
                        fileManager.readFile();
                    }catch(Exception e){
                        System.out.println("The file "+file+" does not exist");
                        System.exit(0);
                    }
                }
                if(args[i].equals("-help")){
                    System.out.println("The arguments are:");
                    System.out.println("-ant <integer> will be the number of ants");
                    System.out.println("-times <integer> will be the number of times we will run the ACO");
                    System.out.println("-file <name.tsp> will be the name of the file in the folder TSP");
                    System.out.println("-help will show the help");
                    System.exit(0);
                }
            }
        }else{
            System.out.println("The arguments are:");
            System.out.println("-ant <integer> will be the number of ants");
            System.out.println("-times <integer> will be the number of times we will run the ACO");
            System.out.println("-file <name.tsp> will be the name of the file in the folder TSP");
            System.out.println("-help will show the help");
            System.exit(0);
        }
        
        // we create the graph
        GraphMaker graphMaker = new GraphMaker();
        System.out.println("Creating graph...");
        graph = graphMaker.makeGraph(file);
        //System.out.println("Graph created!");
        // we create the ACO
        ACO aco = new ACO(graph, ants, graph.getSize());
        // we run the ACO
        System.out.println("Running ACO...");
        Ant bestAnt = aco.run(times);
        //System.out.println("ACO finished!");
        // we show the best ant
        System.out.println("The best ant is:");
        aco.showAnt(bestAnt, graph);
    }
        
}