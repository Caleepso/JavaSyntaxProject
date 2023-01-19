public class Solution {
    private static String key;
    public static void main(String[] args) {
        Menu.setHeader();
        runMode(Menu.getChoice());
    }

    static void runMode (int mode) {
        if (mode == 5) {
            System.out.println("Сеанс работы завершен");
            System.exit(0);
        } else if (mode == 1) {
            Encoder.encryptMode();
        } else if (mode == 2) {
            Decoder.decodeMode();
        } else if (mode == 3) {
            Decoder.bruteForceMode();
        } else if (mode == 4) {
            Decoder.analyzeStatMode();
        }
    }
}