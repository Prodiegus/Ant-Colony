package src;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class will represent the Ant Colony Optimization algorithm
 * and will have the methods to create the ants, to move them and
 * to update the pheromone of the graph that here they will be represented
 * as a matrix of integers where the "x" and "y" will represent the travel
 * between the node "x" to the node "y".
 */
public class ACO {
    Graph graph;
    int ants;
    int pheromonePower;
    double evaporatePheromone;
    double[][] pheromone;
    Ant[] antColony;
    Ant bestAnt;

    public ACO(Graph graph, int ants, int graphSize){
        this.graph = graph;
        this.ants = ants;
        this.pheromone = new double[graphSize][graphSize];
        this.antColony = new Ant[ants];
        this.evaporatePheromone = 0.4;
        this.pheromonePower = 1;
        loadPheromone();
        createAnts();
        this.bestAnt = getBestAnt();

    }

    private void loadPheromone(){
        for(int i = 0; i < this.pheromone.length; i++){
            for(int j = 0; j < this.pheromone.length; j++){
                this.pheromone[i][j] = 0;
            }
        }
        //System.out.println("Pheromone loaded");
    }

    private void createAnts(){
        //System.out.println("Creating ants");
        for(int i = 0; i < this.ants; i++){
            //System.out.println("Creating ant "+(i+1));
            int[] path = new int[pheromone.length-1];
            //System.out.println("Crafing ant genoma");
            int start = (int) (Math.random() * (pheromone.length - 1))+1;
            //System.out.println("Start: "+start);
            path[0] = start;
            //System.out.println("Setting random half genoma");
            for(int j = 1; j < path.length/50; j++){
                int next = (int) (Math.random() * (pheromone.length - 1))+1;
                if(next<=1)next = 2;
                while(!graph.getNode(next-1).isUnlocked()){
                    next = (int) (Math.random() * (pheromone.length - 1))+1;
                    if(next<=1)next = 2;
                }
                path[j] = next;
                pheromone[path[j-1]][next] = this.pheromonePower;
                graph.getNode(next).lock();
            }
            //System.out.println("Filling left half genoma");
            for(int j = 1; j < path.length; j++){
                int next = j;
                while(Arrays.asList(path).contains(next)){
                    j++;
                    next = (j);
                }
                path[j] = next;
                pheromone[path[j-1]][next] = this.pheromonePower;
                graph.getNode(next).unlock();
            }
            this.antColony[i] = new Ant(path, this.pheromonePower, 0.5);
            graph.unlockNodes();
            //System.out.println("Ant "+(i+1)+" created");
        }
        //System.out.println("Ants created");
    }
    
    private void updatePheromone(){
        for(int i = 0; i < pheromone.length; i++){
            for(int j = 0; j < pheromone.length; j++){
                // we evaporate the pheromone in a 40%
                this.pheromone[i][j] *= (1-evaporatePheromone);
            }
        }
    }

    private Ant getBestAnt(){
        Ant bestAnt = this.antColony[0];
        for(int i = 0; i < this.antColony.length; i++){
            if(this.antColony[i].getFitness(this.graph) < bestAnt.getFitness(this.graph)){
                bestAnt = this.antColony[i];
            }
        }
        return bestAnt;
    }

