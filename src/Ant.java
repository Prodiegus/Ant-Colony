package src;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * This class will represent an ant that will travel through the graph
 * and will save the path that it has traveled
 * as well as the distance traveled and the pheromone that it has left
 * in each node, this class will also have the methods to move the ant
 * and to update the pheromone of the path that it has traveled
 * 
 * As we are talik about a genetic algorithm we will have to create
 * a method to create a new ant with the information of the best ant
 * and set a genoma to the new ant.
 */
public class Ant {
    private int nodesNumber; // number of nodes
    private int[] path; 
    private int distance; // distance traveled
    private double Q; // pheromone left
    private Random random; // random number generator

    public Ant(int nodesNumber, double Q) {
        this.nodesNumber = nodesNumber;
        this.Q = Q;
        this.path = new int[this.nodesNumber-1];
        // we will use the time in seconds as a seed for the random number generator
        int seed = (int)System.currentTimeMillis();
        this.random = new Random(seed);
    }

    // we will calculate the distance traveled by the ant (fitness)
    public int calculateDistance(double[][] distanceMap){
        this.distance = 0;
        for(int i = 0; i < this.path.length; i++){
            this.distance += distanceMap[this.path[i]][this.path[i+1]];
        }
        this.distance += distanceMap[this.path[this.path.length-1]][this.path[0]];
        return this.distance;
    }

    private void initPath(){
        for(int i = 0; i < this.path.length; i++){
            this.path[i] = 0;
        }
    }

    /**
     * now in the ant we are going to create a method to move the ant ant
     * with that build a path and calculate the distance traveled
     * @param pheromoneMap
     * @param distanceMap
     * @param alpha
     * @param beta
     */
    public void runAnt(double[][] pheromoneMap, double[][] distanceMap, double alpha, double beta){
        // we set the first node as a random node
        ArrayList<Double> probability;
        int startNode = random.nextInt(this.nodesNumber-1)+1;
        this.path[0] = startNode;
        System.out.println("Start node: "+startNode);
        ArrayList<Integer> posibleNext = new ArrayList<>();
        for(int i = 1; i < this.nodesNumber; i++){
                if(i != startNode){
                    posibleNext.add(i);
                }
            }
        int nextNode = 0;
        double[][] visibilityMap = new double[this.nodesNumber][this.nodesNumber];
        double sum = 0;
        double randomValue = random.nextInt(100);
        for(int node = 1; node < this.nodesNumber; node++){
            if(posibleNext.size() < 1){
                break;
            }
            // we will create a visibility map    
            for(int i = 0; i < this.nodesNumber; i++){
                for(int j = 0; j < this.nodesNumber; j++){
                    if(i != j){
                        visibilityMap[i][j] = 1/distanceMap[i][j];
                    }else{
                        visibilityMap[i][j] = 0;
                    }
                }
            }

            
            /**
             * For this part we need to use a formula to calculate the probability
             * of the ant to move to the next node
             */
            probability = new ArrayList<>();
            sum = 0;
            for(int i = 0; i < posibleNext.size(); i++){
                if(i != path[node-1]){
                    sum += Math.pow(pheromoneMap[path[node-1]][i+1], alpha)*Math.pow(visibilityMap[path[node-1]][posibleNext.get(i)], beta);
                }
            }

            // we are going to show the probabilities
            System.out.println("Probabilities: ");
            for(int i = 0; i < posibleNext.size(); i++){
                if(i != path[node-1]){
                    // we add the probability to the arraylist
                    probability.add((Math.pow(pheromoneMap[path[node-1]][i+1], alpha)*Math.pow(visibilityMap[path[node-1]][posibleNext.get(i)], beta))/sum);
                    //System.out.println("Probability from "+path[node-1]+" to "+posibleNext.get(i)+" is: "+(Math.pow(pheromoneMap[path[node-1]][i+1], alpha)*Math.pow(visibilityMap[path[node-1]][posibleNext.get(i)], beta))/sum);
                }
            }

            // now we acumulate the probabilities in the arraylist
            for(int i = 1; i < probability.size(); i++){
                probability.set(i, probability.get(i)+probability.get(i-1));
                //System.out.println("Probability acumulated: "+probability.get(i));
            }
            // we show the path
            System.out.println("Path: "+path.length);
            for(int i = 0; i < path.length-1; i++){
                System.out.print(path[i]+"->");
            }
            System.out.println(path[path.length-1]);
            // we will generate a random number between 0 and 100
            randomValue = random.nextInt(100);
            randomValue /= 100; // for transforming the random value to a value between 0 and 1
            // we are going to look on the probability array for the value of the first index greater than the random value
            System.out.println("Posible nodes in probability: "+probability.size());
            for (int i = 1; i < probability.size(); i++) {
                if(probability.get(i) > randomValue){
                    nextNode = i;
                    break;
                }
            }
            
            // we set the next node
            System.out.println("Next node: "+posibleNext.get(nextNode-1));
            this.path[node] = posibleNext.get(nextNode-1);
            posibleNext.remove(nextNode-1);
            probability.clear();
            probability = null;
        }
        // we calculate the distance traveled
        calculateDistance(distanceMap);
    }

    private void showArray(double[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                System.out.println("from "+i+" to "+j+" : "+array[i][j]);
            }
            System.out.println();
        }
    }
    // we will update the pheromone of the path traveled by the ant
    public double[][] updatePheromone(double[][] pheromoneMap){
        // we will update the pheromone map with the pheromone left by the ant in the path
        for(int i = 0; i < this.path.length-1; i++){
            pheromoneMap[this.path[i]][this.path[i+1]] += (this.Q/this.distance);
        }
        return pheromoneMap;
    }

    public int getPath(){
        String genoma = "";
        for(int i = 0; i < this.path.length; i++){
            genoma += this.path[i];
        }
        return Integer.parseInt(genoma);
    }

    public int[] getSplitedPath(){
        return this.path;
    }

    public int getDistance(double[][] distanceMap){
        calculateDistance(distanceMap); // we calculate the fitness of the ant (distance traveled
        return this.distance;
    }

    public double getQ(){
        return this.Q;
    }

    public void setPath(int[] path){
        this.path = path;
    }

    @Override
    public String toString(){
        String string = "";
        string += "Ant data:\n";
        string += "Distance traveled: "+this.distance+"\n";
        string += "Path: ";
        for(int i = 0; i < this.path.length-1; i++){
            string += this.path[i]+"->";
        }
        string += this.path[this.path.length-1]+"\n";
        return string;
    }
}
