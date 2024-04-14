import org.apache.commons.lang3.StringUtils;
import util.variant_1.Parser_1;
import java.util.Scanner;

public class Calculator {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Введите задание и нажмите enter\nДля завершения, просто нажмите enter");

        Parser_1 calculator = new Parser_1();
        while (scanner.hasNextLine()) {
            String expression = scanner.nextLine();

            if (StringUtils.isBlank(expression)) {
                System.out.println("Закрытие калькулятора");
                break;
            }

            String result = "ERROR";

            try {
                // подготовительные операции
                expression = formatInputData(calculator, expression);
                // вычисление выражения
                result = calculator.calculate(expression);
                System.out.println(expression + " = " + result);
            } catch (Exception exception) {
                System.out.println(expression + " = " + result);
                System.out.println(exception.toString());
            }
            System.out.println("Введите задание и нажмите enter\nДля завершения, просто нажмите enter");
        }
    }

    private static String formatInputData(Parser_1 calculator, String expression) {
        expression = calculator.deleteSpaces(expression);
        expression = calculator.fixNegativeValue(expression);
        expression = calculator.fixMultipleSignLack(expression);
        return expression;
    }
}