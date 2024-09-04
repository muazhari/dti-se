package org.dti.se;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Task {
    /*
    Write a program to calculate the sum of digits in a given number.
    Input: 1234
    Output: 10
    */
    public static Double proceedOne(Double input) {
        return Stream
                .of(input.toString().replaceAll("[^0-9]", "").split(""))
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    /*
    Create a function to check if a number is prime.
    Input: 17
    Output: true
    Input: 4
    Output: false
    */
    public static Boolean proceedTwo(Double input) {
        // https://stackoverflow.com/a/29155554/10912304
        return BigInteger.valueOf(input.longValue()).isProbablePrime(Integer.MAX_VALUE);
    }

    /*
    Write a program to find the largest element in an array.
    Input: [3, 7, 2, 8, 1]
    Output: 8
    */
    public static Double proceedThree(List<Double> input) {
        return input
                .stream()
                .parallel()
                .max(Double::compareTo)
                .orElseThrow();
    }

    /*
    Implement a simple calculator that can perform addition, subtraction, multiplication, and division.
    Input: 5 + 3
    Output: 8
    Input: 10 / 2
    Output: 5
    */
    public static Double proceedFour(String input) {
        return Stream
                .of(input.split("[+\\-*/]"))
                .map(String::strip)
                .map(Double::parseDouble)
                .reduce((a, b) -> {
                    if (input.contains("+")) {
                        return a + b;
                    } else if (input.contains("-")) {
                        return a - b;
                    } else if (input.contains("*")) {
                        return a * b;
                    } else if (input.contains("/")) {
                        return a / b;
                    } else {
                        throw new IllegalArgumentException("Invalid input");
                    }
                })
                .orElseThrow();
    }

    /*
    Write a function to reverse a string without using built-in reverse functions.
    Input: "hello"
    Output: "olleh"
    */
    public static String proceedFive(String input) {
        return Stream
                .of(input.split(""))
                .reduce("", (accumulated, current) -> current + accumulated);
    }

    /*
    Create a program to generate the Fibonacci sequence up to n terms.
    Input: 6
    Output: 0 1 1 2 3 5
    */
    public static String proceedSix(Double input) {
        Double[] cache = new Double[input.intValue()];
        cache[0] = 0.0;
        cache[1] = 1.0;
        for (int term = 2; term < input; term++) {
            cache[term] = cache[term - 1] + cache[term - 2];
        }

        return Stream
                .of(cache)
                .map(String::valueOf)
                .reduce("", (accumulated, current) -> accumulated + " " + current)
                .strip();
    }

    /*
    Write a function to count the number of vowels in a string.
    Input: "programming"
    Output: 3
    */
    public static Double proceedSeven(String input) {
        return (double) input.replaceAll("[^aiueoAIUEO]", "").length();
    }

    /*
    Implement a program to sort an array of integers using bubble sort. Pls Without built in methods :)
    Input: [64, 34, 25, 12, 22, 11, 90]
    Output: [11, 12, 22, 25, 34, 64, 90]
    */
    public static List<Double> proceedEight(List<Double> input) {
        ArrayList<Double> copiedInput = new ArrayList<>(input);
        for (int i = 0; i < copiedInput.size(); i++) {
            for (int j = 0; j < copiedInput.size() - i - 1; j++) {
                if (copiedInput.get(j) > copiedInput.get(j + 1)) {
                    Double temp = copiedInput.get(j);
                    copiedInput.set(j, copiedInput.get(j + 1));
                    copiedInput.set(j + 1, temp);
                }
            }
        }

        return copiedInput;
    }

    /*
    Create a function to check if two strings are anagrams.
    Input: "listen", "silent"
    Output: true
    Input: "hello", "world"
    Output: false
    */
    public static Boolean proceedNine(String inputOne, String inputTwo) {
        List<Map<String, Long>> maps = Stream
                .of(inputOne.split(""), inputTwo.split(""))
                .parallel()
                .map(array -> Stream
                        .of(array)
                        .collect(Collectors.groupingBy(substring -> substring, Collectors.counting()))
                )
                .collect(Collectors.toList());

        return maps
                .stream()
                .parallel()
                .allMatch(map -> map.equals(maps.getFirst()));
    }

    /*
    Write a program to find the second smallest element in an array.
    Input: [5, 3, 8, 1, 2, 9]
    Output: 2
    */
    public static Double proceedTen(List<Double> input) {
        return input
                .stream()
                .parallel()
                .sorted()
                .skip(1)
                .findFirst()
                .orElseThrow();
    }

}

public class Main {
    public static void main(String[] args) {
        Double taskOneOutputOne = Task.proceedOne(1234.0);
        System.out.println("Task 1 Output 1: " + taskOneOutputOne);
        assert taskOneOutputOne == 10.0;

        Boolean taskTwoOutputOne = Task.proceedTwo(17.0);
        System.out.println("Task 2 Output 1: " + taskTwoOutputOne);
        assert taskTwoOutputOne == true;

        Boolean taskTwoOutputTwo = Task.proceedTwo(4.0);
        System.out.println("Task 2 Output 2: " + taskTwoOutputTwo);
        assert taskTwoOutputTwo == false;

        Double taskThreeOutputOne = Task.proceedThree(List.of(3.0, 7.0, 2.0, 8.0, 1.0));
        System.out.println("Task 3 Output 1: " + taskThreeOutputOne);
        assert taskThreeOutputOne == 8.0;

        Double taskFourOutputOne = Task.proceedFour("5 + 3");
        System.out.println("Task 4 Output 1: " + taskFourOutputOne);
        assert taskFourOutputOne == 8.0;

        Double taskFourOutputTwo = Task.proceedFour("10 / 2");
        System.out.println("Task 4 Output 2: " + taskFourOutputTwo);
        assert taskFourOutputTwo == 5.0;

        String taskFiveOutputOne = Task.proceedFive("hello");
        System.out.println("Task 5 Output 1: " + taskFiveOutputOne);
        assert taskFiveOutputOne.equals("olleh");

        String taskSixOutputOne = Task.proceedSix(6.0);
        System.out.println("Task 6 Output 1: " + taskSixOutputOne);
        assert taskSixOutputOne.equals("0.0 1.0 1.0 2.0 3.0 5.0");

        Double taskSevenOutputOne = Task.proceedSeven("programming");
        System.out.println("Task 7 Output 1: " + taskSevenOutputOne);
        assert taskSevenOutputOne == 3.0;

        List<Double> taskEightOutputOne = Task.proceedEight(List.of(64.0, 34.0, 25.0, 12.0, 22.0, 11.0, 90.0));
        System.out.println("Task 8 Output 1: " + taskEightOutputOne);
        assert taskEightOutputOne.equals(List.of(11.0, 12.0, 22.0, 25.0, 34.0, 64.0, 90.0));

        Boolean taskNineOutputOne = Task.proceedNine("listen", "silent");
        System.out.println("Task 9 Output 1: " + taskNineOutputOne);
        assert taskNineOutputOne == true;

        Boolean taskNineOutputTwo = Task.proceedNine("hello", "world");
        System.out.println("Task 9 Output 2: " + taskNineOutputTwo);
        assert taskNineOutputTwo == false;

        Double taskTenOutputOne = Task.proceedTen(List.of(5.0, 3.0, 8.0, 1.0, 2.0, 9.0));
        System.out.println("Task 10 Output 1: " + taskTenOutputOne);
        assert taskTenOutputOne == 2.0;
    }

}