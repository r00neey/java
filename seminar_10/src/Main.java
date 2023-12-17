import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("foo", 2.0);
        System.out.println(RPNCalculator.rpnCalc("1.1 2 +"));
        System.out.println(RPNCalculator.rpnCalc("1 2 + 4 * 6 +"));
        System.out.println(RPNCalculator.rpnCalc("1 foo +", variables));
        //System.out.println(RPNCalculator.rpnCalc("1 foox +", variables));
        System.out.println(RPNCalculator.rpnCalc("1.1 2 <"));
        System.out.println(RPNCalculator.rpnCalc("#f #t <"));
        System.out.println(RPNCalculator.rpnCalc("#f #t >"));
        System.out.println(RPNCalculator.rpnCalc("1 2 3 4 < ?"));
        //System.out.println(RPNCalculator.rpnCalc("1 #f ="));
        //System.out.println(RPNCalculator.rpnCalc("1 2 3 4 + ?"));
        //System.out.println(RPNCalculator.rpnCalc("1 0 /"));
        String expression = RPNCalculator.readExpressionFromFile("input.txt");
        Map<String, Double> variables2 = RPNCalculator.readVariablesFromFile("variables.txt");
        double result = RPNCalculator.rpnCalc(expression, variables2);
        System.out.println("Result: " + result);
        RPNCalculator.saveResultToFile("output.txt", result);
    }
}