package data1;

public interface FiniteSet {
        
    public int cardinality();
    
    public boolean isEmptyHuh();
    
    public boolean member(int elt);
    
    public FiniteSet add(int elt);
    
    public FiniteSet remove(int elt);
    
    public FiniteSet union(FiniteSet set);
    
    public FiniteSet inter(FiniteSet set);
    
    public FiniteSet diff(FiniteSet set);
    
    public boolean equal(FiniteSet set);
    
    public boolean subset(FiniteSet set);
    
    public String toString();
    
    // included testers in FiniteSet and not just Branch to allow tests to involve
    // all sets, rather than just nonempty sets
    public boolean prop1Tester(int x, int y);
    public boolean prop2Tester(FiniteSet set, int x);
    

}
