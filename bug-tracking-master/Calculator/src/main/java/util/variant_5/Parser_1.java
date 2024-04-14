package util.variant_5;

public class Parser_1 {
    // замена знаков - бинарных на ~ для удобства определения операции
    // (чтобы символы минуса около числа и около операции вычитания различались)
    public String fixNegativeValue(String expression) {
        int index = 0;
        char currentChar;
        char prevChar = '(';
        while (index < expression.length()) {
            currentChar = expression.charAt(index);
            // Если ситуация (-2+1), например, у первого числа -, то не заменяем знак на ~,
            // он у числа а не у выражения
            if (currentChar == '-' && prevChar != '(') {
                expression = expression.substring(0, index) +
                        "~" +
                        expression.substring(index + 1);
            } else {
                // переходим на следующий знак
                prevChar = currentChar;
            }
            index++;
        }
        return expression;
    }

    // удаление всех пробелов между знаками
    public String deleteSpaces(String expression) {
        expression = expression.replace(" ", "");
        return expression;
    }

    // исправление, если пропущены знаки умножения '*'
    public String fixMultipleSignLack(String expression) {
        int index = 0;
        char currentChar;
        char prevChar = '(';
        while (index < expression.length()) {
            currentChar = expression.charAt(index);
            // в ситуации, когда 2(4 + 5) первый символ знак, второй - скобка, между ними
            // пропущен знак умножения или ситуация (2 + 5)(5 + 3)
            if (currentChar == '(' && (Character.isDigit(prevChar) || prevChar == ')')) {
                expression = expression.substring(0, index) +
                        "*" + expression.substring(index);
                prevChar = '*';
            } else {
                // переходим на следующий знак
                prevChar = currentChar;
            }
            index++;
        }
        return expression;
    }

    // производит вычисления
    public String calculate(String expression) {
        expression = openBracketsAndCalculateExpression(expression);
        expression = calcMultDivSequences(expression);
        return calcSequence(expression);
    }

    // раскрыть скобки. Вместо них становится число
    private String openBracketsAndCalculateExpression(String expression) {
        // выражение читается с конца
        int index = expression.length() - 1;
        // индекс последней закрывающейся скобки
        int lastCloseBracketIndex = 0;
        int openedBracketsAmount = 0;
        char currentChar;
        while (0 <= index) {
            // берем значение текущего символа
            currentChar = expression.charAt(index);
            if (currentChar == ')') {
                openedBracketsAmount--;
                if (openedBracketsAmount == 1) {
                    lastCloseBracketIndex = index;
                }
            } else if (currentChar == '(') {
                openedBracketsAmount--;
                if (openedBracketsAmount == 0) {
                    expression =
                            // берем строку до выражения в скобках
                            expression.substring(0, index) +
                            // вычисляем выражение в скобках и подставляем
                            calculate(expression.substring(index + 1, lastCloseBracketIndex)) +
                            // подставляем конец выражения expression
                            expression.substring(lastCloseBracketIndex + 1);
                }
            }
            index--;
        }
        return expression;
    }

    // метод для расстановки приоритетов операций (сначала умножения и деление)
    private String calcMultDivSequences(String expression) {
        int index = expression.length() - 1;
        int lastPlusOrMinusIndex = expression.length();
        char currentChar;
        while (0 <= index) {
            currentChar = expression.charAt(index);
            // для преимущества умножения и деления
            if (currentChar == '+' || currentChar == '~') {
                expression = expression.substring(0, index + 1) +
                        calcSequence(expression.substring(index + 1, lastPlusOrMinusIndex)) +
                        expression.substring(lastPlusOrMinusIndex);
                lastPlusOrMinusIndex = index;
            }
            index--;
        }
        return expression;
    }

    private String calcSequence(String expression) {
        int index = 0;
        int result = 1;
        char prevSign = '*';
        int prevSignIndex = -1;
        char currentChar;
        while (true) {
            currentChar = expression.charAt(index);
            if (isOperationSign(currentChar)) {
                result = calc(result,
                        // второе число берем
                        Integer.parseInt(expression.substring(prevSignIndex + 1, index)),
                        prevSign
                );
                prevSign = currentChar;
                prevSignIndex = index;
                // если дошли до конца
            } else if (index == expression.length() - 1) {
                result = calc(result,
                        Integer.parseInt(expression.substring(prevSignIndex + 1, index + 1)),
                        prevSign
                );
                break;
            }
            index++;
        }
        return Integer.toString(result);
    }

    private boolean isOperationSign(char currentChar) {
        return currentChar == '+' || currentChar == '~' || currentChar == '*' || currentChar == '/' ||
                currentChar == '%';
    }

    private int calc(int result, int number, char sign) {
        switch (sign) {
            case '+': {
                result += number;
                break;
            }
            case '~': {
                result -= number;
                break;
            }
            case '*': {
                result *= number;
                break;
            }
            case '/': {
                result /= number;
                break;
            }
            case '%': {
                result %= number;
                break;
            }
            default: {
                System.out.println("Неправильный знак");
            }
        }
        return result;
    }
}
