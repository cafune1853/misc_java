package com.doggy.algorithm;

public class LDistance {
    public static void main(String[] args) {
        System.out.println(computeDistance("xxxs", "xxxy"));
    }

    private static int computeDistance(String s1, String s2){
        int n = s1.length();
        int m = s2.length();
        if(n == 0){
            return m;
        }
        if(m == 0){
            return n;
        }
        int[][] distance = new int[n+1][m+1];
        distance[0][0] = 0;
        for (int j = 0; j < n + 1; j++){
            int y = j;
            int x = 0;
            while (x < m + 1 && y >= 0){
                if(x > 0 && y > 0){
                    int min;
                    if(s1.charAt(x - 1) == s2.charAt(y - 1)){
                        min = distance[x - 1][y - 1];
                    }else {
                        min = distance[x - 1][y - 1] + 1;
                    }
                    min = Math.min(min, distance[x-1][y] + 1);
                    min = Math.min(min, distance[x][y-1] + 1);
                    distance[x][y] = min;
                }else if(x > 0 && y == 0){
                    distance[x][y] = distance[x-1][y] + 1;
                }else if(y > 0 && x == 0){
                    distance[x][y] = distance[x][y-1] + 1;
                }
                x ++;
                y --;
            }
        }
        return distance[n][m];
    }
}
