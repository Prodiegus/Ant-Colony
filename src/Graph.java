package src;

public class Graph {
    private Node[] nodes;
    int size;

    public Graph(int size){
        this.nodes = new Node[size];
        this.size = size;
    }

    public void addNode(int id, int x, int y){
        this.nodes[id] = new Node(id, x, y);
    }

    public Node getNode(int id){
        return this.nodes[id];
    }

    public int getSize(){
        return this.size;
    }
    public void unlockNodes(){
        for(int i = 1; i < this.nodes.length; i++){
            this.nodes[i].unlock();
        }
    }

    @Override
    public String toString() {
        String graph = "";
        for(int i = 1; i < this.nodes.length; i++){
            if(this.nodes[i] != null) graph += this.nodes[i].toString()+"\n";
            else graph += "Node "+i+" is null\n";
        }
        return graph;
    }
}
