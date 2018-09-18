/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package branchandbound;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amelie
 */
public class BranchAndBound {
    //
    private int W;
    private ArrayList<Loot> loots;
    //
    
    
    private Node tree;
    
    public BranchAndBound(String fileName) {
        this.loots = new ArrayList<>();
        try {
            initByFile(fileName);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collections.sort(loots);
        printLoots();
        
        this.tree = new Node(false);
        createTree(tree, 0, 0);
        
        printTree(tree, 0);
    }
    
    private float createTree(Node t, float totalWeight, int i) {
        if (i > loots.size() - 1) return 0;
        
        int j = i + 1;
        float leftVal = 0;
        float rightVal = 0;
        
        t.addLeftNode();
        if(totalWeight + loots.get(i).getWeight() < W) {
            leftVal = createTree(t.getLeftNode(), totalWeight + loots.get(i).getWeight(), j);
        }
        t.addRightNode();
        rightVal = createTree(t.getRightNode(), totalWeight, j);
        
        float value = 0;
        if (leftVal > rightVal) {
            removeNode(t.getRightNode());
            value = leftVal;
        }
        else {
            removeNode(t.getLeftNode());
            value = rightVal;
        }
        
        return value;
    }
    
    private void removeNode(Node n) {
        if (null == n) return;
        removeNode(n.getLeftNode());
        removeNode(n.getRightNode());
        n.setLeftNode(null);
        n.setRightNode(null);
        n = null;
    }
    
    public void printTree(Node n, int i) {
        int j = i + 1;
        if (n.getLeftNode() != null)
            printTree(n.getLeftNode(), j);
        System.out.println(i + ": " + n.getTakeLoot());
        if (n.getRightNode() != null)
            printTree(n.getRightNode(), j);
    }
    
    private void addLoot(float w, float v) {
        Loot loot = new Loot(w, v);
        loots.add(loot);
    }
    
    
    
    
    //
    private void initByFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        this.W = Integer.parseInt(br.readLine());
        while ((line = br.readLine()) != null) {
            String str[] = line.split(" ");
            addLoot(parseFloat(str[0]), parseFloat(str[1]));
        }
    }
    
    public void printLoots() {
        loots.forEach((l) -> {
            System.out.println(l.toString() + " ");
        });
    }
    //
}
