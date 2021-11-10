package utils;

import java.util.ArrayList;

public class Utils {

    public static void swap(char[] chs, int a, int b){
        char tmp = chs[a];
        chs[a] = chs[b];
        chs[b] = tmp;
    }

}
