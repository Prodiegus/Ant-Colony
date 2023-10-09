package src;
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
    private int[] path; // in this case the genoma will be the path that the ant has traveled
    private double pheromoneSense; // the power of the ant pheromone sense
    private int fitness; // the distance traveled by the ant
    private double pheromone; // the power of the ant pheromone

    public Ant(int[] path, double pheromone, double pheromoneSense){
        this.path = path;
        this.fitness = 0;
        this.pheromone = pheromone;
        this.pheromoneSense = pheromoneSense;
    }

    public Ant(double pheromone, double pheromoneSense){
        this.pheromone = pheromone;
        this.pheromoneSense = pheromoneSense;
        this.fitness = 0;
    }

    // we will calculate the distance traveled by the ant (fitness)
    public int calculateFitness(Graph graph){
        int distance = 0;
        for(int i = 1; i < this.path.length-2; i+=2){
            int node1ID = this.path[i];
            int node2ID = this.path[i+1];
            int node1x = graph.getNode(node1ID).getX();
            int node1y = graph.getNode(node1ID).getY();
            int node2x = graph.getNode(node2ID).getX();
            int node2y = graph.getNode(node2ID).getY();
            distance += Math.sqrt(Math.pow(node2x-node1x, 2)+Math.pow(node2y-node1y, 2));
        }
        this.fitness = distance;
        return distance;
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

    public int getFitness(Graph graph){
        calculateFitness(graph); // we calculate the fitness of the ant (distance traveled
        return this.fitness;
    }

    public double getPheromone(){
        return this.pheromone;
    }

    public void setPheromoneSense(double pheromoneSense) {
        this.pheromoneSense = pheromoneSense;
    }
    public double getPheromoneSense() {
        return pheromoneSense;
    }

    public void setPath(int[] path){
        this.path = path;
    }

    @Override
    public String toString(){
        String string = "";
        string += "Ant data:\n";
        string += "Distance traveled: "+this.fitness+"\n";
        string += "Pheromone: "+this.pheromone+"\n";
        string += "Pheromone sense: "+this.pheromoneSense+"\n";
        string += "Path: ";
        for(int i = 0; i < this.path.length-1; i++){
            string += this.path[i]+"->";
        }
        string += this.path[this.path.length-1]+"\n";
        return string;
    }
}
