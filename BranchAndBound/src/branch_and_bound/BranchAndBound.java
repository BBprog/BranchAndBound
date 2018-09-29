package branch_and_bound;


public class BranchAndBound { 
    private final FileLoader data;
    private final Node tree;
    
    public BranchAndBound(FileLoader data) {
        this.data = data;
        this.tree = new Node();
    }
    
    public void execute() {
        createTreeRecurs(tree, 0, 0, 0);
        System.out.println("Valeur trouvée : " + tree.tmp);
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
            if (nextWeight < data.getW()) {
                borneSupp += data.getLoot(i).getValue();
                totalWeight = nextWeight;
            }
            else {
                borneSupp += ((data.getW() - totalWeight) / data.getLoot(i).getWeight()) * data.getLoot(i).getValue();
                break;
            }
        }
        
        return borneSupp;
    }
    
    

}
