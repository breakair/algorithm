package Test;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Test {
    public static int numOfSubarrays(int[] arr, int k, int threshold) {
        int ans = 0;
        int sum = 0;
        for(int i = 0; i < k; i++){
            sum += arr[i];
        }
        if(sum >= threshold * k){
            ans += 1;
        }
        for(int j = k; j < arr.length; j++){
            sum -= arr[j - k];
            sum += arr[j];
            if(sum >= threshold * k){
                ans += 1;
            }
        }
        return ans;
    }
    public static void main(String[] args){
        System.out.println(numOfSubarrays(new int[]{4,4,4,4}, 4, 1));
    }
}