    private boolean checkPath(int[] path){
        // we check if the path is valid (all nodes are visited and we don't repeat any node)
        int i = 0;
        while(graph.getNode(path[i]).isUnlocked()){ 
            i++;
        }
        if(i == graph.getSize()){
            return true;
        }
        return false;
    }
    private void mutateAnts(){
        // the mutation power will be a random number between 0 and 100
        int mutationPower = (int) (Math.random() * 80)+20;
        for (int i = 0; i < antColony.length; i++) {
            int[] path = antColony[i].getSplitedPath();
            int mutationPoint = (int) (Math.random() * (path.length - 1))+1;
            // from this point we will change the path following the same rules as the createAnts method but having in mind the pheromone
            // if the mutationPower is greater than 50 we will decrease the pheromone sense
            double mutationChange = (double)(mutationPower)/(double)(100);
            if(mutationPower > 50){
                //System.out.println("mutation sense: "+(antColony[i].getPheromoneSense()*mutationChange));
                double newSense = antColony[i].getPheromoneSense()*mutationChange;
                if(newSense > 1) newSense = 1;
                antColony[i].setPheromoneSense(antColony[i].getPheromoneSense()*mutationChange);
            }else{
                // if the mutationPower is less than 50 we will increase the pheromone sense
                mutationChange = 1+mutationChange;
                //System.out.println("mutation sense: "+(antColony[i].getPheromoneSense()*mutationChange));
                double newSense = antColony[i].getPheromoneSense()*mutationChange;
                if(newSense > 1) newSense = 1;
                antColony[i].setPheromoneSense(antColony[i].getPheromoneSense()*mutationChange);
            }
            // we will change the path from the mutationPoint to the end
            for(int j = mutationPoint; j < path.length; j++){
                int next = 0;
                do{
                    ArrayList<Integer> posibleNex = new ArrayList<Integer>();
                    for (int k = 0; k < pheromone.length-1; k++) {
                        if(pheromone[mutationPoint][k] > antColony[i].getPheromoneSense()){
                            posibleNex.add(k);
                        }
                    }
                    if(posibleNex.size() == 0){
                        next = (int) (Math.random() * (path.length - 1))+1;
                    }else{
                        next = posibleNex.get((int) (Math.random() * (posibleNex.size() - 1)))+1;
                    }
                    
                }while(Arrays.asList(path).contains(next));
                path[j] = next;
                //graph.getNode(next-1).lock();
            }
            antColony[i].setPath(path);
            graph.unlockNodes();
        }
    }

    private void crossBestAnt(){
        //  we going to remplace the ants below fitness average with the best ant
        // we will calculate the average
        getBestAnt();
        int average = 0;
        for(int i = 0; i < antColony.length; i++){
            average += antColony[i].getFitness(this.graph);
        }
        average /= antColony.length;
        // we will replace the ants below average with the best ant
        for(int i = 0; i < antColony.length; i++){
            if(antColony[i].getFitness(this.graph) < average){
                antColony[i] = this.bestAnt;
            }
        }
    }

    private void rePathAnts(){
        for(int i = 0; i < antColony.length; i++){
            int[] path = getAntPath(antColony[i]);
            antColony[i].setPath(path);
        }
        // we lock for the worst ant
        int worstAnt = 0;
        for(int i = 0; i < antColony.length; i++){
            if(antColony[i].getFitness(this.graph) > antColony[worstAnt].getFitness(this.graph)){
                worstAnt = i;
            }
        }
        // we will replace the worst ant with the best ant
        antColony[worstAnt] = this.bestAnt;
    }

    private int[] getAntPath(Ant ant){
        int[] path = ant.getSplitedPath();
        for(int i = 0; i < path.length; i++){
            int next = 1;
            do{
                ArrayList<Integer> posibleNex = new ArrayList<Integer>();
                for (int j = path.length/2; j < pheromone.length-2; j++) {
                    if(pheromone[i][j] > ant.getPheromoneSense()){
                        posibleNex.add(j+1);
                    }
                }
                if(posibleNex.size() == 0){
                    do{
                        next = (int) (Math.random() * (path.length - 1))+1;
                    }while(next == 0 );
                }else{
                    do{
                        next = posibleNex.get((int) (Math.random() * (posibleNex.size() - 1)))+1;
                    }while(next == 0 );
                }
                
            }while(Arrays.asList(path).contains(next));
            path[i] = next;
            if(i > 0) pheromone[path[i-1]][next] = this.pheromonePower;
            graph.getNode(next).lock();
        }
        graph.unlockNodes();
        return path;
    }

    public Ant run(int times){
        // we will run the algorithm n times
        for(int i = 0; i < times; i++){
            // if i is a multiple of 10000 we will show the time i
            if((i+1)%800 == 0 && i != 0){
                System.out.println("Running ACO "+(i+1)+" of "+times+" Percentage: "+((i+1)*100/times)+"%");
            }
            //System.out.println("Running ACO "+(i+1)+" of "+times);
            // we will update the best ant
            //System.out.println("Updating best ant");
            this.bestAnt = getBestAnt();
            // we will mutate the ants
            //System.out.println("Mutating ants");
            mutateAnts();
            // we will cross the best ant
            //System.out.println("Crossing best ant");
            crossBestAnt();
            // we will re-path the ants
            //System.out.println("Re-pathing ants");
            rePathAnts();
            // we will update the pheromone
            //System.out.println("Updating pheromone");
            updatePheromone();
        }
        return this.bestAnt;
    }
    public void showAnt(Ant bestAnt, Graph graph){
        bestAnt.calculateFitness(graph);
        System.out.println(bestAnt.toString());
    }
}
