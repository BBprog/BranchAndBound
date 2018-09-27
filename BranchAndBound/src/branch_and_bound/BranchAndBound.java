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


public class BranchAndBound { 
    private final FileLoader data;
    private final Node tree;
    
    public BranchAndBound(String fileName) {
        this.data = new FileLoader();
        this.data.load(fileName);
        this.tree = new Node();
    }
    
    public void execute() {
        createTreeRecurs(tree, 0, 0, 0);
        System.out.println(tree.tmp);
    }
    
    public void changeFile(String fileName) {
        data.load(fileName);
    }
    
    private void createTreeRecurs(Node t, float weight, float value, int i) {
        if (weight > data.getW()) return;
        
        if (i > data.getLootsSize() - 1) {
            if (t.tmp < value) {
                t.tmp = value;
            }
            return;
        }
        
        float borne = calculBorneSupp(i, weight) + value;
        
        if (borne < t.tmp) return;
        
        t.addLeftNode();
        createTreeRecurs(t.getLeftNode(), weight + data.getLoot(i).getWeight(), value + data.getLoot(i).getValue(), i + 1);
        
        t.addRightNode();
        createTreeRecurs(t.getRightNode(), weight, value, i + 1);
    }
    
    private float calculBorneSupp(int index, float weight) {
        float borneSupp = 0;
        float totalWeight = weight;
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
                System.out.println(l);
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
