package com.peshkoff;

import java.util.*;
import java.util.stream.Stream;

/* https://docs.oracle.com/javase/tutorial/java/generics/QandE/generics-questions.html
* */
public class GenericsTest {

static void runAllTests() {
        runTest1();
        runTest3();
        runTest8();
}

/* 1. Write a generic method to count the number of elements in a collection that have a
      specific property (for example, odd integers, prime numbers, palindromes).
*/
interface SpecificPropertyTest <T>{
    boolean test( T t);
}
static class OddIntegerTest implements SpecificPropertyTest<Integer> {
    public boolean test( Integer t) { if( t % 2 != 0) return true; return false;}
}
static void runTest1() {
    int b = 10%2!=0 ? 3 :5;
    List<Integer> l = Arrays.asList( 1,2,3,4,5,6,7,8,9,10);

    SpecificPropertyTest<Integer> specificPropertyTest = new SpecificPropertyTest<Integer>() {
        public boolean test( Integer t) { return t % 2 != 0;}
    };
    System.out.println( "CountSpecificProperty()="+CountSpecificProperty( l, specificPropertyTest));
    System.out.println( "CountSpecificProperty()="+CountSpecificProperty( l, new OddIntegerTest()));
    System.out.println( "CountSpecificProperty()="+CountSpecificProperty( l, t -> t%2 != 0 ? true:false));
}
static <T> int CountSpecificProperty(Collection<T> l, SpecificPropertyTest<T> t) {
        int count = 0;
        for( T i : l) {
            System.out.println(i);
            if( t.test( i)) count++;
        }
        return count;
}

/* 3.  Write a generic method to exchange the positions of two different elements in an array.*/
static void runTest3() {
    Integer[] intArr = Arrays.asList( 1,2,3,4,5).toArray( new Integer[ 5]);

    System.out.println( Arrays.asList( intArr));
    ExchangePositionsInArray( intArr);
    System.out.println( Arrays.asList( intArr));
}

static <T> void ExchangePositionsInArray( T[] tArr) {
    T t = tArr[ 0];
    tArr[ 0] = tArr[ 1];
    tArr[ 1] = t;
}

/* 8. Write a generic method to find the maximal element in the range [begin, end) of a list.*/
/*interface CompareToo <O> {
    boolean test( O obj1, O obj2);
}*/
static void runTest8() {
    System.out.println( "findMaxItemOfInterim()="+
            findMaxItemOfInterim( Arrays.asList( 1,2,3,4,5,6,7,8,9), 2, 5));
}
static <T extends Comparable> T findMaxItemOfInterim(List<T> l, int begin, int end) {
    T maxElem = l.get(begin);
    for (++begin; begin < end; ++begin)
        if (maxElem.compareTo(l.get(begin)) < 0)
            maxElem = l.get(begin);
    return maxElem;
}

}
