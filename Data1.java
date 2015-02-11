package data1;

public class Data1 {
    
    //testing
    
    public FiniteSet empty = new Leaf();
    public FiniteSet t1 = new Branch(empty, 1, empty);

    public FiniteSet t3 = new Branch(empty, 3, empty);

    public FiniteSet t5 = new Branch(empty, 5, empty);
    public FiniteSet t7 = new Branch(empty, 7, empty);
    public FiniteSet t2 = new Branch(t1, 2, t3);
    public FiniteSet t6 = new Branch(t5, 6, t7);
    public FiniteSet t4 = new Branch(t2, 4, t6);
  
    public static void main(String[] args) {
        // TODO tests
    }
    
}
