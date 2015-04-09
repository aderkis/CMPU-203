package data2;

public abstract class MultiSet<D extends Comparable> implements Sequenced<D> {
    
    public int count;

    public int multiplicity() {
        return this.count;
    }
    
    public abstract int multiplicity(D elt);
    
    public abstract int cardinality();
    
    public abstract int height();
    
    public abstract boolean isEmptyHuh();
    
    public abstract boolean member(D elt);
    
    public abstract MultiSet<D> add(D elt);
    
    public abstract MultiSet<D> add(D elt, int count);
    
    public abstract MultiSet<D> remove(D elt);
    
    public abstract MultiSet<D> remove(D elt, int count);
    
    public abstract MultiSet<D> removeAll(D elt);
    
    public abstract MultiSet<D> union(MultiSet<D> set);
    
    public abstract MultiSet<D> inter(MultiSet<D> set);
    
    public abstract MultiSet<D> diff(MultiSet<D> set);
    
    public abstract boolean equal(MultiSet<D> set);
    
    public abstract boolean subset(MultiSet<D> set);
    
    public abstract MultiSet<D> balance();
    
    public abstract MultiSet<D> rotateLeft();
    
    public abstract MultiSet<D> rotateRight();
    
    public abstract Sequence<D> seq();
    
    public abstract String toString();
    
    
    
    
    

    
}
