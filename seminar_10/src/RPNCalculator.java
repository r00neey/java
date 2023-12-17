import java.io.*;
import java.util.*;

public class RPNCalculator {
    public static double rpnCalc(String expr) {
        return rpnCalc(expr, Collections.emptyMap());
    }

    public static double rpnCalc(String expr, Map<String, Double> variables) {
        String[] tokens = expr.split("\\s+");
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else if (variables.containsKey(token)) {
                stack.push(variables.get(token));
            } else if (token.equals("#t")) {
                stack.push(1.0);
            } else if (token.equals("#f")) {
                stack.push(0.0);
            } else if (isComparisonOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: " + expr);
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                stack.push(compareValues(operand1, operand2, token));
            } else if (token.equals("?")) {
                if (stack.size() < 3) {
                    throw new IllegalArgumentException("Invalid expression: " + expr);
                }
                double condition = stack.pop();
                if (condition != 0 && condition != 1) {
                    throw new IllegalArgumentException("Invalid condition for ternary operator: " + expr);
                }
                double trueValue = stack.pop();
                double falseValue = stack.pop();
                stack.push((condition != 0) ? trueValue : falseValue);
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: " + expr);
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                stack.push(evaluateOperation(operand1, operand2, token));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: " + expr);
        }

        return stack.pop();
    }

    private static boolean isComparisonOperator(String operator) {
        return Arrays.asList(">", "<", ">=", "<=", "==", "!=").contains(operator);
    }

    private static double compareValues(double operand1, double operand2, String operator) {
        switch (operator) {
            case ">":
                return operand1 > operand2 ? 1.0 : 0.0;
            case "<":
                return operand1 < operand2 ? 1.0 : 0.0;
            case ">=":
                return operand1 >= operand2 ? 1.0 : 0.0;
            case "<=":
                return operand1 <= operand2 ? 1.0 : 0.0;
            case "==":
                return operand1 == operand2 ? 1.0 : 0.0;
            case "!=":
                return operand1 != operand2 ? 1.0 : 0.0;
            default:
                throw new IllegalArgumentException("Invalid comparison operator: " + operator);
        }
    }

    private static double evaluateOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    // Nová metoda pro načítání výrazu ze souboru
    public static String readExpressionFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(" ");
            }
            return sb.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("Error reading expression from file", e);
        }
    }

    // Nová metoda pro ukládání výsledku do souboru
    public static void saveResultToFile(String filePath, double result) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(result);
        } catch (IOException e) {
            throw new RuntimeException("Error saving result to file", e);
        }
    }

    // Nová metoda pro načítání vazeb proměnných ze souboru
    public static Map<String, Double> readVariablesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Map<String, Double> variables = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    String variable = parts[0];
                    double value = Double.parseDouble(parts[1]);
                    variables.put(variable, value);
                }
            }
            return variables;
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error reading variables from file", e);
        }
    }
}
