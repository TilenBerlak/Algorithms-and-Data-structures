import java.util.Scanner;

public class Naloga1 {

    private static final int MAX_STACKS = 42;

    private static Sequence<Stack<String>> stacks = new ArrayDeque<>();

    private static boolean condition = false;
    private static int stackID = 0;
    private static int funCounter = 0;


    private static boolean readOperation(String s) throws CollectionException {

        if(isNumeric(s)) {
            // Add a number to stack
            stacks.get(stackID).push(s);

            return true;
        }

        if(funCounter > 0) {
            stacks.get(stackID).push(s);
        } else {
            findOperation(s);
        }

        return true;
    }

    private static boolean findOperation(String s) throws CollectionException {

        // ?... izvajanje ukaza pod pogojem
        if(!condition && s.toCharArray()[0] == '?') {
            return false;
        }

        if(condition && s.length() > 1 && s.toCharArray()[0] == '?') {

            char[] sArray = s.toCharArray();
            s = "";

            for(int i = 1; i < sArray.length; i++) {
                s += sArray[i];

            }

        }

        switch(s) {
            case "echo": opEcho(); break;
            case "pop": opPop(); break;
            case "dup": opDup(); break;
            case "dup2": opDup2(); break;
            case "swap": opSwap(); break;

            // Naslednje operacije zamenjajo vrh glavnega
            // sklada z ustreznim rezultatom (x -> y)
            case "char": opChar(); break;
            case "even": opEven(); break;
            case "odd": opOdd(); break;
            case "!": opFactorial(); break;
            case "len": opLen(); break;

            // Naslednje operacije zamenjajo vrhnja dva elementa
            // glavnega sklada z ustreznim rezultatom (x y -> r)
            case "<>": opNotEqual(); break;
            case "<": opSmaller(); break;
            case "<=": opSmallerEqual(); break;
            case "==": opEqual(); break;
            case ">": opBigger(); break;
            case ">=": opBiggerEqual(); break;
            case "+": opTopPairSum(); break;
            case "-": opTopPairDifference(); break;
            case "*": opTopPairProduct(); break;
            case "/": opTopPairQuotient(); break;
            case "%": opTopPairRemainder(); break;
            case ".": opTopPairCombine(); break;
            case "rnd": opTopPairRandom(); break;

            // Naslednje operacije omogočajo izvedbo pogojnega stavka
            // (izpolnjenost pogoja hranimo v interni spremeljivki)
            case "then": opThen(); break;
            case "else": opElse(); break;

            // Za delo s poljubnim skladom (glavnim ali pomožnimi) imamo na voljo spodnje ukaze.
            // Pri tem velja, da število na vrhu glavnega sklada določa indeks sklada, nad katerim se izvaja ukaz
            case "print": opPrint(); break;
            case "clear": opClear(); break;
            case "run": opRun(false); break;
            case "loop": opLoop(); break;
            case "fun": opFun(); break;
            case "move": opMove(); break;
            case "reverse": opReverse(); break;

            default:
                stacks.get(stackID).push(s);
                return false;
        }

        return true;
    }

    private static void opReverse() throws CollectionException {

        int index = Integer.parseInt(stacks.get(stackID).pop());

        stackID = index;

        String[] arr = new String[stacks.get(stackID).size()];

        for(int i = 0; i < arr.length; i++) {
            arr[i] = stacks.get(stackID).pop();
        }

        for(int i = 0; i < arr.length; i++) {
            stacks.get(stackID).push(arr[i]);
        }

        stackID = 0;

    }

    private static void opMove() throws CollectionException {

        int index = Integer.parseInt(stacks.get(stackID).pop());
        int num = Integer.parseInt(stacks.get(stackID).pop());

        stackID = index;

        stackID = 0;

        for(int i = 0; i < num; i++) {

            String e = stacks.get(stackID).pop();
            stackID = index;
            stacks.get(stackID).push(e);
            stackID = 0;
        }


    }

    private static void opFun() throws CollectionException {

        int index = Integer.parseInt(stacks.get(stackID).pop());
        int num = Integer.parseInt(stacks.get(stackID).pop());

        stackID = index;
        funCounter = num;

    }

    private static void opLoop() throws CollectionException {

        int index = Integer.parseInt(stacks.get(stackID).pop());
        int num = Integer.parseInt(stacks.get(stackID).pop());

        for(int i = 0; i < num; i++){
            stackID = index;
            opRun(true);
        }

        stackID = 0;

    }

    private static void opRun(boolean inLoop) throws CollectionException {

        if(!inLoop) {
            stackID = Integer.parseInt(stacks.get(stackID).pop());
        }

        String[] arr = new String[stacks.get(stackID).size()];

        for(int i = arr.length - 1; i >= 0; i--) {
            arr[i] = stacks.get(stackID).pop();
        }

        for(String e : arr) {
            stacks.get(stackID).push(e);
        }

        stackID = 0;

        for(String e: arr) {
            readOperation(e);
        }

    }

