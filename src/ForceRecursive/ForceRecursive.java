package ForceRecursive;

import utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ForceRecursive {

    // 打印n层汉诺塔
    public static void hanota(int i, String start, String end, String other){
        if(i == 1){
            System.out.println("从"+start+"移动到"+end);
        } else {
            hanota(i - 1, start, other, end);
            System.out.println("从"+start+"移动到"+end);
            hanota(i - 1, other, end, start);
        }
    }

    // 打印所有的子序列
    public static void printAllSubsquences(String str){
        char[] chs = str.toCharArray();
        process(chs, 0);
    }

    public static void process(char[] strs, int i){
        if(i == strs.length){
            System.out.println(String.valueOf(strs));
            return;
        }
        process(strs, i + 1);
        char tmp = strs[i];
        strs[i] = 0;
        process(strs, i + 1);
        strs[i] = tmp;
    }

    public static ArrayList<String> perMutation(String str){
        ArrayList<String> res = new ArrayList<>();
        if(str == null || str.length() == 0){
            return res;
        }
        char[] chs = str.toCharArray();
        process(chs, 0, res);
        return res;
    }

    /**
     * 取牌函数
     * @param chs
     * @param i
     * @param res
     */

    public static void process(char[] chs, int i, ArrayList<String> res){
        if(chs.length == i){
            res.add(String.valueOf(chs));
            return;
        }
        boolean[] visited = new boolean[26];
        for(int j = i; j < chs.length; j++){
            if(!visited[chs[j] - 'a']){
                visited[chs[j] - 'a'] = true;
                Utils.swap(chs, i, j);
                process(chs, i + 1, res);
                Utils.swap(chs, i, j);
            }
        }
    }

    public  static int win1(int[] arr){
        if(arr == null || arr.length == 0){
            return 0;
        }
        return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
    }

    public static int f(int[] arr, int i, int j){
        if(i == j){
            return arr[i];
        }
        return Math.max(arr[i] + s(arr, i + 1, j), arr[j] + s(arr, i, j - 1));
    }

    public static int s(int[] arr, int i, int j){
        if(i == j){
            return 0;
        }
        return Math.min(f(arr, i + 1, j), f(arr, i, j - 1));
    }

    // 给定一个栈，逆序栈并且不能申请额外的数据结构
    public static void reverse(Stack<Integer> stack){
        if(stack.isEmpty()){
            return;
        }
        int i = f(stack);
        reverse(stack);
        stack.push(i);
    }

    public static int f(Stack<Integer> stack){
        int result = stack.pop();
        if(stack.isEmpty()){
            return result;
        } else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

    /**
     * 规定1和A对应、2和B对应，3和C对应。。。
     * 那么一个数字字符串比如“111”，就可以转化为“AAA”、“KA”和“AK”
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
     * @param str
     */
    public static int transformNumberStr(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        char[] chs = str.toCharArray();
        return process1(chs, 0);
    }

    public static int process1(char[] chs, int i){
        if(i == chs.length){
            return 1;
        }
        if(chs[i] == '0'){
            return 0;
        }
        if(chs[i] == '1'){
            int res = process1(chs, i + 1);
            if(i + 1 < chs.length){
                res += process1(chs, i + 2);
            }
            return res;
        }
        if(chs[i] == '2'){
            int res = process1(chs, i + 1);
            if(i + 1 < chs.length && (chs[i + 1] >= '0' && chs[i + 1] <= '6')){
                res += process1(chs, i + 2);
            }
            return res;
        }
        return process1(chs, i + 1);
    }

    /**
     * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表
     * i号物品的重量和价值。给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超
     * 过这个重量。返回你能装下最多的价值。
     * @param weights, values, bag
     */
    public static int maxValue(int[] weights, int[] values, int bag){
        return process(weights, values, 0,0, bag, 0);
    }

    // 结果貌似有问题
    public static int process(int[] weights, int[] values, int weight, int bag, int i){
        if(weight > bag){
            return 0;
        }
        if(i == weights.length){
            return 0;
        }
        return Math.max(process(weights, values, weight, bag, i + 1), values[i] + process(weights, values, weight + weights[i], bag, i + 1));
    }

    // 未验证是否有问题
    public static int process(int[] weights, int[] values, int weight, int value, int bag, int i){
        if(weight > bag){
            return 0;
        }
        if(i == values.length){
            return value;
        }
        return Math.max(process(weights, values, weight, value, bag, i + 1), process(weights, values, value + values[i], weight + weights[i], bag, i + 1));
    }

    public static void main(String[] args){
        hanota(3, "左", "右", "中");
        printAllSubsquences("abc");
        System.out.println(String.valueOf(perMutation("abc")));
        System.out.println(win1(new int[]{2, 3, 100, 5}));
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        reverse(stack);
        System.out.println(String.valueOf(stack));
        System.out.println(transformNumberStr("111"));
        System.out.println(maxValue(new int[]{2, 5, 3, 1}, new int[]{3, 2, 1, 5}, 5));
    }

}
