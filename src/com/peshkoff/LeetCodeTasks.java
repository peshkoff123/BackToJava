package com.peshkoff;

import java.util.*;
import java.util.function.Consumer;

public class LeetCodeTasks {
    public static void RunAllTasks() {
        LeetCode_Task2();
        LeetCode_Task14();
        LeetCode_Task20();
        TreePassLeftRight();
        TreePassTopBottom();
        LeetCode_Task100();
        LeetCode_Task217();
        LeetCode_Task771();
        LeetCode_Task1470();
        LeetCode_Task1315();
        LeetCode_Task1114();
        SQRT_();
        LeetCode_Task647();
        LeetCode_Task5();
        LeetCode_Task91();
        LeetCode_Task322();
        LeetCode_Task56();
    }

    //https://www.youtube.com/watch?v=W9iMTMeD9uY&list=PLlsMRoVt5sTPCbbIW2QZ-hRMW80lymEYR&index=2
    public static void LeetCode_Task2() {
        LinkedList<Integer> l1 = new LinkedList<>(), l2 = new LinkedList<>(), lRes = new LinkedList<>();
        l1.add( 2); l1.add( 4); l1.add( 3);
        l2.add( 5); l2.add( 6); l2.add( 4);

        ListIterator it = l1.listIterator( l1.size());
        String s1 = "", s2 = "";
        while( it.hasPrevious())  s1 = s1 + it.previous();
        it = l2.listIterator( l2.size());
        while( it.hasPrevious())  s2 = s2 + it.previous();

        int sum = Integer.parseInt( s1) + Integer.parseInt( s2);

        //StringBuilder sumStr = (new StringBuilder( (new Integer( sum)).toString())).reverse();
        String ss = String.valueOf( sum);
        for( int i=ss.length()-1; i>=0; i--)
            lRes.add( new Integer( ss.substring( i,i+1)));

        System.out.println( "Result="+ lRes);
    }

    //LongestCommonPrefix {"flower","flow","flight"} => "fl"
    public static void LeetCode_Task14() {
        String[] sArr = {"flower","flow","flight"};
        System.out.println( "LeetCode_Task14( {\"flower\",\"flow\",\"flight\"}) = "+_LeetCode_Task14( sArr));
    }
    private static String _LeetCode_Task14( String[] sArr) {
        if( sArr.length == 0) return "";
        if( sArr.length == 1) return sArr[ 0];
        String res = "";
        char[][] cArr = new char[ sArr.length][];

        for( int i=0; i < sArr.length; i++)
            cArr[ i] = sArr[ i].toCharArray();

        for( int i=0; i < cArr[ 0].length; i++) {
            for( int j = 1; j < sArr.length; j++)
                if( cArr[ 0][ i] != cArr[ j][ i]) return res;
            res = res + cArr[ 0][ i];
        }
        return res;
    }

    //ValidParentheses () {} [] s="(}","{([])}"
    public static void LeetCode_Task20() {
        LeetCode_Task20_PrintResult( null);
        LeetCode_Task20_PrintResult( "");
        LeetCode_Task20_PrintResult( "(}");
        LeetCode_Task20_PrintResult( "(){}({[]})");
        LeetCode_Task20_PrintResult( "(");
        LeetCode_Task20_PrintResult( "({");
        LeetCode_Task20_PrintResult( "({[}]");
        LeetCode_Task20_PrintResult( "(){}({[()[]]})");
        LeetCode_Task20_PrintResult( "(){}({[()[]]})[");
    }
    private static void LeetCode_Task20_PrintResult( String s) {
        System.out.println( "isValidParenthesis( \""+s+"\") = "+isValidParenthesis( s));
    }
    private static boolean isValidParenthesis( String s) {
        if( s == null || s.length() == 0) return false;
        char[] openArr = new char[ s.length()/2+1];
        int openCount = 0;
        for( int i=0; i < s.length(); i++) {
            switch( s.charAt( i)) {
                case '(':
                case '{':
                case '[': openArr[ openCount++] = s.charAt( i);
                    if( openCount*2 > s.length())
                        return false;
                    break;
                case ')': if( openArr[ openCount-1] == '(') { openCount--; break;}
                else return false;
                case '}': if( openArr[ openCount-1] == '{') { openCount--; break;}
                else return false;
                case ']': if( openArr[ openCount-1] == '[') { openCount--; break;}
                else return false;
                default:  return false;
            }
        }

        if( openCount == 0) return true;
        return false;
    }


