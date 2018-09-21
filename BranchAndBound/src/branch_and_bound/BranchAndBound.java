/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package branch_and_bound;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
    
    private FileLoader data;
    private Node tree;
    
    public BranchAndBound(String fileName) {
        this.data = new FileLoader();
        this.data.load(fileName);
        this.tree = new Node(false);
    }
    
    public void execute() {
        removeNode(tree);
        createTreeRecurs(tree, 0, 0, 0);
        //printTree(tree, 0);
        System.out.println(printSolution(tree, 0));
    }
    
    public void changeFile(String fileName) {
        data.load(fileName);
    }
    
    private float createTreeRecurs(Node t, float weight, float value, int i) {
        if (i > data.getLootsSize() - 1) return 0;
        if(weight > data.getW()) return 0;
        
        int j = i + 1;
        float leftVal = 0;
        float rightVal = 0;
        
        t.addLeftNode();
        float nextWeight = weight + data.getLoot(i).getWeight();
        leftVal = createTreeRecurs(t.getLeftNode(), nextWeight, data.getLoot(i).getValue(), j);
        
        t.addRightNode();
        rightVal = createTreeRecurs(t.getRightNode(), weight, 0, j);
        
        float val = 0;
        if (leftVal > rightVal) {
            removeNode(t.getRightNode());
            t.setRightNode(null);
            val = leftVal;
        }
        else {
            removeNode(t.getLeftNode());
            t.setLeftNode(null);
            val = rightVal;
        }
        
        return val + value;
    }
    
    private void removeNode(Node n) {
        n = null;
        /*
        if (null == n) return;
        removeNode(n.getLeftNode());
        removeNode(n.getRightNode());
        n.setLeftNode(null);
        n.setRightNode(null);
        */
    }
    
    public void printTree(Node n, int i) {
        int j = i + 1;
        if (n.getLeftNode() != null)
            printTree(n.getLeftNode(), j);
        System.out.println(i + ": " + n.getTakeLoot());
        if (n.getRightNode() != null)
            printTree(n.getRightNode(), j);
    }
    
    public float printSolution(Node n, int i) {
        int next = i + 1;
        float value = 0;
        
        if (n.getLeftNode() != null)
            value += printSolution(n.getLeftNode(), next);
        if (n.getTakeLoot()) {
            System.out.println(data.getLoot(i - 1));
            value += data.getLoot(i - 1).getValue();
        }
        if (n.getRightNode() != null)
            value += printSolution(n.getRightNode(), next);
        
        return value;
    }
    
    private class FileLoader {
        
        private String fileName;
        private float W;
        private ArrayList<Loot> loots;

        public void load(String fileName) {
            this.loots = new ArrayList<>();
            this.fileName = fileName;
            init();
        }

        private void init() {
            try {
                readFile();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            Collections.sort(loots);
        }

        private void readFile() throws FileNotFoundException, IOException {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            String line;
            this.W = parseFloat(br.readLine());
            while ((line = br.readLine()) != null) {
                String str[] = line.split(" ");
                addLoot(parseFloat(str[0]), parseFloat(str[1]));
            }
        }

        private void addLoot(float w, float v) {
            Loot loot = new Loot(w, v);
            loots.add(loot);
        }

        public void printLoots() {
            loots.forEach((l) -> {
                System.out.println(l.toString() + " ");
            });
        }

        public float getW() {
            return W;
        }

        public ArrayList<Loot> getLoots() {
            return loots;
        }

        public int getLootsSize() {
            return loots.size();
        }

        public Loot getLoot(int i) {
            return loots.get(i);
        }

    }

}
