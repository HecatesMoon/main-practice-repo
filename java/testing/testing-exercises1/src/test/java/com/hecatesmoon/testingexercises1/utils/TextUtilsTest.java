package com.hecatesmoon.testingexercises1.utils;

import java.util.HashMap;
import java.util.Map;

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




    @Test
    public void testReverseText_LowerCaseWord(){
        String result = textUtils.reverseText("hipopotamo");

        Assertions.assertEquals("omatopopih", result);
    }

    @Test
    public void testReverseText_UpperCaseWord(){
        String result = textUtils.reverseText("TOMATES");

        Assertions.assertEquals("SETAMOT", result);
    }
    
    @Test
    public void testReverseText_MoreThanOneWord(){
        String result = textUtils.reverseText("i ate pizza");

        Assertions.assertEquals("azzip eta i", result);
    }

    
    @Test
    public void testReverseText_WordWithRandomSpaces(){
        String result = textUtils.reverseText("  azu  ca re ro   ");

        Assertions.assertEquals("   or er ac  uza  ", result);
    }
    
    @Test
    public void testReverseText_EmptyString(){
        String result = textUtils.reverseText("");

        Assertions.assertEquals("", result);
    }

    @Test
    public void testReverseText_OneLetter(){
        String result = textUtils.reverseText("a");

        Assertions.assertEquals("a", result);
    }

    @Test
    public void testReverseText_OnlySpaces(){
        String result = textUtils.reverseText("    ");

        Assertions.assertEquals("    ", result);
    }

    @Test
    public void testReverseText_ConcatenatedString(){
        String result = textUtils.reverseText("El gran " + "Salto");

        Assertions.assertEquals("otlaS narg lE", result);
    }




    @Test
    public void testFrequencyCounter_LowerCaseWord(){
        Map<Character, Integer> result = textUtils.frequencyCounter("mezosoico");

        Map<Character, Integer> expected = Map.of('m', 1, 'e', 1, 'z', 1, 'o', 3, 's', 1, 'i', 1 , 'c', 1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_UpperCaseWord(){
        Map<Character, Integer> result = textUtils.frequencyCounter("AZUCAR");

        Map<Character, Integer> expected = Map.of('A', 2, 'Z', 1, 'U', 1, 'C', 1, 'R', 1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_OneLetter(){
        Map<Character, Integer> result = textUtils.frequencyCounter("p");

        Map<Character, Integer> expected = Map.of('p', 1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_OneRepeatedLetter(){
        Map<Character, Integer> result = textUtils.frequencyCounter("lllll");

        Map<Character, Integer> expected = Map.of('l', 5);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_OneRepeatedLetterWithMixedCases(){
        Map<Character, Integer> result = textUtils.frequencyCounter("aaAAaaAAa");

        Map<Character, Integer> expected = Map.of('a', 5, 'A', 4);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_WordWithAccent(){
        Map<Character, Integer> result = textUtils.frequencyCounter("mamífero");

        Map<Character, Integer> expected = Map.of('m', 2, 'a', 1, 'í', 1, 'f', 1, 'e', 1, 'r', 1, 'o', 1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_WordWithBorderSpaces(){
        Map<Character, Integer> result = textUtils.frequencyCounter(" dedalo ");

        Map<Character, Integer> expected = Map.of(' ', 2, 'd', 2, 'e', 1, 'a', 1, 'l', 1, 'o', 1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_MultipleWords(){
        Map<Character, Integer> result = textUtils.frequencyCounter("fight without fear");

        Map<Character, Integer> expected = new HashMap();
        Map<Character, Integer> part1 = Map.of('f', 2, 'i', 2, 'g', 1, 'h', 2, 't', 3, ' ', 2, 'w', 1, 'o', 1, 'u', 1, 'e', 1);
        Map<Character, Integer> part2 = Map.of('a', 1, 'r', 1);
        expected.putAll(part1);
        expected.putAll(part2);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_EmptyString(){
        Map<Character, Integer> result = textUtils.frequencyCounter("");

        Map<Character, Integer> expected = new HashMap<>();

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFrequencyCounter_SingleSpace(){
        Map<Character, Integer> result = textUtils.frequencyCounter(" ");

        Map<Character, Integer> expected = Map.of(' ', 1);

        Assertions.assertEquals(expected, result);
    }
}
