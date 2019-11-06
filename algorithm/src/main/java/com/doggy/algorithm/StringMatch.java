package com.doggy.algorithm;

import java.util.*;

public class StringMatch {
    public static void main(String[] args) {
        checkStrategy(new BruteForceMatchStrategy(), "xxxxabxxxxxddddcabcab", "cabcab", 15);
        checkStrategy(new BoyerMooreMatchStrategy(), "xxxxabxxxxxddddcabcab", "cabcab", 15);
        checkStrategy(new KMPMatchStrategy(), "xxxxabxxxxxddddcabcab", "cabcab", 15);
    }

    private static void checkStrategy(StringMatchStrategy strategy, CharSequence source, CharSequence match, int expectIndex){
        int calIndex = strategy.indexOf(source, match);
        if(calIndex == expectIndex){
            System.out.println(String.format("Strategy:%s %s indexOf %s success", strategy.getClass().getSimpleName(), source, match));
        }else{
            System.out.println(String.format("Strategy:%s %s indexOf %s failed, expect:%d return:%d", strategy.getClass().getSimpleName(), source, match, expectIndex, calIndex));
        }
    }

    /**
     * KMP策略，实际上采用好前缀的思想，其从前往后匹配，直到出现坏字符停下来。之前已匹配的部分称为好前缀。
     * 当好前缀的某个前缀子串和其某个后缀子串完全匹配时， 则可以快速移动模式串，使模式串的前缀子串（也是好前缀的前缀子串）与好前缀的后缀子串对齐。
     * 如果存在多个可匹配的前缀子串，为了避免滑动过多，那么我们采用最长前缀子串。我们引入next[]失效数组。
     * 记a为模式串，next[i] = k 表示好前缀a[0, i] 的 可匹配最长前缀子串为a[0, k].
     *
     * KMP核心步骤为以下两部分
     * 1. 则当模式串与母串在下标j不匹配时，则我们知道模式串应该右移到next[j - 1] + 1 继续匹配（next[j - 1]即为好前缀的可匹配最长子前缀下标x，从x + 1继续匹配）。
     * 2. 计算失效数组:要计算next[i], 则先考察a[0, i - 1]的可匹配最长前缀子串， 如果a[next[i - 1] + 1] == a[i], 则直接next[i] = next[i - 1] + 1,
     * 如果不等，则继续考察a[0, i - 1]的可匹配次长前缀子串，由于该次长前缀子串即为可匹配最长前缀子串的最长前缀子串，于是转而判断a[next[next[i - 1]] + 1] == a[i], 依次执行
     */
    private static class KMPMatchStrategy implements StringMatchStrategy{
        @Override
        public int indexOf(CharSequence fullSeq, CharSequence matchSeq) {
            int[] next = calculateNext(matchSeq);
            int j = 0;
            for (int i = 0; i < fullSeq.length(); i++) {
                while (j > 0 && fullSeq.charAt(i) != matchSeq.charAt(j)){
                    j = next[j - 1] + 1;
                }
                if(fullSeq.charAt(i) == matchSeq.charAt(j)){
                    j++;
                }
                if(j == matchSeq.length()){
                    return i - matchSeq.length() + 1;
                }
            }
            return -1;
        }

        private int[] calculateNext(CharSequence matchSeq){
            int[] next = new int[matchSeq.length()];
            next[0] = -1;
            for (int i = 1; i < matchSeq.length() - 1; i++) {
                // k表示当前最长子前缀的下标
                int k = next[i - 1];
                while (k != -1 && matchSeq.charAt(k + 1) != matchSeq.charAt(i)){
                    k = next[k];
                }
                if(matchSeq.charAt(k + 1) == matchSeq.charAt(i)){
                    next[i] = k + 1;
                }else{
                    next[i] = matchSeq.charAt(0) == matchSeq.charAt(i) ? 0 : -1;
                }
            }
            return next;
        }
    }

    /**
     * Boyer-Moore算法采用母串与模式串从后往前匹配的方式匹配，已匹配的部分称为好后缀，不匹配的字符称为坏字符。
     * 其子串滑动规则分为坏字符规则与好后缀规则。
     * 1. 坏字符规则即为拿着坏字符，从不匹配的模式串的位置向前找，直到找到第一个与坏字符相同的字符，然后移动模式串，使该字符与坏字符对其。
     * 2. 好后缀规则即为拿着好后缀在模式串中向前匹配，直到找到第一个与好后缀相同的子串，然后滑动模式串直到该子串与好后缀对其。
     * 如果不存在，则查找好后缀的最长子后缀与模式串的前缀相匹配，然后移动模式串，直到模式串的前缀与好后缀的后缀对其。
     *
     * 移动之后重新从模式串的最后一个字符开始匹配。
     */
    private static class BoyerMooreMatchStrategy implements StringMatchStrategy{
        @Override
        public int indexOf(CharSequence fullSeq, CharSequence matchSeq) {
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
                    return start;
                }
                int lastMatchCount = matchSeq.length() - 1 - i;
                int goodSuffixShouldMove = calculateMoveDistanceByGoodSuffix(matchSeq.length(), lastMatchCount, suffix, matchHead);
                // 坏字符匹配：右移匹配串直到找到一个字符和坏字符一致
                char badChar = fullSeq.charAt(start + i);
                int recentCorrectIndex = getRecentLastIndex(charIndexListMap, badChar, i);
                int badCharShouldMove = i - recentCorrectIndex;
                start += Math.max(goodSuffixShouldMove, badCharShouldMove);
            }
            return -1;
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

        // 通过好后缀计算移动的距离
        private static int calculateMoveDistanceByGoodSuffix(int m, int mMatchedLength, int[] suffix, boolean[] matchHead){
            if(mMatchedLength == 0){
                return 1;
            }
            if(suffix[mMatchedLength] != -1){
                return m - mMatchedLength - suffix[mMatchedLength];
            }else{
                for (int i = mMatchedLength - 1; i >= 1; i--) {
                    if(matchHead[i]){
                        return m - i;
                    }
                }
                return m;
            }
        }
    }

    private static class BruteForceMatchStrategy implements StringMatchStrategy{
        @Override
        public int indexOf(CharSequence fullSeq, CharSequence matchSeq) {
            if(fullSeq.length() < matchSeq.length()){
                return -1;
            }
            for (int i = 0; i <= fullSeq.length() - matchSeq.length(); i++) {
                int j = 0;
                for (; j < matchSeq.length(); j++) {
                    if(matchSeq.charAt(j) != fullSeq.charAt(i + j)){
                        break;
                    }
                }
                if (j == matchSeq.length()){
                    return i;
                }
            }
            return -1;
        }
    }

    private static interface StringMatchStrategy{
        int indexOf(CharSequence fullSeq, CharSequence matchSeq);
    }
}
