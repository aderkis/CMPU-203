package data1;

public class Data1 {

    public static void main(String[] args) {
        //test sets
        FiniteSet empty = new Leaf();
        FiniteSet t1 = new Branch(empty, 1, empty);
        FiniteSet t3 = new Branch(empty, 3, empty);
        FiniteSet t5 = new Branch(empty, 5, empty);
        FiniteSet t7 = new Branch(empty, 7, empty);
        FiniteSet t2 = new Branch(t1, 2, t3);
        FiniteSet t6 = new Branch(t5, 6, t7);
        FiniteSet t4 = new Branch(t2, 4, t6);
        
        //cardinality
        System.out.println("Cardinality tests:");
        System.out.println("leaf cardinality vs 0: " + Integer.toString(empty.cardinality()));
        System.out.println("t1 cardinality vs 1: " + Integer.toString(t1.cardinality()));
        System.out.println("t6 cardinality vs 3: " + Integer.toString(t6.cardinality()));
        // isEmptyHuh
        System.out.println("isEmptyHuh tests:");
        System.out.println("leaf isEmptyHuh vs true: " + Boolean.toString(empty.isEmptyHuh()));
        System.out.println("t5 isEmptyHuh vs false: " + Boolean.toString(t5.isEmptyHuh()));
        // member
        System.out.println("Member tests:");
        System.out.println("3 member of leaf vs false: " + Boolean.toString(empty.member(3)));
        System.out.println("1 member of t1 vs true: " + Boolean.toString(t1.member(1)));
        System.out.println("1 member of t4 vs true: " + Boolean.toString(t4.member(1)));
        // add
        System.out.println("Add tests:");
        System.out.println("4 member of adding 4 to leaf vs true: " + Boolean.toString(empty.add(4).member(4)));
        System.out.println("4 member of adding 5 to t2 vs false: " + Boolean.toString(t2.add(5).member(4)));
        // remove
        System.out.println("Remove tests:");
        System.out.println("7 member of removing 7 from leaf vs false: " + Boolean.toString(empty.remove(7).member(7)));
        System.out.println("6 member of removing 6 from t4 vs false: " + Boolean.toString(t4.remove(6).member(6)));
        System.out.println("3 member of removing 3 from t1 vs false: " + Boolean.toString(t1.remove(3).member(3)));
        // union
        System.out.println("Union tests:");
        System.out.println("Leaf union leaf cardinality vs 0: " + Integer.toString(empty.union(empty).cardinality()));
        System.out.println("Leaf union t1 cardinality vs 1: " + Integer.toString(empty.union(t1).cardinality()));
        System.out.println("t2 union leaf cardinality vs 3: " + Integer.toString(t2.union(empty).cardinality()));
        System.out.println("t2 union t6 cardinality vs 6: " + Integer.toString(t2.union(t6).cardinality()));
        // inter
        System.out.println("Intersection tests:");
        System.out.println("Leaf intersect leaf cardinality vs 0: " + Integer.toString(empty.inter(empty).cardinality()));
        System.out.println("Leaf intersect t5 cardinality vs 0: " + Integer.toString(empty.inter(t5).cardinality()));
        System.out.println("t2 intersect t6 cardinality vs 0: " + Integer.toString(t2.inter(t6).cardinality()));
        System.out.println("t2 intersect t6 cardinality vs 0: " + Integer.toString(t2.inter(t6).cardinality()));
        System.out.println("t2 intersect t4 cardinality vs 3: " + Integer.toString(t2.inter(t4).cardinality()));
        // diff
        System.out.println("Difference tests:");
        System.out.println("Leaf difference leaf cardinality vs 0: " + Integer.toString(empty.diff(empty).cardinality()));
        System.out.println("t1 difference leaf cardinality vs 0: " + Integer.toString(t1.diff(empty).cardinality()));
        System.out.println("Leaf difference t2 cardinality vs 3: " + Integer.toString(empty.diff(t2).cardinality()));
        System.out.println("t6 difference t4 cardinality vs 4: " + Integer.toString(t6.diff(t4).cardinality()));
        // equal
        System.out.println("Equal tests:");
        System.out.println("Leaf equals leaf vs true: " + Boolean.toString(empty.equal(empty)));
        System.out.println("Leaf equals t2 vs false: " + Boolean.toString(empty.equal(t2)));
        System.out.println("t3 equals leaf vs false: " + Boolean.toString(t3.equal(empty)));
        System.out.println("t5 equals t6 vs false: " + Boolean.toString(t5.equal(t6)));
        System.out.println("t4 equals t4 vs true: " + Boolean.toString(t4.equal(t4)));
        // subset
        System.out.println("Subset tests:");
        System.out.println("Leaf subset leaf vs true: " + Boolean.toString(empty.subset(empty)));
        System.out.println("Leaf subset t3 vs true: " + Boolean.toString(empty.subset(t3)));
        System.out.println("t2 subset leaf vs false: " + Boolean.toString(t2.subset(empty)));
        System.out.println("t1 subset t2 vs true: " + Boolean.toString(t1.subset(t2)));
        System.out.println("t4 subset t2 vs false: " + Boolean.toString(t4.subset(t2)));
        System.out.println("t7 subset t7 vs true: " + Boolean.toString(t7.subset(t7)));
    }

}
