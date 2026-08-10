package com.hecatesmoon.testingexercises1.utils;

import com.hecatesmoon.testingexercises1.exceptions.InvalidRutException;

public class RutUtils {

    public RutUtils (){}

    public boolean rutValidator(String rut){

        if(rut.isBlank()){
            throw new InvalidRutException("you need to write a rut: " + rut);
        }

        if (!rut.matches("^[0-9]+-[0-9kK]{1}$")){
            throw new InvalidRutException("you need to write it in a valid format (12345678-9)");
        }

        return verificationDigitCheck(rut);
    }

    private boolean verificationDigitCheck(String rut){

        int[] multipliers = {2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7};

        String cleanRut = rut.substring(0, rut.length() - 2);
        String reversedRut = reverse(cleanRut);

        int total = 0;

        for (int i = 0; i<reversedRut.length(); i++){
            char currentChar = reversedRut.charAt(i);
            int toInt = currentChar - '0';
            total += multipliers[i] * toInt;
        }

        int verificationDigit = 11 - (total % 11);
        String stringVerificationDigit = String.valueOf(verificationDigit);
        stringVerificationDigit = (verificationDigit == 11) ? "0" : stringVerificationDigit;
        stringVerificationDigit = (verificationDigit == 10) ? "k" : stringVerificationDigit;
    
        return rut.toLowerCase().endsWith("-" + stringVerificationDigit);
        
    }

    private String reverse(String string){
        StringBuilder builder = new StringBuilder();
        
        for (int i = string.length() - 1 ; i >= 0; i--){
            builder.append(string.charAt(i));
        }

        return builder.toString();
    }
    
}
