package data2;

public class Data2 {
    
    public static MultiSet<Integer> set = new Leaf();
    
    public static void main(String[] args) {
        set = set.add(1);
        set = set.add(2);
        set = set.add(3);
        set = set.add(4);
        set = set.add(5);
        set = set.add(6);
        set = set.add(7);
        
        System.out.println(set.toString());
        
    
    }
    
}
