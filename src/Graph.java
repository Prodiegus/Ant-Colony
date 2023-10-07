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
        for(int i = 0; i < this.nodes.length; i++){
            this.nodes[i].unlock();
        }
    }
}
