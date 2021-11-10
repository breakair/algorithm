package Greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Greedy{
    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    public static class MyComparator1 implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    // 获取字典序最小的字符串
    public static String lowestString(String[] strs){
        if(strs == null || strs.length == 0){
            return "";
        }
        Arrays.sort(strs, new MyComparator());
        String res = "";
        for(int i = 0; i < strs.length; i++){
            res += strs[i];
        }
        return res;
    }

    // 哈夫曼编码问题
    // 将一个数切分成相应的数组，每次切分花费切割前的数的代价，求总花费最小的算法
    public static int lessMoney(int[] nums){
        PriorityQueue<Integer> queue = new PriorityQueue<>(new MyComparator1());
        for(int i = 0; i < nums.length; i++){
            queue.add(nums[i]);
        }
        int sum = 0;
        int cur = 0;
        while(queue.size() > 1){
            cur = queue.poll() + queue.poll();
            sum += cur;
            queue.add(cur);
        }
        return sum;
    }

    // n皇后问题
    // 经典解法
    public static int nQueens(int n){
        if(n < 1){
            return 0;
        }
        int[] record = new int[n];
        return process(0, record, n);
    }

    // 目前来到了第i行
    // record[0...i-1]表示之前放了皇后位置的行
    // n代表整体一共有多少行
    // 返回值是表示有多少种合理的摆法
    public static int process(int i, int[] record, int n){
        if(i == n){
            return 1;
        }
        int res = 0;
        for(int j = 0; j < n; j++){
            if(isValid(record, i, j)){
                record[i] = j;
                res += process(i + 1, record, n);
            }
        }
        return res;
    }

    public static boolean isValid(int[] record, int i, int j){
        for(int k = 0; k < i; k++){
            if(j == record[k] || Math.abs(j - record[k]) == Math.abs(k - i)){
                return false;
            }
        }
        return true;
    }

    // n皇后问题
    // 位运算解法
    // 区间 0 - 32
    public static int nQueensBit(int n){
        if(n < 1 || n > 32){
            return 0;
        }
        int limit = n == 32? -1: (1 << n) - 1;
        return process(limit, 0, 0, 0);
    }

    public static int process(int limit, int colLim, int leftDiaLim, int rightDiaLim){
        if(colLim == limit){
            return 1;
        }
        int pos = 0;
        int mostRightOne = 0;
        pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int res = 0;
        while (pos != 0){
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1, (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args){
        String[] strs1 = { "jibw", "ji", "jp", "bw", "jibw" };
        System.out.println(lowestString(strs1));
        String[] strs2 = {"ba", "b"};
        System.out.println(lowestString(strs2));

        int[] nums = {30, 50, 20};
        System.out.println(lessMoney(nums));

        System.out.println(nQueens(8));
        System.out.println(nQueensBit(8));
    }
}