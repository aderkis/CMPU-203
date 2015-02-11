package data1;

public class Leaf implements FiniteSet {
    
    public Leaf() {}
    
    public static FiniteSet empty() {
        return new Leaf();
    }
    
    public int cardinality() {
        return 0;
    }
    
    public boolean isEmptyHuh() {
        return true;
    }
    
    public boolean member(int elt) {
        return false;
    }
    
    public FiniteSet add(int elt) {
        return new Branch(this, elt, this);
    }
    
    public FiniteSet remove(int elt) {
        return this;
    }
    
    public FiniteSet union(FiniteSet set) {
        return set;
    }
    
    public FiniteSet inter(FiniteSet set) {
        return this;
    }
    
    public FiniteSet diff(FiniteSet set) {
        return this;
    }
    
    public boolean equal(FiniteSet set) {
        return set.isEmptyHuh();
    }
    
    public boolean subset(FiniteSet set) {
        return true;
    }
    
}
