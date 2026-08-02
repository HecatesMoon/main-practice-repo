package com.hecatesmoon.testingexercises1.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TextUtils {
    
    public TextUtils (){}

    public boolean isPalindrome(String word){

        if(word.isBlank()){
            return false;
        }

        word = word.replaceAll(" ", "");
        word = word.toLowerCase();

        int start = 0;
        int end = word.length()-1;
        int half = end / 2;

        for (int i = 1; i <= half ; i++){
            if (word.charAt(start) != word.charAt(end)){
                return false;
            }

            start++;
            end--;
        }

        return true;
    }

    public int countVocals(String word){

        int counter = 0;
        
        for (int i = 0; i < word.length(); i++){
            if (isVocal(word.charAt(i))) counter++;
        }

        return counter;
    }

    private boolean isVocal(char letter){
        String stringLetter = String.valueOf(letter);
        stringLetter = stringLetter.toLowerCase();
        if(stringLetter.equals("a")) return true;
        if(stringLetter.equals("e")) return true;
        if(stringLetter.equals("i")) return true;
        if(stringLetter.equals("o")) return true;
        if(stringLetter.equals("u")) return true;

        if(stringLetter.equals("á")) return true;
        if(stringLetter.equals("é")) return true;
        if(stringLetter.equals("í")) return true;
        if(stringLetter.equals("ó")) return true;
        if(stringLetter.equals("ú")) return true;

        return false;
    }

    public String reverseText(String text){
        char[] charArray = text.toCharArray();
        StringBuilder reversedText = new StringBuilder();

        for (int i = charArray.length -1; i >= 0; i--) {
            reversedText.append(charArray[i]);
        }

        return reversedText.toString();
    }

    public Map<Character, Integer> frequencyCounter(String text){
        Map<Character, Integer> result = new HashMap<>();

        String[] textArray = text.split("");

        Object[] textLetters = Arrays.stream(textArray).distinct().toArray();

        Arrays.stream(textLetters).forEach(l -> {
            Long counter = Arrays.stream(textArray).filter(t -> t.equals(l)).count();
            Integer newCounter = counter.intValue();
            Character letter = l.toString().charAt(0);
            result.put(letter, newCounter);
        });

        return result;
    } 

}
