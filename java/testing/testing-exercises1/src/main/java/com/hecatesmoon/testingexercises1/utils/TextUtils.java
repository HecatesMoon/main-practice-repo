package com.hecatesmoon.testingexercises1.utils;

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

}
