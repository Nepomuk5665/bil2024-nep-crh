public class Main {
    public static void main(String[] args) {
        int pin = 0;
        int length = 5;
        int result = 1;

        for (int i = 0; i < length; i++) {
            result = 10 * result;
        }

        for (int i = 0; i < result; i++) {
            System.out.printf("%0" + length + "d\n", i);
        }
    }
}