    static class Node {
        int val;
        Node left, right;
        Node( int val) { this.val = val;}
        Node( int val, Node left, Node right) { this( val); this.left = left; this.right = right;}
        public String toString() { return Integer.toString( val);}
    }

    // SameBinaryTree
    public static void LeetCode_Task100() {
        Node a = new Node( 1, new Node( 2), new Node( 3, new Node( 4), null));
        Node b = new Node( 1, new Node( 2), new Node( 3, new Node( 4), null));
        System.out.println( "isSameBinaryTree( ) = "+_isSameBinaryTree( a, b));
    }
    public static boolean _isSameBinaryTree( Node a, Node b) {
        if( a == null && b == null) return true;
        if( a == null || b == null) return false;
        if( a.val != b.val) return false;
        return _isSameBinaryTree( a.left, b.left) && _isSameBinaryTree( a.right, b.right);
    }
    public static void TreePassLeftRight() {
        Node curr = new Node( 1, new Node( 2), new Node( 3, null, new Node( 4)));

        _TreePassLeftRight( curr, ( c)-> { System.out.println( c);} );
    }
    private static void _TreePassLeftRight(Node curr, Consumer payLoad) {
        LinkedList<Node> stack = new LinkedList<>();
        Node backFrom = null;

        while( curr != null) {
            if( backFrom == null) payLoad.accept( curr);

            if( curr.left != null && !(curr.left == backFrom || curr.right == backFrom)) {
                stack.push( curr);
                curr = curr.left;
                backFrom = null;
                continue;
            }
            if( curr.right != null && curr.right != backFrom) {
                stack.push( curr);
                curr = curr.right;
                backFrom = null;
                continue;
            }
            backFrom = curr;
            curr = stack.pollFirst();
        }
    }
    public static void TreePassTopBottom() {
        Node curr = new Node( 1, new Node( 2), new Node( 3, null, new Node( 4)));
        _TreePassTopBottom( curr, ( c)-> { System.out.println( c);} );
    }
    private static void _TreePassTopBottom( Node curr, Consumer payLoad) {
        if (curr == null) return;
        Node[] nodeArr = {curr};
        Node[] newNodeArr;
        int nodeArrCount = 1;

        do{ newNodeArr = new Node[ nodeArrCount * 2];
            nodeArrCount = 0;
            for( Node n : nodeArr) {
                if( n == null) break;
                payLoad.accept( n);
                if( n.left != null)
                    newNodeArr[ nodeArrCount++] = n.left;
                if( n.right != null)
                    newNodeArr[ nodeArrCount++] = n.right;
            }
            nodeArr = newNodeArr;
        } while( nodeArrCount > 0);

    }

    // ContainsDuplicate
    public static void LeetCode_Task217() {
        System.out.println( "isContainDuplicate( 1,2,3,4) = "+_isContainDuplicate( 1,2,3,4));
        System.out.println( "isContainDuplicate( 1,2,3,1) = "+_isContainDuplicate( 1,2,3,1));
    }
    private static boolean _isContainDuplicate( int... sour) {
        Set<Integer> s = new HashSet<>();
        for( int next : sour)
            if( !s.add( next)) return true;
        return false;
    }

