package com.doggy.algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * N皇后问题
 */
public class NQueue {
    public static void main(String[] args) {
        List<String> allPath = listAllPath(8);
        System.out.println(allPath.size());
        System.out.println(allPath);
    }

    private static List<String> listAllPath(int n){
        int[][] checkerboard = new int[n][n];
        List<String> result = new LinkedList<>();
        tryStep(0, n, checkerboard, result);
        return result;
    }

    private static void tryStep(int line, int lastLine, int[][] checkerboard, List<String> result){
        if(line == lastLine){
            addCheckerBoardToResult(line, checkerboard, result);
            return;
        }
        for (int y = 0; y < lastLine; y++) {
            if(checkPositionOK(line, y, lastLine, checkerboard)){
                checkerboard[line][y] = 1;
                tryStep(line + 1, lastLine, checkerboard, result);
                checkerboard[line][y] = 0;
            }
        }
    }

    private static boolean checkPositionOK(int x, int y, int lastLine, int[][] checkerboard){
        for (int i = x - 1; i >= 0; i--) {
            if(checkerboard[i][y] == 1){
                return false;
            }
        }
        for (int i = x - 1, j = y - 1; i >=0 && j >=0 ; i--, j--) {
            if(checkerboard[i][j] == 1){
                return false;
            }
        }
        for (int i = x - 1, j = y + 1; i >=0 && j < lastLine ; i--, j++) {
            if(checkerboard[i][j] == 1){
                return false;
            }
        }
        return true;
    }

    private static void addCheckerBoardToResult(int n, int[][] checkerboard, List<String> result){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(checkerboard[i][j] == 1){
                    stringBuilder.append(j);
                    stringBuilder.append(",");
                    break;
                }
            }
        }
        result.add(stringBuilder.toString());
    }
}
