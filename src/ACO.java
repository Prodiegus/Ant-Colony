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
    int[][] pheromone;
    Ant[] antColony;

    public ACO(Graph graph, int ants, int graphSize){
        this.graph = graph;
        this.ants = ants;
        this.pheromone = new int[graphSize][graphSize];
        this.antColony = new Ant[ants];
        loadPheromone();
        createAnts();
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
            int[] genoma = new int[pheromone.length];
            int start = (int) (Math.random() * (pheromone.length - 1));
            genoma[0] = start;
            for(int j = 1; j < genoma.length/50; j++){
                int next = (int) (Math.random() * (pheromone.length - 1));
                while(!graph.getNode(next).isUnlocked()){
                    next = (int) (Math.random() * (pheromone.length - 1));
                }
                genoma[j] = next;
                graph.getNode(next).lock();
            }
            for(int j = 0; j < genoma.length; j++){
                int next = j;
                while(!graph.getNode(next).isUnlocked()){
                    next = (j+=1);
                }
                genoma[j] = next;
                graph.getNode(next).unlock();
            }
            this.antColony[i] = new Ant(genoma, 1);
        }
    }
    
}
