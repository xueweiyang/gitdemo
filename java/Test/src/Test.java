import stack.ArrayQueue;
import stack.ArrayStack;
import stack.Log;

public class Test {

    public static void main(String[] args) {
//        ArrayStack arrayStack = new ArrayStack<String>(6);
//        for (int i = 0; i < 7; i++) {
//            arrayStack.push("还好" + i);
//        }
//        String s;
//        while ((s = (String) arrayStack.pop()) != null) {
//            stack.Log.log(s);
//        }

//        String[] datas = {"{}[]()", "{}[()]", "{}[)(]"};
//        for (String data : datas) {
//            stack.Log.log(String.format("%s : %b", data, isValid(data)));
//        }

//        String[] datas2 = {"1+2*3-4","2*7+4-1*2-8"};//, "(2+3)*(4-2)"
//        for (String data : datas2) {
//            Log.log(String.format("%s : %d", data, calculate(data)));
//        }

        ArrayQueue arrayQueue = new ArrayQueue<String>(5);
        for (int i = 0; i < 3; i++) {
            arrayQueue.enqueue("hhh"+i);
        }
        Log.log((String) arrayQueue.dequeue());
        Log.log((String) arrayQueue.dequeue());
        arrayQueue.enqueue("ddd");
        Log.log("ddd");
//        while (!arrayQueue.isEmpty()) {
////
////        }
    }

    /**
     * 实现简单的计算器 leetcode-224
     *
     * @param s
     * @return
     */
    private static int calculate(String s) {
        ArrayStack numStack = new ArrayStack<Integer>(20);
        ArrayStack optStack = new ArrayStack(20);
        if (s.isEmpty()) {
            return 0;
        }
        int length = s.length();
        int pos = 0;
        boolean lastIsSum = false;
        int lastValue = 0;
        while (pos < length) {
            char c = s.charAt(pos);
            if (isNum(c)) {
                int num = Integer.parseInt(c + "");
                numStack.push(num);
//                if (lastIsSum) {
//                    lastValue = lastValue*10+num;
//                } else {
//                    lastValue=num;
//                }
//                lastIsSum = true;
            } else if (isOpt(c)) {
                Object o = optStack.top();
                if (o == null) {
                    optStack.push(c);
                } else {
                    char c2 = (char) o;
                    if (compareOpt(c2, c)) {
                        int num2 = (int) numStack.pop();
                        int num1 = (int) numStack.pop();
                        numStack.push(optNum(num1, num2, c2));
                        optStack.pop();
                    }
                    optStack.push(c);
                }
            }
            pos++;
        }
//        numStack.printData();
//        optStack.printData();
//        return 0;
        while (optStack.top()!= null) {
            char c = (char) optStack.pop();
            int num2 = (int) numStack.pop();
            int num1 = (int) numStack.pop();
            numStack.push(optNum(num1, num2, c));
        }
        return (int) numStack.pop();
    }

    /**
     * 根据操作符和两个数字操作得出结果
     *
     * @param num1
     * @param num2
     * @param opt
     * @return
     */
    private static int optNum(int num1, int num2, char opt) {
        int result = 0;
        switch (opt) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
        }
        return result;
    }

    /**
     * 判断操作符c1是否优先级比c2高
     *
     * @param c1
     * @param c2
     * @return
     */
    private static boolean compareOpt(char c1, char c2) {
        boolean result = false;
        switch (c2) {
            case '-':
            case '+':
                result = c1 == '-' || c1 == '+' || c1 == '*' || c1 == '/' || c1 == '(' || c1 == ')';
                break;
            case '*':
            case '/':
                result = c1 == '(' || c1 == ')';
                break;
        }
        return result;
    }

    private static boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isOpt(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    /**
     * 只包含{}[]()的字符串，判断是否是有效的闭合，leetcode-20
     *
     * @param s
     * @return
     */
    private static boolean isValid(String s) {
        int length = 0;
        if (s.isEmpty() || (length = s.length()) % 2 != 0) {
            return false;
        }
        ArrayStack arrayStack = new ArrayStack(10);
        int pos = 0;
        while (pos < length) {
            char c = s.charAt(pos);
            if (isLeft(c)) {
                arrayStack.push(c);
            } else {
                Object o = arrayStack.pop();
                if (o == null) {
                    return false;
                } else {
                    char cStack = (char) o;
                    if (!isDouble(cStack, c)) {
                        return false;
                    }
                }
            }
            pos++;
        }
        return arrayStack.isEmpty();
    }

    private static boolean isLeft(char c) {
        return ('{' == c || '[' == c || '(' == c);
    }

    private static boolean isDouble(char c1, char c2) {
        boolean result = false;
        switch (c1) {
            case '{':
                result = c2 == '}';
                break;
            case '[':
                result = c2 == ']';
                break;
            case '(':
                result = c2 == ')';
                break;
        }
        return result;
    }
}
