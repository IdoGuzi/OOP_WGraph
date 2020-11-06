package unitest;

import org.junit.jupiter.api.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test class for the Pair data structure class.
 * @author Ido Guzi
 */
class PairTest {
    Random rnd = new Random();


    /**
     * Test function for the swap method.
     * saving the elements of the pair, swapping and check it swapped.
     * assumptions: getLeft, getRight working.
     */
    @Test
    void swap() {
        Collection<Pair<Integer>> pairs = factory(random(100,300));
        Iterator<Pair<Integer>> it = pairs.iterator();
        while (it.hasNext()){
            Pair<Integer> p = it.next();
            int bl = p.getLeft(), br = p.getRight();
            p.swap();
            assertEquals(true, bl==p.getRight() && br==p.getLeft());
        }
    }


    /**
     * Test function for the getLeft and getRight methods.
     * assumptions: none.
     */
    @Test
    void getLeftRight() {
        int m = random(100,300);
        for (int i=0;i<m;i++){
            int a = random(0,500);
            int b = random(0,500);
            Pair<Integer> p = new Pair<>(a,b);
            assertEquals(true, p.getLeft()==a && p.getRight()==b);
        }
    }

    /**
     * Test function the setLeft and setRight methods.
     * assumptions: getLeft, getRight working.
     */
    @Test
    void setLeftRight() {
        Collection<Pair<Integer>> pairs = factory(random(100,300));
        Iterator<Pair<Integer>> it = pairs.iterator();
        while (it.hasNext()) {
            Pair<Integer> p = it.next();
            int bl = p.getLeft(), br = p.getRight();
            p.setLeft(bl+1); p.setRight(br+1);
            assertEquals(true, bl!=p.getLeft() && br!=p.getRight());

        }
    }


    /**
     * Test function for the hashcode method.
     * hashcode can be the same for different pairs,
     * but need to happen rarely.
     */
    @Test
    void testHashCode() {
        int failCount =0;
        for (int i=0;i<200;i++){
            for (int j=0;j<200;j++){
                for (int k=0;k<200;k++){
                    for (int l=0;l<200;l++){
                        Pair<Integer> p1 = new Pair<>(i,j);
                        Pair<Integer> p2 = new Pair<>(k,l);
                        if ((i!=k && i!=l) || (j!=k && j!=l)){
                            if (p1.hashCode()==p2.hashCode()) {
                                System.out.println("i=" + i + ", j=" + j + ", k=" + k + ", l=" + l);
                                System.out.println("p1 hash=" + p1.hashCode() + ", p2 hash=" + p2.hashCode());
                                failCount++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(failCount);
        assertEquals(true, failCount<50);
    }

    /**
     * Test function for the equals method.
     */
    @Test
    void testEquals() {
        Collection<Pair<Integer>> pairs = factory(random(100,300));
        Iterator<Pair<Integer>> it = pairs.iterator();
        while (it.hasNext()) {
            Pair<Integer> p = it.next();
            Iterator<Pair<Integer>> it2 = pairs.iterator();
            while (it2.hasNext()){
                Pair<Integer> p2= it2.next();
                int l1=p.getLeft(),r1=p.getRight(),l2=p2.getLeft(),r2=p2.getRight();
                if ((l1==l2 && r1==r2) || (l1==r2 && r1==l2)){
                    assertEquals(true, p.equals(p2) && p2.equals(p));
                }else {
                    assertEquals(false, p.equals(p2) && p2.equals(p));
                }
            }
        }
    }

    private Collection<Pair<Integer>> factory(int pairs){
        ArrayList<Pair<Integer>> arr = new ArrayList<>();
        for (int i=0;i<pairs;i++){
            arr.add(new Pair<>(random(0,pairs), random(0,pairs)));
        }
        return arr;
    }

    private int random(int min,int max){
        return rnd.nextInt(max-min)+min;
    }
}