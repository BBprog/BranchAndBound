package branch_and_bound;


public class Node {   
    public static float tmp = 0;
        
    private Node leftNode;
    private Node rightNode;
    private float borneSupp;
    
    public Node() {
        this.leftNode = null;
        this.rightNode = null;
    }
    
    public void addLeftNode() {
        Node n = new Node();
        this.leftNode = n;
    }
    public void addRightNode() {
        Node n = new Node();
        this.rightNode = n;
    }
    
    public boolean hasNext() {
        return (hasLeftNode() || hasRightNode());
    }
    public boolean hasLeftNode() {
        return leftNode != null;
    }
    public boolean hasRightNode() {
        return rightNode != null;
    }
    
    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }
    
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    
    public void setBorneSupp(float borne) {
        this.borneSupp = borne;
    }
    
}
