/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package branchandbound;

/**
 *
 * @author Amelie
 */
public class Node {
    private Node leftNode;
    private Node rightNode;
    private boolean takeLoot;
    
    public Node(boolean take) {
        this.leftNode = null;
        this.rightNode = null;
        this.takeLoot = take;
    } 
    public Node(Node left, Node right, boolean take) {
        this.leftNode = left;
        this.rightNode = right;
        this.takeLoot = take;
    }

    public void addLeftNode() {
        Node n = new Node(true);
        this.leftNode = n;
    }
    public void addRightNode() {
        Node n = new Node(false);
        this.rightNode = n;
    }
    
    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public boolean getTakeLoot() {
        return takeLoot;
    }
    
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    
}
