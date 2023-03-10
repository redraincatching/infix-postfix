import javax.swing.JOptionPane;

public class Test {
    public Test() {
    }

    public static void main(String[] args) {
        boolean running = true;
        NotationConverter app = new NotationConverter();

        while(running) {
            String input = JOptionPane.showInputDialog("Please enter an infix expression");
            running = !app.validate(input);
            if (!running) {
                app.solve(input);
            }
        }

    }
}
