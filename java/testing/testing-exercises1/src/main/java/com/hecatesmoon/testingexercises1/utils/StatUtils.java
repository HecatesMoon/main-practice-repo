package com.hecatesmoon.testingexercises1.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatUtils {
    
    public StatUtils(){}

    public Double calculateAverage(List<Integer> numbers){
        return numbers.stream().collect(Collectors.averagingInt(n -> n));
    }

    public Double calculateMedian(List<Integer> numbers){

        ArrayList<Integer> list = new ArrayList<>();

        list.addAll(numbers);

        list.sort(Comparator.naturalOrder());

        if (list.size() % 2 == 0){
            Integer value1 = list.get((list.size() / 2)-1);
            Integer value2 = list.get((list.size() / 2));

            return (Double.valueOf(value1) + Double.valueOf(value2)) / 2;
        } else {
            Integer result = list.get((list.size() / 2));

            return Double.valueOf(result);
        }
    }

}
