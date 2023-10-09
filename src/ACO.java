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
    }

    private void createAnts(){
        for(int i = 0; i < this.ants; i++){
            int[] path = new int[pheromone.length];
            int start = (int) (Math.random() * (pheromone.length - 1))+1;
            path[0] = start;
            for(int j = 1; j < path.length/50; j++){
                int next = (int) (Math.random() * (pheromone.length - 1))+1;
                while(!graph.getNode(next).isUnlocked()){
                    next = (int) (Math.random() * (pheromone.length - 1))+1;
                }
                path[j] = next;
                graph.getNode(next).lock();
            }
            for(int j = 1; j < path.length; j++){
                int next = j;
                while(!graph.getNode(next).isUnlocked()){
                    j++;
                    next = (j);
                }
                path[j] = next;
                graph.getNode(next).unlock();
            }
            this.antColony[i] = new Ant(path, this.pheromonePower, 0.5);
            graph.unlockNodes();
        }
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
        for(int i = 1; i < this.antColony.length; i++){
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
        int mutationPower = (int) (Math.random() * 100);
        for (int i = 0; i < antColony.length; i++) {
            int[] path = antColony[i].getSplitedPath();
            int mutationPoint = (int) (Math.random() * (path.length - 1));
            // from this point we will change the path following the same rules as the createAnts method but having in mind the pheromone
            // if the mutationPower is greater than 50 we will decrease the pheromone sense
            if(mutationPower > 50){
                antColony[i].setPheromoneSense(antColony[i].getPheromoneSense()*(mutationPower/100));
            }else{
                // if the mutationPower is less than 50 we will increase the pheromone sense
                antColony[i].setPheromoneSense(antColony[i].getPheromoneSense()*(1+(mutationPower/100)));
            }
            // we will change the path from the mutationPoint to the end
            for(int j = mutationPoint; j < path.length; j++){
                int next = 0;
                do{
                    ArrayList<Integer> posibleNex = new ArrayList<Integer>();
                    for (int k = 0; k < pheromone.length; k++) {
                        if(pheromone[mutationPoint][k] > antColony[i].getPheromoneSense()){
                            posibleNex.add(k);
                        }
                    }
                    if(posibleNex.size() == 0){
                        next = (int) (Math.random() * (path.length - 1))+1;
                    }else{
                        next = posibleNex.get((int) (Math.random() * (posibleNex.size() - 1)));
                    }
                    
                }while(Arrays.asList(path).contains(next));
                path[j] = next;
                graph.getNode(next).lock();
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
    }

    private int[] getAntPath(Ant ant){
        int[] path = ant.getSplitedPath();
        for(int i = 0; i < path.length; i++){
            int next = 0;
            do{
                ArrayList<Integer> posibleNex = new ArrayList<Integer>();
                for (int j = 0; j < pheromone.length; j++) {
                    if(pheromone[i][j] > ant.getPheromoneSense()){
                        posibleNex.add(j);
                    }
                }
                if(posibleNex.size() == 0){
                    next = (int) (Math.random() * (path.length - 1))+1;
                }else{
                    next = posibleNex.get((int) (Math.random() * (posibleNex.size() - 1)));
                }
                
            }while(Arrays.asList(path).contains(next));
            path[i] = next;
            graph.getNode(next).lock();
        }
        graph.unlockNodes();
        return path;
    }

    public Ant run(int times){
        // we will run the algorithm n times
        for(int i = 0; i < times; i++){
            // we will update the best ant
            this.bestAnt = getBestAnt();
            // we will mutate the ants
            mutateAnts();
            // we will cross the best ant
            crossBestAnt();
            // we will re-path the ants
            rePathAnts();
            // we will update the pheromone
            updatePheromone();
        }
        return this.bestAnt;
    }
    public void showAnt(Ant bestAnt, Graph graph){
        bestAnt.calculateFitness(graph);
        System.out.println(bestAnt.toString());
    }
}
