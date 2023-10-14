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
    private double Q; // learning rate used in the formula
    public ACO(int antsNumber, double[][] distanceMap, double alpha, double beta, double evaporationRate, double Q) {
        this.antsNumber = antsNumber;
        this.distanceMap = distanceMap;
        this.nodesNumber = distanceMap.length;
        this.pheromoneMap = new double[this.nodesNumber][this.nodesNumber];
        this.ants = new Ant[this.antsNumber];
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.Q = Q;
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
    public Ant getBestAnt(){
        return this.besAnt;
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
        // we will give a mesage of progress to the user evry 2 seconds of the program
        long startTime2 = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        int roundedTime = 0;
        int roundedPercent = 0;
        int updateMargin = 2;
        int totalAnts = antsNumber*times;
        for(int i = 0; i < times; i++){
            currentTime = System.currentTimeMillis();
            //System.out.println("time: "+(currentTime - startTime)+" updateMargin: "+updateMargin+" currentTime - startTime % updateMargin: "+(currentTime - startTime % updateMargin)+" currentTime - startTime: "+(currentTime - startTime)+" updateMargin: "+updateMargin+" currentTime - startTime > updateMargin: "+(currentTime - startTime > updateMargin));
            if(((currentTime - startTime)) % updateMargin==0 && currentTime - startTime > updateMargin){
                // we will calculate the rounded time
                roundedTime = (int) Math.round((double)(currentTime - startTime2)/1000);
                // we will calculate the rounded percent to the second decimal
                roundedPercent = (int) Math.round((((double)i)/((double)times))*(double)(10000))/100;
                System.out.println("Progress "+roundedPercent+"%"+": "+i+"/"+times+" Time: "+roundedTime+" seconds");
                startTime = currentTime;
            }
            // we will create the ants
            for(int j = 0; j < this.antsNumber; j++){
                Ant ant = new Ant(this.nodesNumber, this.Q, j+i, i);
                ant.runAnt(this.pheromoneMap, this.distanceMap, this.alpha, this.beta);
                this.ants[j] = ant;
                this.setBestAnt(this.ants[j]);
                //System.out.println("Ant "+j+" distance: "+this.ants[j].getDistance(this.distanceMap));
                if(((currentTime - startTime)) % updateMargin==0 && currentTime - startTime > updateMargin){
                    roundedTime = (int) Math.round((double)(currentTime - startTime2)/1000);
                    roundedPercent = (int) Math.round((((double)i)/((double)times))*(double)(10000))/100;
                    System.out.println("Progress "+roundedPercent+"%"+": "+i+"/"+times+" Ant "+(j+i)+"/"+totalAnts+ " Time: "+roundedTime+" seconds");
                    startTime = currentTime;
                }
            }
            // we update the pheromone map here
            this.updatePheroMap();
            
        }
        double finalTime = (double)(System.currentTimeMillis() - startTime2)/1000;
        if(finalTime < 60){
            System.out.println("Time: "+finalTime+" seconds");
        }else if(finalTime < 3600){
            System.out.println("Time: "+(finalTime/60)+" minutes");
        }else{
            System.out.println("Time: "+(finalTime/3600)+" hours");
        }
        
    }
    
}
