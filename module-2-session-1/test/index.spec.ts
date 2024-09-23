import {taskFive, taskFour, taskOne, taskThree, taskTwo} from "../src";
import "mocha";
import * as chai from 'chai'

chai.should()

describe("tasks", () => {
    it("taskOne should return 15", () => {
        const result = taskOne(5, 3);
        result.should.equal(15);
    })

    it("taskTwo should return CircleResult", () => {
        const result = taskTwo(5);
        result.diameter.should.equal(10);
        result.circumference.should.equal(31.41592653589793);
        result.area.should.equal(78.53981633974483);
    })

    it("taskThree should return 35", () => {
        const result = taskThree(80, 65);
        result.should.equal(35);
    })

    it("taskFour should return 2", () => {
        const result = taskFour(new Date("2024-03-19"), new Date("2024-03-21"));
        result.should.equal(2);
    })

    it("taskFive should return JD", () => {
        const result = taskFive("John Doe");
        result.should.equal("JD");
    })
});
