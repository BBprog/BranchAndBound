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
public class Main {
    
    private static final String FILEPATH = System.getProperty("user.dir") + "/src/files/";
    private static final int nbFiles = 5;
    
    public static void main(String[] args) {
        BranchAndBound algo = new BranchAndBound(FILEPATH.concat("sac0.txt"));
        for (int i = 0; i < nbFiles; ++i) {
            String fileName = "sac" + i + ".txt";
            algo.changeFile(FILEPATH.concat(fileName));
            
            System.out.println("===== FILE " + fileName + " =====");
            algo.execute();
            System.out.println("");
        }
    }

}
