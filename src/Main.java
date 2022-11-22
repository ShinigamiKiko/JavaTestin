import java.util.*;

public class Main {

    private static final Map<String, Integer> romanNumbers = new HashMap<>() {{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
    }};

    public static void main(String[] args) {
        System.out.println("Введите выражение:");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        String[] s = new String[]{"+", "-", "/", "*"};
        int position = -1;
        for (String z : s) {
            int pos = expression.indexOf(z);
            if (pos != -1)
                position = pos;
        }
        String op = String.valueOf(expression.charAt(position));
        String c1 = expression.substring(0, position).trim();
        String c2 = expression.substring(position + 1).trim();
        System.out.println(processNumbers(c1, c2, op));
    }

    public static String processNumbers(String c1, String c2, String op) {
        if (c1.matches("\\d+") && c2.matches("\\d+")) {
            int d1 = Integer.parseInt(c2);
            int d2 = Integer.parseInt(c1);
            int res = calculate(d1, d2, op);
            if ((d1 > 10 || d1 < 1) || (d2 > 10 || d2 < 1))
                throw new RuntimeException("Неправильное число:");
            return String.valueOf(res);
        } else {
            int num1 = toArabic(c1);
            int num2 = toArabic(c2);
            int res = calculate(num1, num2, op);
            if (res <= 0) throw new ArithmeticException("Отрицательное число");
            return toRoman(res);
        }
    }

    public static int toArabic(String number) {
        Integer roman = romanNumbers.getOrDefault(number, -1);
        if (roman != -1)
            return roman;
        throw new IllegalArgumentException("Неправильное число");
    }

    public static String toRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }


    public static int calculate(int c1, int c2, String op) {
        return switch (op) {
            case "+" -> c1 + c2;
            case "/" -> c1 / c2;
            case "*" -> c1 * c2;
            case "-" -> c1 - c2;
            default -> throw new IllegalArgumentException("Неверное операция");
        };
    }

}
