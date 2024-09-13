package org.dti.se.module1session7;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

class Task {
    /*
    Write a Java Program to Enter numbers to calculate average and enter 'q' to finish.

    Input: 1, 2, 3, 4, 5, q
    Output: 3

    Input: 1, 2, 3, a, b, 4, 5, q
    Output: 3

    Explanation: print "Invalid input. Please enter a valid number or 'q' to finish." if user not inserting the expected number or character.
    */
    public static Double runOne(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        Double sum = 0.0;
        Double count = 0.0;

        while (true) {
            System.out.print("Enter a number: ");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                break;
            }

            try {
                sum += Double.parseDouble(input);
                count++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'q' to finish.");
            }
        }

        scanner.close();

        return sum / count;
    }

    /*
    Create a csv file reader that calculates the summary of product based on the data inside the file. Make sure to handle the exceptions properly

    Example CSV file here
    Expected Output

    Total Sales: xx
    Total Product Sold: xx
    Most Bought Product:  product_xx
    Least Bought Product: product_xx
    */
    public static void runTwo() throws IOException {
        Path path = Path.of("module-1-session-7/src/main/resources/product_sales_data.csv");
        Scanner scanner = new Scanner(path);

        Double totalSales = 0.0;
        Double totalProductSold = 0.0;
        Map.Entry<String, Double> mostBoughProduct = Map.entry("", Double.MIN_VALUE);
        Map.Entry<String, Double> leastBoughProduct = Map.entry("", Double.MAX_VALUE);

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            String productName = data[0];
            System.out.println(Arrays.stream(data).toList());
            Double totalSold = Double.parseDouble(data[1]);
            Double itemPrice = Double.parseDouble(data[2]);

            totalSales += totalSold * itemPrice;
            totalProductSold += totalSold;

            if (mostBoughProduct.getValue() < totalSold) {
                mostBoughProduct = Map.entry(productName, totalSold);
            }

            if (leastBoughProduct.getValue() > totalSold) {
                leastBoughProduct = Map.entry(productName, totalSold);
            }
        }

        scanner.close();

        System.out.println("Total Sales: " + totalSales);
        System.out.println("Total Product Sold: " + totalProductSold);
        System.out.println("Most Bought Product: " + mostBoughProduct.getKey());
        System.out.println("Least Bought Product: " + leastBoughProduct.getKey());
    }
}

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = new ByteArrayInputStream("1\n2\n3\n4\n5\nq\n".getBytes());
        Double taskOneOutputOne = Task.runOne(inputStream);
        System.out.println(taskOneOutputOne);
        assert taskOneOutputOne.equals(3.0);

        inputStream = new ByteArrayInputStream("1\n2\n3\na\nb\n4\n5\nq\n".getBytes());
        Double taskOneOutputTwo = Task.runOne(inputStream);
        System.out.println(taskOneOutputTwo);
        assert taskOneOutputTwo.equals(3.0);

        try {
            Task.runTwo();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}