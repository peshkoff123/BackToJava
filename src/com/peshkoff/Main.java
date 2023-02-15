package com.peshkoff;

import java.util.*;

interface A {
    int AAA = 100;

    static void getAAA() { System.out.println("getAAA()");}
    class A_A {
        void invouk_A_A() {
            System.out.println("invouk_A_A()");
        }
    }
}


interface B <T> {
  void myPrint( T arg);
}
class BClass<T> implements B<T> {
  //static T TFieldStat;                     ??????
  T TField;
  public void myPrint( T arg) {
      TField = arg;
      System.out.println("BClass.myPrint" + TField);
  }
}
class CClass implements B<Integer> {
    public void myPrint( Integer arg) {
        System.out.println( "CClass.myPrint" + arg);
    }
}

class AAA {
    public int intValue;
    static void ForEachTest() {;}
    //private
    protected void prt() { System.out.println("AAA.prt()"); }
    public void prt1() { prt();}
}
class AAA_Desc extends AAA {
    static final void ForEachTest() {;}
    public void prt() {  System.out.println("AAA_Desc.prt()"); }
    private int intVar = -1;
    class asd { asd() { intVar = 1; AAA_Desc.this.prt1(); prt();}    }
}
//_________________________________ ENUM
enum myEnum {
     //SUNDAY, MONDAY, TUESDAY, WEDNESDA
     val1( "nameVal1", 1), val2( "nameVal2", 2);

     static final public void staticMetTest() {
         System.out.println("myEnum.valueOf( \"val1\")="+myEnum.valueOf( "val1"));
     }
     String name;
     int ordinal;
     myEnum( String name, int ordinal) {
       this.name = name;
       this.ordinal = ordinal;
     }
    @Override
    public String toString() {
        return super.toString();
    }
}
// ________________________________ LambdaExpr-s
interface lambdaInterface {
    int lambdaMethod( String s);
}

// ________________________________
public class Main {

    private static String privateStaticString = "private static String ";
    private String privateString = "private String ";

    static <RetType, ArgType1, ArgType2> RetType severalGenericTypesTest(ArgType1 argType1, RetType argRetType,
                                                                         ArgType2[] ArgType2Arr) {
        System.out.println("severalGenericTypesTest(" + argType1 + ", " + argRetType + ")");
        for (ArgType2 b : ArgType2Arr)
            System.out.println(b);

        return argRetType;
    }

    static void genericTests() {
        System.out.println("genericTests()...");
        B b = new BClass();
        b.myPrint(123);
        b = new CClass();
        b.myPrint(new Integer(345));
    }

    static void ForEachTest() {
        byte[] byteArr = {1, 2, 3, 4, 5};
        int byteArrCount = 0;
        ArrayList arrList = new ArrayList();
        for (byte abyte : byteArr) {
            System.out.println("abyte=" + abyte + " ; byteArrCount=" + byteArrCount);
            byteArrCount++;
            arrList.add(abyte);
        }
        for (Object arrListItem : arrList)
            System.out.println("arrListItem=" + arrListItem);
    }

    public static void AAA_Test() {
        AAA_Desc aaa = new AAA_Desc();
        aaa.prt();
        aaa.prt1();

        // ClassCastException !
        AAA_Desc _aaa = (AAA_Desc)new AAA();
    }

    public static void enumTest() {
        myEnum.staticMetTest();
        String s = "myEnum[";
        for (myEnum m : myEnum.values())
            s = s + m.name() + "(" + m.ordinal() + ")" + ", ";
        s = s.substring(0, s.length() - 2);
        System.out.println(s + "]");

        myEnum myEnumVar = myEnum.val1;
    }

    public static void lambdaTest() {
        lambdaInterface lI = ( s) -> {
            System.out.println( "lambdaInterface.lambdaMethod("+s+");"+ privateStaticString);
            return 1;
        };
        lI.lambdaMethod( "1");

        lI =  ss -> {
            System.out.println("lambdaInterface.lambdaMethod("+ss+"); // Reassign method = creates new NamelessClass");
            return 1;
        };
        lI.lambdaMethod( "2");



        Main main = new Main();
        main._lambdaTest(  s -> { System.out.println("lambdaInterface.lambdaMethod("+s+") ");
                                  return 1;
                                } );
    }
    public void _lambdaTest( lambdaInterface lInt) {
        lInt.lambdaMethod( "5");

        lambdaInterface lI1 = new lambdaInterface() {
            String thisMyString = "thisMyString";
            public int lambdaMethod( String sss) {
                System.out.println( "lambdaInterface.lambdaMethod("+sss+"); // typical NamelessClass");
                System.out.println( "this.MyString="+this.thisMyString + "; Main.this.privateString="+Main.this.privateString);
                return 0;
            }
        };
        lI1.lambdaMethod( "3");

        String localString = "localString", s = null;
        lambdaInterface lI = (ss) -> {
            System.out.println("lambdaInterface.lambdaMethod(" + ss + ");" + this.privateString);
            System.out.println("localString=" + localString);
            return 1;
        };
        lI.lambdaMethod( "4");
    }

    public static void methodInputParameterTest(
            int _int,                                               // by value
            String _s, Double _d, final AAA _aaa, byte[] byteArr)   // by reference
    {  _int = 100; _s = "_s_100"; _d = new Double( 100); _aaa.intValue = 100; byteArr[ 0] = 100;   }


    public static void main(String[] args) {
        Q_And_A.hashTest();
        LeetCodeTasks.RunAllTasks();
        Q_And_A.privConstrTest();
/*

        int _int = 1; String _s = "_s"; Double _d = new Double( 1);
        AAA _aaa = new AAA(); byte[] byteArr = new byte[ 1];
        methodInputParameterTest( _int, _s, _d, _aaa, byteArr);

        Streams.runAllTests();
        GenericsTest.runAllTests();

        lambdaTest();

        enumTest();

        AAA_Test();

        ForEachTest();

        genericTests();
        Integer[] antArr = new Integer[] {1,2};
        System.out.println( "severalGenericTypesTest() = " + severalGenericTypesTest( "ArgType1", 127.123, antArr));
        //byte[] byteArr = { 1,2,3};
        System.out.println( "severalGenericTypesTest() = " + severalGenericTypesTest( "ArgType1", new byte[]{ 1,2,3}, new Byte[] {3,2,1}));

        A.getAAA();
        A.A_A a = new A.A_A();
        a.invouk_A_A();
	    abs:
        for( int i = 0; i < 5; i++) {
           System.out.println("FuckYou world");
           if( i == 2) continue abs;
       }
*/

        //BaseForm bf = new BaseForm();
    }
}
