public class Main {
    public static void main(String[] args) {
        int start = 3;
        int end = 27;

        int result = 0;

        int currentNumber = start;

        for (int i = start; i <= end; i++) {
            result = currentNumber + result;
            currentNumber++;
        }
        System.out.println(result);
    }
}