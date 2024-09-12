package org.dti.se.module1session8;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Task {

    /*
    Create a program that prints a multiplication table up to n x n.
    Input: 5
    Output:
    1 2 3 4 5
    2 4 6 8 10
    3 6 9 12 15
    4 8 12 16 20
    5 10 15 20 25
    */
    public static String runOne(Double input) {
        return Stream
                .iterate(1, i -> i + 1)
                .parallel()
                .limit(input.intValue())
                .map(i -> Stream
                        .iterate(i, j -> j + i)
                        .parallel()
                        .limit(input.intValue())
                        .map(Object::toString)
                        .collect(Collectors.joining(" "))
                )
                .collect(Collectors.joining("\n"));
    }

    /*
    Write a program to print the following pattern for n rows
    *
    **
    ***
    ****
    *****
    */
    public static String runTwo(Double input) {
        return Stream
                .iterate(1, i -> i + 1)
                .parallel()
                .limit(input.intValue())
                .map(i -> Stream
                        .iterate(1, j -> j + 1)
                        .parallel()
                        .limit(i)
                        .map(j -> "*")
                        .collect(Collectors.joining())
                )
                .collect(Collectors.joining("\n"));
    }

    /*
    Write a code that reads n number of input from scanner
    Example: 1 (press y to continue), 2 (press n to stop) → 1,2
    */
    public static String runThree() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();
        while (true) {
            System.out.println("Enter a number: ");
            System.out.println("Press n to stop");
            String input = scanner.next();
            if (input.equals("n")) {
                break;
            }
            inputs.add(input);
        }

        return inputs
                .stream()
                .parallel()
                .collect(Collectors.joining(","));
    }

    /*
    Create a simple number guessing game where the computer generates a random number between 1 and 100, and the user tries to guess it. The program should provide hints like "Too high" or "Too low" after each guess.
    The program generates a random number.
    It prompts the user to enter a guess.
    It provides feedback ("Too high", "Too low", or "Correct!") after each guess.
    The game continues until the user guesses correctly.
    After the correct guess, it displays the number of attempts taken.
    */
    public static Double runFour() {
        Scanner scanner = new Scanner(System.in);
        Double randomNumber = Math.floor(Math.random() * 100) + 1;
        Double attempts = 0.0;
        Double guess = null;

        while (!Objects.equals(guess, randomNumber)) {
            System.out.println("Enter your guess: ");
            guess = scanner.nextDouble();
            attempts++;
            if (guess > randomNumber) {
                System.out.println("Too high");
            } else if (guess < randomNumber) {
                System.out.println("Too low");
            } else {
                System.out.println("Correct!");
            }
        }

        return attempts;
    }

    /*
    Write a code to swap the case of each character from string
    Example : ‘The QuiCk BrOwN Fox’ -> ‘ tHE qUIcK bRoWn fOX’
    */
    public static String runFive(String input) {
        return Stream
                .of(input.split(""))
                .parallel()
                .map(substring -> {
                    if (!substring.matches("[a-zA-Z]")) {
                        return substring;
                    }

                    if (Character.isUpperCase(substring.charAt(0))) {
                        return substring.toLowerCase();
                    } else {
                        return substring.toUpperCase();
                    }
                })
                .collect(Collectors.joining());
    }


}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String taskOneOutputOne = Task.runOne(5.0);
        System.out.println("Task 1 Output 1: \n" + taskOneOutputOne);
        assert taskOneOutputOne.equals("1 2 3 4 5\n2 4 6 8 10\n3 6 9 12 15\n4 8 12 16 20\n5 10 15 20 25");

        String taskTwoOutputOne = Task.runTwo(5.0);
        System.out.println("Task 2 Output 1: \n" + taskTwoOutputOne);
        assert taskTwoOutputOne.equals("*\n**\n***\n****\n*****");

        String taskThreeOutputOne = Task.runThree();
        System.out.println("Task 3 Output 1: " + taskThreeOutputOne);
        assert taskThreeOutputOne.equals("1,2");

        Double taskFourOutputOne = Task.runFour();
        System.out.println("Task 4 Output 1: " + taskFourOutputOne);
        assert taskFourOutputOne > 0.0;

        String taskFiveOutputOne = Task.runFive("The QuiCk BrOwN Fox");
        System.out.println("Task 5 Output 1: " + taskFiveOutputOne);
        assert taskFiveOutputOne.equals("tHE qUIcK bRoWn fOX");
    }
}