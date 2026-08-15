package com.hecatesmoon.testingexercises1.utils;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatUtilsTest {

    private final StatUtils statUtils = new StatUtils();

    public StatUtilsTest() {

    }

    @Test
    public void testCalculateAverage_OddQuantityNumberList(){
        List<Integer> list = List.of(2, 4, 6, 8, 10);

        Double result = statUtils.calculateAverage(list);

        Assertions.assertEquals(6, result);
    }

    @Test
    public void testCalculateAverage_EvenQuantityNumberList(){
        List<Integer> list = List.of(2, 4, 6, 8, 10, 12);

        Double result = statUtils.calculateAverage(list);

        Assertions.assertEquals(7, result);
    }

    @Test
    public void testCalculateAverage_NonWholeNumberResult(){
        List<Integer> list = List.of(2, 2, 3, 3);

        Double result = statUtils.calculateAverage(list);

        Assertions.assertEquals(2, result);
    }

    @Test
    public void testCalculateAverage_EmptyList(){
        List<Integer> list = List.of();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            statUtils.calculateAverage(list);
        });
    }
}
