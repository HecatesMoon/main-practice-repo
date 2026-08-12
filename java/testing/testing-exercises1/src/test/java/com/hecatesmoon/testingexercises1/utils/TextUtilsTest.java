package com.hecatesmoon.testingexercises1.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextUtilsTest {
    
    private final TextUtils textUtils = new TextUtils();

    public TextUtilsTest(){
    }

    @Test
    public void testIsPalindrome_ValidPalindrome(){
        boolean result = textUtils.isPalindrome("reconocer");

        Assertions.assertTrue(result);
    }

    @Test
    public void testIsPalindrome_InvalidPalindrome(){
        boolean result = textUtils.isPalindrome("abismo");

        Assertions.assertFalse(result);
    }

    @Test
    public void testIsPalindrome_ValidPalindromeWithSpaces(){
        boolean result = textUtils.isPalindrome("anita lava la tina");

        Assertions.assertTrue(result);
    }

    @Test
    public void testIsPalindrome_ValidPalindromeWithSpacesAndCaps(){
        boolean result = textUtils.isPalindrome("Somos O No Somos");

        Assertions.assertTrue(result);
    }

    @Test
    public void testIsPalindrome_ValidPalindromeWithSpacesAtSidesAndBetween(){
        boolean result = textUtils.isPalindrome("    Ar e  pe ra  ");

        Assertions.assertTrue(result);
    }

    @Test
    public void testIsPalindrome_EmptyString(){
        boolean result = textUtils.isPalindrome("");

        Assertions.assertFalse(result);
    }

    @Test
    public void testIsPalindrome_OnlySpaces(){
        boolean result = textUtils.isPalindrome("       ");

        Assertions.assertFalse(result);
    }

}
