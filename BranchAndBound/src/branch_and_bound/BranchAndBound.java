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
        this.tree = new Node(null, 0, 0);
    }
    
    public void execute() {
        //createTree(tree);
        createTreeRecurs(tree, 0, 0, 0);
        System.out.println(Node.tmp);
        //printTree(tree, 0);
        //System.out.println(printSolution(tree, 0));
        //data.printLoots();
    }
    
    public void changeFile(String fileName) {
        data.load(fileName);
    }

    /*
    private void createTree(Node t) {
        int i = 0;
        float totalWeight = 0;
        
        while (i >= 0) {
            System.out.println(i + " : " + totalWeight);
            if (i >= data.getLootsSize()) {
                t.setVisited(true);
                t = t.getParent();
                --i;
            }
            else if (t.isVisited()) {
                if (t.hasLeftNode() && t.getLeftNode().getValue() > t.getRightNode().getValue()) {
                    t.addValue(t.getLeftNode().getValue());
                    if (t.hasRightNode()) t.getRightNode().remove();
                    t.setRightNode(null);
                }
                else {
                    t.addValue(t.getRightNode().getValue());
                    //if (t.hasLeftNode()) t.getLeftNode().remove();
                    if (t.hasLeftNode()) totalWeight -= data.getLoot(i).getWeight();
                    t.setLeftNode(null);
                }
                t = t.getParent();
                --i;
            }
            else {
                float nextWeight = data.getLoot(i).getWeight();
                if (totalWeight + nextWeight < data.getW()) {
                    if (!t.hasLeftNode()) {
                        t.addLeftNode(data.getLoot(i).getValue(), nextWeight);
                        t = t.getLeftNode();
                        totalWeight += nextWeight;
                        ++i;
                    }
                    else if (!t.hasRightNode()) {
                        totalWeight -= data.getLoot(i).getWeight();
                        t.addRightNode();
                        t.setVisited(true);
                        t = t.getRightNode();
                        ++i;
                    }
                }
                else {
                    if (!t.hasRightNode()) {
                        t.addRightNode();
                        t.setVisited(true);
                        t = t.getRightNode();
                        ++i;
                    }
                }
            }
        }     
    }
*/
    
    private float createTreeRecurs(Node t, float weight, float value, int i) {
        if (i > data.getLootsSize() - 1) {
            t.tmp = value;
            return 0;
        }                 
        if (weight > data.getW()) return 0;
        
        float borne = calculBorneSupp(i) + value;
        System.out.println(i + " " + borne + " " + value + " " + t.tmp);
        
        if (borne < t.tmp) return 0;
        
        t.addLeftNode(data.getLoot(i).getValue(), data.getLoot(i).getWeight());
        createTreeRecurs(t.getLeftNode(), weight + data.getLoot(i).getWeight(), value + data.getLoot(i).getValue(), i + 1);
        
        t.addRightNode();
        createTreeRecurs(t.getRightNode(), weight, value, i + 1);
        
        return 1;
        
        /*
        if (borneSupp <= t.tmp) {
            
        }
        
        int j = i + 1;
        
        t.addLeftNode(data.getLoot(i).getValue(), data.getLoot(i).getWeight());
        float nextWeight = weight + data.getLoot(i).getWeight();
        float leftVal = createTreeRecurs(t.getLeftNode(), nextWeight, data.getLoot(i).getValue(), j);
        
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
        */
    }
    
    private float calculBorneSupp(int index) {
        float borneSupp = 0;
        float totalWeight = 0;
        float nextWeight = 0;
        for (int i = index; i < data.getLootsSize(); ++i) {
            nextWeight = totalWeight + data.getLoot(i).getWeight();
            if (nextWeight < data.W) {
                borneSupp += data.getLoot(i).getValue();
                totalWeight = nextWeight;
            }
            else {
                borneSupp += ((data.W - totalWeight) / data.getLoot(i).getWeight()) * data.getLoot(i).getValue();
                break;
            }
        }
        return borneSupp;
    }
    
    public void printTree(Node n, int i) {
        int j = i + 1;
        if (n.getLeftNode() != null)
            printTree(n.getLeftNode(), j);
        System.out.println(i + ": " + n.getValue());
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
                System.out.println(l);
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
