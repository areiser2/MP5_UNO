package com.example.mp5;
import java.util.LinkedList;
import java.util.Collections;

/**
 * only somewhat used.
 */

public class DeckFunctions {
    private static String[] colors = {"red", "blue", "green", "yellow"};
    private static String[] numbers = {"0", "1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8",
            "8", "9", "9", "Skip", "Skip", "Reverse", "Reverse", "DrawTwo", "DrawTwo"};
    private static LinkedList<String> deckList = new LinkedList<>();
    private static String[] deck = new String[100];

    public static String top(String[] currentDeck) {
        for (int i = 0; i < currentDeck.length; i++) {
            if (!currentDeck[i].equals(null)) {
                return currentDeck[i];
            }
        }
        return null;
    }


    public static String bottom(String[] currentDeck) {
        for (int i = currentDeck.length - 1; i > 0; i--) {
            if (!currentDeck[i].equals(null)) {
                return currentDeck[i];
            }
        }
        return null;
    }


    public static String[] createDeck() {
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                deckList.add(colors[i] + numbers[j]);
            }
        }
        Collections.shuffle(deckList);
        Object[] deckOb = deckList.toArray();
        for (int i = 0; i < deckOb.length; i++) {
            deck[i] = deckOb[i].toString();
        }
        return deck;
    }

    public static void configure(String[] figure) {
        String[] copy = new String[figure.length];

        for (int i = 0; i < figure.length; i++) {

        }
    }
}
