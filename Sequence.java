package data2;

public interface Sequence<D extends Comparable> {
    
    public D here();
    public Sequence<D> next();
    public boolean hasNext();
    
}