    private static void opClear() throws CollectionException {

        int index = Integer.parseInt(stacks.get(stackID).pop());
        stackID = index;

        while(stacks.get(stackID).size() > 0) {
            stacks.get(stackID).pop();
        }

        stackID = 0;

    }

    private static void opPrint() throws CollectionException {

        int index = Integer.parseInt(stacks.get(stackID).pop());
        stackID = index;

        String[] arr = new String[stacks.get(stackID).size()];

        for(int i = arr.length - 1; i >= 0; i--) {
            arr[i] = stacks.get(stackID).pop();
        }

        for(String e : arr) {
            System.out.print(e + " ");
            stacks.get(stackID).push(e);
        }

        System.out.println();

        stackID = 0;

    }

    private static void opElse() {

        condition = !condition;

    }

    private static void opThen() throws CollectionException {

        if(Integer.parseInt(stacks.get(stackID).pop()) != 0) {
            condition = true;
        } else {
            condition = false;
        }

    }

    private static void opTopPairRandom() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        int rndNumber = (int)(Math.random() * ((x - y) + 1)) + y;
        stacks.get(stackID).push(Integer.toString(rndNumber));

    }

    private static void opTopPairCombine() throws CollectionException {

        String y = stacks.get(stackID).pop();
        String x = stacks.get(stackID).pop();

        String combine = x + y;
        stacks.get(stackID).push(combine);

    }

    private static void opTopPairRemainder() throws CollectionException {

        if(Integer.parseInt(stacks.get(stackID).top()) != 0) {
            int y = Integer.parseInt(stacks.get(stackID).pop());
            int x = Integer.parseInt(stacks.get(stackID).pop());

            String remainder = Integer.toString(x % y);
            stacks.get(stackID).push(remainder);
        }


    }


    private static void opTopPairQuotient() throws CollectionException {

        if(Integer.parseInt(stacks.get(stackID).top()) != 0) {
            int y = Integer.parseInt(stacks.get(stackID).pop());
            int x = Integer.parseInt(stacks.get(stackID).pop());

            String quotient = Integer.toString((int) x / y);
            stacks.get(stackID).push(quotient);
        }

    }

    private static void opTopPairProduct() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        String product = Integer.toString(x * y);
        stacks.get(stackID).push(product);

    }

    private static void opTopPairDifference() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        String difference = Integer.toString(x - y);
        stacks.get(stackID).push(difference);

    }

    private static void opTopPairSum() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        String sum = Integer.toString(x + y);
        stacks.get(stackID).push(sum);

    }

    private static void opBigger() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        if(x > y) {
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).push("0");
        }

    }

    private static void opBiggerEqual() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        if(x >= y) {
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).push("0");
        }
    }

    private static void opEqual() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        if(x == y) {
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).push("0");
        }

    }

    private static void opSmallerEqual() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        if(x <= y) {
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).push("0");
        }

    }

    private static void opSmaller() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        if(x < y) {
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).push("0");
        }

    }

    private static void opNotEqual() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());

        if(x != y) {
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).push("0");
        }

    }

    private static void opLen() throws CollectionException {

        String e = stacks.get(stackID).pop();
        String eLength = Integer.toString(e.length());
        stacks.get(stackID).push(eLength);

    }

    private static void opFactorial() throws CollectionException {

        int xInInteger = Integer.parseInt(stacks.get(stackID).pop());

        if(xInInteger == 0) {
            stacks.get(stackID).push(Integer.toString(1));
        } else {
            int xFactorial = xInInteger;

            for(int i = xInInteger - 1; i > 0; i--) {
                xFactorial *= i;
            }

            stacks.get(stackID).push(Integer.toString(xFactorial));
        }


    }

    private static void opOdd() throws CollectionException {

        if(Integer.parseInt(stacks.get(stackID).top()) % 2 == 0) {
            stacks.get(stackID).pop();
            stacks.get(stackID).push("0");
        } else {
            stacks.get(stackID).pop();
            stacks.get(stackID).push("1");
        }
    }

    private static void opEven() throws CollectionException {

        if(Integer.parseInt(stacks.get(stackID).top()) % 2 == 0) {
            stacks.get(stackID).pop();
            stacks.get(stackID).push("1");
        } else {
            stacks.get(stackID).pop();
            stacks.get(stackID).push("0");
        }

    }

    private static void opChar() throws CollectionException {
        String x = stacks.get(stackID).pop();
        String xInUnicode = Character.toString((char)Integer.parseInt(x));

        stacks.get(stackID).push(xInUnicode);

    }

    private static void opEcho() throws CollectionException {
        if(stacks.get(stackID).isEmpty()) {
            System.out.println();
        } else {
            System.out.println(stacks.get(stackID).top());
        }
    }

    private static void opPop() throws CollectionException {
        stacks.get(stackID).pop();
    }

    private static void opDup() throws CollectionException {
        stacks.get(stackID).push(stacks.get(stackID).top());
    }

    private static void opDup2() throws CollectionException {
        String y  = stacks.get(stackID).pop();
        String x  = stacks.get(stackID).pop();
        stacks.get(stackID).push(x);
        stacks.get(stackID).push(y);
        stacks.get(stackID).push(x);
        stacks.get(stackID).push(y);
    }

    private static void opSwap() throws CollectionException {
        String y = stacks.get(stackID).pop();
        String x = stacks.get(stackID).pop();
        stacks.get(stackID).push(y);
        stacks.get(stackID).push(x);
    }

    // Helper methods

    private static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private static Stack<Integer> getStackPair() throws CollectionException {

        int y = Integer.parseInt(stacks.get(stackID).pop());
        int x = Integer.parseInt(stacks.get(stackID).pop());
        stacks.get(stackID).push(Integer.toString(x));
        stacks.get(stackID).push(Integer.toString(y));

        Stack<Integer> integerStack = new ArrayDeque<Integer>();
        integerStack.push(x);
        integerStack.push(y);

        return integerStack;
    }



    public static void main(String[] args) throws CollectionException {

        Scanner scr = new Scanner(System.in);


        while(scr.hasNextLine()) {

            for(int i = 0; i < MAX_STACKS; i++) {
                stacks.add(new ArrayDeque<String>());
            }

            String expr = scr.nextLine();

            String[] exprArray = expr.split("\\s+");


            for (int i = 0; i < exprArray.length; i++) {

                if (funCounter == 0) {
                    stackID = 0;
                    readOperation(exprArray[i]);
                } else {
                    readOperation(exprArray[i]);
                    funCounter--;
                }

            }

            stacks = new ArrayDeque<>();
            condition = false;
            funCounter = 0;

        }

        scr.close();
    }
}

