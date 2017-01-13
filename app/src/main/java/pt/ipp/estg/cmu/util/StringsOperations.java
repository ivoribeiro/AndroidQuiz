package pt.ipp.estg.cmu.util;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author 8130031
 * @author 8130258
 */
public class StringsOperations {

    private static int NUMBER_GAME_BUTTONS = 15;
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int saltSize;
    private ArrayList<Integer> usedIndex = new ArrayList<Integer>();
    private String mRandomString;

    public StringsOperations(String stringToGenerate) {
        this.mRandomString = stringToGenerate;
    }

    public String generateString() {
        if (mRandomString.length() < NUMBER_GAME_BUTTONS) {
            saltSize = NUMBER_GAME_BUTTONS - mRandomString.length();
            generateLeftString();

            int indexRnd = 0;
            Random rng = new Random();
            String text = "";

            while (text.length() < NUMBER_GAME_BUTTONS) {
                indexRnd = rng.nextInt(mRandomString.length());
                if (!usedIndex.contains(indexRnd)) {
                    usedIndex.add(indexRnd);
                    char ch = mRandomString.charAt(indexRnd);
                    text = text + ch;
                }
            }
            return text;
        } else {
            return null;
        }
    }

    private void generateLeftString() {
        Random rng = new Random();
        ArrayList<Character> result = new ArrayList<>();
        while (result.size() < saltSize) {
            char ch = alphabet.charAt(rng.nextInt(alphabet.length()));
            if (!result.contains(ch)) {
                result.add(ch);
            }
        }
        String text = "";
        for (int i = 0; i < result.size(); ++i) {
            text = text + result.get(i);
        }
        mRandomString = mRandomString + text;
    }

    public static String arrayToString(int size, String[] array) {
        String result = "";
        for (int i = 0; i < size; ++i) {
            if (null != array[i] && !array[i].equals("#")) {
                result += array[i];
            } else if (null == array[i]) {
                result += " ";
            }
        }
        return result;
    }

    public static String[] stringToArray(String valueOriginal, String valueAtual) {
        int size = valueOriginal.length();
        String[] result = new String[size];

        for (int i = 0; i < size; ++i) {
            if (valueOriginal.charAt(i) == ' ') {
                result[i] = "#";
            }
        }
        if (valueAtual.length() == 0) {
            return result;
        }

        int indexAtual = 0;
        for (int i = 0; i < size; ++i) {
            if (null == result[i]) {

                if (indexAtual == valueAtual.length()) {
                    break;
                }
                if (' ' != valueAtual.charAt(indexAtual)) {
                    result[i] = Character.toString(valueAtual.charAt(indexAtual));
                }
                ++indexAtual;
            }
        }

        return result;
    }

    public static int getIndexOfFirstSpace(int size, String value) {
        for (int i = 0; i < size; ++i) {
            if (i < value.length()) {
                if (' ' == value.charAt(i)) {
                    return i;
                }
            }
        }
        return value.length();
    }


    public static int getNumberOfLetterInValue(int size, String value) {
        int result = 0;
        for (int i = 0; i < size; ++i) {
            if (i < value.length()) {
                if (' ' != value.charAt(i)) {
                    ++result;
                }
            }
        }
        return result;
    }

}

