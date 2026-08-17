package com.hecatesmoon.testingexercises1.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.hecatesmoon.testingexercises1.exceptions.InvalidRutException;

public class RutUtilsTest {
    
    private final RutUtils rutUtils = new RutUtils();

    public RutUtilsTest(){}

    @Nested
    class WithValidRut{
        @ParameterizedTest
        @CsvSource({
        "5126663-3",
        "5811892-3",
        "8714763-0",
        "24763195-k"
        })
        public void testRutValidator_ValidRuts(String rut){
            boolean result = rutUtils.rutValidator(rut);

            Assertions.assertTrue(result);
        }

        @Test
        public void testRutValidator_ValidRutWithUpperCaseK(){
            String rut = "24763195-K";

            boolean result = rutUtils.rutValidator(rut);

            Assertions.assertTrue(result);
        }
    }

    @Nested
    class WithInvalidRut{
        @ParameterizedTest
        @CsvSource({
        "5126663-k",
        "5811892-0",
        "8714763-2",
        })
        public void testRutValidator_InvalidVerificationDigit(String rut){
            boolean result = rutUtils.rutValidator(rut);

            Assertions.assertFalse(result);
        }

        @Test
        public void testRutValidator_EmptyString(){
            String rut = "";

            Assertions.assertThrows(InvalidRutException.class, () -> {
                rutUtils.rutValidator(rut);
            });
        }

        @Test
        public void testRUtValidator_InvalidRutWithDotsBetween(){
            String rut = "8.714.763-0";

            Assertions.assertThrows(InvalidRutException.class, () -> {
                rutUtils.rutValidator(rut);
            });
        }
        @Test
        public void testRUtValidator_InvalidRutWithLettersBetween(){
            String rut = "87ia763-0";

            Assertions.assertThrows(InvalidRutException.class, () -> {
                rutUtils.rutValidator(rut);
            });
        }
        @Test
        public void testRUtValidator_InvalidRutWithoutHypen(){
            String rut = "87147630";

            Assertions.assertThrows(InvalidRutException.class, () -> {
                rutUtils.rutValidator(rut);
            });
        }
    }
}
