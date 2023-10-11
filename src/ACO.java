/**
 * Here we are going to implement the Ant Colony Optimization algorithm
 */
package src;

import java.util.ArrayList;
import java.util.Random;

public class ACO {
    private int antsNumber; // number of ants
    private Ant besAnt; // best ant
    private Ant[] ants; // ants
    private int nodesNumber; // number of nodes
    private double[][] pheromoneMap; // pheromone map
    private double[][] distanceMap; // distance map
    private double alpha; // alpha value used in the formula
    private double beta; // beta value used in the formula
    private double evaporationRate; // evaporation rate used for evaporation
    public ACO(int antsNumber, double[][] distanceMap) {
        this.antsNumber = antsNumber;
        this.distanceMap = distanceMap;
        this.nodesNumber = distanceMap.length;
        this.pheromoneMap = new double[this.nodesNumber][this.nodesNumber];
        this.ants = new Ant[this.antsNumber];
        this.alpha = 1.0;
        this.beta = 1.0;
        this.evaporationRate = 0.01;
        this.initPheromoneMap();

    }

    private void initPheromoneMap(){
        // the pheromoneMap[0][y] and pheromoneMap[x][0] will be 0
        for(int i = 0; i < this.nodesNumber; i++){
            this.pheromoneMap[0][i] = 0;
            this.pheromoneMap[i][0] = 0;
        }
        for(int i = 1; i < this.nodesNumber; i++){
            for(int j = 1; j < this.nodesNumber; j++){
                this.pheromoneMap[i][j] = 0.1;
            }
        }
    }

    private void updatePheroMap(){
        // we will update the pheromone map with the pheromone left by the ant
        for(int i = 0; i < this.nodesNumber; i++){
            for(int j = 0; j < this.nodesNumber; j++){
                this.pheromoneMap[i][j] = (1-this.evaporationRate)*this.pheromoneMap[i][j];
            }
        }
    }

    private void setBestAnt(Ant ant){
        if(this.besAnt == null){
            this.besAnt = ant;
        }else{
            if(ant.getDistance(this.distanceMap) < this.besAnt.getDistance(this.distanceMap)){
                this.besAnt = ant;
            }
        }
    }

    public void showBestAnt(){
        System.out.println(this.besAnt);
    }
    private void showArray(double[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                System.out.println("from "+i+" to "+j+" : "+array[i][j]);
            }
            System.out.println();
        }
    }
    
    public void runACO(int times){
        for(int i = 0; i < times; i++){
            // we will create the ants
            for(int j = 0; j < this.antsNumber; j++){
                Ant ant = new Ant(this.nodesNumber, 1);
                ant.runAnt(this.pheromoneMap, this.distanceMap, this.alpha, this.beta);
                this.ants[j] = ant;
                this.setBestAnt(this.ants[j]);
                //System.out.println("Ant "+j+" distance: "+this.ants[j].getDistance(this.distanceMap));
            }
            // we update the pheromone map here
            this.updatePheroMap();
            
        }
    }
    
}
