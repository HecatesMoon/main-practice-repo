package com.hecatesmoon.testingexercises1.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StatUtils {
    
    public StatUtils(){}

    public Double calculateAverage(List<Integer> numbers){

        if (numbers.isEmpty()) throw new IllegalArgumentException("list is empty");

        return numbers.stream().collect(Collectors.averagingInt(n -> n));
    }

    public Double calculateMedian(List<Integer> numbers){

        if (numbers.isEmpty()) throw new IllegalArgumentException("list is empty");

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

    public Optional<Integer> calculateMode(List<Integer> numbers){

        if (numbers.isEmpty()) throw new IllegalArgumentException("list is empty");

       Map<Integer, Long> frequency = numbers.stream().collect(Collectors.groupingBy(n -> n, Collectors.counting()));

        List<Integer> orderedForFrequency = frequency.keySet().stream()
                                                    .sorted(Comparator.comparingLong(frequency::get).reversed())
                                                    .toList();

        List<Optional<Integer>> modeListKeys = orderedForFrequency.stream()
                                                    .filter(k -> frequency.get(k) == frequency.get(orderedForFrequency.get(0)))
                                                    .map(k -> Optional.of(k))
                                                    .toList();

        if (modeListKeys.size() == orderedForFrequency.size()) {
            return Optional.empty();
        } else {
            return modeListKeys.get(0); //made the method to give more than one mode
        }
    }

}
