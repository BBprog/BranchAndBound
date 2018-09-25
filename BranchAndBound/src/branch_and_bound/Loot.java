package branch_and_bound;


public class Loot implements Comparable<Loot> {
    private final float weight;
    private final float value;
    
    public Loot(float w, float v) {
        this.weight = w;
        this.value = v;
    }

    public float getWeight() {
        return weight;
    }

    public float getValue() {
        return value;
    }
    
    public float getRatio() {
        return value / weight;
    }

    @Override
    public String toString() {
        return "Loot{" + "w=" + weight + ", v=" + value + ", r=" + this.getRatio() + '}';
    }

    @Override
    public int compareTo(Loot l) {
        int ratio = (int) (this.getRatio() * 10000);
        int comparedRatio = (int) (l.getRatio() * 10000);

        return (comparedRatio - ratio);
    }
    
}
