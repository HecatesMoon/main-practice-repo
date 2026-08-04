package com.hecatesmoon.testingexercises1.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StatUtils {
    
    public StatUtils(){}

    public Double calculateAverage(List<Integer> numbers){
        return numbers.stream().collect(Collectors.averagingInt(n -> n));
    }

}
