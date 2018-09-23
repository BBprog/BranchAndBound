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
    
    private final FileLoader data;
    private final Node tree;
    
    public BranchAndBound(String fileName) {
        this.data = new FileLoader();
        this.data.load(fileName);
        this.tree = new Node();
    }
    
    public void execute() {
        //createTree(tree);
        createTreeRecurs(tree, 0, 0, 0);
        //printTree(tree, 0);
        System.out.println(printSolution(tree, 0));
        data.printLoots();
    }
    
    public void changeFile(String fileName) {
        data.load(fileName);
    }

    /*
    private void createTree(Node t) {
        int i = 0;
        float totalWeight = 0;
        float totalValue = 0;
        
        while (i >= 0 && i < data.getLootsSize()) {
            float nextWeight = totalWeight + data.getLoot(i).getWeight();
            
            if(t.isVisited()) {
                
            }
            else {
                
            }
            
            if (totalWeight >= data.getW()) {
                t = t.getParent();
                --i;
                totalWeight -= data.getLoot(i).getWeight();
                totalValue -= data.getLoot(i).getValue();
            }
            else if (t.isVisited()) {
                
            }
            else {
                if (!t.hasLeftNode()) {
                    t.addLeftNode();
                    t = t.getLeftNode();
                    totalWeight += data.getLoot(i).getWeight();
                    totalValue += data.getLoot(i).getValue();
                    ++i;
                }
                else if (!t.hasRightNode()) {
                    t.addRightNode();
                    t = t.getRightNode();
                    ++i;
                }
            }
            
            
            t.setVisited(true);
            ++i;
        }     
    }*/
    
    private float createTreeRecurs(Node t, float weight, float value, int i) {
        if (i > data.getLootsSize() - 1) return 0;                 
        if(weight > data.getW()) return 0;
        if(weight + data.getMinWeight() > data.getW()) return value;
        
        int j = i + 1;
        
        t.addLeftNode();
        float nextWeight = weight + data.getLoot(i).getWeight();
        float leftVal = createTreeRecurs(t.getLeftNode(), nextWeight, data.getLoot(i).getValue(), j);
        
        if (i == 0) System.out.println(i);
        
        t.addRightNode();
        float rightVal = createTreeRecurs(t.getRightNode(), weight, 0, j);

        if (leftVal > rightVal) {
            t.setRightNode(null);   
            value += leftVal;
        }
        else {
            t.setLeftNode(null);
            value += rightVal;
        }
        
        return value;
    }
    
    public void printTree(Node n, int i) {
        int j = i + 1;
        if (n.getLeftNode() != null)
            printTree(n.getLeftNode(), j);
        System.out.println(i + ": " );
        if (n.getRightNode() != null)
            printTree(n.getRightNode(), j);
    }
    
    public float printSolution(Node n, int i) {
        float value = 0;
        
        while (n.hasNext()) {
            if (n.hasLeftNode()) {
                value += data.getLoot(i).getValue();
                n = n.getLeftNode();
            }
            else {
                n = n.getRightNode();
            }
            ++i;
        }

        return value;
    }
    
    private class FileLoader {
        
        private String fileName;
        private float W;
        private float minWeight;
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
            this.minWeight = W;
            while ((line = br.readLine()) != null) {
                String str[] = line.split(" ");
                this.minWeight = Math.min(parseFloat(str[0]), this.minWeight);
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
        
        public float getMinWeight() {
            return minWeight;
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