    // JewelsAndStones
    public static void LeetCode_Task771() {
        String jewels = "aA"; String stones = "aAAbbb";
        System.out.println( "_CountJewels( "+jewels+", "+stones+") = "+_CountJewels( jewels, stones));
        jewels = "z"; stones = "ZZZ";
        System.out.println( "_CountJewels( "+jewels+", "+stones+") = "+_CountJewels( jewels, stones));
    }
    private static int _CountJewels( String jewels, String stones) {
        int count = 0;
        char[] cArr =  jewels.toCharArray();
        Arrays.sort( cArr);
        char[] sArr = stones.toCharArray();
        for( char c : sArr)
            if( Arrays.binarySearch( cArr, c) >= 0 )
                count++;

        return count;
    }

    // ShuffleArray
    public static void LeetCode_Task1470() {
        _ShuffleArray( "x1","x2", "y1","y2");
        _ShuffleArray( "x1","x2","x3", "y1","y2","y3");
        _ShuffleArray( "x1","x2","x3","x4", "y1","y2","y3","y4");
        _ShuffleArray( "x1","x2","x3","x4","x5", "y1","y2","y3","y4","y5");
    }
    private static void _ShuffleArray( String... sour) {
        System.out.print( "sourArr="+Arrays.toString(sour));

        String[] t = Arrays.copyOfRange( sour, 1, sour.length/2);
        for( int i=1, j=sour.length/2, k=0; k<t.length; k++, j++) {
            sour[ i++] = sour[ j];
            sour[ i++] = t[ k];
        }

        System.out.println( "; ShuffledArr="+Arrays.toString(sour));
    }

    // Sum of nodes with EvenGrandParent
    public static void LeetCode_Task1315() {
        //Node curr = new Node(1);
        Node curr = new Node(
                6,
                new Node( 7, new Node( 2, new Node( 9), null),
                        new Node( 7, new Node( 1), new Node( 4))),
                new Node( 8, new Node( 1),
                        new Node( 3, null, new Node( 5))));
        sum = 0;
        _EvenGrandParent_1( curr);
        System.out.println( "Sum of nodes with EvenGrandParent_1 ="+sum);

        sum = 0;
        nodePath = new LinkedList<>();
        _EvenGrandParent_2( curr);
        System.out.println( "Sum of nodes with EvenGrandParent_2 ="+sum);
    }
    private static int sum = 0;
    private static void _EvenGrandParent_1( Node curr) {
        if( curr == null) return;
        if( curr.val % 2 ==0) {
            if( curr.left != null  && curr.left.left != null)   sum += curr.left.left.val;
            if( curr.left != null  && curr.left.right != null)  sum += curr.left.right.val;
            if( curr.right != null && curr.right.left != null)  sum += curr.right.left.val;
            if( curr.right != null && curr.right.right != null) sum += curr.right.right.val;
        }
        _EvenGrandParent_1( curr.left);
        _EvenGrandParent_1( curr.right);
    }
    private static LinkedList<Node> nodePath = null;
    private static void _EvenGrandParent_2( Node curr) {
        if( curr == null) return;
        Node grandPa = null;
        if( nodePath.size() - 2 >= 0 && (grandPa = nodePath.get( nodePath.size() - 2)) != null)
            if( grandPa.val % 2 == 0)
                sum += curr.val;
        nodePath.add( curr);
        _EvenGrandParent_2( curr.left);
        _EvenGrandParent_2( curr.right);
        nodePath.removeLast();
    }

