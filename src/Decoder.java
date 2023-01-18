import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class Decoder {
    public static String key;
    public static final String DECODED = "src/Files/decoded.txt";
    public static final String DICT = "src/Files/dictionary.txt";
    public static void decodeMode() {
        System.out.println("Работа в режиме расшифровки:");
        System.out.print("Введите криптографический ключ: ");
        checkFile();
        Scanner s = new Scanner(System.in);
        while (true) {
            key = s.next();
            if (!Menu.validate(key,2)) {
                System.out.print("Криптографический ключ - это целое положительное число, повторите ввод: ");
            } else {
                break;
            }
        }
        decode(Encoder.ENCRYPTED, DECODED, key);
        System.out.println("Расшифрованный файл располагается в директории Files/decoded.txt");
    }
    public static void checkFile() {
        File f = new File(Encoder.ENCRYPTED);
        if (!f.isFile()) {
            System.out.print("Файл не найден, повторите процедуру шифрования");
            System.exit(0);
        }
        try {
            Path decoded = Paths.get(DECODED);
            Files.deleteIfExists(decoded);
            Files.createFile(decoded);
        } catch (IOException e) {
            System.err.println("Что-то пошло совсем не так, сеанс работы завершен: " + e);
            System.exit(1);
        }
    }

    public static void decode(String r, String w, String key) {
        try (FileReader reader = new FileReader(r);
             FileWriter writer = new FileWriter(w)) {
            while (reader.ready()) {
                char symbol = (char)reader.read();
                int indexA = Alphabet.ALPHABET.indexOf(symbol);
                int indexL = Alphabet.LETTERS.indexOf(symbol);
                int indexN = Alphabet.NUMBERS.indexOf(symbol);
                if (indexA != -1) {
                    if (indexN != -1) {
                        symbol = Alphabet.getDecoded(Alphabet.NUMBERS, Integer.parseInt(key), indexN);
                    } else if (indexL != -1) {
                        symbol = Alphabet.getDecoded(Alphabet.LETTERS, Integer.parseInt(key)*2, indexL);
                    }
                }
                writer.write(symbol);
            }
        } catch (IOException e) {
            System.err.println("Что-то пошло совсем не так, сеанс работы завершен : " + e);
            System.exit(1);
        }
    }

    public static void bruteForce() {
        System.out.println("Работа в режиме bruteForce-расшифровки файла (шифр Цезаря):");
        System.out.print("Введите абсолютный путь к зашифрованному .txt-файлу: ");
        String file = Menu.checkPath();
        try(Scanner s = new Scanner(System.in)) {
            int i = 0;
            while (true) {
                String con;
                decode(file, DECODED, Integer.toString(i + 1));
                i++;
                System.out.print("Итерация расшифровки №" + i + " завершена. Проверьте файл по пути src/Files/decoded.txt." +
                        "Продолжить попытки расшифровки? (да\\нет) ");
                while (true) {
                    con = s.next();
                    if (!(con.equalsIgnoreCase("да") | con.equalsIgnoreCase("нет"))) {
                        System.out.print("Введите ответ: да \\ нет ");
                    } else {
                        break;
                    }
                }
                if (con.equalsIgnoreCase("нет")) {
                    System.out.println("Сеанс работы завершен");
                    break;
                }
            }
        }
    }

    public static void analyzeStat() {
        System.out.println("Работа в режиме статистического анализа содержимого файла (шифр Цезаря):");
        System.out.print("Введите абсолютный путь к зашифрованному .txt-файлу: ");
        String f = Menu.checkPath();
        File file = new File(f);
        try {
            String fileToString = FileUtils.readFileToString(file, Charset.defaultCharset());
            System.out.println(fileToString);
            HashMap<Character, Integer> map = getMap(fileToString);
            char c = 'а';
            for (int i = 0; i < 7; i++) {
                int maxValueInMap = (Collections.max(map.values()));
                System.out.println(i);
                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == maxValueInMap) {
                        System.out.println(map);
                        System.out.println(maxValueInMap);
                        c = entry.getKey();
                        System.out.println(c);
                        map.remove(c);
                        break;
                    }
                }
                int maxPos = Alphabet.LETTERS.indexOf(c) / 2;
                int shift = Math.abs(maxPos - Alphabet.secretPositions[i]);
                decode(f, DECODED, Integer.toString(shift));
                Scanner s = new Scanner(System.in);
                String con;
                System.out.print("Итерация расшифровки №"+(i+1)+" завершена. Проверьте файл по пути src/Files/decoded.txt."+
                            " Продолжить попытки расшифровки? (да\\нет) ");
                while (true) {
                    con = s.next();
                    if (!(con.equalsIgnoreCase("да") | con.equalsIgnoreCase("нет"))) {
                        System.out.print("Введите ответ: да \\ нет ");
                    } else {
                        break;
                    }
                }
                if (con.equalsIgnoreCase("нет")) {
                    System.out.println("Сеанс работы завершен");
                    break;
                }
            }
        } catch (IOException e){}
    }

    public static HashMap<Character, Integer> getMap (String s) { //в разрезе строки раскодированного файла
        String str = s.replaceAll("[^а-яёА-ЯЁ]","").toLowerCase();
        char[] charArray = str.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : charArray) {
            int count = StringUtils.countMatches(s, c);
            map.put(c, count);
        }
        return map;
   }
}
