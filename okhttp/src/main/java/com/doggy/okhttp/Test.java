package com.doggy.okhttp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author doggy1853
 */
public class Test {
    public static void main(String[] args) {
        List<String> candidateEas = fileToEaList("D:\\test\\candidate.txt");
        List<String> filterEas = fileToEaList("D:\\test\\filter.txt");
        List<String> mustEas = fileToEaList("D:\\test\\must.txt");
        candidateEas.removeAll(filterEas);
        mustEas.retainAll(candidateEas);
        eaListToFile(mustEas, "D:\\test\\eas.txt");
        System.out.println(mustEas.size());
    }

    private static void eaListToFile(List<String> eas, String filePath){
        File file = new File(filePath);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file))){
            eas.forEach(ea -> {
                pw.println(ea);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<String> fileToEaList(String filePath){
        List<String> eas = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(new FileInputStream(file))){
             while (scanner.hasNextLine()){
                 eas.add(scanner.nextLine().trim());
             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return eas;
    }
}
