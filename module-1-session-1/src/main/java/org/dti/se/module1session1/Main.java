package org.dti.se.module1session1;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

abstract class Shape {

    abstract Double findArea();
}

class Rectangle extends Shape {

    private Double length;
    private Double width;

    public Rectangle(Double length, Double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    Double findArea() {
        return length * width;
    }
}

class Circle extends Shape {

    private Double radius;

    public Circle(Double radius) {
        this.radius = radius;
    }

    @Override
    Double findArea() {
        return Math.PI * radius * radius;
    }

    Double findDiameter() {
        return 2 * radius;
    }

    Double findCircumference() {
        return 2 * Math.PI * radius;
    }
}

class Triangle extends Shape {

    private Double angleOne;
    private Double angleTwo;

    public Triangle(Double angleOne, Double angleTwo) {
        this.angleOne = angleOne;
        this.angleTwo = angleTwo;
    }

    @Override
    Double findArea() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Double findAngleThree() {
        return 180 - (angleOne + angleTwo);
    }
}

class Dater {
    private LocalDate timeOne;
    private LocalDate timeTwo;

    public Dater(String timeOne, String timeTwo) {
        this.timeOne = LocalDate.parse(timeOne);
        this.timeTwo = LocalDate.parse(timeTwo);
    }

    public Long getDifferenceInDays() {
        return ChronoUnit.DAYS.between(timeOne, timeTwo);
    }

}

class Initialer {
    private String name;

    public Initialer(String name) {
        this.name = name;
    }

    public String getInitial() {
        return Arrays.stream(name.split("[ ]+"))
                .map(s -> s.substring(0, 1))
                .map(String::toUpperCase)
                .reduce("", (a, b) -> a + b);
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Rectangle rectangleOne = new Rectangle(5.0, 3.0);
        System.out.println("Task 1:");
        System.out.println("Area of Rectangle: " + rectangleOne.findArea());

        Circle circleOne = new Circle(5.0);
        System.out.println("Task 2:");
        System.out.println("Diameter of Circle: " + circleOne.findDiameter());
        System.out.println("Circumference of Circle: " + circleOne.findCircumference());
        System.out.println("Area of Circle: " + circleOne.findArea());

        Triangle triangleOne = new Triangle(80.0, 65.0);
        System.out.println("Task 3:");
        System.out.println("Angle Three of Triangle: " + triangleOne.findAngleThree());

        Dater daterOne = new Dater("2024-03-19", "2024-03-21");
        System.out.println("Task 4:");
        System.out.println("Difference in Days: " + daterOne.getDifferenceInDays());

        Initialer initialerOne = new Initialer("John Doe");
        System.out.println("Task 5:");
        System.out.println("Initial: " + initialerOne.getInitial());
    }
}