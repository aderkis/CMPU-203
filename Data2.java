package data2;

import java.util.Random;

public class Data2 {
    
    static Random rng = new Random();
    static MultiSet<Integer> testSet1 = new Leaf<>();
    static MultiSet<Integer> testSet2 = new Leaf<>();
    
    public static void main(String[] args) {
        
        testSet1 = testSet1.add(1);
        testSet1 = testSet1.add(2);
        testSet1 = testSet1.add(3);
        testSet1 = testSet1.add(3);
        testSet1 = testSet1.add(4);
        
        testSet2 = testSet2.add(1);
        testSet2 = testSet2.add(3);
        testSet2 = testSet2.add(3);
        testSet2 = testSet2.add(3);
        testSet2 = testSet2.add(4);
        
        
        System.out.println("set 1: " + testSet1.toString());
        System.out.println("set 2: " + testSet2.toString());
        System.out.println("Union: " + testSet1.union(testSet2).toString());
        System.out.println("Intersection: " + testSet1.inter(testSet2).toString());
        System.out.println("Difference: " + testSet2.diff(testSet1).toString());
        System.out.println("Set 1 without any 3s: " + testSet1.removeAll(3).toString());
        System.out.println("Set 2 subset of Set 1: " + testSet2.subset(testSet1));
        System.out.println("Set 2 without 3s: " + testSet2.removeAll(3).toString());
        System.out.println("Set 1 without 2s: " + testSet1.removeAll(2).toString());
        System.out.println("inter subset union: " + testSet1.inter(testSet2).subset(testSet1.union(testSet2)));
        
        Integer answer1 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            MultiSet<Integer> set2 = randIntSet(10);
            if(!test1(set1, set2)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                System.out.println("Set 2: " + set2.toString());
                System.out.println("Inter: " + set1.inter(set2).toString());
                System.out.println("Union: " + set1.union(set2).toString());
                answer1++;
            }
        }       
        System.out.println("Test 1 failures: " + answer1.toString());  
        
        Integer answer2 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            Integer randElt = rng.nextInt(100);
            if(!test2(set1, randElt)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                System.out.println("elt: " + randElt.toString());
                System.out.println("After removal: " + set1.remove(randElt).toString());
                answer2++;
            }
        }       
        System.out.println("Test 2 failures: " + answer2.toString());
        
        Integer answer3 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            MultiSet<Integer> set2 = randIntSet(10);
            int randElt = rng.nextInt(100);
            if(!test3(set1, set2, randElt)) {
                answer3++;
            }
        }       
        System.out.println("Test 3 failures: " + answer3.toString()); 
        
        Integer answer4 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            MultiSet<Integer> set2 = randIntSet(10);
            int randElt = rng.nextInt(100);
            if(!test4(set1, set2, randElt)) {
                answer4++;
            }
        }       
        System.out.println("Test 4 failures: " + answer4.toString());
        
        Integer answer5 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            MultiSet<Integer> set2 = randIntSet(10);
            int randElt = rng.nextInt(100);
            if(!test5(set1, set2, randElt)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                System.out.println("Set 2: " + set2.toString());
                System.out.println("Inter: " + set1.inter(set2).toString());
                System.out.println("Diff: " + set2.diff(set1).toString());
                answer5++;
            }
        }       
        System.out.println("Test 5 failures: " + answer5.toString());
        
        Integer answer6 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            MultiSet<Integer> set2 = randIntSet(10);
            if(!test6(set1, set2)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                System.out.println("Set 2: " + set2.toString());
                System.out.println("Inter: " + set1.inter(set2).toString());
                System.out.println("Diff: " + set2.diff(set1).toString());
                answer6++;
            }
        }       
        System.out.println("Test 6 failures: " + answer6.toString());
        
        Integer answer7 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            MultiSet<Integer> set2 = new Leaf();
            if(!test7(set1, set2)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                System.out.println("Set 2: " + set2.toString());
                System.out.println("Inter: " + set1.inter(set2).toString());
                answer7++;
            }
        }       
        System.out.println("Test 7 failures: " + answer7.toString());
        
        Integer answer8 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            int randElt = rng.nextInt(100);
            if(!test8(set1, randElt)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                System.out.println("Set 1 add remove: " + set1.add(randElt).remove(randElt).toString());
                answer8++;
            }
        }       
        System.out.println("Test 8 failures: " + answer8.toString());
        
        Integer answer9 = 0;
        for(int i=0; i<1000; i++) {        
            MultiSet<Integer> set1 = randIntSet(10);
            int randElt = rng.nextInt(100);
            if(!test9(set1)) {
                System.out.println("Failed test!");
                System.out.println("Set 1: " + set1.toString());
                answer9++;
            }
        }       
        System.out.println("Test 9 failures: " + answer9.toString());
        
        
        
        
    }
    
    public static MultiSet<Integer> randIntSet(int size) {
        MultiSet<Integer> answer = new Leaf<>();
        for(int i=0; i<size; i++) {
            answer = answer.add(rng.nextInt(100));     
        }
        return answer;
    }
    
    public static MultiSet<Character> randCharSet(int size) {
        MultiSet<Character> answer = new Leaf<>();
        for(int i=0; i<size; i++) {
            answer = answer.add((char)(65+rng.nextInt(57)));     
        }
        return answer;
    }
    
    //tests union + intersection
    public static <D extends Comparable> boolean test1(MultiSet<D> set1, MultiSet<D> set2) {
        return set1.inter(set2).subset(set1.union(set2));
    }
    
    //tests removeAll, remove, and member
    public static <D extends Comparable> boolean test2(MultiSet<D> set1, D elt) {
        return set1.removeAll(elt).member(elt)==false;
    }
    
    //tests multiplicity and union
    public static <D extends Comparable> boolean test3(MultiSet<D> set1, MultiSet<D> set2, D elt) {
        return set1.multiplicity(elt)+set2.multiplicity(elt)==
                set1.union(set2).multiplicity(elt);
    }
    
    //tests multiplicity and intersection
    public static <D extends Comparable> boolean test4(MultiSet<D> set1, MultiSet<D> set2, D elt) {
        return Math.min(set1.multiplicity(elt), set2.multiplicity(elt))==
                set1.inter(set2).multiplicity(elt);
    }
    
    public static <D extends Comparable> boolean test5(MultiSet<D> set1, MultiSet<D> set2, D elt) {
        if(set1.multiplicity(elt)-set2.multiplicity(elt)<0) {
            return set2.diff(set1).multiplicity(elt)==0;
        } else {
            return set2.diff(set1).multiplicity(elt)==set1.multiplicity(elt)-set2.multiplicity(elt);
        }
        
    }
    
    public static <D extends Comparable> boolean test6(MultiSet<D> set1, MultiSet<D> set2) {
        return set2.diff(set1).cardinality()==set1.cardinality()-set1.inter(set2).cardinality();       
    }
    
    public static <D extends Comparable> boolean test7(MultiSet<D> set1, MultiSet<D> set2) {
        return set1.inter(set2).isEmptyHuh()==true;
    }
    
    public static <D extends Comparable> boolean test8(MultiSet<D> set1, D elt) {
        return set1.add(elt).remove(elt).equal(set1);
    }
    
    public static <D extends Comparable> boolean test9(MultiSet<D> set1){
        Sequence<D> seq = set1.seq();
        int answer = 0;
        while(seq.hasNext()) {
            seq = seq.next();
            answer += 1;
        }
        return answer==set1.cardinality();
    }
    
    
}
