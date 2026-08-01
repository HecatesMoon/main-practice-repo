package com.hecatesmoon.testingexercises1.utils;

public class TextUtils {
    
    public TextUtils (){}

    public boolean isPalindrome(String word){

        if(word.isBlank()){
            return false;
        }

        word = cleanSpaces(word);
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

    private String cleanSpaces(String word){
        String[] stringArray = word.split(" ");
        StringBuilder newString = new StringBuilder();

        for (String string : stringArray) {
            if (!string.isBlank()){
                newString.append(string);
            }
        }

        return newString.toString();
    }

}
