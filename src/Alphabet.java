public class Alphabet {
    public static final String LETTERS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";
    public static final String NUMBERS = "0123456789";
    public static final int[] SECRET = {15, 5, 0, 9, 14, 19, 18};

    public static char getEncoded(String s, int key, int curPos){
        int shift = key % s.length();
        int shiftIndex = curPos + shift;
        if (shiftIndex > s.length()-1) {
            shiftIndex = shiftIndex - s.length();
        }
        return s.charAt(shiftIndex);
    }
    public static char getDecoded(String s, int key, int curPos){
        int shift = key % s.length();
        int shiftIndex = curPos - shift;
        if (shiftIndex < 0) {
            shiftIndex = s.length() + shiftIndex;
        }
        return s.charAt(shiftIndex);
    }
}
