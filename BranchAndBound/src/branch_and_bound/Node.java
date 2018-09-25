/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package branch_and_bound;

/**
 *
 * @author Amelie
 */
public class Node {
    private Node parent;
    private Node leftNode;
    private Node rightNode;
    private boolean visited;
    private float value;
    private float weight;
    private float borneSupp;
    
    public static float tmp = 0;
    
    public Node(Node parent, float value, float weight) {
        this.parent = parent;
        this.value = value;
        this.weight = weight;
        this.leftNode = null;
        this.rightNode = null;
    }
    
    public void addLeftNode(float value, float weight) {
        Node n = new Node(this, value, weight);
        this.leftNode = n;
    }
    public void addRightNode() {
        Node n = new Node(this, 0, 0);
        this.rightNode = n;
    }
    
    public void remove() {
        leftNode.remove();
        rightNode.remove();
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
    
    public boolean isVisited() {
        return visited;
    }
    
    public float getValue() {
        return value;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    
    public void setVisited(boolean v) {
        this.visited = v;
    }
    
    public void addValue(float val) {
        this.value += val;
    }
    
}
