package org.dti.se;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Task {
    /*
    Java Array Program For Array Rotation

    Input: arr[] = {1, 2, 3, 4, 5, 6, 7},  d = 2
    Output:  3 4 5 6 7 1 2

    Explanation: d=2 so 2 elements are rotated to the end of the array. So, 1 2 is rotated back
    So, Final result: 3, 4, 5, 6, 7, 1, 2
    */
    public static List<Double> runOne(List<Double> arr, Double d) {
        return Stream
                .iterate(0, i -> i < arr.size(), i -> i + 1)
                .parallel()
                .map(i -> arr.get((i + d.intValue()) % arr.size()))
                .collect(Collectors.toList());
    }

    /*
    Write a Java Program to Check if Array Contain Duplicates
    Example 1:
    Input: nums = [1,2,3,1]
    Output: true

    Example 2:
    Input: nums = [1,2,3,4]
    Output: false

    Example 3:
    Input: nums = [1,1,1,3,3,4,3,2,4,2]
    Output: true
    */
    public static Boolean runTwo(List<Double> nums) {
        return nums
                .stream()
                .parallel()
                .distinct()
                .count() != nums.size();
    }

    /*
    Java Array Program to Remove Duplicate Elements From an Array

    Input: [ 1, 2, 2, 3, 3, 3, 4, 5 ]
    Output: [ 1, 2, 3, 4, 5 ]
    */
    public static List<Double> runThree(List<Double> arr) {
        return arr
                .stream()
                .parallel()
                .distinct()
                .collect(Collectors.toList());
    }

    /*
    Java Array Program to sort array in increasing & decreasing order

    Example 1:
    Input: [ 8, 7, 5, 2], direction = "asc"
    Output: [ 2, 5, 7, 8 ]

    Example 2:
    Input: [ 8, 7, 5, 2], direction = "desc"
    Output: [ 8,7, 5, 2 ]
    */
    public static List<Double> runFour(List<Double> arr, String direction) {
        return arr
                .stream()
                .parallel()
                .sorted((a, b) -> direction.equals("asc") ? Double.compare(a, b) : Double.compare(b, a))
                .collect(Collectors.toList());
    }

    /*
    Java Array Program to Remove All Occurrences of an Element in an Array

    Input: array = [ 1, 2, 1, 3, 5, 1 ] , key = 1
    Output: [2, 3, 5]

    Explanation: all the occurrences of element 1 is removed from the array So, array becomes from
    [ 1, 2, 1, 3, 5, 1 ]  to
    Final result: [2, 3, 5]
    */
    public static List<Double> runFive(List<Double> arr, Double key) {
        return arr
                .stream()
                .parallel()
                .filter(i -> !i.equals(key))
                .collect(Collectors.toList());
    }

    /*
    Java Program to Reverse a String Without Using Built-in Methods

    Example 1:
    Input: "malang"
    Output: "gnalam"

    Example 2:
    Input: "mas"
    Output: "sam"
    */
    public static String runSix(String str) {
        return Stream
                .iterate(str.length() - 1, i -> i >= 0, i -> i - 1)
                .parallel()
                .map(str::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /*
    Java String Program to Find all Duplicates on Array

    Example 1:
    Input: nums = [4,3,2,7,8,2,3,1]
    Output: [2,3]

    Example 2:
    Input: nums = [1,1,2]
    Output: [1]

    Example 3:
    Input: nums = [1]
    Output: []
    */
    public static List<Double> runSeven(List<Double> nums) {
        return nums
                .stream()
                .parallel()
                .collect(Collectors.groupingBy(i -> i))
                .entrySet()
                .stream()
                .parallel()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /*
    Java Program to get the number of days you have to wait after the i-th day to get a warmer temperature

    Example 1:
    Input: temperatures = [73,74,75,71,69,72,76,73]
    Output: [1,1,4,2,1,1,0,0]

    Example 2:
    Input: temperatures = [30,40,50,60]
    Output: [1,1,1,0]

    Example 3:
    Input: temperatures = [30,60,90]
    Output: [1,1,0]
    */
    public static List<Double> runEight(List<Double> temperatures) {
        return Stream
                .iterate(0, i -> i < temperatures.size(), i -> i + 1)
                .parallel()
                .map(i -> Stream
                        .iterate(i + 1, j -> j < temperatures.size(), j -> j + 1)
                        .parallel()
                        .filter(j -> temperatures.get(j) > temperatures.get(i))
                        .map(j -> Double.valueOf(j - i))
                        .min(Double::compare)
                        .orElse(0.0)
                )
                .collect(Collectors.toList());
    }
}

public class Main {

    public static void main(String[] args) {
        var taskOneOutputOne = Task.runOne(List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0), 2.0);
        System.out.println(taskOneOutputOne);
        assert taskOneOutputOne.equals(List.of(3.0, 4.0, 5.0, 6.0, 7.0, 1.0, 2.0));

        var taskTwoOutputOne = Task.runTwo(List.of(1.0, 2.0, 3.0, 1.0));
        System.out.println(taskTwoOutputOne);
        assert taskTwoOutputOne.equals(true);

        var taskTwoOutputTwo = Task.runTwo(List.of(1.0, 2.0, 3.0, 4.0));
        System.out.println(taskTwoOutputTwo);
        assert taskTwoOutputTwo.equals(false);

        var taskTwoOutputThree = Task.runTwo(List.of(1.0, 1.0, 1.0, 3.0, 3.0, 4.0, 3.0, 2.0, 4.0, 2.0));
        System.out.println(taskTwoOutputThree);
        assert taskTwoOutputThree.equals(true);

        var taskThreeOutputOne = Task.runThree(List.of(1.0, 2.0, 2.0, 3.0, 3.0, 3.0, 4.0, 5.0));
        System.out.println(taskThreeOutputOne);
        assert taskThreeOutputOne.equals(List.of(1.0, 2.0, 3.0, 4.0, 5.0));

        var taskFourOutputOne = Task.runFour(List.of(8.0, 7.0, 5.0, 2.0), "asc");
        System.out.println(taskFourOutputOne);
        assert taskFourOutputOne.equals(List.of(2.0, 5.0, 7.0, 8.0));

        var taskFourOutputTwo = Task.runFour(List.of(8.0, 7.0, 5.0, 2.0), "desc");
        System.out.println(taskFourOutputTwo);
        assert taskFourOutputTwo.equals(List.of(8.0, 7.0, 5.0, 2.0));

        var taskFiveOutputOne = Task.runFive(List.of(1.0, 2.0, 1.0, 3.0, 5.0, 1.0), 1.0);
        System.out.println(taskFiveOutputOne);
        assert taskFiveOutputOne.equals(List.of(2.0, 3.0, 5.0));

        var taskSixOutputOne = Task.runSix("malang");
        System.out.println(taskSixOutputOne);
        assert taskSixOutputOne.equals("gnalam");

        var taskSixOutputTwo = Task.runSix("mas");
        System.out.println(taskSixOutputTwo);
        assert taskSixOutputTwo.equals("sam");

        var taskSevenOutputOne = Task.runSeven(List.of(4.0, 3.0, 2.0, 7.0, 8.0, 2.0, 3.0, 1.0));
        System.out.println(taskSevenOutputOne);
        assert taskSevenOutputOne.equals(List.of(2.0, 3.0));

        var taskSevenOutputTwo = Task.runSeven(List.of(1.0, 1.0, 2.0));
        System.out.println(taskSevenOutputTwo);
        assert taskSevenOutputTwo.equals(List.of(1.0));

        var taskSevenOutputThree = Task.runSeven(List.of(1.0));
        System.out.println(taskSevenOutputThree);
        assert taskSevenOutputThree.equals(List.of());

        var taskEightOutputOne = Task.runEight(List.of(73.0, 74.0, 75.0, 71.0, 69.0, 72.0, 76.0, 73.0));
        System.out.println(taskEightOutputOne);
        assert taskEightOutputOne.equals(List.of(1.0, 1.0, 4.0, 2.0, 1.0, 1.0, 0.0, 0.0));

        var taskEightOutputTwo = Task.runEight(List.of(30.0, 40.0, 50.0, 60.0));
        System.out.println(taskEightOutputTwo);
        assert taskEightOutputTwo.equals(List.of(1.0, 1.0, 1.0, 0.0));

        var taskEightOutputThree = Task.runEight(List.of(30.0, 60.0, 90.0));
        System.out.println(taskEightOutputThree);
        assert taskEightOutputThree.equals(List.of(1.0, 1.0, 0.0));
    }
}
