package pt.ipp.estg.cmu.util;

import java.util.ArrayList;
import java.util.Random;


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

    public static String arrayToString(int index, String[] array) {
        String result = "";
        for (int i = 0; i < index; ++i) {
            if (null != array[i]) {
                result += array[i];
            } else {
                result += " ";
            }
        }
        return result;
    }
}

