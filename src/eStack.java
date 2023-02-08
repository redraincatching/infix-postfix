import javax.swing.JOptionPane;

public class eStack {
    private int index = -1;     // current highest filled index
    private final int cap;            // manipulable capacity
    private static final int CAP = 1000;    // default capacity
    private final Object[] stack;     // the data in the stack

    public eStack() {
        // default constructor
        this(CAP);
    }

    public eStack(int capacity) {
        // overloaded - allows for specified size greater than 0
        cap = (capacity > 0) ? capacity : CAP;
        stack = new Object[cap];
    }


    // methods
    public void push (Object o) {
        if (isFull()) {
            JOptionPane.showMessageDialog(null, "[error]: stack is full");
            return;
        }
        stack[++index] = o;
    }

    public Object pop() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "[error]: stack is empty");
            return null;
        }
        return stack[index--];
    }

    public Object top() {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "[error]: stack is empty");
            return null;
        }
        return stack[index];
    }

    public int size() {
        return (index + 1);
    }

    public boolean isEmpty() {
        return (index < 0);
    }

    public boolean isFull() {
        return (index == cap - 1);
    }

    public void makeEmpty() {
        for (Object o : stack) {
            o = null;
        }
    }
}