    // Print in order ( Concurrency)
    public static void LeetCode_Task1114() {
        Foo foo = new Foo();
        Random random = new Random();

        threadNumber = 20;
        myThread[] myThreadArr = new myThread[ threadNumber+1];
        String s = "";
        for( int i=0, n=0; i <= threadNumber; i++) {
            n = random.nextInt(threadNumber);
            nextRand:
            for (int j = 0; j < i; j++)
                    if (myThreadArr[j].myTurn == n) {
                        n = random.nextInt(threadNumber+1);
                        j = -1;
                        continue nextRand;
                    }
            myThreadArr[i] = new myThread(foo, n);
            s += n+", ";
            myThreadArr[i].start();
        }
        System.out.println( "Run threads:"+s);
    }
    private static int threadNumber = 3;
    private static volatile int turn = 0;
    private static volatile String res = "";
    private static class myThread extends Thread {
        Foo foo;
        int myTurn;
        public myThread( Foo foo, int myTurn) { super(); this.foo = foo; this.myTurn = myTurn;}
        public void run() {
          //System.out.println( "Run "+myTurn);
          while( turn != myTurn)
              try {
                  Thread.sleep(10);
              } catch( Exception ex) { ex.printStackTrace();}

          res += myTurn;
          turn++;

          //System.out.println( "Stop "+myTurn);
          if( myTurn == threadNumber)
              System.out.println( "res= "+res);
        }
    }
    private static class Foo {
        public void first()  { System.out.println( "first");}
        public void second() { System.out.println( "second");}
        public void fird()   { System.out.println( "fird");}
    }

    // SQRT
    public static void SQRT_() {
        int number = 5;
        System.out.println( "_SQRT( "+number+") = "+_SQRT( number)+"; Math.sqrt( "+number+") = "+ Math.sqrt( number));
        number = 25;
        System.out.println( "_SQRT( "+number+") = "+_SQRT( number)+"; Math.sqrt( "+number+") = "+ Math.sqrt( number));
        number = 0;
        System.out.println( "_SQRT( "+number+") = "+_SQRT( number)+"; Math.sqrt( "+number+") = "+ Math.sqrt( number));
        number = 3000;
        System.out.println( "_SQRT( "+number+") = "+_SQRT( number)+"; Math.sqrt( "+number+") = "+ Math.sqrt( number));
        number = 317;
        System.out.println( "_SQRT( "+number+") = "+_SQRT( number)+"; Math.sqrt( "+number+") = "+ Math.sqrt( number));
    }
    private static int _SQRT( int number) {
    int min = 0, max = (int)Math.floor( Math.sqrt( Integer.MAX_VALUE)),  med = 0;

    while( true) {
        med = (min + max) / 2;
        if (med == min || med == max)
            return min;
        if (number >= med * med)
            min = med;
        else max = med;
    }
    }

    // Find palindrome number
    //https://www.youtube.com/watch?v=3RR8jDwkgUY
    public static void LeetCode_Task647() {
        /*int a = 12345;          // find sum of digits
        int sum = 0;
        do{ sum += a % 10;
            a = a / 10;
          } while( a > 0);
        System.out.println( "sum = "+sum);*/

        String s = "abc";
        System.out.println( "palindromeNumber( "+s+") = "+palindromeNumber( s));
        s = "aab";
        System.out.println( "palindromeNumber( "+s+") = "+palindromeNumber( s));
        s = "aabb";
        System.out.println( "palindromeNumber( "+s+") = "+palindromeNumber( s));
        s = "aaa";
        System.out.println( "palindromeNumber( "+s+") = "+palindromeNumber( s));
        s = "aaaa";
        System.out.println( "palindromeNumber( "+s+") = "+palindromeNumber( s));
    }
    public static int palindromeNumber( String s) {
      char[] c = s.toCharArray();
      int res = 0;
      // for palindromes with even len
      for( int palLen=2; palLen <= c.length; palLen += 2) {
          int curLenPalCount = 0;
          for (int offs = 0; offs <= c.length - palLen; offs++)
              if (isPalindrome(c, offs, palLen))
                  curLenPalCount++;
          if( curLenPalCount == 0) // if there is no pal-m with len=2 then there is no pal-m with len=4,6,8..
              break;
          res += curLenPalCount;
      }
      // for palindromes with odd len
      for( int palLen=3; palLen <= c.length; palLen += 2) {
          int curLenPalCount = 0;
          for (int offs = 0; offs <= c.length - palLen; offs++)
              if (isPalindrome(c, offs, palLen))
                   curLenPalCount++;
          if( curLenPalCount == 0) // if there is no pal-m with len=2 then there is no pal-m with len=4,6,8..
              break;
          res += curLenPalCount;
      }
      return res + s.length();
    }
    public static boolean isPalindrome( char[] cArr, int offs, int len) {
        for( int i=0, j=offs+len-1; i < len/2; i++, j--)
           if( cArr[ offs+i] != cArr[ j])
               return false;
        return true;
    }
    // Find longest palindromic substring
    //https://www.youtube.com/watch?v=xV6eQ71r0mQ&t=0s
    public static void LeetCode_Task5() {
        String s = "abc";
        System.out.println( "longestPalindrome( "+s+") = "+longestPalindrome( s));
        s = "aba";
        System.out.println( "longestPalindrome( "+s+") = "+longestPalindrome( s));
        s = "aab";
        System.out.println( "longestPalindrome( "+s+") = "+longestPalindrome( s));
        s = "abaaa";
        System.out.println( "palindromeNumber( "+s+") = "+longestPalindrome( s));
        s = "adcfaaaa";
        System.out.println( "palindromeNumber( "+s+") = "+longestPalindrome( s));
    }
    public static String longestPalindrome( String s) {
        char[] c = s.toCharArray();

        for( int palLen=c.length; palLen > 1; palLen--)
           for( int offs = 0; offs <= c.length - palLen; offs++)
              if( isPalindrome( c, offs, palLen))
                  return s.substring( offs, offs+palLen);

        return s.substring( 0, 1);
    }

