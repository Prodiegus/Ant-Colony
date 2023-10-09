package src;

public class Node {
    private int id;
    private int x;
    private int y;
    private boolean unlocked;
    public Node(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.unlocked = true;
    }

    public void lock(){
        this.unlocked = false;
    }
    public void unlock(){
        this.unlocked = true;
    }
    public boolean isUnlocked(){
        return this.unlocked;
    }
    // getters
    public int getId(){
        return this.id;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", unlocked=" + unlocked +
                '}';
    }
}
