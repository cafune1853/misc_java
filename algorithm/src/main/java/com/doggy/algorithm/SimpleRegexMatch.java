package com.doggy.algorithm;

/**
 * 只支持*与？的完全正则匹配
 */
public class SimpleRegexMatch {
    public static void main(String[] args) {
        System.out.println(isMatch("123*", "*"));
        System.out.println(isMatch("xxx*", "*"));
        System.out.println(isMatch("xxx*", "*?"));
        System.out.println(isMatch("xxx*", "*?x"));
        System.out.println(isMatch("xxx*xy", "*?x?"));
    }

    public static boolean isMatch(String stringToMatch, String regex){
        return rMatch(stringToMatch, 0, regex, 0);
    }

    public static boolean rMatch(String stringToMatch, int startMatch, String regex, int regexStart){
        if(startMatch == stringToMatch.length() && regexStart == regex.length()){
            return true;
        }
        if(startMatch == stringToMatch.length()){
            return false;
        }
        if(regexStart == regex.length()){
            return false;
        }
        char currentRegexChar = regex.charAt(regexStart);
        if('*' ==  currentRegexChar){
            for (int i = startMatch; i <= stringToMatch.length(); i++) {
                if(rMatch(stringToMatch, i, regex, regexStart + 1)){
                    return true;
                }
            }
        }else if('?' == currentRegexChar){
            if(rMatch(stringToMatch, startMatch, regex, regexStart + 1) || rMatch(stringToMatch, startMatch + 1, regex, regexStart + 1)){
                return true;
            }
        }else{
            if(stringToMatch.charAt(startMatch) == currentRegexChar){
                return rMatch(stringToMatch, startMatch + 1, regex, regexStart + 1);
            }
        }
        return false;
    }
}
