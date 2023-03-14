package com.peshkoff;
/**
 *
 * **/


import java.util.*;

public class Q_And_A {
    abstract class ABC {  }  // abstract Class without methods - Yes

    interface C { default void c() { int i = 6; System.out.println("default C.c()"+this.toString()); }    }

    interface A { default void a() { System.out.println("default A.a()"); }    }
    interface B { default void a() { System.out.println("default B.a()"); }    }
    static class AandB implements A, B {
        public void a() { System.out.println("default B.a()"); } //MUST be overridden !!!
    }

    static class privConstr {
        int a;
        private privConstr( int a) { super(); this.a = a;}
    }
    static class privConstr1 extends privConstr { private privConstr1() {    super( 1);}    }
    public static void privConstrTest() {
        privConstr privConstr_= new privConstr1();
    }

    public static void hashTest(){
      HashMap<Integer,String> map = new HashMap<>();
      map.put(1,"1_Val");
      map.put(2,"2_Val");

      Object o = map.clone();
      System.out.println("map.containsKey(1)=" + map.containsKey(1));
      System.out.println("map.containsKey(1)=" + map.containsValue( "1_Val"));
      String s = map.get( 1);
      System.out.println("map.get( 1)=" + s);
      Set keySet = map.keySet();
      Set<Integer> keySet1 = map.keySet();
      Set<Map.Entry<Integer,String>> entrySet = map.entrySet();
      Collection colValues = map.values();
      Collection<String> colValues1 = map.values();
      for( Map.Entry me : entrySet)
          System.out.println( me.getKey()+" : " + me.getValue());
    }


    }

