package data2;

public abstract class MultiSet<D extends Comparable> {
    
    public abstract int cardinality();
    
    public abstract boolean isEmptyHuh();
    
    public abstract boolean member(D elt);
    
    public abstract MultiSet<D> add(D elt);
    
    public abstract MultiSet<D> remove(D elt);
    
    public abstract MultiSet<D> union(MultiSet<D> set);
    
    public abstract MultiSet<D> inter(MultiSet<D> set);
    
    public abstract MultiSet<D> diff(MultiSet<D> set);
    
    public abstract boolean equal(MultiSet<D> set);
    
    public abstract boolean subset(MultiSet<D> set);
    
}