@SuppressWarnings("unchecked")
class ArrayDeque<T> implements Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;

    private T[] a;
    private int front, back, size;

    public ArrayDeque() {

        this.a = (T[]) new Object[DEFAULT_CAPACITY];
        this.front = 0;
        this.back = 0;
        this.size = 0;

    }

    // ###########################################
    /// Collection methods

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean isFull() {
        return (size == DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("[");

        if(size > 0) {
            sb.append(a[front].toString());
        }

        for(int i = 0; i < size - 1; i++) {
            sb.append("," + a[ next(front + i)] );
        }
        sb.append("]");

        return sb.toString();
    }

    // #########################################
    /// Helper methods

    private int next(int i) { return (i+1) % DEFAULT_CAPACITY; }

    private int prev(int i) { return (DEFAULT_CAPACITY + i - 1) % DEFAULT_CAPACITY; }

    // ###########################################
    /// Stack methods

    @Override
    public T top() throws CollectionException {

        if(isEmpty()) {
            throw new CollectionException(ERR_MSG_EMPTY);
        }

        return a[prev(back)];
    }

    @Override
    public void push(T x) throws CollectionException {

        if(isFull()) {
            throw new CollectionException(ERR_MSG_FULL);
        }

        a[back] = x;
        back = next(back);

        size++;

    }

    @Override
    public T pop() throws CollectionException {

        if(isEmpty()) {
            throw new CollectionException(ERR_MSG_EMPTY);

        }

        back = prev(back);

        T o = a[back];
        a[back] = null;
        size--;

        return o;
    }

    // ##########################################################
    /// Sequence methods

    @Override
    public T get(int i) throws CollectionException {

        if(isEmpty()) {
            throw new CollectionException(ERR_MSG_EMPTY);
        }

        if(i < 0 || i >= size) {
            throw new CollectionException(ERR_MSG_INDEX);
        }

        return a[index(i)];
    }

    @Override
    public void add(T x) throws CollectionException {

        if(isFull()) {
            throw new CollectionException(ERR_MSG_FULL);
        }

        a[back] = x;
        back = next(back);

        size++;
    }

    // Mapping - used in Sequence : public T get
    private int index(int i) {
        return (front + i ) % DEFAULT_CAPACITY;
    }


}

class CollectionException extends Exception {

    public CollectionException(String msg) {
        super(msg);
    }
}

interface Collection {

    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}

interface Stack<T> extends Collection {

    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;

}

interface Sequence<T> extends Collection {

    static final String ERR_MSG_INDEX = "Wrong index in sequence.";
    T get(int i) throws CollectionException;
    void add(T x) throws CollectionException;

}



