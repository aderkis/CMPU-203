package data1;

public class Leaf implements FiniteSet {
    
    public Leaf() {}
    
    // creates a new Leaf object representing an empty set
    public static FiniteSet empty() {
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
    public boolean member(int elt) {
        return false;
    }
    
    // adding an element turns the leaf into a branch
    public FiniteSet add(int elt) {
        return new Branch(this, elt, this);
    }
    
    // removing from an empty set results in the empty set
    public FiniteSet remove(int elt) {
        return this;
    }
    
    // the union of an empty set and another set is equal to the other set
    public FiniteSet union(FiniteSet set) {
        return set;
    }
    
    // the intersection of an empty set and another set is tbe empty set
    public FiniteSet inter(FiniteSet set) {
        return this;
    }
    
    // the difference between an empty set and another set is equal to the other set
    public FiniteSet diff(FiniteSet set) {
        return set;
    }
    
    // a set is equal to another set iff it is empty
    public boolean equal(FiniteSet set) {
        return set.isEmptyHuh();
    }
    
    // the empty set is a subset of all sets
    public boolean subset(FiniteSet set) {
        return true;
    }
    
    // toString() is for testing purposes
    public String toString() {
        return "";
    }
    
    // see explanation in FiniteSet.java
    public boolean prop1Tester(int x, int y) {
        return true;
    }
    public boolean prop2Tester(FiniteSet set, int x) {
        return true;
    }
    
}
