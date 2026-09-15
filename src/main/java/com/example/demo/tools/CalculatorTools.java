package com.example.demo.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    @Tool(description = " Performs basic mathematical calculations.Supported operations are: add, subtract, multiply, divide, power, and mod. The 'divide' and 'mod' operations cannot use zero as the second number.")
    public double calculate(
            @ToolParam(description = "The mathematical operation: add, subtract, multiply, divide, power, or mod")
            String op,

            @ToolParam(description = "The first number")
            double a,

            @ToolParam(description = "The second number")
            double b)  {

        System.out.println("Calculated tools called");

        if (op.equals("add")) {
            return a + b;

        } else if (op.equals("subtract")) {
            return a - b;

        } else if (op.equals("multiply")) {
            return a * b;

        } else if (op.equals("divide")) {
            if (b == 0) {
                throw new IllegalArgumentException("b should not be zero.");
            }
            return a / b;

        } else if (op.equals("power")) {
            return Math.pow(a, b);

        } else if (op.equals("mod")) {
            if (b == 0) {
                throw new IllegalArgumentException("b should not be zero.");
            }
            return a % b;

        } else {
            throw new IllegalArgumentException("Unsupported operation: " + op);
        }
    }
}
