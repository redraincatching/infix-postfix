import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotationConverter {
    // note: searches for the negation
    Pattern valid = Pattern.compile("[^0-9+\\-*/^()]");
    Matcher validM;
    private final Stack working = new Stack();  // stack used to interpret

    // may drop this one too
    private final Stack solver = new Stack();   // stack used to solve final postfix expression

    public NotationConverter() {

    }

    public void solve (String input) {
        String output = convert(input);
        display(input, output, calculate(output));

    }


    public String convert(String input) {
        StringBuilder postfix = new StringBuilder();
        Pattern operators = Pattern.compile("[+\\-*/^()]");
        Pattern operands = Pattern.compile("[0-9]");
        Matcher operatorM;
        Matcher operandM;
        char c;

        // validate
        if(!validate(input)) { return ""; }

        // 1) scan through infix expression, ltr
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            operatorM = operators.matcher(Character.toString(c));
            operandM = operands.matcher(Character.toString(c));

            // 2) if current char is an operand, immediately append to postfix
            if (operandM.find()) {
                postfix.append(c);
            }
            // 3) if current char is left bracket, push to stack
            else if (c == '(') {
                working.push(c);
            }
            // 4) if current char is right bracket, pop contents and append to postfix until reaching the corresponding left bracket
            else if (c == ')') {
                while (!working.isEmpty()) {
                    if ((char) working.top() != '(') {
                        postfix.append(working.pop());
                    }
                    else { working.pop(); }
                }
            }
            // 5) if current char is operator, pop operators of equal or higher precedence and append them. then push to stack.
            else if (operatorM.find()) {
                if (working.isEmpty()) {
                    working.push(c);
                }
                else if ('(' == (char) working.top()) {
                    working.push(c);
                }
                else {
                    if (orderOfOperations(c) >= orderOfOperations((char) working.top())) {
                        postfix.append(working.pop());
                        working.push(c);
                    }
                }
            }
        }

        // append anything left in the stack
        while (!working.isEmpty()) {
            postfix.append(working.pop());
        }

        return postfix.toString();
    }


    public double calculate(String input) {
        char c;

        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);

            if (Character.isDigit(c)) {
                // immediately push any digits
                solver.push(c - '0');
            } else {
                // because some or the operators are right-associative
                int operand1 = (int) solver.pop();
                int operand2 = (int) solver.pop();

                switch (c) {
                    // solve and push
                    case '^' -> solver.push(Math.pow(operand2, operand1));
                    case '*' -> solver.push(operand2 * operand1);
                    case '/' -> solver.push(operand2 / operand1);
                    case '+' -> solver.push(operand2 + operand1);
                    case '-' -> solver.push(operand2 - operand1);
                }
            }
        }

        // more casts here than on broadway
        return (double) ((Integer) solver.pop());
    }

    // helper functions
    public boolean validate (String input) {
        validM = valid.matcher(input);

        if (validM.find()) {
            JOptionPane.showMessageDialog(null, "[error]: the only valid characters are 0-9, +, -, *, /, (, and )");
            return false;
        }
        else if (input.length() > 20 || input.length() < 3) {
            JOptionPane.showMessageDialog(null, "[error]: please keep input between 3 and 20 characters");
            return false;
        }
        else {
            return true;
        }
    }

    // easier than an enum for finding order of operations
    public int orderOfOperations(char c) {
        return switch (c) {
            case '(', ')' -> 0;
            case '^' -> 1;
            case '*', '/' -> 2;
            case '+', '-' -> 3;
            default -> 4;
        };
    }

    public void display(String in, String out, double ans) {
        if ("".equals(out)) {
            JOptionPane.showMessageDialog(null, "[error]: the equation you entered does not seem to be valid");
        } else {
            JOptionPane.showMessageDialog(null, "infix: " + in + "\npostfix: " + out + "\nanswer: " + ans);
        }
    }
}
