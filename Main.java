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
        // we take from the command line the number of ants (-ant <number>) and the name of file (-file name.tsp) in the graph
        int ants = 5; // default number of ants
        Graph graph;
        String file = "TSP/"; // file folder from src
        // we read the command line arguments and validate that they are correct and valid
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-ant")) {
                ants = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-file")) {
                file += args[i + 1];
            }
        }
        // we create the graph
        GraphMaker graphMaker = new GraphMaker();
        System.out.println("Creating graph...");
        graph = graphMaker.makeGraph(file);
        System.out.println("Graph created!");

    }
        
}