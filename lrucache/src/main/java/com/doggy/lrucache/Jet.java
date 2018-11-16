package com.doggy.lrucache;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doggy1853
 */
public class Jet {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("!\\[.*?]\\((TA_.+?)\\)");
        String x = "xxxm ![img:TA_XXXXX](TA_X) ![img:TA_XXXXX](TAX) [img:] [img:*] []";
        Matcher matcher = pattern.matcher(x);
        while (matcher.find()){

            System.out.println(matcher.group(0));
            String taPath = matcher.group(1);
            System.out.println(taPath);

            matcher.appendReplacement(sb,"yahaha");
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }
}
