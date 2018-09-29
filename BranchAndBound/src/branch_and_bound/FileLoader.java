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


public final class FileLoader {
        
    private String fileName;
    private float W;
    private ArrayList<Loot> loots;

    public FileLoader() {
    }
    
    public void load() {
        this.loots = new ArrayList<>();
        init();
    }
    
    public void setFile(String fileName) {
        this.fileName = fileName;
        load();
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