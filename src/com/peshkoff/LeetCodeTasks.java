package com.peshkoff;

import com.sun.deploy.security.BadCertificateDialog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sun.awt.windows.WSystemTrayPeer;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeetCodeTasks {

    public static void RunAllTasks() {
        LeetCode_Task2();
        LeetCode_Task14();
        LeetCode_Task20();
        TreePassLeftRight();
        TreePassTopBottom();
        searchTreeToOrderedArr();
        FindSumInSearchTree_I();
        FindSumInSearchTree_II();
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
        LeetCode_Task39();
        LeetCode_Task39_2();
        LeetCode_Task79();
        LeetCode_Task347();
        LeetCode_Task122();
        LeetCode_Task309();
        LeetCode_Task153();
        LeetCode_Task33();
        LeetCode_Task424();
        LeetCode_Task3();
        LeetCode_Task200();
        LeetCode_Task59();
        LeetCode_Task48();
        LeetCode_Task6();
        LeetCode_Task7();
        LeetCode_Task12();
        LeetCode_Task13();
        HashTagSearchSort();
        LeetCode_Task15();
        LeetCode_Task16();
        LeetCode_Task17();
        LeetCode_Task19();

        LeetCode_Task21();
        LeetCode_Task22();
        LeetCode_Task23();
        LeetCode_Task24();
        LeetCode_Task26();

        LeetCode_Task27();
        LeetCode_Task28();
        LeetCode_Task29();
        LeetCode_Task32();
        LeetCode_Task34();

        LeetCode_Task35();
        LeetCode_Task36();
        LeetCode_Task37();
        
        //09.08.2023
        LeetCode_Task40();
        LeetCode_Task45();

        LeetCode_Task46();
        LeetCode_Task47();
        LeetCode_Task49();
        LeetCode_Task53();
        LeetCode_Task54();

        // missed:
        // 55. Jump Game

        //22.10.23
        PrintEvenOddInTwoThreads();
    }

    //https://www.youtube.com/watch?v=W9iMTMeD9uY&list=PLlsMRoVt5sTPCbbIW2QZ-hRMW80lymEYR&index=2
    public static void LeetCode_Task2() {
        LinkedList<Integer> l1 = new LinkedList<>(), l2 = new LinkedList<>(), lRes = new LinkedList<>();
        l1.add(2);
        l1.add(4);
        l1.add(3);
        l2.add(5);
        l2.add(6);
        l2.add(4);
        ListIterator it = l1.listIterator(l1.size());
        String s1 = "", s2 = "";
        while (it.hasPrevious()) s1 = s1 + it.previous();
        it = l2.listIterator(l2.size());
        while (it.hasPrevious()) s2 = s2 + it.previous();

        int sum = Integer.parseInt(s1) + Integer.parseInt(s2);

        //StringBuilder sumStr = (new StringBuilder( (new Integer( sum)).toString())).reverse();
        String ss = String.valueOf(sum);
        for (int i = ss.length() - 1; i >= 0; i--)
            lRes.add(new Integer(ss.substring(i, i + 1)));

        System.out.println("Result=" + lRes);
    }

    //LongestCommonPrefix {"flower","flow","flight"} => "fl"
    public static void LeetCode_Task14() {
        String[] sArr = {"flower", "flow", "flight"};
        System.out.println("LeetCode_Task14( {\"flower\",\"flow\",\"flight\"}) = " + _LeetCode_Task14(sArr));
    }

    private static String _LeetCode_Task14(String[] sArr) {
        if (sArr.length == 0) return "";
        if (sArr.length == 1) return sArr[0];
        String res = "";
        char[][] cArr = new char[sArr.length][];

        for (int i = 0; i < sArr.length; i++)
            cArr[i] = sArr[i].toCharArray();

        for (int i = 0; i < cArr[0].length; i++) {
            for (int j = 1; j < sArr.length; j++)
                if (cArr[0][i] != cArr[j][i]) return res;
            res = res + cArr[0][i];
        }
        return res;
    }

    //ValidParentheses () {} [] s="(}","{([])}"
    public static void LeetCode_Task20() {
        LeetCode_Task20_PrintResult(null);
        LeetCode_Task20_PrintResult("");
        LeetCode_Task20_PrintResult("(}");
        LeetCode_Task20_PrintResult("(){}({[]})");
        LeetCode_Task20_PrintResult("(");
        LeetCode_Task20_PrintResult("({");
        LeetCode_Task20_PrintResult("({[}]");
        LeetCode_Task20_PrintResult("(){}({[()[]]})");
        LeetCode_Task20_PrintResult("(){}({[()[]]})[");
    }

    private static void LeetCode_Task20_PrintResult(String s) {
        System.out.println("isValidParenthesis( \"" + s + "\") = " + isValidParenthesis(s));
    }

    private static boolean isValidParenthesis(String s) {
        if (s == null || s.length() == 0) return false;
        char[] openArr = new char[s.length() / 2 + 1];
        int openCount = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                case '{':
                case '[':
                    openArr[openCount++] = s.charAt(i);
                    if (openCount * 2 > s.length())
                        return false;
                    break;
                case ')':
                    if (openArr[openCount - 1] == '(') {
                        openCount--;
                        break;
                    } else return false;
                case '}':
                    if (openArr[openCount - 1] == '{') {
                        openCount--;
                        break;
                    } else return false;
                case ']':
                    if (openArr[openCount - 1] == '[') {
                        openCount--;
                        break;
                    } else return false;
                default:
                    return false;
            }
        }

        if (openCount == 0) return true;
        return false;
    }


    static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node left, Node right) {
            this(val);
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return Integer.toString(val);
        }
    }

    // SameBinaryTree
    public static void LeetCode_Task100() {
        Node a = new Node(1, new Node(2), new Node(3, new Node(4), null));
        Node b = new Node(1, new Node(2), new Node(3, new Node(4), null));
        System.out.println("isSameBinaryTree( ) = " + _isSameBinaryTree(a, b));
    }

    public static boolean _isSameBinaryTree(Node a, Node b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.val != b.val) return false;
        return _isSameBinaryTree(a.left, b.left) && _isSameBinaryTree(a.right, b.right);
    }

    public static void TreePassLeftRight() {
        Node curr = new Node(1, new Node(2), new Node(3, null, new Node(4)));

        _TreePassLeftRight(curr, (c) -> {
            System.out.println(c);
        });
    }

    private static void _TreePassLeftRight(Node curr, Consumer payLoad) {
        LinkedList<Node> stack = new LinkedList<>();
        Node backFrom = null;

        while (curr != null) {
            if (backFrom == null) payLoad.accept(curr);

            if (curr.left != null && !(curr.left == backFrom || curr.right == backFrom)) {
                stack.push(curr);
                curr = curr.left;
                backFrom = null;
                continue;
            }
            if (curr.right != null && curr.right != backFrom) {
                stack.push(curr);
                curr = curr.right;
                backFrom = null;
                continue;
            }
            backFrom = curr;
            curr = stack.pollFirst();
        }
    }

    public static void TreePassTopBottom() {
        Node curr = new Node(1, new Node(2), new Node(3, null, new Node(4)));
        _TreePassTopBottom(curr, (c) -> {
            System.out.println(c);
        });
    }

    private static void _TreePassTopBottom(Node curr, Consumer payLoad) {
        if (curr == null) return;
        Node[] nodeArr = {curr};
        Node[] newNodeArr;
        int nodeArrCount = 1;

        do {
            newNodeArr = new Node[nodeArrCount * 2];
            nodeArrCount = 0;
            for (Node n : nodeArr) {
                if (n == null) break;
                payLoad.accept(n);
                if (n.left != null)
                    newNodeArr[nodeArrCount++] = n.left;
                if (n.right != null)
                    newNodeArr[nodeArrCount++] = n.right;
            }
            nodeArr = newNodeArr;
        } while (nodeArrCount > 0);

    }

    //        4
    //     2     6
    //   1   3  5  7    => 1234567
    private static int[] _treeToArr = null;
    private static int _treeToArrCount = 0;

    public static void searchTreeToOrderedArr() {
        Node a = new Node(4, new Node(2, new Node(1), new Node(3)),
                new Node(6, new Node(5), new Node(7)));

        _treeToArr = new int[4];
        _treeToArrCount = 0;
        _searchTreeToOrderedArr(a);

        System.out.println("_searchTreeToOrderedArr( Node root) = ");
        Arrays.stream(_treeToArr).mapToObj(i -> String.valueOf(i) + ", ").forEach(System.out::print);
        System.out.print("\r\n");
    }

    private static void _searchTreeToOrderedArr(Node n) {
        if (n.left != null)
            _searchTreeToOrderedArr(n.left);

        if (_treeToArrCount >= _treeToArr.length)
            _treeToArr = Arrays.copyOf(_treeToArr, _treeToArr.length * 2);
        _treeToArr[_treeToArrCount++] = n.val;

        if (n.right != null)
            _searchTreeToOrderedArr(n.right);
    }

    // SearchTree -> OrderedList -> SearchInOrderedList
    public static void FindSumInSearchTree_I() {
        // Tree from searchTreeToOrderedArr()
        Node a = new Node(4, new Node(2, new Node(1), new Node(3)),
                new Node(6, new Node(5), new Node(7)));

        _treeToArr = new int[7];
        _treeToArrCount = 0;
        _searchTreeToOrderedArr(a);

        System.out.println("_searchTreeToOrderedArr( " + Arrays.toString(_treeToArr) + ", " + 12 + ") = " +
                Arrays.toString(_searchSumInOrderedArr(_treeToArr, 12)));

        _treeToArr = new int[]{1, 2, 4, 5, 6, 7, 9};
        System.out.println("_searchTreeToOrderedArr( " + Arrays.toString(_treeToArr) + ", " + 12 + ") = " +
                Arrays.toString(_searchSumInOrderedArr(_treeToArr, 12)));
        _treeToArr = new int[]{1, 2, 4, 5, 6, 7, 10};
        System.out.println("_searchTreeToOrderedArr( " + Arrays.toString(_treeToArr) + ", " + 13 + ") = " +
                Arrays.toString(_searchSumInOrderedArr(_treeToArr, 13)));

    }

    // 1 2 3 4 5 6 7; 12 => 7+5, 9 => 6+3,..
    private static int[] _searchSumInOrderedArr(int[] orderedArr, int sum) {
        int[] res = {0, 0};
        int l = 0, r = orderedArr.length - 1;
        while (l < r) {
            if (orderedArr[l] + orderedArr[r] == sum) {
                res = new int[]{orderedArr[l], orderedArr[r]};
                break;
            }
            if (orderedArr[l] + orderedArr[r] < sum)
                l++;
            if (orderedArr[l] + orderedArr[r] > sum)
                r--;
        }
        return res;
    }

    //        4
    //     2     6
    //   1   3  5  7    => 1234567
    // FindInSearchTree(Sum/2 <= n < Sum) + FindInSearchTree( m = Sum - n) -> n+m=Sum
    public static void FindSumInSearchTree_II() {
        Node a = new Node(4, new Node(2, new Node(1), new Node(3)),
                new Node(6, new Node(5), new Node(7)));
        int Sum = 7;
        int max = Sum;
        while (true) {
            max = findBiggestLessMax(a, max);
            if (max == Integer.MIN_VALUE) break;
            if (max < Sum / 2) break;
            if (findEqual(a, Sum - max)) {
                System.out.println("FindSumInSearchTree_II( " + Sum + ") = " + max + " + " + (Sum - max));
                break;
            }
        }
    }

    private static int findBiggestLessMax(Node n, int max) {
        if (n == null) return Integer.MIN_VALUE;
        if (n.val < max) return Integer.max(n.val, findBiggestLessMax(n.right, max));
        if (n.val >= max) return findBiggestLessMax(n.left, max);

        return Integer.MIN_VALUE;
    }

    private static boolean findEqual(Node n, int val) {
        if (n == null) return false;
        if (n.val == val) return true;
        if (n.val < val) return findEqual(n.right, val);
        return findEqual(n.left, val);
    }

    // ContainsDuplicate
    public static void LeetCode_Task217() {
        System.out.println("isContainDuplicate( 1,2,3,4) = " + _isContainDuplicate(1, 2, 3, 4));
        System.out.println("isContainDuplicate( 1,2,3,1) = " + _isContainDuplicate(1, 2, 3, 1));
    }

    private static boolean _isContainDuplicate(int... sour) {
        Set<Integer> s = new HashSet<>();
        for (int next : sour)
            if (!s.add(next)) return true;
        return false;
    }

    // JewelsAndStones
    public static void LeetCode_Task771() {
        String jewels = "aA";
        String stones = "aAAbbb";
        System.out.println("_CountJewels( " + jewels + ", " + stones + ") = " + _CountJewels(jewels, stones));
        jewels = "z";
        stones = "ZZZ";
        System.out.println("_CountJewels( " + jewels + ", " + stones + ") = " + _CountJewels(jewels, stones));
    }

    private static int _CountJewels(String jewels, String stones) {
        int count = 0;
        char[] cArr = jewels.toCharArray();
        Arrays.sort(cArr);
        char[] sArr = stones.toCharArray();
        for (char c : sArr)
            if (Arrays.binarySearch(cArr, c) >= 0)
                count++;

        return count;
    }

    // ShuffleArray
    public static void LeetCode_Task1470() {
        _ShuffleArray("x1", "x2", "y1", "y2");
        _ShuffleArray("x1", "x2", "x3", "y1", "y2", "y3");
        _ShuffleArray("x1", "x2", "x3", "x4", "y1", "y2", "y3", "y4");
        _ShuffleArray("x1", "x2", "x3", "x4", "x5", "y1", "y2", "y3", "y4", "y5");
    }

    private static void _ShuffleArray(String... sour) {
        System.out.print("sourArr=" + Arrays.toString(sour));

        String[] t = Arrays.copyOfRange(sour, 1, sour.length / 2);
        for (int i = 1, j = sour.length / 2, k = 0; k < t.length; k++, j++) {
            sour[i++] = sour[j];
            sour[i++] = t[k];
        }

        System.out.println("; ShuffledArr=" + Arrays.toString(sour));
    }

    // Sum of nodes with EvenGrandParent
    public static void LeetCode_Task1315() {
        //Node curr = new Node(1);
        Node curr = new Node(
                6,
                new Node(7, new Node(2, new Node(9), null),
                        new Node(7, new Node(1), new Node(4))),
                new Node(8, new Node(1),
                        new Node(3, null, new Node(5))));
        sum = 0;
        _EvenGrandParent_1(curr);
        System.out.println("Sum of nodes with EvenGrandParent_1 =" + sum);

        sum = 0;
        nodePath = new LinkedList<>();
        _EvenGrandParent_2(curr);
        System.out.println("Sum of nodes with EvenGrandParent_2 =" + sum);
    }

    private static int sum = 0;

    private static void _EvenGrandParent_1(Node curr) {
        if (curr == null) return;
        if (curr.val % 2 == 0) {
            if (curr.left != null && curr.left.left != null) sum += curr.left.left.val;
            if (curr.left != null && curr.left.right != null) sum += curr.left.right.val;
            if (curr.right != null && curr.right.left != null) sum += curr.right.left.val;
            if (curr.right != null && curr.right.right != null) sum += curr.right.right.val;
        }
        _EvenGrandParent_1(curr.left);
        _EvenGrandParent_1(curr.right);
    }

    private static LinkedList<Node> nodePath = null;

    private static void _EvenGrandParent_2(Node curr) {
        if (curr == null) return;
        Node grandPa = null;
        if (nodePath.size() - 2 >= 0 && (grandPa = nodePath.get(nodePath.size() - 2)) != null)
            if (grandPa.val % 2 == 0)
                sum += curr.val;
        nodePath.add(curr);
        _EvenGrandParent_2(curr.left);
        _EvenGrandParent_2(curr.right);
        nodePath.removeLast();
    }

    // Print in order ( Concurrency)
    public static void LeetCode_Task1114() {
        Foo foo = new Foo();
        Random random = new Random();

        threadNumber = 20;
        myThread[] myThreadArr = new myThread[threadNumber + 1];
        String s = "";
        for (int i = 0, n = 0; i <= threadNumber; i++) {
            n = random.nextInt(threadNumber);
            nextRand:
            for (int j = 0; j < i; j++)
                if (myThreadArr[j].myTurn == n) {
                    n = random.nextInt(threadNumber + 1);
                    j = -1;
                    continue nextRand;
                }
            myThreadArr[i] = new myThread(foo, n);
            s += n + ", ";
            myThreadArr[i].start();
        }
        System.out.println("Run threads:" + s);
    }

    private static int threadNumber = 3;
    private static volatile int turn = 0;
    private static volatile String res = "";

    private static class myThread extends Thread {
        Foo foo;
        int myTurn;

        public myThread(Foo foo, int myTurn) {
            super();
            this.foo = foo;
            this.myTurn = myTurn;
        }

        public void run() {
            //System.out.println( "Run "+myTurn);
            while (turn != myTurn)
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            res += myTurn;
            turn++;

            //System.out.println( "Stop "+myTurn);
            if (myTurn == threadNumber)
                System.out.println("res= " + res);
        }
    }

    private static class Foo {
        public void first() {
            System.out.println("first");
        }

        public void second() {
            System.out.println("second");
        }

        public void fird() {
            System.out.println("fird");
        }
    }

    // SQRT
    public static void SQRT_() {
        int number = 5;
        System.out.println("_SQRT( " + number + ") = " + _SQRT(number) + "; Math.sqrt( " + number + ") = " + Math.sqrt(number));
        number = 25;
        System.out.println("_SQRT( " + number + ") = " + _SQRT(number) + "; Math.sqrt( " + number + ") = " + Math.sqrt(number));
        number = 0;
        System.out.println("_SQRT( " + number + ") = " + _SQRT(number) + "; Math.sqrt( " + number + ") = " + Math.sqrt(number));
        number = 3000;
        System.out.println("_SQRT( " + number + ") = " + _SQRT(number) + "; Math.sqrt( " + number + ") = " + Math.sqrt(number));
        number = 317;
        System.out.println("_SQRT( " + number + ") = " + _SQRT(number) + "; Math.sqrt( " + number + ") = " + Math.sqrt(number));
    }

    private static int _SQRT(int number) {
        int min = 0, max = (int) Math.floor(Math.sqrt(Integer.MAX_VALUE)), med = 0;

        while (true) {
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
        System.out.println("palindromeNumber( " + s + ") = " + palindromeNumber(s));
        s = "aab";
        System.out.println("palindromeNumber( " + s + ") = " + palindromeNumber(s));
        s = "aabb";
        System.out.println("palindromeNumber( " + s + ") = " + palindromeNumber(s));
        s = "aaa";
        System.out.println("palindromeNumber( " + s + ") = " + palindromeNumber(s));
        s = "aaaa";
        System.out.println("palindromeNumber( " + s + ") = " + palindromeNumber(s));
    }

    public static int palindromeNumber(String s) {
        char[] c = s.toCharArray();
        int res = 0;
        // for palindromes with even len
        for (int palLen = 2; palLen <= c.length; palLen += 2) {
            int curLenPalCount = 0;
            for (int offs = 0; offs <= c.length - palLen; offs++)
                if (isPalindrome(c, offs, palLen))
                    curLenPalCount++;
            if (curLenPalCount == 0) // if there is no pal-m with len=2 then there is no pal-m with len=4,6,8..
                break;
            res += curLenPalCount;
        }
        // for palindromes with odd len
        for (int palLen = 3; palLen <= c.length; palLen += 2) {
            int curLenPalCount = 0;
            for (int offs = 0; offs <= c.length - palLen; offs++)
                if (isPalindrome(c, offs, palLen))
                    curLenPalCount++;
            if (curLenPalCount == 0) // if there is no pal-m with len=2 then there is no pal-m with len=4,6,8..
                break;
            res += curLenPalCount;
        }
        return res + s.length();
    }

    public static boolean isPalindrome(char[] cArr, int offs, int len) {
        for (int i = 0, j = offs + len - 1; i < len / 2; i++, j--)
            if (cArr[offs + i] != cArr[j])
                return false;
        return true;
    }

    // Find longest palindromic substring
    //https://www.youtube.com/watch?v=xV6eQ71r0mQ&t=0s
    public static void LeetCode_Task5() {
        String s = "abc";
        System.out.println("longestPalindrome( " + s + ") = " + longestPalindrome(s));
        s = "aba";
        System.out.println("longestPalindrome( " + s + ") = " + longestPalindrome(s));
        s = "aab";
        System.out.println("longestPalindrome( " + s + ") = " + longestPalindrome(s));
        s = "abaaa";
        System.out.println("palindromeNumber( " + s + ") = " + longestPalindrome(s));
        s = "adcfaaaa";
        System.out.println("palindromeNumber( " + s + ") = " + longestPalindrome(s));
    }

    public static String longestPalindrome(String s) {
        char[] c = s.toCharArray();

        for (int palLen = c.length; palLen > 1; palLen--)
            for (int offs = 0; offs <= c.length - palLen; offs++)
                if (isPalindrome(c, offs, palLen))
                    return s.substring(offs, offs + palLen);

        return s.substring(0, 1);
    }

    // Decode ways
    //https://www.youtube.com/watch?v=pxBZASoThI4
    public static void LeetCode_Task91() {
        String s = "0";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));
        s = "1";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));
        s = "12";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));
        s = "10";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));
        s = "102";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));
        s = "11106";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));
        s = "011106";
        System.out.println("decodeWays( " + s + ") = " + decodeWays(s));

        //List<Number> a = new ArrayList<Integer>();
        //a.add( new Integer( 1));
    }

    public static int decodeWays(String s) {
        if (s == null || s.length() == 0) return 0;
        c = s.toCharArray();
        pos = 0;
        wayCounter = 0;
        nextSymbol();

        return wayCounter;
    }

    private static char[] c;
    private static int pos, wayCounter;

    private static final void nextSymbol() {
        if (pos == c.length) {
            wayCounter++;
            return;
        }

        if (c[pos] < '1' || c[pos] > '9') return;
        // decode symbol {c[ pos]}
        pos++;
        nextSymbol();
        pos--;
        if (pos + 1 >= c.length) return;
        if ((c[pos] == '1' || c[pos] == '2') && (c[pos + 1] >= '0' && c[pos + 1] <= '9')) {
            // decode symbol {c[ pos], c[ pos+1]}
            pos += 2;
            nextSymbol();
            pos -= 2;
        }
    }

    //https://www.youtube.com/watch?v=4n_jORRTDfs
    public static void LeetCode_Task322() {
        int[] coins = {1, 2, 5};
        int amount = 1;
        System.out.println("coinChange( " + Arrays.toString(coins) + ", " + amount + ") = " +
                coinChange(coins, amount));
        amount = 11;
        System.out.println("coinChange( " + Arrays.toString(coins) + ", " + amount + ") = " +
                coinChange(coins, amount));
        amount = 54;
        System.out.println("coinChange( " + Arrays.toString(coins) + ", " + amount + ") = " +
                coinChange(coins, amount));
        coins = new int[]{1, 4, 5, 7};
        amount = 9;
        System.out.println("coinChange( " + Arrays.toString(coins) + ", " + amount + ") = " +
                coinChange(coins, amount));
    }

    public static int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) return 0;
        if (amount <= 0) return 0;
        for (int i = 1; i <= amount / coins[0]; i++)
            if (findChange(coins, amount, coins.length - 1, i))
                return i;
        return -1;
    }

    public static boolean findChange(int[] coins, int amount, int coinsInd, int coinLim) {
        if (coinsInd < 0) return false;
        if (amount / coins[coinsInd] >= coinLim && amount % coins[coinsInd] != 0) return false;
        if (amount / coins[coinsInd] == coinLim && amount % coins[coinsInd] == 0) return true;

        for (int i = amount / coins[coinsInd]; i >= 0; i--)
            if (findChange(coins, amount - i * coins[coinsInd], coinsInd - 1, coinLim - i))
                return true;

        return false;
    }

    //https://www.youtube.com/watch?v=qRz1PmIl7ds
    public static void LeetCode_Task56() {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("mergeIntervals( " + intervalToStr(intervals) + ") = " +
                intervalToStr(merge(intervals)));
        intervals = new int[][]{{1, 3}, {2, 7}, {4, 7}, {8, 15}, {15, 18}};
        System.out.println("mergeIntervals( " + intervalToStr(intervals) + ") = " +
                intervalToStr(merge(intervals)));
        intervals = new int[][]{{1, 4}, {4, 5}};
        System.out.println("mergeIntervals( " + intervalToStr(intervals) + ") = " +
                intervalToStr(merge(intervals)));
    }

    public static String intervalToStr(int[][] intervals) {
        String res = "";
        for (int[] ints : intervals)
            res += Arrays.toString(ints) + ", ";
        return res;
    }

    public static int[][] merge(int[][] intervals) {
        ArrayList<int[]> intervalList = new ArrayList<>(intervals.length);
        for (int[] ints : intervals)
            intervalList.add(ints);
        for (int i = 0; i < intervalList.size(); i++) {
            int[] a = intervalList.get(i);
            for (int j = i + 1; j < intervalList.size(); j++) {
                int[] res = mergeIntervals(a, intervalList.get(j));
                if (res != null) {
                    a = res;
                    intervalList.remove(j--);
                }
            }
        }
        int[][] resArr = new int[intervalList.size()][];
        for (int i = 0; i < intervalList.size(); i++)
            resArr[i] = intervalList.get(i);

        return resArr;
    }

    public static int[] mergeIntervals(int[] a, int[] b) {
        if (a[0] > b[1] || a[1] < b[0]) return null;
        a[0] = Math.min(a[0], b[0]);
        a[1] = Math.max(a[1], b[1]);
        return a;
    }

    //https://www.youtube.com/watch?v=0VM4HjQXGe0
    private static int[] candidates;
    private static int[] candidates_K;
    private static int target;
    private static String res_Task39;

    public static void LeetCode_Task39() {
        candidates = new int[]{2, 3, 6, 7};   // sorted and distinct !!!
        target = 7;
        System.out.println("combinationSum( " + Arrays.toString(candidates) + ", " + target + ") = " + combinationSum());

        candidates = new int[]{2, 3, 5};   // sorted and distinct !!!
        target = 8;
        System.out.println("combinationSum( " + Arrays.toString(candidates) + ", " + target + ") = " + combinationSum());

        candidates = new int[]{2};   // sorted and distinct !!!
        target = 1;
        System.out.println("combinationSum( " + Arrays.toString(candidates) + ", " + target + ") = " + combinationSum());
    }

    public static String combinationSum() {
        res_Task39 = "";
        candidates_K = new int[candidates.length];
        _combinationSum(candidates.length - 1);
        return "[ " + res_Task39 + "]";
    }

    public static void _combinationSum(int startIndex) {
        int sum = -1;
        do {
            if (startIndex - 1 >= 0) {
                _combinationSum(startIndex - 1);
                candidates_K[startIndex - 1] = 0;
            }
            candidates_K[startIndex]++;
        } while ((sum = calcSum()) < target);
        if (sum > target) return;
        res_Task39 += "[" + combinationSumStr() + "]";
    }

    private static int calcSum() {
        int res = 0;
        for (int i = 0; i < candidates.length; i++)
            res += candidates[i] * candidates_K[i];
        return res;
    }

    private static String combinationSumStr() {
        String res = "";
        for (int i = 0; i < candidates.length; i++)
            for (int j = 0; j < candidates_K[i]; j++)
                res += candidates[i] + ",";
        return res;
    }

    public static void LeetCode_Task39_2() {
        candidates = new int[]{2, 3, 6, 7};   // sorted and distinct !!!
        target = 7;
        System.out.println("combinationSum( " + Arrays.toString(candidates) + ", " + target + ") = " + combinationSum_2());
        candidates = new int[]{2, 3, 5};   // sorted and distinct !!!
        target = 8;
        System.out.println("combinationSum( " + Arrays.toString(candidates) + ", " + target + ") = " + combinationSum_2());

    }

    public static String combinationSum_2() {
        res_Task39 = "";
        ArrayList<Integer> candidatesList = (ArrayList<Integer>) Arrays.stream(candidates).boxed().collect(Collectors.toList());
        combinationSum_21(0, "[", candidatesList);
        return "[ " + res_Task39 + "]";
    }

    public static void combinationSum_21(int sum, String res, ArrayList<Integer> options) {
        ArrayList<Integer> l = new ArrayList<>();
        for (int i : options) {
            if (sum + i < target) l.add(i);
            if (sum + i == target) res_Task39 += res + i + "],";
        }
        for (int next : l)
            combinationSum_21(sum + next, res + next + ",", l);
    }

    // https://www.youtube.com/watch?v=a2UIsxR-8uU
    public static void LeetCode_Task79() {
        char[][] board = {{'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}};
        String word = "ABCCED";
        String s = "";
        for (int i = 0; i < board.length; i++)
            s += Arrays.toString(board[i]) + ", ";
        System.out.println("wordExist( [" + s + "], " + word + ") = " + wordExist(board, word));

        word = "ABA";
        s = "";
        for (int i = 0; i < board.length; i++)
            s += Arrays.toString(board[i]) + ", ";
        System.out.println("wordExist( [" + s + "], " + word + ") = " + wordExist(board, word));

        word = "AF";
        s = "";
        for (int i = 0; i < board.length; i++)
            s += Arrays.toString(board[i]) + ", ";
        System.out.println("wordExist( [" + s + "], " + word + ") = " + wordExist(board, word));

    }

    @AllArgsConstructor
    private static class CharPosition {
        public int x, y;
    }

    public static boolean wordExist(char[][] board, String word) {
        char wordFirstChar = word.charAt(0);
        for (int i = 0; i < board.length; i++)
            upHere:
                    for (int j = 0; j < board[i].length; j++)
                        if (board[i][j] == wordFirstChar) {
                            LinkedList<CharPosition> wordCharPositionList = new LinkedList<>();
                            CharPosition nextCharPos = new CharPosition(i, j);
                            wordCharPositionList.push(nextCharPos);
                            for (int k = 1; k < word.length(); k++) {
                                nextCharPos = nextChar(board, wordCharPositionList, word.charAt(k));
                                if (nextCharPos == null)
                                    continue upHere;
                                wordCharPositionList.push(nextCharPos);
                            }
                            return true;
                        }
        return false;
    }

    public static CharPosition nextChar(char[][] board, LinkedList<CharPosition> l, char nextChar) {
        CharPosition pos = l.peekFirst();
        if (pos.x - 1 >= 0 && !selfInterception(l, pos.x - 1, pos.y)
                && board[pos.x - 1][pos.y] == nextChar) return new CharPosition(pos.x - 1, pos.y);
        if (pos.x + 1 < board.length && !selfInterception(l, pos.x + 1, pos.y)
                && board[pos.x + 1][pos.y] == nextChar) return new CharPosition(pos.x + 1, pos.y);
        if (pos.y - 1 >= 0 && !selfInterception(l, pos.x, pos.y - 1)
                && board[pos.x][pos.y - 1] == nextChar) return new CharPosition(pos.x, pos.y - 1);
        if (pos.y + 1 < board[0].length && !selfInterception(l, pos.x, pos.y + 1)
                && board[pos.x][pos.y + 1] == nextChar) return new CharPosition(pos.x, pos.y + 1);
        return null;
    }

    public static boolean selfInterception(LinkedList<CharPosition> l, int x, int y) {
        for (CharPosition nextCharPos : l)
            if (nextCharPos.x == x && nextCharPos.y == y)
                return true;
        return false;
    }

    //
    public static void LeetCode_Task347() {
        int[] nums = new int[]{1, 1, 1, 2, 2, 5, 6, 7, 6, 6, 6};
        int K = 4;
        System.out.println("top_K_frequentElemens( " + Arrays.toString(nums) + ", " + K + ") = " + top_K_frequentElements(nums, K));

        nums = new int[]{5, 6, 7, 1, 2, 5, 6, 7, 6, 6, 6};
        K = 5;
        System.out.println("top_K_frequentElemens( " + Arrays.toString(nums) + ", " + K + ") = " + top_K_frequentElements(nums, K));

        nums = new int[]{5};
        K = 3;
        System.out.println("top_K_frequentElemens( " + Arrays.toString(nums) + ", " + K + ") = " + top_K_frequentElements(nums, K));
    }

    @AllArgsConstructor
    private static class myInt {
        private int value;

        public void inc() {
            value++;
        }

        public int hashCode() {
            return value;
        }

        public String toString() {
            return Integer.toString(value);
        }
    }

    static String top_K_frequentElements(int[] nums, int K) {
        Map<Integer, myInt> m = new HashMap<>(nums.length);
        for (int i : nums) {
            myInt freq = m.get(i);
            if (freq != null) freq.inc();
            else m.put(i, new myInt(1));
        }

        Map<myInt, Integer> m1 = new HashMap<>(m.size());
        m.forEach((Integer k, myInt v) -> m1.put(v, k));

        String res = "";
        Collection<Integer> vals = m1.values();
        Object[] valsArr = vals.toArray();
        for (int i = valsArr.length - 1, k = 0; i >= 0 && k < K; i--, k++)
            res += valsArr[i] + ",";

        return res;
    }

    // https://www.youtube.com/watch?v=ZTOJty8zpW0
    public static void LeetCode_Task122() {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("SellStockII( " + Arrays.toString(prices) + ") = " + SellStockII(prices));
        prices = new int[]{1, 2, 3, 4, 5};
        System.out.println("SellStockII( " + Arrays.toString(prices) + ") = " + SellStockII(prices));
        prices = new int[]{7, 5, 4, 4, 1};
        System.out.println("SellStockII( " + Arrays.toString(prices) + ") = " + SellStockII(prices));
    }

    public static int SellStockII(int[] prices) {
        int buy = prices[0], sell = prices[0], sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < sell) {
                sum += sell - buy;
                buy = prices[i];
                sell = prices[i];
            }
            if (prices[i] > sell)
                sell = prices[i];
        }
        sum += sell - buy;

        return sum;
    }

    // https://www.youtube.com/watch?v=F2Mb7tTbvuE
    public static void LeetCode_Task309() {
        int[] prices = new int[]{1, 2, 3, 0, 2};
        System.out.println("SellStockColldown( " + Arrays.toString(prices) + ") = " + SellStockColldown(prices));
        prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("SellStockColldown( " + Arrays.toString(prices) + ") = " + SellStockColldown(prices));
        prices = new int[]{1, 2, 3, 4, 5};
        System.out.println("SellStockColldown( " + Arrays.toString(prices) + ") = " + SellStockColldown(prices));
        prices = new int[]{7, 5, 4, 4, 1};
        System.out.println("SellStockColldown( " + Arrays.toString(prices) + ") = " + SellStockColldown(prices));
    }

    public static int SellStockColldown(int[] prices) {
        int buy = prices[0], sell = prices[0], sum = 0, cd = 2;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < sell) {
                sum += sell - buy;
                buy = prices[i];
                sell = prices[i];
                cd++;
            }
            if (prices[i] > sell) {
                sell = prices[i];
                if (cd == 1) {
                    int sum1 = prices[i] - prices[i - 3],
                            sum2 = prices[i - 2] - prices[i - 3],
                            sum3 = prices[i] - prices[i - 1];
                    if (sum1 >= sum2 && sum1 >= sum2)   // unite intervals
                        buy = prices[i - 2];
                    if (sum2 > sum1 && sum2 > sum3)    // stepOver first point of currInterval
                        buy = prices[i];
                    if (sum3 > sum1 && sum3 > sum2)    // loose last point of prevInterval
                        sum = sum - sum2;
                }
                cd = 0;
            }
        }
        sum += sell - buy;
        return sum;
    }

    // https://www.youtube.com/watch?v=VbEYaDXGgRo
    public static void LeetCode_Task153() {
        int[] nums = new int[]{3, 4, 5, 1, 2};
        System.out.println("minRotatedSortedUniqArray( " + Arrays.toString(nums) + ") = " + minRotatedSortedUniqArray(nums, 0, nums.length - 1));
        nums = new int[]{3, 4, 5, 0, 1, 2};
        System.out.println("minRotatedSortedUniqArray( " + Arrays.toString(nums) + ") = " + minRotatedSortedUniqArray(nums, 0, nums.length - 1));
        nums = new int[]{3, 4, 5, 6, 7, 8, 0, 1, 2};
        System.out.println("minRotatedSortedUniqArray( " + Arrays.toString(nums) + ") = " + minRotatedSortedUniqArray(nums, 0, nums.length - 1));
    }

    public static int minRotatedSortedUniqArray(int[] nums, int a, int b) {
        int i = (b + a) / 2;
        if (i == a)
            return Math.min(nums[i], nums[b]);
        if (nums[a] < nums[i])
            return minRotatedSortedUniqArray(nums, i, b);
        return minRotatedSortedUniqArray(nums, a, i);
    }

    // https://www.youtube.com/watch?v=dLjLvnj_f6s
    public static void LeetCode_Task33() {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        int target = 0;  //        Output: 4
        System.out.println("searchRotatedSortedUniqArray( " + Arrays.toString(nums) + ", " + target + ") = " +
                searchRotatedSortedUniqArray(nums, 0, nums.length - 1, target));
        target = 7;
        System.out.println("searchRotatedSortedUniqArray( " + Arrays.toString(nums) + ", " + target + ") = " +
                searchRotatedSortedUniqArray(nums, 0, nums.length - 1, target));
        target = 4;
        System.out.println("searchRotatedSortedUniqArray( " + Arrays.toString(nums) + ", " + target + ") = " +
                searchRotatedSortedUniqArray(nums, 0, nums.length - 1, target));
        target = 2;
        System.out.println("searchRotatedSortedUniqArray( " + Arrays.toString(nums) + ", " + target + ") = " +
                searchRotatedSortedUniqArray(nums, 0, nums.length - 1, target));

    }

    public static int searchRotatedSortedUniqArray(int[] nums, int a, int b, int target) {
        int i = (b + a) / 2;
        if (i == a) {
            if (nums[a] == target) return a;
            if (nums[b] == target) return b;
            return -1;
        }
        if (target <= nums[i]) {
            if (target < nums[a])
                return searchRotatedSortedUniqArray(nums, i, b, target);
            return searchRotatedSortedUniqArray(nums, a, i, target);
        }
        if (target <= nums[b])
            return searchRotatedSortedUniqArray(nums, i, b, target);
        return searchRotatedSortedUniqArray(nums, a, i, target);
    }

    //https://www.youtube.com/watch?v=26MPOmLZgcc
    public static void LeetCode_Task424() {
        String s = "ABAB";
        int numberOfChanges = 2;  //      Output: 4
        System.out.println("longestRepeatingString( " + s + ", " + numberOfChanges + ") = " +
                longestRepeatingString(s, numberOfChanges));
        s = "AABABA";
        numberOfChanges = 1;  //      Output: 4
        System.out.println("longestRepeatingString( " + s + ", " + numberOfChanges + ") = " +
                longestRepeatingString(s, numberOfChanges));
    }

    public static int longestRepeatingString(String s, int k) {
        int maxLen = -1, nextLen;
        for (int i = 0; i < s.length() - k; i++)
            if ((nextLen = _longestRepeatingString(s, k, i)) > maxLen)
                maxLen = nextLen;
        s = new StringBuffer(s).reverse().toString();
        for (int i = 0; i < s.length() - k; i++)
            if ((nextLen = _longestRepeatingString(s, k, i)) > maxLen)
                maxLen = nextLen;

        return maxLen;
    }

    private static int _longestRepeatingString(String s, int k, int begPos) {
        char c = s.charAt(begPos);
        int i;
        for (i = begPos + 1; i < s.length(); i++)
            if (c != s.charAt(i))
                if (k > 0) k--;
                else break;

        return i - begPos;
    }

    // https://www.youtube.com/watch?v=SdTr29rm3TE
    public static void LeetCode_Task3() {
        String s = "abcabcbb"; // "Output: 3"; s consists of English letters [a..z] !!!
        System.out.println("longestNoRepeatingString( " + s + ") = " + longestNoRepeatingString(s));
        s = "bbbbb"; //        Output: 1
        System.out.println("longestNoRepeatingString( " + s + ") = " + longestNoRepeatingString(s));
        s = "pwwkew"; //        Output: 3
        System.out.println("longestNoRepeatingString( " + s + ") = " + longestNoRepeatingString(s));
    }

    private static int charPos(char c) {
        return (int) c - (int) 'a';
    }

    public static int longestNoRepeatingString(String s) {
        int maxLen = 0;
        int begPos = 0, endPos = begPos + 1;
        int[] charPosArray = new int[charPos('z')];
        char c = s.charAt(begPos);
        charPosArray[charPos(c)] = begPos + 1;
        for (; endPos < s.length(); endPos++) {
            c = s.charAt(endPos);
            int repPos = charPosArray[charPos(c)];
            if (repPos > 0) {
                for (int i = begPos; i < repPos; i++) {
                    char cc = s.charAt(i);
                    charPosArray[charPos(cc)] = 0;
                }
                begPos = repPos;
            }
            charPosArray[charPos(c)] = endPos + 1;
            if (endPos - begPos > maxLen)
                maxLen = endPos - begPos;
        }

        return maxLen + 1;
    }

    //https://www.youtube.com/watch?v=iwkoqQQNLcg
    public static void LeetCode_Task200() {
        byte[][] land = {{1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0}};
        System.out.println("numberOfIslands( " + landToString(land) + ") = " + numberOfIslands(land));
        land = new byte[][]{{1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 1, 1}};
        System.out.println("numberOfIslands( " + landToString(land) + ") = " + numberOfIslands(land));
        land = new byte[][]{{1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {1, 1, 1, 1, 1}};
        System.out.println("numberOfIslands( " + landToString(land) + ") = " + numberOfIslands(land));

        land = new byte[][]{{1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1}};
        System.out.println("numberOfIslands( " + landToString(land) + ") = " + numberOfIslands(land));
    }

    private static String landToString(byte[][] land) {
        String res = "";
        for (int i = 0; i < land.length; i++)
            res += "\r\n" + Arrays.toString(land[i]);
        return res;
    }

    public static int numberOfIslands(byte[][] land) {
        List<byte[][]> islandList = new ArrayList<>();

        for (int i = 0; i < land.length; i++)
            for (int j = 0; j < land[0].length; j++) {
                if (land[i][j] == 0) continue;

                byte[][] island1 = islandByXY(islandList, i - 1, j), island2 = islandByXY(islandList, i, j - 1);
                if (island1 != null && island2 != null) {
                    if (island1 != island2) {
                        island1 = concatIsland(island1, island2);
                        islandList.remove(island2);
                    }
                } else if (island2 != null) island1 = island2;

                if (island1 == null) {
                    island1 = new byte[land.length][land[0].length];
                    islandList.add(island1);
                }
                island1[i][j] = 1;
            }

        return islandList.size();
    }

    private static byte[][] islandByXY(List<byte[][]> islandList, int i, int j) {
        if (i < 0 || j < 0)
            return null;
        for (byte[][] nextIsland : islandList)
            if (nextIsland[i][j] == 1)
                return nextIsland;
        return null;
    }

    private static byte[][] concatIsland(byte[][] island1, byte[][] island2) {
        for (int i = 0; i < island1.length; i++)
            for (int j = 0; j < island1[0].length; j++)
                island1[i][j] = (byte) (island1[i][j] | island2[i][j]);

        return island1;
    }


    //https://www.youtube.com/watch?v=ot7kdFsddTg
    public static void LeetCode_Task59() {
        System.out.println("generateMatrix : " + landToString(generateMatrix((byte) 3)));
        System.out.println("generateMatrix : " + landToString(generateMatrix((byte) 4)));
    }

    private static byte[][] generateMatrix(byte n) {
        class XY {
            public int x = 0, y = 0, dir = 1;

            public void changeDir() {
                dir++;
                if (dir == 5)
                    dir = 1;
            }

            public boolean isOutOfBounds(int n) {
                if (x < 0 || x >= n) return true;
                if (y < 0 || y >= n) return true;
                return false;
            }

            public void nextStep() {
                switch (dir) {
                    case 1:
                        x++;
                        break;
                    case 2:
                        y++;
                        break;
                    case 3:
                        x--;
                        break;
                    case 4:
                        y--;
                        break;
                }
            }

            public void backStep() {
                switch (dir) {
                    case 1:
                        x--;
                        break;
                    case 2:
                        y--;
                        break;
                    case 3:
                        x++;
                        break;
                    case 4:
                        y++;
                        break;
                }
            }
        }

        byte[][] res = new byte[n][n];
        byte val = 0;
        XY xy = new XY();
        res[xy.y][xy.x] = ++val;

        while (val < n * n) {
            xy.nextStep();
            if (xy.isOutOfBounds(n) || res[xy.y][xy.x] != 0) {
                xy.backStep();
                xy.changeDir();
            } else res[xy.y][xy.x] = ++val;
        }

        return res;
    }

    //
    public static void LeetCode_Task48() {
        byte[][] image = new byte[][]{{1, 2},
                {3, 4}};
        System.out.println("Source Image : " + landToString(image));
        System.out.println("Rotated Image : " + landToString(rotateImage(image)));

        image = new byte[][]{{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        System.out.println("Source Image : " + landToString(image));
        System.out.println("Rotated Image : " + landToString(rotateImage(image)));

        image = new byte[][]{{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        System.out.println("Source Image : " + landToString(image));
        System.out.println("Rotated Image : " + landToString(rotateImage(image)));

    }

    private static byte[][] rotateImage(byte[][] image) {
        int beg = 0, end = image[0].length - 1;

        do {
            for (int i = beg; i < end; i++) {
                byte t = image[beg][beg + i];
                image[beg][beg + i] = image[end - i][beg];
                image[end - i][beg] = image[end][end - i];
                image[end][end - i] = image[beg + i][end];
                image[beg + i][end] = t;
            }
            beg++;
            end--;
        } while (beg < end);

        return image;
    }

    public static void LeetCode_Task6() {
        String s = "PAYPALISHIRING";
        int rows = 4;
        System.out.println("convert( " + s + ", " + rows + ") = " + convert(s, rows));
        rows = 3;
        System.out.println("convert( " + s + ", " + rows + ") = " + convert(s, rows));
    }

    private static String convert(String s, int rowNum) {
        String res = null;
        String[] sArr = s.split("");
        StringBuffer[] sBufs = new StringBuffer[rowNum];
        for (int i = 0; i < rowNum; i++)
            sBufs[i] = new StringBuffer();

        int j = 0;
        sBufs[0].append(sArr[j++]);
        next:
        for (; j < sArr.length; ) {
            for (int i = 1; i < rowNum; i++) {
                if (j >= sArr.length) break next;
                sBufs[i].append(sArr[j++]);
            }
            for (int i = rowNum - 2; i >= 0; i--) {
                if (j >= sArr.length) break;
                sBufs[i].append(sArr[j++]);
            }
        }

        for (j = 1; j < sBufs.length; j++)
            sBufs[0].append(sBufs[j]);

        return sBufs[0].toString();
    }

    public static void LeetCode_Task7() {
        int rows = -123;
        System.out.println("reverse( " + rows + ") = " + reverse(rows));
        rows = 123;
        System.out.println("reverse( " + rows + ") = " + reverse(rows));
        rows = 1200;
        System.out.println("reverse( " + rows + ") = " + reverse(rows));

    }

    private static int reverse(int x) {
        int res = 0, _x = x;
        while (_x != 0) {
            res = res * 10 + (_x % 10);
            _x = _x / 10;
        }
        return res;
    }

    /*
    I             1
    V             5
    X             10
    L             50
    C             100
    D             500
    M             1000*/
    public static void LeetCode_Task12() {
        int rows = 12;
        System.out.println("intToRoman( " + rows + ") = " + intToRoman(rows));
        rows = 27;   // "XXVII"
        System.out.println("intToRoman( " + rows + ") = " + intToRoman(rows));
        rows = 58;   // "LVIII"
        System.out.println("intToRoman( " + rows + ") = " + intToRoman(rows));
        rows = 1994; // "MCMXCIV"
        System.out.println("intToRoman( " + rows + ") = " + intToRoman(rows));
    }

    private static String intToRoman(int x) {
        String res = oneDigitToRoman(x % 10, "I", "V", "X");
        x = x / 10;
        res = oneDigitToRoman(x % 10, "X", "L", "C") + res;
        x = x / 10;
        res = oneDigitToRoman(x % 10, "C", "D", "M") + res;
        x = x / 10;
        res = oneDigitToRoman(x % 10, "M", "", "") + res;
        return res;
    }

    private static String oneDigitToRoman(int x, String item, String itemFive, String itemTen) {
        if (4 == x) return item + itemFive;
        if (5 == x) return itemFive;
        if (9 == x) return item + itemTen;
        String res = "";
        if (6 <= x && x <= 8) {
            res = itemFive;
            x = x - 5;
        }
        for (int i = 0; i < x; i++)
            res = res + item;
        return res;
    }

    public static void LeetCode_Task13() {
        Map<String, Integer> romeInts = new HashMap<>();
        romeInts.put("I", 1);
        romeInts.put("IV", 4);
        romeInts.put("V", 5);
        romeInts.put("IX", 9);
        romeInts.put("X", 10);
        romeInts.put("XL", 40);
        romeInts.put("L", 50);
        romeInts.put("XC", 90);
        romeInts.put("C", 100);
        romeInts.put("CD", 400);
        romeInts.put("D", 500);
        romeInts.put("CM", 900);
        romeInts.put("M", 1000);
        String rom = "III";
        System.out.println("intToRoman( " + rom + ") = " + RomanToInt(rom, romeInts));
        rom = "";
        System.out.println("intToRoman( " + rom + ") = " + RomanToInt(rom, romeInts));
        rom = "XXVII";
        System.out.println("intToRoman( " + rom + ") = " + RomanToInt(rom, romeInts));
        rom = "LVIII";
        System.out.println("intToRoman( " + rom + ") = " + RomanToInt(rom, romeInts));
        rom = "MCMXCIV";
        System.out.println("intToRoman( " + rom + ") = " + RomanToInt(rom, romeInts));
    }

    private static int RomanToInt(String r, Map<String, Integer> romeInts) {
        int offs = 0;
        int res = 0;
        while (true) {
            if (offs + 2 <= r.length()) {
                String s = r.substring(offs, offs + 2);
                if (romeInts.containsKey(s)) {
                    offs += 2;
                    res += romeInts.get(s);
                    continue;
                }
            }
            if (offs + 1 > r.length()) break;
          /*String s = r.substring(offs, offs+1);
          offs += 1;
          res += romeInts.get(s);*/
            res += romeInts.get(r.substring(offs++, offs));
        }
        return res;
    }

    //https://www.youtube.com/watch?v=160QH3Gi56Y
    public static void HashTagSearchSort() {
        List<String> sour = Arrays.asList("#Java sldfjsldk dfgds sdfsd #C++",
                "sldfjsldk dfgds #Java sldfjsldk dfgds sdfsd #Delphi #JavaScript",
                "#Java sldfjsldk dfgds sdfsd #JavaScript sldfjsldk dfgds",
                "#JavaScript");
        hashTagSearchSort(sour);
    }

    private static void hashTagSearchSort(List<String> sour) {
        HashMap<String, Integer> tagMap = new HashMap<>();
        sour.forEach(nextStr -> {
            Stream.of(nextStr.split(" ")).filter(nextWord -> nextWord.startsWith("#"))
                    .forEach(nextTag -> {
                        tagMap.put(nextTag, tagMap.getOrDefault(nextTag, 0) + 1);
                    });
        });

        @AllArgsConstructor
        @ToString
        class item implements Comparable<item> {
            private int freq;
            public String tag;

            public int compareTo(item i) {
                if (freq > i.freq) return 1;
                if (freq < i.freq) return -1;
                return 0;
            }
        }

        ArrayList<item> itemList = new ArrayList<>();
        tagMap.forEach((k, v) -> itemList.add(new item(v, k)));
        Collections.sort(itemList);
        List<String> res = itemList.stream().map(item -> item.tag).collect(Collectors.toList());
        System.out.println("res=" + res);
        res = itemList.stream()
                .sorted((i1, i2) -> {
                    if (i1.freq > i2.freq) return -1;
                    if (i1.freq < i2.freq) return 1;
                    return 0;
                })
                .map(i -> i.tag).collect(Collectors.toList());
        System.out.println("res=" + res);

        Set<Map.Entry<String, Integer>> entrySet = tagMap.entrySet();
        List<String> res1 = entrySet.stream()
                .sorted((e1, e2) -> {
                    if (e1.getValue() > e2.getValue()) return -1;
                    if (e1.getValue() < e2.getValue()) return 1;
                    return 0;
                })
                .map(e -> e.getKey())
                .collect(Collectors.toList());
        System.out.println("res1=" + res1);
    }

    //15. 3Sum
    public static void LeetCode_Task15() {
        //Input: nums = [-1,0,1,2,-1,-4]
        //Output: [[-1,-1,2],[-1,0,1]]
        int[] sourArr = {-1, 0, 1, 2, -1, -4};
        System.out.println("threeSum( " + Arrays.toString(sourArr) + ") = " + threeSum(sourArr));
        //Input: nums = [0,1,1]
        //Output: []
        sourArr = new int[]{0, 1, 1};
        System.out.println("threeSum( " + Arrays.toString(sourArr) + ") = " + threeSum(sourArr));
        //Input: nums = [0,0,0]
        //Output: [[0,0,0]]
        sourArr = new int[]{0, 0, 0};
        System.out.println("threeSum( " + Arrays.toString(sourArr) + ") = " + threeSum(sourArr));

    }

    private static List<List<Integer>> threeSum(int[] sour) {
        Arrays.sort(sour); // -4,-1,-1,0,1,2,

        List<List<Integer>> res = new ArrayList<>();
        for (int i = sour.length - 1; i > 1; i--) {
            int l = 0, r = i - 1;
            while (l < r) {
                if (sour[l] + sour[r] + sour[i] > 0) {
                    r--;
                    continue;
                }
                if (sour[l] + sour[r] + sour[i] < 0) {
                    l++;
                    continue;
                }
                res.add(Arrays.asList(sour[l], sour[r], sour[i]));
                break;
            }
        }
        return res;
    }

    //16. 3Sum Closest
    public static void LeetCode_Task16() {
        //Input: nums = [-1,2,1,-4], target = 1
        //Output: 2
        int[] sourArr = {-1, 2, 1, -4};
        int target = 1;
        System.out.println("threeSumClosest( " + Arrays.toString(sourArr) + ", " + target + ") = " + threeSumClosest(sourArr, target));
        //Input: nums = [0,0,0], target = 1
        //Output: 0
        sourArr = new int[]{0, 0, 0};
        target = 1;
        System.out.println("threeSumClosest( " + Arrays.toString(sourArr) + ", " + target + ") = " + threeSumClosest(sourArr, target));
    }

    private static int threeSumClosest(int[] sour, int target) {
        Arrays.sort(sour); // -4,-1,1,2,

        int res = 0, d = Integer.MAX_VALUE;
        for (int i = 2; i < sour.length; i++) {
            int l = 0, r = i - 1;
            while (l < r) {
                int _d = Math.abs((sour[l] + sour[r] + sour[i]) - target);
                if (_d < d) {
                    d = _d;
                    res = sour[l] + sour[r] + sour[i];
                }

                if (sour[l] + sour[r] + sour[i] > target) {
                    r--;
                    continue;
                }
                if (sour[l] + sour[r] + sour[i] < target) {
                    l++;
                    continue;
                }
                return sour[l] + sour[r] + sour[i];
            }
        }

        return res;
    }

    //17. Letter Combinations of a Phone Number
    public static void LeetCode_Task17() {
        //Input: digits = "23"
        //Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
        char[][] phoneMap = {{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};
        String digits = "2";
        List<String> l = new ArrayList<>();
        letterCombinations(digits, 0, "", phoneMap, l);
        System.out.println("letterCombinations( " + digits + ") = " + l);
        //Input: digits = "23"
        //Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
        l.clear();
        digits = "23";
        letterCombinations(digits, 0, "", phoneMap, l);
        System.out.println("letterCombinations( " + digits + ") = " + l);
    }

    private static void letterCombinations(String digits, int ind, String pref, char[][] phoneMap, List<String> l) {
        for (char c : phoneMap[digits.charAt(ind) - '2']) {
            String s = pref + String.valueOf(c);
            if (ind == digits.length() - 1)
                l.add(s);
            else letterCombinations(digits, ind + 1, s, phoneMap, l);
        }
    }

    //19. Remove Nth Node From End of List
    @AllArgsConstructor
    private static class ListNode {
        int val;
        ListNode next;

        public String toString() {
            return Integer.toString(val);
        }
    }

    public static void LeetCode_Task19() {
        ListNode root = null;
        for (int i = 5; i > 0; i--)
            root = new ListNode(i, root);
        //Input: head = [1,2,3,4,5], n = 2
        //Output: [1,2,3,5]
        int N = 1;
        System.out.println("removeNthFromEnd( [" + ListNode_toString(root) + "], " + N + ")=" +
                ListNode_toString(removeNthFromEnd(root, N)));
    }
    private static String ListNode_toString(ListNode root) {
        String res = "";
        while (root != null) {
            res += root.toString() + ",";
            root = root.next;
        }
        return res;
    }
    private static ListNode removeNthFromEnd(ListNode root, int N) {
        ArrayList<ListNode> nodeList = new ArrayList<>();
        ListNode r = root;
        while (r != null) {
            nodeList.add(r);
            r = r.next;
        }
        int prev = nodeList.size() - N;
        if (prev == 0)
            return root.next;
        nodeList.get(prev - 1).next = nodeList.get(prev).next;
        return root;
    }

    public static void LeetCode_Task21() {
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(4, null)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4, null)));
        System.out.println("mergeTwoLists( " + ListNode_toString(l1) + ", " + ListNode_toString(l2) + ")=" +
                ListNode_toString(mergeTwoLists(l1, l2)));
        //Input: list1 = [], list2 = [0]
        //Output: [0]
        l1 = null;
        l2 = new ListNode(0, null);
        System.out.println("mergeTwoLists( " + ListNode_toString(l1) + ", " + ListNode_toString(l2) + ")=" +
                ListNode_toString(mergeTwoLists(l1, l2)));
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode res = null;
        if (l1.val < l2.val) {
            res = l1;
            l1 = l1.next;
        } else {
            res = l2;
            l2 = l2.next;
        }
        ListNode n = res;
        while (l1 != null && l2 != null)
            if (l1.val < l2.val) {
                n.next = l1;
                n = n.next;
                l1 = l1.next;
            } else {
                n.next = l2;
                n = n.next;
                l2 = l2.next;
            }
        if (l1 != null) n.next = l1;
        if (l2 != null) n.next = l2;

        return res;
    }

    public static void LeetCode_Task22() {
        int n = 0;
        System.out.println("mergeTwoLists( "+n+") = "+generateParenthesis( n));
        n = 1;
        System.out.println("mergeTwoLists( "+n+") = "+generateParenthesis( n));
        n = 2;
        System.out.println("mergeTwoLists( "+n+") = "+generateParenthesis( n));
        n = 3;
        System.out.println("mergeTwoLists( "+n+") = "+generateParenthesis( n));
        n = 4;
        System.out.println("mergeTwoLists( "+n+") = "+generateParenthesis( n));
    }
    private static List<String> generateParenthesis( int n) {
      List<String> res = new ArrayList<>();
      if( n < 1) return res;
      if( n == 1) {
          res.add("()");
          return res;
      }
      res.add("()()");
      res.add("(())");
      /*for( int i=3; i<=n; i++) {  // No streams analog - works fine
          List<String> _res = new ArrayList<>( res.size()*3);
          res.stream().flatMap( s-> Stream.of( "()"+s, s+"()", "("+s+")")).collect(Collectors.toList())
          res.forEach( s -> {
              String _s1 = "()"+s, _s2 = s+"()";
              _res.add( _s1);
              if( !_s1.equals( _s2))
                  _res.add( _s2);
              _res.add( "("+s+")");
          });
          res = _res;
      }*/
      for( int i=3; i<=n; i++)
         res = res.stream().flatMap( s-> Stream.of( "()"+s, s+"()", "("+s+")")).distinct().collect( Collectors.toList());

      return res;
    }

    public static void LeetCode_Task23() {
        //Input: lists = [[1,4,5],[1,3,4],[2,6]]
        //Output: [1,1,2,3,4,4,5,6]
        ListNode[] lArr = { new ListNode(1, new ListNode(4, new ListNode(5, null))),
                            new ListNode(1, new ListNode(3, new ListNode(4, null))),
                            new ListNode(2, new ListNode(6, null))                     };
        System.out.println( "mergeSortedLists( "+ListArray_toString( lArr)+")="+ListNode_toString( mergeSortedLists( lArr)));
    }
    private static String ListArray_toString( ListNode[] lArr) {
        return Arrays.stream( lArr).map( l -> ListNode_toString( l)+", ").reduce( "", ( r, n) -> r+n);
    }
    private static ListNode mergeSortedLists( ListNode[] lArr) {
        ListNode res = nextMinListNode( lArr);
        if( res == null) return null;

        ListNode rr = res, r = null;
        while( (r=nextMinListNode( lArr)) != null ) {
            rr.next = r;
            rr = r;
        }

        return res;
    }
    private static ListNode nextMinListNode( ListNode[] lArr) {
        int min = Integer.MAX_VALUE, minInd = -1;
        for( int i=0; i< lArr.length; i++) {
            if( lArr[ i] == null) continue;
            if( lArr[ i].val < min) {
                min = lArr[ i].val;
                minInd = i;
            }
        }
        if( minInd < 0) return null;
        ListNode res = lArr[ minInd];
        lArr[ minInd] = lArr[ minInd].next;
        return res;
    }

    public static void LeetCode_Task24() {
        //Input: head = [1,2,3,4]
        //Output: [2,1,4,3]
        ListNode l = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, null))));
        System.out.println( "swapPairs( "+ListNode_toString( l)+")="+ListNode_toString( swapPairs( l)));

        l = new ListNode(1, new ListNode(4, new ListNode(5, null)));
        System.out.println( "swapPairs( "+ListNode_toString( l)+")="+ListNode_toString( swapPairs( l)));
    }
    private static ListNode swapPairs( ListNode l) {
        if( l == null) return null;
        if( l.next == null) return l;
        ListNode a = null, b = l, c = b.next;
        ListNode res = l.next;
        while( b != null && b.next != null) {
            c = b.next;
            if( a != null)
                a.next = c;
            b.next = c.next;
            c.next = b;

            a = b;
            b = a.next;
        }
        return res;
    }
    public static void LeetCode_Task26() {
        //Input: nums = [0,0,1,1,1,2,2,3,3,4]
        //Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        System.out.println( "removeDuplicates( "+Arrays.toString( nums)+")="+removeDuplicates( nums)+"; => "+Arrays.toString( nums));

        nums = new int[]{0,0,1,1,1,2,2,3,3,3};
        System.out.println( "removeDuplicates( "+Arrays.toString( nums)+")="+removeDuplicates( nums)+"; => "+Arrays.toString( nums));
        //Input: nums = [1,1,2]
        //Output: 2, nums = [1,2,_]
        nums = new int[]{1,1,2};
        System.out.println( "removeDuplicates( "+Arrays.toString( nums)+")="+removeDuplicates( nums)+"; => "+Arrays.toString( nums));
    }
    private static int removeDuplicates( int[] sour) {
       if( sour == null || sour.length < 2) return 0;
       int res = 0;
       int srcPos = 0, destPos = 0;
       int[] dst = new int[ sour.length];
       for( int i=1; i<sour.length; i++) {
           if( sour[i] != sour[i-1])  continue;
           res++;
           int l = i-srcPos;
           if( l > 0) {
               System.arraycopy(sour, srcPos, dst, destPos, l);
               destPos += l;
           }
           srcPos = i+1;
       }
       int l = sour.length - srcPos;
       if( l > 0) {
           System.arraycopy(sour, srcPos, dst, destPos, l);
           destPos += l;
       }
       System.arraycopy(dst, 0, sour, 0, destPos);

       return res;
    }

    //27. Remove Element
    public static void LeetCode_Task27() {
        //Input: nums = [3,2,2,3], val = 3
        //Output: 2, nums = [2,2,_,_]
        int[] nums = {3,3,2,2,3,4};
        int elem = 3;
        System.out.println( "removeElement( "+Arrays.toString( nums)+", "+elem+")="+removeElement( nums, elem)+"; => "+Arrays.toString( nums));
        nums = new int[]{};
        elem = 3;
        System.out.println( "removeElement( "+Arrays.toString( nums)+", "+elem+")="+removeElement( nums, elem)+"; => "+Arrays.toString( nums));
        nums = new int[]{3};
        elem = 3;
        System.out.println( "removeElement( "+Arrays.toString( nums)+", "+elem+")="+removeElement( nums, elem)+"; => "+Arrays.toString( nums));
        //Input: nums = [0,1,2,2,3,0,4,2], val = 2
        //Output: 5, nums = [0,1,4,0,3,_,_,_]
        nums = new int[]{0,1,2,2,3,0,4,2};
        elem = 2;
        System.out.println( "removeElement( "+Arrays.toString( nums)+", "+elem+")="+removeElement( nums, elem)+"; => "+Arrays.toString( nums));
    }
    private static int removeElement( int[] sour, int elem) {
        if( sour == null || sour.length < 1) return 0;
        int srcPos = -1, destPos = -1;
        for( int i=0; i<sour.length; i++) {
            if( sour[i] != elem) continue;

            if( destPos == -1)
                destPos = i;
            int l = i-srcPos;
            if( l > 0 && srcPos > -1) {
                System.arraycopy( sour, srcPos, sour, destPos, l);
                destPos += l;
            }
            srcPos = i+1;
        }
        int l = sour.length-srcPos;
        if( l > 0) {
            System.arraycopy(sour, srcPos, sour, destPos, l);
            destPos += l;
        }
        return destPos;
    }

    //28. Find the Index of the First Occurrence in a String
    public static void LeetCode_Task28() {
        //Input: haystack = "sadbutsad", needle = "sad"
        //Output: 0
        String sour = "sadbutsad", sub = "sad";
        System.out.println( "indexOf( "+sour+", "+sub+")="+indexOf( sour, sub));
        sour = "qweswadbutsade"; sub = "sade";
        System.out.println( "indexOf( "+sour+", "+sub+")="+indexOf( sour, sub));
        //Input: haystack = "leetcode", needle = "leeto"
        //Output: -1
        sour = "leetcode"; sub = "leeto";
        System.out.println( "indexOf( "+sour+", "+sub+")="+indexOf( sour, sub));
    }
    private static int indexOf( String sour, String sub) {
        if( sour == null || sub == null) return -1;
        if( sour.length() == 0 || sub.length() == 0) return -1;
        char[] s = sour.toCharArray(), d = sub.toCharArray();
/*        for( int i=0, j=0; i<s.length; i++)  // works fine
          if( s[ i] == d[ j]) {
                 j++;
                 if( j == d.length) return i-j+1;
          } else j = 0;
*/
        for( int i=0; i<s.length; i++) {
            if( s[ i] != d[ 0]) continue;

            int beg = 1, end = d.length-1;
            if( (end-beg)%2 == 0)
                beg = 0;
            boolean suc = true;
            while( beg < end) {
                if (s[i + beg] == d[beg] && s[i + end] == d[end]) {
                         beg++; end--;
                } else { suc = false; break;}
            }
            if( suc) return i;
        };

        return -1;
    }

    //29. Divide Two Integers
    public static void LeetCode_Task29() {
        //Input: dividend = 7, divisor = -3
        //Output: -2
        int dividend = 7, divisor = -3;
        System.out.println( "divide( "+dividend+", "+divisor+")="+divide( dividend, divisor));
        dividend = 10; divisor = -3;
        System.out.println( "divide( "+dividend+", "+divisor+")="+divide( dividend, divisor));
        dividend = 27; divisor = 7;
        System.out.println( "divide( "+dividend+", "+divisor+")="+divide( dividend, divisor));
    }

    private static int divide( int dividend, int divisor) {
        int a = dividend >>> 31, b = divisor >>>31;//a = dividend & (1<<31), b = divisor & (1<<31);
        if( a == 1)
            dividend = ~dividend +1;
        if( b == 1)
            divisor = ~divisor +1;

        int res = 0;
        int rem = dividend;
        while( (rem -= divisor) >= 0)
            res++;

        int sign = a^b;
        if( sign == 0) return res;
        return ~(res-1) | (sign<<31);
    }

    //32. Longest Valid Parentheses
    public static void LeetCode_Task32() {
        //Input: s = "(()"
        //Output: 2
        String s = "(()";
        System.out.println( "longestValidParentheses( "+s+")="+longestValidParentheses( s));
        //Input: s = ")()())"
        //Output: 4
        s = ")()())";
        System.out.println( "longestValidParentheses( "+s+")="+longestValidParentheses( s));
        s = ")((())()()))";
        System.out.println( "longestValidParentheses( "+s+")="+longestValidParentheses( s));
    }
    private static int longestValidParentheses( String s) {
        if( s == null || s.length() < 2) return 0;
        int l = s.length();
        if( l % 2 != 0) l--;
        for( int i=l; i>=2; i -= 2)
            for( int j=0; j<=s.length()-i; j++) {
                 int count = 0;
                 for( int k=j; k<j+i; k++) {
                     char c = s.charAt( k);
                     if( c == '(')
                          count++;
                     else count--;
                 }
                 if( count == 0) return i;
            }
        return -1;
    }

    //34. Find First and Last Position of Element in Sorted Array
    public static void LeetCode_Task34() {
        //Input: nums = [5,7,7,8,8,10], target = 8
        //Output: [3,4]
        int[] nums = {5,7,7,8,8,8,8,10};
        int target = 8;
        int[] res = { -1, -1};
        findFirstPosition( nums, target, 0, nums.length-1, res);
        findLastPosition( nums, target, 0, nums.length-1, res);
        System.out.println( "First and Last Position in Sorted Array:"+Arrays.toString( nums)+"; target="+target+
                            "; res="+Arrays.toString( res));
        res = new int[]{ -1, -1};
        target = 6;
        findFirstPosition( nums, target, 0, nums.length-1, res);
        findLastPosition( nums, target, 0, nums.length-1, res);
        System.out.println( "First and Last Position in Sorted Array:"+Arrays.toString( nums)+"; target="+target+
                "; res="+Arrays.toString( res));
    }
    private static void findFirstPosition( int[] nums, int target, int lPos, int rPos, int[] res) {
       if( nums == null || nums.length == 0) return;
       if( lPos == rPos) {
           if( nums[lPos] == target) res[0] = lPos;
           return;
       }
       if( lPos+1 == rPos) {
           if (nums[rPos] == target) res[0] = rPos;
           if (nums[lPos] == target) res[0] = lPos;
           return;
       }
       int m = (lPos+rPos)/2;
       if( nums[m] > target) {
           findFirstPosition( nums, target, lPos, m-1, res);
           return;
       }
       if( nums[m] < target) {
           findFirstPosition( nums, target, m+1, rPos, res);
           return;
       }
       res[0] = m;
       findFirstPosition( nums, target, lPos, m-1, res);
    }
    private static void findLastPosition( int[] nums, int target, int lPos, int rPos, int[] res) {
        if( lPos == rPos) {
            if( nums[lPos] == target) res[1] = lPos;
            return;
        }
        if( lPos+1 == rPos) {
            if (nums[lPos] == target) res[1] = lPos;
            if (nums[rPos] == target) res[1] = rPos;
            return;
        }
        int m = (lPos+rPos)/2;
        if( nums[m] > target) {
            findLastPosition( nums, target, lPos, m-1, res);
            return;
        }
        if( nums[m] < target) {
            findLastPosition( nums, target, m+1, rPos, res);
            return;
        }
        res[1] = m;
        findLastPosition( nums, target, m+1, rPos, res);
    }

    //35. Search Insert Position
    public static void LeetCode_Task35() {
        //Input: nums = [1,3,5,6], target = 5
        //Output: 2
        int[] nums = {1,3,5,6};
        int target = 5;
        System.out.println( "findInsertPosition( "+Arrays.toString(nums)+", "+target+")="+findInsertPosition( nums, target, 0, nums.length-1));
        target = 4;
        System.out.println( "findInsertPosition( "+Arrays.toString(nums)+", "+target+")="+findInsertPosition( nums, target, 0, nums.length-1));
        target = 6;
        System.out.println( "findInsertPosition( "+Arrays.toString(nums)+", "+target+")="+findInsertPosition( nums, target, 0, nums.length-1));
        //Input: nums = [1,3,5,6], target = 2
        //Output: 1
        nums = new int[]{1,3,5,6};
        target = 2;
        System.out.println( "findInsertPosition( "+Arrays.toString(nums)+", "+target+")="+findInsertPosition( nums, target, 0, nums.length-1));
    }
    private static int findInsertPosition( int[] nums, int target, int lPos, int rPos) {
        if( lPos == rPos) {
            if( nums[lPos] < target) return lPos+1;
            return lPos;
        }
        if( lPos+1 == rPos) {
            if (nums[lPos] >= target) return lPos;
            if (nums[rPos] <= target) return rPos+1;
            return rPos;
        }
        int m = (lPos+rPos)/2;
        if( nums[m] > target)
            return findInsertPosition( nums, target, lPos, m-1);
        if( nums[m] < target)
            return findInsertPosition( nums, target, m+1, rPos);
        return m;
    }

    //36. Valid Sudoku
    private static String[][] board =            //Output: true
                    {{"5","3",".",".","7",".",".",".","."}
                    ,{"6",".",".","1","9","5",".",".","."}
                    ,{".","9","8",".",".",".",".","6","."}
                    ,{"8",".",".",".","6",".",".",".","3"}
                    ,{"4",".",".","8",".","3",".",".","1"}
                    ,{"7",".",".",".","2",".",".",".","6"}
                    ,{".","6",".",".",".",".","2","8","."}
                    ,{".",".",".","4","1","9",".",".","5"}
                    ,{".",".",".",".","8",".",".","7","9"}};
    public static void LeetCode_Task36() {
        //Arrays.stream( board).forEach( sArr-> {System.out.println( Arrays.toString( sArr)); });
        Arrays.stream( board).map( Arrays::toString).forEach( System.out::println);
        System.out.println( "isValidSudoku() : "+isValidSudoku( board));
    }
    private static boolean isValidSudoku( String[][] board){
       if( !isValidSudokuLines( board, false)) return false;
       if( !isValidSudokuLines( board, true)) return false;

        for ( int i=0; i<9; i+=3) {
            for (int j = 0; j<9; j+=3) {
                boolean[] bArr = new boolean[10];
                for ( int ii=i; ii<i+3; ii++) {
                    for (int jj = j; jj < j + 3; jj++) {
                        String s = board[ii][jj];
                        if (".".equals(s)) continue;
                        int v = Integer.parseInt(s);
                        if (bArr[v]) return false;
                        bArr[v] = true;
                    }
                }
            }
        }
       return true;
    }
    private static boolean isValidSudokuLines( String[][] board, boolean isCol){
        for ( int i=0; i<9; i++) {
            boolean[] bArr = new boolean[ 10];
            for (int j = 0; j < 9; j++) {
                String s = board[i][j];
                if( isCol) s = board[j][i];
                if (".".equals(s)) continue;
                int v = Integer.parseInt(s);
                if (bArr[v]) return false;
                bArr[v] = true;
            }
        }
        return true;
    }

    //37. Sudoku Solver
    public static void LeetCode_Task37() {
/*        System.out.println( " "+isRigntValue( 4, 5, 3));
        System.out.println( " "+isRigntValue( 3, 3, 2));
        System.out.println( " "+isRigntValue( 1, 1, 5));
        System.out.println( " "+isRigntValue( 2, 0, 3));
*/
        Arrays.stream( board).map( Arrays::toString).forEach( System.out::println);
        if( solveSudoku())
             System.out.println("solveSudoku() : ok");
        else System.out.println("solveSudoku() : impossible");
        Arrays.stream(board).map(Arrays::toString).forEach(System.out::println);

        System.out.println( "isValidSudoku() : "+isValidSudoku( board));
    }
    /*              {{"5","3",".",".","7",".",".",".","."}
                    ,{"6",".",".","1","9","5",".",".","."}
                    ,{".","9","8",".",".",".",".","6","."}
                    ,{"8",".",".",".","6",".",".",".","3"}
                    ,{"4",".",".","8",".","3",".",".","1"}
                    ,{"7",".",".",".","2",".",".",".","6"}
                    ,{".","6",".",".",".",".","2","8","."}
                    ,{".",".",".","4","1","9",".",".","5"}
                    ,{".",".",".",".","8",".",".","7","9"}};*/
    private static boolean solveSudoku() {
        for ( int i=0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                // find next pos to change - nearest "."
                if( !".".equals(board[i][j])) continue;
                // check variants
                for( int val=1; val<10; val++)
                if( isRigntValue( i, j, val)) {
                      board[i][j] = Integer.toString(val);
                      if( solveSudoku()) return true;
                }
                board[i][j] = ".";
                return false;
            }
        }
        return true;
    }
    private static boolean isRigntValue( int posX, int posY, int val) {
        for( int j = 0; j < 9; j++) {
            String s = board[posX][j];
            if( ".".equals(s)) continue;
            if( Integer.parseInt(s) == val)
                return false;
        }
        for( int j = 0; j < 9; j++) {
            String s = board[j][posY];
            if( ".".equals(s)) continue;
            if( Integer.parseInt(s) == val)
                return false;
        }
        int beg_i = 3*((int)posX/3), beg_j = 3*((int)posY/3);
        for ( int ii=beg_i; ii<beg_i+3; ii++) {
            for (int jj = beg_j; jj < beg_j + 3; jj++) {
                String s = board[ii][jj];
                if( ".".equals(s)) continue;
                if( Integer.parseInt(s) == val)
                    return false;
            }
        }
        return true;
    }

    //40. Combination Sum II
    public static void LeetCode_Task40() {
        // Input: candidates = [10,1,2,7,6,1,5], target = 8
        // Output: [ [1,1,6],[1,2,5],[1,7],[2,6]]
        int[] candidates = {1,2,6,1,5};;//{10,1,2,7,6,1,5};
        int target = 8;
        Arrays.sort( candidates);
        List<List<Integer>> resList = new ArrayList<>();
        combinationSum2( candidates, candidates.length-1, resList, new ArrayList<Integer>(), target);
        System.out.println( "combinationSum2( "+ Arrays.toString( candidates)+", "+ target+") = "+resList);
        //Input: candidates = [2,5,2,1,2], target = 5
        //Output: [[1,2,2],[5]]
        resList.clear();
        candidates = new int[]{2,5,2,1,2};
        target = 5;
        Arrays.sort( candidates);
        combinationSum2( candidates, candidates.length-1, resList, new ArrayList<Integer>(), target);
        System.out.println( "combinationSum2( "+ Arrays.toString( candidates)+", "+ target+") = "+resList);
    }
    public static void combinationSum2( int[] cand, int startInd, List<List<Integer>> resList, List<Integer> res, int target) {
       for( int i=startInd, lastCand=Integer.MAX_VALUE; i>=0; i--) {
          if( cand[i] == lastCand) continue;
          lastCand = cand[i];
          if( cand[i] > target) continue;
          res.add( cand[i]);
          if( cand[i] == target)
               resList.add( new ArrayList<Integer>( res));
          else combinationSum2( cand, i-1, resList, res, target-cand[ i]);
          res.remove( res.size()-1);
       }
    }

    // 45. Jump Game II
    private static int minJumps = Integer.MAX_VALUE;
    public static void LeetCode_Task45() {
        // Input: nums = [2,3,1,1,4]
        //Output: 2
        int[] nums = { 2,3,0,1,4};
        minJumps = Integer.MAX_VALUE;
        jumpII( nums, 0, 1);
        System.out.println( "jumpII( "+ Arrays.toString( nums)+") = "+minJumps);
    }
    public static void jumpII( int[] nums, int ind, int jumps) {
      if( ind + nums[ ind] >= nums.length-1) {
          if (jumps < minJumps) minJumps = jumps;
          return;
      }
      for( int i=1; i<=nums[ ind]; i++)
          jumpII( nums, i+ind, jumps+1);
    }

    //46. Permutations
    public static void LeetCode_Task46() {
        //Input: nums = [1,2,3]
        //Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
        List<List<Integer>> resList = new ArrayList<>();
        int[] nums = {1,2,3};
        permute( resList, new LinkedHashSet<Integer>(), nums);
        System.out.println( "permutations( "+Arrays.toString( nums)+") = "+resList);
        //Input: nums = [0,1]
        //Output: [[0,1],[1,0]]
        resList.clear();
        nums = new int[] {0,1};
        permute( resList, new LinkedHashSet<Integer>(), nums);
        System.out.println( "permutations( "+Arrays.toString( nums)+") = "+resList);
    }
    public static void permute( List<List<Integer>> resList, LinkedHashSet<Integer> nextComb, int[] nums) {
        if( nextComb.size() == nums.length) {
            resList.add(new ArrayList<>(nextComb));
            return;
        }

        for( int i=0; i < nums.length; i++) {
            if( nextComb.contains( nums[i])) continue;
            Integer in = new Integer( nums[i]);
            nextComb.add( in);
            permute( resList, nextComb, nums);
            nextComb.remove( in);
        }
    }

    //47. Permutations
    public static void LeetCode_Task47() {
        List<List<Integer>> resList = new ArrayList<>();
        int[] nums = {1,2,3};
        permuteII( resList, new ArrayList<>(nums.length), new HashSet<Integer>(nums.length), nums, new boolean[ nums.length]);
        System.out.println( "permutationsII( "+Arrays.toString( nums)+") = "+resList);
        //Input: nums = [1,1,2]
        //Output: [ [1,1,2],[1,2,1], [2,1,1]]
        resList.clear();
        nums = new int[] {1,1,2};
        permuteII( resList, new ArrayList<>(nums.length), new HashSet<Integer>(nums.length), nums, new boolean[ nums.length]);
        System.out.println( "permutationsII( "+Arrays.toString( nums)+") = "+resList);
    }
    public static void permuteII( List<List<Integer>> resList, List<Integer> nextComb, Set<Integer> uniqNums,
                                  int[] nums, boolean[] numsUsed) {
        if( nextComb.size() == nums.length) {
            if( !resList.contains( nextComb))
                resList.add( new ArrayList<>(nextComb));
            return;
        }

        for( int i=0; i < nums.length; i++) {
            if( numsUsed[ i]) continue;
            if( nextComb.size() == 0) {
                if( uniqNums.contains( nums[i])) continue;
                uniqNums.add( nums[i]);
            }
            nextComb.add( nums[i]);
            numsUsed[ i] = true;
            permuteII( resList, nextComb, uniqNums, nums, numsUsed);
            nextComb.remove( nextComb.size()-1);
            numsUsed[ i] = false;
        }
    }

    //49. Group Anagrams
    public static void LeetCode_Task49() {
        //Input: strs = ["eat","tea","tan","ate","nat","bat"]
        //Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
        String[] sArr = {"eat","tea","tan","ate","nat","bat"};
        System.out.println( "groupAnagrams( "+Arrays.toString(sArr)+")="+groupAnagrams( sArr));
        //Input: strs = [""]
        //Output: [[""]]
        sArr = new String[]{""};
        System.out.println( "groupAnagrams( "+Arrays.toString(sArr)+")="+groupAnagrams( sArr));
        //Input: strs = ["a"]
        //Output: [["a"]]
        sArr = new String[]{"a"};
        System.out.println( "groupAnagrams( "+Arrays.toString(sArr)+")="+groupAnagrams( sArr));
    }
    public static List<List<String>> groupAnagrams( String[] sArr) {
        HashMap<HashSet<Character>, List<String>> anMap = new HashMap<>();
        for( String s : sArr) {
            HashSet<Character> hs = new HashSet<>();
            char[] cArr = s.toCharArray();
            for( char c : cArr)
                hs.add( c);
            anMap.computeIfAbsent( hs, k->new ArrayList<String>()).add(s);
            /*List<String> l = anMap.get( hs); // analog - works
            if( l == null) {
                l = new ArrayList<String>();
                anMap.put( hs, l);
            }
            l.add( s);*/
        }
        return new ArrayList( anMap.values());
    }

    //53. Maximum Subarray
    public static void LeetCode_Task53() {
        //Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
        //Output: 6
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println( "maxSubArray( "+ Arrays.toString( nums)+")="+maxSubArray( nums));
        System.out.println( "maxSubArray2( "+ Arrays.toString( nums)+")="+maxSubArray2( nums, 0, nums.length-1)+" WRONG!");
        //Input: nums = [5,4,-1,7,8]
        //Output: 23
        nums = new int[]{5,4,-1,7,8};
        System.out.println( "maxSubArray( "+ Arrays.toString( nums)+")="+maxSubArray( nums));
        System.out.println( "maxSubArray2( "+ Arrays.toString( nums)+")="+maxSubArray2( nums, 0, nums.length-1)+" WRONG!");
    }
    public static int maxSubArray( int[] nums) {
        int maxSum = nums[ 0], sum = nums[ 0];
        for( int i=1; i<nums.length; i++) {
            if( nums[i] > nums[i]+sum)
                 sum = nums[i];
            else sum = nums[i]+sum;
            if( sum > maxSum)
                maxSum = sum;
        }
        return maxSum;
    }
    // Stupid approach - alogorithm works wrong!!!
    public static int maxSubArray2( int[] nums, int a, int b) {
        numInt res = _maxSubArray2( nums, 0, nums.length-1, new numInt( 0, 0, nums));
        return res.sum;
    }
    private static class numInt {
        public int lInd, rInd, sum;
        public numInt( int l, int r, int[] nums) {
            lInd = l; rInd = r;
            sum = 0;
            for( int i = lInd; i<= rInd; i++)
                sum += nums[ i];
        }
        public void copy( numInt newVals) { this.sum = newVals.sum; this.lInd = newVals.lInd; this.rInd = newVals.rInd;}
    }//-2,5,-3,4
    private static numInt _maxSubArray2( int[] nums, int a, int b, numInt max) {
        if( a == b) {
            numInt res = new numInt( a, a, nums);
            if( res.sum > max.sum) max.copy( res);
            return res;
        }
        int med = (a+b)/2;
        numInt lSum = _maxSubArray2( nums, a, med, max),
               rSum = _maxSubArray2( nums, med+1, b, max);
        numInt _max = null;
        int midSum = 0;
        for( int i = lSum.rInd+1; i < rSum.lInd; i++)
            midSum += nums[ i];
        if( lSum.sum >= rSum.sum) {
            _max = lSum;
            if( rSum.sum + midSum >= 0)
                 _max = new numInt( lSum.lInd, rSum.rInd, nums);
        } else {
            _max = rSum;
            if( lSum.sum + midSum >= 0)
                _max = new numInt( lSum.lInd, rSum.rInd, nums);
        }

        if( _max.sum > max.sum) max.copy( _max);

        return _max;
    }

    // 54. Spiral Matrix
    public static void LeetCode_Task54() {
        // Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
        // Output: [1,2,3,4,8,12,11,10,9,5,6,7]
        int[][] matrix = { {1,2,3,4},
                           {5,6,7,8},
                           {9,10,11,12}};
        Arrays.stream( matrix).map( Arrays::toString).forEach( System.out::println);
        System.out.println( "spiralOrder() = "+spiralOrder( matrix));
    }
    public static List<Integer> spiralOrder( int[][] nums) {
        int x0 = 0, y0 = 0, xN = nums[0].length-1, yN = nums.length-1;
        List<Integer> res = new ArrayList<>( xN*yN);
        while( xN-x0 >= 0 && yN-y0 >= 0) {
            for( int x=x0; x < xN; x++)
                res.add( nums[y0][x]);
            for( int y=y0; y < yN; y++)
                res.add( nums[y][xN]);
            for( int x=xN; x > x0; x--)
                res.add( nums[yN][x]);
            for( int y=yN; y > y0; y--)
                res.add( nums[y][x0]);
            x0++; y0++; xN--; yN--;
        }

        return res;
    }

    static class Printer implements Runnable {
        int count = 0;
        public void run() {
            while( count < 10) {
                if( Thread.currentThread().getName().equals( "evenThreadName"))
                    printEven();
                else printOdd();
            }
        }
        public synchronized void printEven() {
            if( count%2 == 0) {
                System.out.println(Thread.currentThread().getName()+" count = "+count);
                count++;
                this.notify();
            }
            try{ this.wait();
            } catch ( Exception ex) { ex.printStackTrace();}
        }
        public synchronized void printOdd() {
            if( count%2 != 0) {
                System.out.println(Thread.currentThread().getName()+" count = "+count);
                count++;
                this.notify();
            }
            try{ this.wait();
            } catch ( Exception ex) { ex.printStackTrace();}
        }
    }
    public static void PrintEvenOddInTwoThreads(){
        System.out.println( "PrintEvenOddInTwoThreads ");
        Printer p = new Printer();
        new Thread( p, "evenThreadName").start();
        new Thread( p, "oddThreadName").start();

    }
}