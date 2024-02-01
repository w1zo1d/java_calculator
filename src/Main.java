import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input:");
            String input = scanner.nextLine();
            try {
                String result = calc(input);
                System.out.println("Output: " + result);
            } catch (Exception e) {
                System.out.println("Output: " + e.getMessage());
                System.exit(0);
            }
        }
    }

    static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        NumberType numberType1 = getNumberType(operand1);
        NumberType numberType2 = getNumberType(operand2);

        if (numberType1 != numberType2) {
            throw new Exception("Используются одновременно разные системы счисления");
        }


        int num1 = getNumber(operand1, numberType1);
        int num2 = getNumber(operand2, numberType1);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Формат математической операции не удовлетворяет заданию");
        }

        String result;
        switch (operator) {
            case "+":
                result = String.valueOf(num1 + num2);
                break;
            case "-":
                result = String.valueOf(num1 - num2);
                break;
            case "*":
                result = String.valueOf(num1 * num2);
                break;
            case "/":
                result = String.valueOf(num1 / num2);
                break;
            default:
                throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        return formatResult(result, numberType1);
    }

    private static NumberType getNumberType(String str) {
        return str.matches("^[IVXLCD]+$") ? NumberType.ROMAN : NumberType.ARABIC;
    }

    private static int getNumber(String str, NumberType type) throws Exception {
        try {
            return (type == NumberType.ROMAN) ? romanToArabic(str) : Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new Exception("Формат математической операции не удовлетворяет заданию");
        }
    }

    private static String formatResult(String result, NumberType type) {
        return (type == NumberType.ROMAN) ? arabicToRoman(Integer.parseInt(result)) : result;
    }

    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(roman.charAt(i));
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }
        return result;
    }

    private static String arabicToRoman(int arabic) {
        if (arabic < 1) {
            throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
        }

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] arabicValues = {1, 4, 5, 9, 10, 40, 50, 90, 100};

        StringBuilder result = new StringBuilder();
        int i = arabicValues.length - 1;

        while (arabic > 0) {
            if (arabic >= arabicValues[i]) {
                result.append(romanSymbols[i]);
                arabic -= arabicValues[i];
            } else {
                i--;
            }
        }
        return result.toString();
    }
}
