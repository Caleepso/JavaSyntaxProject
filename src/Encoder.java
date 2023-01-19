import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Scanner;

public class Encoder {
    public static final String ENCRYPTED = "src/Files/encrypted.txt";
    public static String file;
    public static String key;
    public static void cleanup() {
        try {
            Path encrypted = Paths.get(ENCRYPTED);
            Files.deleteIfExists(encrypted);
            Files.createFile(encrypted);
        } catch (IOException e) {
            System.err.println("Что-то пошло совсем не так, сеанс работы завершен: " + e);
            System.exit(1);
        }
    }
    public static void encryptMode () {
        System.out.println("Работа в режиме шифрования:");
        System.out.print("Введите абсолютный путь к .txt-файлу: ");
        cleanup();
        String file =Menu.checkPath();
        System.out.print("Введите криптографический ключ: ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            key = sc.next();
            if (!Menu.validate(key,2)) {
                System.out.print("Криптографический ключ - это целое положительное число, повторите ввод: ");
            } else {
                break;
            }
        }
        encrypt(file, ENCRYPTED, key);
        System.out.println("Зашифрованный файл располагается в директории Files/encrypted.txt");
    }

    public static void encrypt(String r, String w, String key) {
        try (FileReader reader = new FileReader(r);
             FileWriter writer = new FileWriter(w)) {
            while (reader.ready()) {
                char symbol = (char)reader.read();
                int indexL = Alphabet.LETTERS.indexOf(symbol);
                int indexN = Alphabet.NUMBERS.indexOf(symbol);
                if (indexN != -1) {
                    symbol = Alphabet.getEncoded(Alphabet.NUMBERS, Integer.parseInt(key), indexN);
                } else if (indexL != -1) {
                    symbol = Alphabet.getEncoded(Alphabet.LETTERS, Integer.parseInt(key)*2, indexL);
                }
                writer.write(symbol);
            }
        } catch (Exception e) {
            System.err.println("Что-то пошло совсем не так, сеанс работы завершен : " + e);
            System.exit(1);
        }
    }
}
