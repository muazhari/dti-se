package org.dti.se.module1session8;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Task {

    /*
    Complete the implementation of the Word Guessing Game by filling in the missing method implementations. Each method should perform a specific task in the game
    selectRandomWord(): Choose and return a random word from the WORDS array.
    hideWord(String word): Create and return a string of underscores with the same length as the input word.
    getPlayerGuess(): Prompt the player for a guess and return the guessed character.
    isGuessCorrect(String word, char guess): Check if the guessed character is in the word and return a boolean result.
    updateHiddenWord(String word, String hiddenWord, char guess): Update the hidden word by revealing the correctly guessed character and return the new hidden word.
    displayGameResult(String wordToGuess, String hiddenWord, int attemptsLeft): Show the final game result, including whether the player won or lost, and reveal the word if the player didn't guess it.
    */
    public static String selectRandomWord() {
        String[] WORDS = {"java", "python", "javascript", "ruby", "kotlin"};
        return WORDS[(int) (Math.random() * WORDS.length)];
    }

    public static String hideWord(String word) {
        return word.replaceAll(".", "_");
    }

    public static Character getPlayerGuess() {
        System.out.println("Enter your guess: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().charAt(0);
    }

    public static boolean isGuessCorrect(String word, Character guess) {
        return word.indexOf(guess) != -1;
    }

    public static String updateHiddenWord(String word, String hiddenWord, Character guess) {
        return word.replaceAll(String.format("[^%s%c]", hiddenWord, guess), "_");
    }

    public static void displayGameResult(String wordToGuess, String hiddenWord, Integer attemptsLeft) {
        System.out.println("Left attempts: " + attemptsLeft);
        if (hiddenWord.equals(wordToGuess)) {
            System.out.println("Congratulations! You guessed the word: " + wordToGuess);
        } else {
            System.out.println("You lost! The word was: " + wordToGuess);
        }
    }

    public static void runOne() {
        String wordToGuess = selectRandomWord();
        String hiddenWord = hideWord(wordToGuess);
        Integer attemptsLeft = 5;

        while (attemptsLeft > 0) {
            System.out.println("Word: " + hiddenWord);
            Character guess = getPlayerGuess();
            if (isGuessCorrect(wordToGuess, guess)) {
                hiddenWord = updateHiddenWord(wordToGuess, hiddenWord, guess);
                if (hiddenWord.equals(wordToGuess)) {
                    break;
                }
            } else {
                attemptsLeft--;
                System.out.println("Incorrect guess. Attempts left: " + attemptsLeft);
            }
        }

        displayGameResult(wordToGuess, hiddenWord, attemptsLeft);
    }

    /*
    Write a program to handle currency conversion with predefined exchange value below

    USD_TO_EUR = 0.92
    USD_TO_GBP = 0.79
    USD_TO_JPY = 147.65
    Input :
    Enter the amount to convert: 10
    Enter the source currency (USD, EUR, GBP, or JPY): JPY
    Enter the target currency (USD, EUR, GBP, or JPY): USD

    Output:
    10 USD is equal to 1476.5 JPY
    */
    public static String runTwo(Double amount, String sourceCurrency, String targetCurrency) {
        HashMap<String, Double> usdExchangeRates = new HashMap<>();
        usdExchangeRates.put("USD", 1.0);
        usdExchangeRates.put("EUR", 0.92);
        usdExchangeRates.put("GBP", 0.79);
        usdExchangeRates.put("JPY", 147.65);

        Double usdExchangeRate = usdExchangeRates.get(sourceCurrency);
        if (usdExchangeRate != null) {
            usdExchangeRate = 1 / usdExchangeRate;
        } else {
            throw new IllegalArgumentException("Invalid currency pair.");
        }

        Double targetExchangeRate = usdExchangeRates.get(targetCurrency);
        if (targetExchangeRate != null) {
            targetExchangeRate = usdExchangeRate * targetExchangeRate;
        } else {
            throw new IllegalArgumentException("Invalid currency pair.");
        }

        Double convertedAmount = amount * targetExchangeRate;

        return String.format("%s %s is equal to %s %s", amount, sourceCurrency, convertedAmount, targetCurrency);
    }

    /*
    Write a function to remove all odd numbers in an array and return a new array that contains even
    numbers only
    Example: [1,2,3,4,5,6,7,8,9,10] → [2,4,6,8,10]
    */
    public static List<Double> runThree(List<Double> input) {
        return input
                .stream()
                .parallel()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList());
    }

    /*
    Create a function that can loop the number of times according to the input we provide, and will replace multiples of 3 with "Fizz", multiples of 5 with "Buzz", multiples of 3 and 5 with
    "FizzBuzz".
    Example: n = 15 → 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz
    Example: n = 6 →1, 2, Fizz, 4, Buzz, Fizz
    */
    public static List<String> runFour(Double n) {
        return Stream
                .iterate(1, i -> i <= n, i -> i + 1)
                .parallel()
                .map(i -> {
                    if (i % 3 == 0 && i % 5 == 0) {
                        return "FizzBuzz";
                    } else if (i % 3 == 0) {
                        return "Fizz";
                    } else if (i % 5 == 0) {
                        return "Buzz";
                    } else {
                        return i.toString();
                    }
                })
                .collect(Collectors.toList());
    }

    /*
    Given an array of integers nums and an integer target, return first indices of the two numbers such that they add up to target
    Input: nums = [2,7,11,15], target = 9
    Output: [0,1]
    Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
    If no target, print: No pair exist
    */
    public static Map<Integer, Integer> runFive(List<Double> numbers, Double target) {
        return Stream
                .iterate(0, i -> i < numbers.size() - 1, i -> i + 1)
                .parallel()
                .flatMap(i -> Stream
                        .iterate(i + 1, j -> j < numbers.size(), j -> j + 1)
                        .parallel()
                        .filter(j -> numbers.get(i) + numbers.get(j) == target)
                        .map(j -> Map.of(i, j))
                        .findFirst()
                        .stream()
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No pair exist."));
    }


}


public class Main {
    public static void main(String[] args) {
        Task.runOne();

        String taskTwoOutputOne = Task.runTwo(10.0, "USD", "JPY");
        System.out.println("Task 2 Output 1: " + taskTwoOutputOne);
        assert taskTwoOutputOne.equals("10.0 USD is equal to 1476.5 JPY");

        List<Double> taskThreeOutputOne = Task.runThree(List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
        System.out.println("Task 3 Output 1: " + taskThreeOutputOne);
        assert taskThreeOutputOne.equals(List.of(2.0, 4.0, 6.0, 8.0, 10.0));

        List<String> taskFourOutputOne = Task.runFour(15.0);
        System.out.println("Task 4 Output 1: " + taskFourOutputOne);
        assert taskFourOutputOne.equals(List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"));

        List<String> taskFourOutputTwo = Task.runFour(6.0);
        System.out.println("Task 4 Output 2: " + taskFourOutputTwo);
        assert taskFourOutputTwo.equals(List.of("1", "2", "Fizz", "4", "Buzz", "Fizz"));

        Map<Integer, Integer> taskFiveOutputOne = Task.runFive(List.of(2.0, 7.0, 11.0, 15.0), 9.0);
        System.out.println("Task 5 Output 1: " + taskFiveOutputOne);
        assert taskFiveOutputOne.equals(Map.of(0, 1));

        try {
            Map<Integer, Integer> taskFiveOutputTwo = Task.runFive(List.of(2.0, 7.0, 11.0, 15.0), 0.0);
            System.out.println("Task 5 Output 2: " + taskFiveOutputTwo);
        } catch (IllegalArgumentException e) {
            System.out.println("Task 5 Output 2: " + e.getMessage());
            assert e.getMessage().equals("No pair exist.");
        }

    }
}