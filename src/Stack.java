import javax.swing.JOptionPane;

public class Stack {
    private int index = -1;     // current highest filled index
    private int cap;            // manipulable capacity
    private static final int CAP = 1000;    // default capacity
    private Object stack[];     // the data in the stack

    public Stack() {
        // default constructor
        this(CAP);
    }

    public Stack(int capacity) {
        // overloaded - allows for specified size greater than 0
        cap = (capacity > 0) ? capacity : CAP;
        stack = new Object[cap];
    }


    // methods
    public void push (Object o) {
        if (isFull()) { return; }
        stack[++index] = o;
    }

    public Object pop() {
        if (isEmpty()) { return null; }
        return stack[index--];
    }

    public Object top() {
        if (isEmpty()) { return null; }
        return stack[index];
    }

    public int size() {
        return (index + 1);
    }

    public boolean isEmpty() {
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "[error]: stack is empty");
        }
        return (index < 0);
    }

    public boolean isFull() {
        if (index == cap - 1) {
            JOptionPane.showMessageDialog(null, "[error]: stack is full");
        }
        return (index == cap - 1);
    }

    public void makeEmpty() {
        for (Object o : stack) {
            o = null;
        }
    }
}
