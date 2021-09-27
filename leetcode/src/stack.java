import java.util.Stack;

public class stack {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.push(8);
        s.push(10);
        s.push(1);
        s.push(100);
        s.push(122);
        sortStack(s);
        System.out.println(s.toArray().toString());
    }
    public static void sortStack(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<Integer>();
        while(!stack.isEmpty()){
            int cur = stack.pop();
            while(!help.isEmpty() && cur > help.peek()){
                stack.push(help.pop());
            }
            help.push(cur);
        }

        while(!help.isEmpty()){
            stack.push(help.pop());
        }
    }
}
