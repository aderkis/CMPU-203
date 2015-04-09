package data2;

public class Data2 {
    
    public static MultiSet<Integer> set = new Leaf();
    public static MultiSet<Integer> zet = new Leaf();
    
    
    //right now just some temporary stuff to make sure my code is working (it do)
    public static void main(String[] args) {
        set = set.add(1);
        set = set.add(2);
        set = set.add(3);
        set = set.add(4);
        set = set.add(5);
        set = set.add(6);
        set = set.add(7);
        set = set.add(8);
        
        zet = zet.add(8);
        zet = zet.add(9);
        zet = zet.add(10);
        zet = zet.add(9);
        zet = zet.add(13);
        zet = zet.add(6);
        zet = zet.add(4);
        zet = zet.add(4);
        

        MultiSet<Integer> tempLeft = ((Branch)set).left;
        MultiSet<Integer> tempRight = ((Branch)set).right;
        System.out.println("Set: "+ set.toString());
        System.out.println("Zet: " + zet.toString());
        System.out.println("Zet \\/ Set: " + zet.union(set).toString());
        System.out.println("Zet /\\ Set: " + zet.inter(set).toString());
        System.out.println("Set - Zet: " + zet.diff(set).toString());
        System.out.println("Zet - Set: " + set.diff(zet).toString());
        
        System.out.println("Set cardinality (should be 8): " + ((Integer)set.cardinality()).toString());
        System.out.println("Set height (should be 4): " + ((Integer)set.height()).toString());

    
    }
    
}
