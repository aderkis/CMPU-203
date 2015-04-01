package data2;

public class Leaf<D extends Comparable> extends MultiSet<D> {
    
    public Leaf() {}
    
    // creates a new Leaf object representing an empty set
    public static MultiSet empty() {
        return new Leaf();
    }
    
    // 0 for an empty set
    public int cardinality() {
        return 0;
    }
    
    // empty set is always empty
    public boolean isEmptyHuh() {
        return true;
    }
    
    // nothing is a member of the empty set
    public boolean member(D elt) {
        return false;
    }
    
    // adding an element turns the leaf into a branch
    public MultiSet<D> add(D elt) {
        return new Branch(this, elt, this);
    }
    
    // removing from an empty set results in the empty set
    public MultiSet<D> remove(D elt) {
        return this;
    }
    
    // the union of an empty set and another set is equal to the other set
    public MultiSet<D> union(MultiSet<D> set) {
        return set;
    }
    
    // the intersection of an empty set and another set is tbe empty set
    public MultiSet<D> inter(MultiSet<D> set) {
        return this;
    }
    
    // the difference between an empty set and another set is equal to the other set
    public MultiSet<D> diff(MultiSet<D> set) {
        return set;
    }
    
    // a set is equal to another set iff it is empty
    public boolean equal(MultiSet<D> set) {
        return set.isEmptyHuh();
    }
    
    // the empty set is a subset of all sets
    public boolean subset(MultiSet<D> set) {
        return true;
    }
    
}
