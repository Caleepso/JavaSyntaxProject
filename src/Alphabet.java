import java.util.HashMap;

public class Alphabet {
    public static final String LETTERS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";
    public static final String NUMBERS = "0123456789";
    public static final String ALPHABET = LETTERS + NUMBERS;
    public static final int[] secretPositions = {15, 5, 0, 9, 14, 19, 18};

    static char getEncoded(String s, int key, int curPos){
        int shift = key % s.length();
        int shiftIndex = curPos + shift;
        if (shiftIndex > s.length()-1) {
            shiftIndex = shiftIndex - s.length();
        }
        return s.charAt(shiftIndex);
    }
    static char getDecoded(String s, int key, int curPos){
        int shift = key % s.length();
        int shiftIndex = curPos - shift;
        if (shiftIndex < 0) {
            shiftIndex = s.length() + shiftIndex;
        }
        return s.charAt(shiftIndex);
    }
}