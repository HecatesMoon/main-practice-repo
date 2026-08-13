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


    @Test
    public void testCountVocals_FourVocalsWord(){
        int result = textUtils.countVocals("acetato");

        Assertions.assertEquals(4, result);
    }

    @Test
    public void testCountVocals_NoVocalsWord(){
        int result = textUtils.countVocals("rythm");

        Assertions.assertEquals(0, result);
    }

    @Test
    public void testCountVocals_OnlySpaces(){
        int result = textUtils.countVocals("       ");

        Assertions.assertEquals(0, result);
    }

    @Test
    public void testCountVocals_EmptyString(){
        int result = textUtils.countVocals("");

        Assertions.assertEquals(0, result);
    }

    @Test
    public void testCountVocals_OnlyCaps(){
        int result = textUtils.countVocals("MADERA");

        Assertions.assertEquals(3, result);
    }

    @Test
    public void testCountVocals_RandomSpaces(){
        int result = textUtils.countVocals("  l ob ot o mi  a   ");

        Assertions.assertEquals(5, result);
    }

    @Test
    public void testCountVocals_BorderSpaces(){
        int result = textUtils.countVocals("  hamburguesa   ");

        Assertions.assertEquals(5, result);
    }
     
    @Test
    public void testCountVocals_WordWithAccent(){
        int result = textUtils.countVocals("azúcar");

        Assertions.assertEquals(3, result);
    }
     
    @Test
    public void testCountVocals_WordWithAccentAndCaps(){
        int result = textUtils.countVocals("Algodón");

        Assertions.assertEquals(3, result);
    }
     
    @Test
    public void testCountVocals_OnlyVocals(){
        int result = textUtils.countVocals("aaeeioua");

        Assertions.assertEquals(8, result);
    }

    @Test
    public void testCountVocals_OnlyVocalsWithAccents(){
        int result = textUtils.countVocals("áééííóóííú");

        Assertions.assertEquals(10, result);
    }

}
