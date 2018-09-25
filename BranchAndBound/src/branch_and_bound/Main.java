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
    
    public static void main(String[] args) {
        BranchAndBound algo = new BranchAndBound(FILEPATH.concat("sac2.txt"));
        algo.execute();
    }

}
