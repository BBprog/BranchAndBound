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
    private Node leftNode;
    private Node rightNode;
    private boolean visited;
    
    public Node() {
        this.leftNode = null;
        this.rightNode = null;
    } 
    public Node(Node left, Node right) {
        this.leftNode = left;
        this.rightNode = right;
    }

    public void addLeftNode() {
        Node n = new Node();
        this.leftNode = n;
    }
    public void addRightNode() {
        Node n = new Node();
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
    
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    
    public void setVisited(boolean v) {
        this.visited = v;
    }
    
}
