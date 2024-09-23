abstract class Shape {
    abstract area(...args: never): number;
}

class Rectangle extends Shape {
    protected length: number;
    protected width: number;

    public constructor(length: number, width: number) {
        super();
        this.length = length;
        this.width = width;
    }


    public area(): number {
        return this.length * this.width;
    }

}

class Circle extends Shape {
    protected radius: number;

    public constructor(radius: number) {
        super();
        this.radius = radius;
    }

    public area(): number {
        return Math.PI * this.radius * this.radius;
    }

    public circumference(): number {
        return 2 * Math.PI * this.radius;
    }

    public diameter(): number {
        return 2 * this.radius;
    }
}

class CircleResult {
    public diameter: number;
    public circumference: number;
    public area: number;

    public constructor(diameter: number, circumference: number, area: number) {
        this.diameter = diameter;
        this.circumference = circumference;
        this.area = area;
    }
}

class Triangle extends Shape {
    protected angleA: number;
    protected angleB: number;

    public constructor(angleA: number, angleB: number) {
        super();
        this.angleA = angleA;
        this.angleB = angleB;
    }

    public area(): number {
        throw new Error("Method not implemented.");
    }

    public angleC(): number {
        return 180 - this.angleA - this.angleB;
    }

}


class Dater {
    public static differenceInDays(date1: Date, date2: Date): number {
        const diffTime = Math.abs(date2.getTime() - date1.getTime());
        return diffTime / (1000 * 60 * 60 * 24);
    }
}

class Initialer {
    public static getInitials(name: string): string {
        const names = name.split(/\s+/);
        return names.map((name) => name[0].toUpperCase()).join('');
    }
}

/*
Write a code to find area of rectangle
Input: length = 5, width = 3
Output: 15
*/
const taskOne = (length: number, width: number): number => {
    const rectangle = new Rectangle(length, width);
    return rectangle.area();
}

/*
Write a code to find diameter, circumference and area of a circle
Input: radius = 5
Output : diameter = 10, circumference = 31.4159, area = 78.539
*/
const taskTwo = (radius: number): CircleResult => {
    const circle = new Circle(radius);
    return new CircleResult(circle.diameter(), circle.circumference(), circle.area());
}

/*
Write a code to find angles of triangle if two angles are given
Input: a = 80, b = 65
Output: 35
*/
const taskThree = (a: number, b: number): number => {
    const triangle = new Triangle(a, b);
    return triangle.angleC();
}


/*
Write a code to get difference between dates in days.
Input: date1 = 2024-03-19, date2 = 2024-03-21
Output: 2
*/
const taskFour = (date1: Date, date2: Date): number => {
    return Dater.differenceInDays(date1, date2);
}

/*
Write a code to print your name initial in
uppercase
Input: John Doe
Output: JD
*/
const taskFive = (name: string): string => {
    return Initialer.getInitials(name);
}


export {
    taskOne,
    taskTwo,
    taskThree,
    taskFour,
    taskFive
};