    // Decode ways
    //https://www.youtube.com/watch?v=pxBZASoThI4
    public static void LeetCode_Task91() {
        String s = "0";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));
        s = "1";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));
        s = "12";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));
        s = "10";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));
        s = "102";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));
        s = "11106";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));
        s = "011106";
        System.out.println( "decodeWays( "+s+") = "+decodeWays( s));

        //List<Number> a = new ArrayList<Integer>();
        //a.add( new Integer( 1));
    }
    public static int decodeWays( String s) {
        if( s == null || s.length() == 0) return 0;
        c = s.toCharArray();
        pos = 0;
        wayCounter = 0;
        nextSymbol();

        return wayCounter;
    }
    private static char[] c;
    private static int pos, wayCounter;

    private static final void nextSymbol() {
        if( pos == c.length) {
            wayCounter++;
            return;
        }

        if( c[ pos] < '1' || c[ pos] > '9') return;
        // decode symbol {c[ pos]}
        pos++;
        nextSymbol();
        pos--;
        if( pos+1 >= c.length) return;
        if( (c[ pos]=='1' || c[ pos]=='2') && (c[ pos+1]>='0' && c[ pos+1]<='9') ) {
            // decode symbol {c[ pos], c[ pos+1]}
            pos += 2;
            nextSymbol();
            pos -= 2;
        }
    }

    //https://www.youtube.com/watch?v=4n_jORRTDfs
    public static void LeetCode_Task322() {
        int[] coins = { 1, 2, 5};
        int amount = 1;
        System.out.println( "coinChange( "+Arrays.toString( coins)+", "+amount+") = "+
                             coinChange( coins, amount));
        amount = 11;
        System.out.println( "coinChange( "+Arrays.toString( coins)+", "+amount+") = "+
                coinChange( coins, amount));
        amount = 54;
        System.out.println( "coinChange( "+Arrays.toString( coins)+", "+amount+") = "+
                coinChange( coins, amount));
        coins = new int[]{ 1, 4, 5, 7};
        amount = 9;
        System.out.println( "coinChange( "+Arrays.toString( coins)+", "+amount+") = "+
                coinChange( coins, amount));
    }
    public static int coinChange( int[] coins, int amount) {
        if( coins == null || coins.length == 0) return 0;
        if( amount <= 0) return 0;
        for( int i=1; i <= amount/coins[ 0]; i++)
            if( findChange( coins, amount, coins.length-1, i))
                return i;
        return -1;
    }
    public static boolean findChange( int[] coins, int amount, int coinsInd, int coinLim) {
      if( coinsInd < 0) return false;
      if( amount / coins[ coinsInd] >= coinLim && amount % coins[ coinsInd] != 0) return false;
      if( amount / coins[ coinsInd] == coinLim && amount % coins[ coinsInd] == 0) return true;

      for( int i = amount/coins[ coinsInd]; i >= 0; i--)
      if( findChange( coins, amount - i*coins[ coinsInd], coinsInd-1, coinLim-i))
           return true;

      return false;
    }

    //https://www.youtube.com/watch?v=qRz1PmIl7ds
    public static void LeetCode_Task56() {
        int[][] intervals = { {1,3}, {2,6}, {8,10}, {15,18}};
        System.out.println( "mergeIntervals( "+intervalToStr( intervals)+") = "+
                intervalToStr( merge( intervals)));
        intervals = new int[][] { {1,3}, {2,7}, {4,7}, {8,15}, {15,18}};
        System.out.println( "mergeIntervals( "+intervalToStr( intervals)+") = "+
                intervalToStr( merge( intervals)));
        intervals = new int[][]{ {1,4},{4,5}};
        System.out.println( "mergeIntervals( "+intervalToStr( intervals)+") = "+
                intervalToStr( merge( intervals)));
    }
    public static String intervalToStr( int[][] intervals) {
        String res = "";
        for( int[] ints: intervals)
            res += Arrays.toString( ints)+", ";
        return res;
    }
    public static int[][] merge( int[][] intervals) {
        ArrayList<int[]> intervalList = new ArrayList<>( intervals.length);
        for( int[] ints: intervals)
           intervalList.add( ints);
        for( int i=0; i<intervalList.size(); i++) {
            int[] a = intervalList.get( i);
            for( int j=i+1; j<intervalList.size(); j++) {
                int[] res = mergeIntervals(a, intervalList.get(j));
                if( res != null) {
                    a = res;
                    intervalList.remove( j--);
                }
            }
        }
        int[][] resArr = new int[ intervalList.size()][];
        for( int i=0; i<intervalList.size(); i++)
            resArr[ i] = intervalList.get( i);

        return resArr;
    }
    public static int[] mergeIntervals( int[] a, int[] b) {
        if( a[0] > b[1] || a[1] < b[0]) return null;
        a[ 0] = Math.min( a[0], b[0]);
        a[ 1] = Math.max( a[1], b[1]);
        return a;
    }

        //https://www.youtube.com/watch?v=0VM4HjQXGe0
    private static int[] candidates;
    private static int[] candidates_K;
    private static int target;
    private static String res_Task39;
    public static void LeetCode_Task39() {
        candidates = new int[] { 2, 3, 6, 7};   // distinct !!!
        target = 7;
        System.out.println( "combinationSum( "+Arrays.toString( candidates)+", "+target+") = "+
                coinChange( candidates, target));

    }

    public static String combinationSum() {
        Arrays.sort( candidates);
        candidates_K = new int[ candidates.length];
        res_Task39 = "[ ";
        for( int i=0; i<candidates.length; i++)
            _combinationSum( 0, i);

        return res_Task39 + "]";
    }
    // [7,6,3,2]:7
    //  0 0 0 1
    public static void _combinationSum( int sum, int startIndex) {
        while( (sum += candidates[ startIndex]*candidates_K[ startIndex]) < target) {
            if( ++startIndex < candidates.length)
                _combinationSum( sum, startIndex);
            candidates_K[ startIndex]++;
        }
        if( sum > target) return;
        candidates_K[ startIndex]++;
        if( sum == target) {
            res_Task39 += Arrays.toString( candidates_K);
            return;
        }
        _combinationSum( sum, ++startIndex);
        _combinationSum( sum, startIndex);
    }

}
