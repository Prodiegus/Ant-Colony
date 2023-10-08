package src;
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
    int[][] pheromone;
    Ant[] antColony;
    Ant bestAnt;

    public ACO(Graph graph, int ants, int graphSize){
        this.graph = graph;
        this.ants = ants;
        this.pheromone = new int[graphSize][graphSize];
        this.antColony = new Ant[ants];
        this.pheromonePower = 10;
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
            int start = (int) (Math.random() * (pheromone.length - 1));
            path[0] = start;
            for(int j = 1; j < path.length/50; j++){
                int next = (int) (Math.random() * (pheromone.length - 1));
                while(!graph.getNode(next).isUnlocked()){
                    next = (int) (Math.random() * (pheromone.length - 1));
                }
                path[j] = next;
                graph.getNode(next).lock();
            }
            for(int j = 0; j < path.length; j++){
                int next = j;
                while(!graph.getNode(next).isUnlocked()){
                    next = (j+=1);
                }
                path[j] = next;
                graph.getNode(next).unlock();
            }
            this.antColony[i] = new Ant(path, this.pheromonePower, 0.5);
        }
    }
    
    private void updatePheromone(){
        for(int i = 0; i < pheromone.length; i++){
            for(int j = 0; j < pheromone.length; j++){
                // we evaporate the pheromone in a 40%
                this.pheromone[i][j] *= 0.60;
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
        for (int i = 0; i < antColony.length; i++) {
            int[] path = antColony[i].getSplitedPath();
            int mutationPoint = (int) (Math.random() * (path.length - 1));
            do{
                int mutationValue = (int) (Math.random() * (path.length - 1));
                path[mutationPoint] = mutationValue;
            }while(!checkPath(path));
            antColony[i].setPath(path);
        }
    }

    private void crossBestAnt(){
        int[] antsFitness = new int[this.antColony.length];
        
        for(int i = 0; i < this.antColony.length; i++){
            antsFitness[i] = this.antColony[i].getFitness(this.graph);
        }

        // we gonna check for the antConony.length/2 worst ants and we will replace them with the bestAnt
        for(int i = 0; i < this.antColony.length/2; i++){
            int worstAnt = 0;
            for(int j = 1; j < this.antColony.length; j++){
                if(antsFitness[j] > antsFitness[worstAnt]){
                    worstAnt = j;
                }
            }
            this.antColony[worstAnt] = this.bestAnt;
        }
        
    }
}
