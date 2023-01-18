import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Decoder.bruteForce();
        } else if (mode == 4) {
            Decoder.analyzeStat();
        }
    }
}