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




    @Test
    public void testCalculateMedian_OddQuantityNumberSortedList(){
        List<Integer> list = List.of(1, 2, 3, 4, 5);

        Double result = statUtils.calculateMedian(list);

        Assertions.assertEquals(3, result);
    }

    @Test
    public void testCalculateMedian_EvenQuantityNumberSortedList(){
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6);

        Double result = statUtils.calculateMedian(list);

        Assertions.assertEquals(3.5, result);
    }

    @Test
    public void testCalculateMedian_OddQuantityNumberUnsortedList(){
        List<Integer> list = List.of(8, 4, 12, 3, 15);

        Double result = statUtils.calculateMedian(list);

        Assertions.assertEquals(8, result);
    }

    @Test
    public void testCalculateMedian_EvenQuantityNumberUnsortedList(){
        List<Integer> list = List.of(8, 4, 12, 3, 15, 23, 5, 7);

        Double result = statUtils.calculateMedian(list);

        Assertions.assertEquals(7.5, result);
    }

    @Test
    public void testCalculateMedian_EmptyList(){
        List<Integer> list = List.of();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            statUtils.calculateMedian(list);
        });
    }
}