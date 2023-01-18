import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    private static String item;
    public static void setHeader(){
        System.out.println("\n" +
                "❰ ██ ◗ █ █〓 ▚▘  █ ▜▛ ");
        System.out.println("\n" + "Выберите режим работы приложения:" + "\n" + "--------------------------------");
        System.out.println("1. Шифрование (шифр Цезаря)");
        System.out.println("2. Расшифровка (шифр Цезаря)");
        System.out.println("3. Криптоанализ: brute force");
        System.out.println("4. Криптоанализ: статистический анализ");
        System.out.println("5. Завершение работы" + "\n");
        System.out.print("Ваш выбор: ");
    }
    public static int getChoice(){
        int choice = 0;
        Scanner s = new Scanner(System.in);
        while (true) {
            item = s.next();
            if (validate(item, 1)) {
                choice = Integer.parseInt(item);
                break;
            }
            else {
                System.out.print("Попробуйте всё же выбрать пункт меню 1-5: ");
            }
        }
        return choice;
    }
    public static boolean validate(String s, int stage) {
        String regexp = null;
        if (stage == 1) {
            regexp = "[1-5]";
            Pattern p = Pattern.compile(regexp);
            Matcher m = p.matcher(s);
            return m.matches();
        } else if (stage == 2) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++ ){
                if (!Character.isDigit(chars[i]) | (Character.isDigit(chars[i]) && i == 0 && chars[i] == '0')){
                    return false;
                }
            }
        }
        return true;
    }

    public static String checkPath() {
        Scanner s = new Scanner(System.in);
        boolean pathExists = false;
        String file;
        while (true) {
            file = s.next();
            File f = new File(file);
            pathExists = f.isFile();
            if (!pathExists) {
                System.out.print("Файл не найден, повторите ввод: ");
            } else {
                break;
            }
        }
        return file;
    }
}
