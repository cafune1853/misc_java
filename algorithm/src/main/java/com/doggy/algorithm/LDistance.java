package com.doggy.algorithm;

public class LDistance {
    public static void main(String[] args) {
        System.out.println(computeDistance("xxxxsuuui", "xxxyuuuu"));
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
        for (int j = 0; j < m + 1; j++){
            for (int i = 0; i < n + 1; i++) {
                if(i > 0 && j > 0){
                    int min;
                    if(s1.charAt(i - 1) == s2.charAt(j - 1)){
                        min = distance[i - 1][j - 1];
                    }else {
                        min = distance[i - 1][j - 1] + 1;
                    }
                    min = Math.min(min, distance[i-1][j] + 1);
                    min = Math.min(min, distance[i][j-1] + 1);
                    distance[i][j] = min;
                }else if(i > 0 && j == 0){
                    distance[i][j] = distance[i-1][j] + 1;
                }else if(j > 0 && i == 0){
                    distance[i][j] = distance[i][j-1] + 1;
                }
            }
        }
        return distance[n][m];
    }
}
