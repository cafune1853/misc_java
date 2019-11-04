package com.doggy.algorithm;

import java.util.*;

public class StringMatch {
    public static void main(String[] args) {
        String fullText = "xxxxabxxxxxddddcabcab";
        String matchText1 = "cabcab";
        String matchText2 = "xxab";
        System.out.println(bruteForceMatch(fullText, matchText1));
        System.out.println(bruteForceMatch(fullText, matchText2));
        System.out.println(bmMatch(fullText, matchText1));
        System.out.println(bmMatch(fullText, matchText2));
    }

    public static boolean bmMatch(CharSequence fullSeq, CharSequence matchSeq){
        Map<Character, ArrayList<Integer>> charIndexListMap = constructOrderedCharIndexListMap(matchSeq);
        int[] suffix = new int[matchSeq.length()];
        boolean[] matchHead = new boolean[matchSeq.length()];
        setGoodSuffixAndMatchHead(matchSeq, suffix, matchHead);
        int start = 0;
        while(start <= fullSeq.length() - matchSeq.length()) {
            int i = matchSeq.length() - 1;
            for (; i >= 0; i--) {
                if (matchSeq.charAt(i) != fullSeq.charAt(start + i)) {
                    break;
                }
            }
            if (i < 0 ) {
                return true;
            }
            int lastMatchCount = matchSeq.length() - 1 - i;
            int goodSuffixShouldMove = calculateMoveByGoodSuffix(matchSeq.length(), lastMatchCount, suffix, matchHead);
            // 坏字符匹配：右移匹配串直到找到一个字符和坏字符一致
            char badChar = fullSeq.charAt(start + i);
            int recentCorrectIndex = getRecentLastIndex(charIndexListMap, badChar, i);
            int badCharShouldMove = i - recentCorrectIndex;
            start += Math.max(goodSuffixShouldMove, badCharShouldMove);
        }
        return false;
    }

    private static int calculateMoveByGoodSuffix(int m, int mMatchedLength, int[] suffix, boolean[] matchHead){
        if(mMatchedLength == 0){
            return 1;
        }
        if(suffix[mMatchedLength] != -1){
            return m - mMatchedLength - suffix[mMatchedLength];
        }else{
            for (int i = mMatchedLength; i >= 1; i--) {
                if(matchHead[i]){
                    return m - i;
                }
            }
            return m;
        }
    }

    private static void setGoodSuffixAndMatchHead(CharSequence cs, int[] suffix, boolean[] matchHead){
        for (int i = 0; i < suffix.length - 1; i++) {
            suffix[i] = -1;
            matchHead[i] = false;
        }
        // 对cs[0, 0] ... cs[0, m - 2]中的每一个子串与cs求公共子后缀。
        for (int i = 0; i <= cs.length() - 2; i++) {
            int j = i;
            for (; j >= 0; j--) {
                if(cs.charAt(j) == cs.charAt(cs.length() - 1 - i + j)){
                    suffix[i - j + 1] = j;
                }else{
                    break;
                }
            }
            if(j == -1){
                matchHead[i + 1] = true;
            }
        }
    }

    private static <T extends List<Integer> & RandomAccess> int getRecentLastIndex(Map<Character, T> characterListMap, char c, int lastIndex){
        List<Integer> charIndexList = characterListMap.get(c);
        if(charIndexList == null || charIndexList.isEmpty()){
            return -1;
        }
        int index = binarySearchLastLessValue(characterListMap.get(c), 0, charIndexList.size() - 1, lastIndex);
        return index == -1 ? -1 : charIndexList.get(index);
    }

    private static <T extends List<Integer> & RandomAccess> int binarySearchLastLessValue(T list, int start, int end, int value){
        if(start == end){
            return list.get(start) < value ? start : -1;
        }
        int middle = start + (end - start) / 2;
        if(list.get(middle) >= value){
            return binarySearchLastLessValue(list, start, middle, value);
        }
        if(middle + 1 < list.size() && list.get(middle + 1) >= value){
            return middle;
        }else{
            return binarySearchLastLessValue(list, middle + 1, end, value);
        }
    }

    private static Map<Character, ArrayList<Integer>> constructOrderedCharIndexListMap(CharSequence cs){
        Map<Character, ArrayList<Integer>> characterListMap = new HashMap<>();
        for (int i = 0; i < cs.length(); i++) {
            characterListMap.putIfAbsent(cs.charAt(i), new ArrayList<>());
            characterListMap.get(cs.charAt(i)).add(i);
        }
        characterListMap.values().forEach(charIndexList -> {
            charIndexList.sort(Integer::compare);
        });
        return characterListMap;
    }

    public static boolean kmpMatch(CharSequence fullSeq, CharSequence matchSeq){
        return false;
    }

    public static boolean bruteForceMatch(CharSequence fullSeq, CharSequence matchSeq){
        if(fullSeq.length() < matchSeq.length()){
            return false;
        }
        for (int i = 0; i <= fullSeq.length() - matchSeq.length(); i++) {
            boolean allMatch = true;
            for (int j = 0; j < matchSeq.length(); j++) {
                if(matchSeq.charAt(j) != fullSeq.charAt(i + j)){
                    allMatch = false;
                    break;
                }
            }
            if (allMatch){
                return true;
            }
        }
        return false;
    }
}
