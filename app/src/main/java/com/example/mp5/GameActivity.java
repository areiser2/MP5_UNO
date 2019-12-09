package com.example.mp5;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.LinearLayout;



public class GameActivity extends AppCompatActivity {

    private static boolean p1turn = true;
    private static String[] deck = new String[100];
    private static String[] p1Hand = new String[0];
    private static String[] p2Hand = new String[0];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView topDeck = findViewById(R.id.topofDeck);
        TextView player1Hand = findViewById(R.id.player1Hand);
        TextView player2Hand = findViewById(R.id.player2Hand);
        TextView p1Title = findViewById(R.id.p1Title);
        TextView p2Title = findViewById(R.id.p2Title);
        Button playCard = findViewById(R.id.playButton);
        Button pickUp = findViewById(R.id.drawButton);
        EditText chooseCard = findViewById(R.id.input);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        //Sets up the hands

        deck = DeckFunctions.createDeck();
        topDeck.setText(DeckFunctions.bottom(deck));
        for (int i = 0; i < 7; i++) {
            p1Hand = draw(p1Hand);
        }
        for (int i = 0; i < 7; i++) {
            p2Hand = draw(p2Hand);
        }

        Intent endIntent = new Intent(this, NewGameActivity.class);
        endIntent.putExtra("p1Name", intent.getStringExtra("user1"));
        endIntent.putExtra("p2Name", intent.getStringExtra("user2"));

        p1Title.setText(intent.getStringExtra("user1") + "'s hand");
        p2Title.setText(intent.getStringExtra("user2") + "'s hand");
        displayer();

        chooseCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                playCard.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                playCard.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (p1turn) {
                    if (cardChecker(p1Hand)) {
                        playCard.setVisibility(View.VISIBLE);
                    }
                }
                if (!p1turn) {
                    if (cardChecker(p2Hand)) {
                        playCard.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        pickUp.setOnClickListener(unused -> {
            if (p1turn) {
                p1Hand = draw(p1Hand);
                p1turn = false;
                displayer();
            } else {
                p2Hand = draw(p2Hand);
                p1turn = true;
                displayer();
            }
        });
        playCard.setOnClickListener(unused -> {
            if (p1turn) {
                p1Hand = updateGame(p1Hand);
                if (chooseCard.getText().toString().contains("DrawTwo")) {
                    p2Hand = draw(p2Hand);
                    p2Hand = draw(p2Hand);
                }
                if (p1Hand.length == 0) {
                    endIntent.putExtra("p1Wins", true);
                    startActivity(endIntent);
                    finish();
                }

                p1turn = false;
                if (chooseCard.getText().toString().contains("Skip") || chooseCard.getText().toString().contains("Reverse")) {
                    p1turn = true;
                }
                displayer();
            } else {
                p2Hand = updateGame(p2Hand);
                if (chooseCard.getText().toString().contains("DrawTwo")) {
                    p1Hand = draw(p1Hand);
                    p1Hand = draw(p1Hand);
                }
                if (p2Hand.length == 0) {
                    endIntent.putExtra("p1Wins", false);
                    startActivity(endIntent);
                    finish();
                }
                p1turn = true;
                if (chooseCard.getText().toString().contains("Skip") || chooseCard.getText().toString().contains("Reverse")) {
                    p1turn = false;
                }
                displayer();
            }
        });
    }
    public String[] updateGame(String[] handInput) {

        EditText chooseCard = findViewById(R.id.input);
        String theCard = chooseCard.getText().toString();
        String[] newDeck = new String[deck.length + 1];
        System.out.println("new" + newDeck.length);
        System.out.println("old" + deck.length);
        int cardIndex = -1;
        for (int i = 0; i < deck.length; i++) {
            newDeck[i] = deck[i];
        }
        String[] newHand = new String[handInput.length - 1];
        for (int i = 0; i < handInput.length; i++) {
            if (handInput[i].equals(theCard)) {
                cardIndex = i;
            }
        }
        for (int i = 0; i < newHand.length; i++) {
            if (i < cardIndex) {
                newHand[i] = handInput[i];
            }
            else if (i >= cardIndex) {
                newHand[i] = handInput[i + 1];
            }
        }
        newDeck[newDeck.length - 1] = handInput[cardIndex];

        deck = newDeck;
        return newHand;

    }

    /**
     * Helper function that "draws" a card for the hand.
     * @param hand the initial hand
     * @return the new hand after drawing
     */
    public String[] draw(String[] hand) {
        String[] newHand = new String[hand.length + 1];
        String[] newDeck = new String[deck.length - 1];
        for (int i = 0; i < hand.length; i++) {
            newHand[i] = hand[i];
        }
        newHand[newHand.length - 1] = DeckFunctions.top(deck);
        for (int j = 0; j < newDeck.length; j++) {
            newDeck[j] = deck[j + 1];
        }
        deck = newDeck;
        return newHand;
    }

    /**
     * Help function to update the UI
     */
    public void displayer() {
        Button playCard = findViewById(R.id.playButton);
        TextView topDeck = findViewById(R.id.topofDeck);
        TextView player1Hand = findViewById(R.id.player1Hand);
        TextView player2Hand = findViewById(R.id.player2Hand);
        TextView p1Title = findViewById(R.id.p1Title);
        TextView p2Title = findViewById(R.id.p2Title);
        String p1String = new String("");
        for (int i = 0; i < p1Hand.length; i++) {
            p1String += p1Hand[i] + " ";
        }
        String p2String = new String("");
        for (int i = 0; i < p2Hand.length; i++) {
            p2String += p2Hand[i] + " ";
        }
        player1Hand.setText(p1String);
        player2Hand.setText(p2String);
        topDeck.setText(DeckFunctions.bottom(deck));
        if (p1turn) {
            p2Title.setVisibility(View.GONE);
            player2Hand.setVisibility(View.GONE);
            p1Title.setVisibility(View.VISIBLE);
            player1Hand.setVisibility(View.VISIBLE);
            playCard.setVisibility(View.GONE);
        } else {
            player1Hand.setVisibility(View.GONE);
            p1Title.setVisibility(View.GONE);
            p2Title.setVisibility(View.VISIBLE);
            player2Hand.setVisibility(View.VISIBLE);
            playCard.setVisibility(View.GONE);

        }
    }

    /**
     * Tests to see if input is valid card.
     * @return true if card contains a working value
     */
    public boolean cardChecker(String[] theHand) {
        EditText chooseCard = findViewById(R.id.input);
        String check = DeckFunctions.bottom(deck);
        String theCard = chooseCard.getText().toString();
        if (check.contains("green") && theCard.contains("green")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("green")) {
                    return true;
                }
            }
        }
        if (check.contains("blue") && theCard.contains("blue")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("blue")) {
                    return true;
                }
            }
        }
        if (check.contains("yellow") && theCard.contains("yellow")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("yellow")) {
                    return true;
                }
            }
        }
        if (check.contains("red") && theCard.contains("red")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("red")) {
                    return true;
                }
            }
        }
        //Numbers
        if (check.contains("0") && theCard.contains("0")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("0")) {
                    return true;
                }
            }
        }
        if (check.contains("1") && theCard.contains("1")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("1")) {
                    return true;
                }
            }
        }
        if (check.contains("2") && theCard.contains("2")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("2")) {
                    return true;
                }
            }
        }
        if (check.contains("3") && theCard.contains("3")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("3")) {
                    return true;
                }
            }
        }
        if (check.contains("4") && theCard.contains("4")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("4")) {
                    return true;
                }
            }
        }
        if (check.contains("5") && theCard.contains("5")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("5")) {
                    return true;
                }
            }
        }
        if (check.contains("6") && theCard.contains("6")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("6")) {
                    return true;
                }
            }
        }
        if (check.contains("7") && theCard.contains("7")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("7")) {
                    return true;
                }
            }
        }
        if (check.contains("8") && theCard.contains("8")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("8")) {
                    return true;
                }
            }
        }
        if (check.contains("9") && theCard.contains("9")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("9")) {
                    return true;
                }
            }
        }
        if (check.contains("DrawTwo") && theCard.contains("DrawTwo")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("DrawTwo")) {
                    return true;
                }
            }
        }
        if (check.contains("Reverse") && theCard.contains("Reverse")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("Reverse")) {
                    return true;
                }
            }
        }
        if (check.contains("Skip") && theCard.contains("Skip")) {
            for (int i = 0; i < theHand.length; i++) {
                if (theHand[i].contains("Skip")) {
                    return true;
                }
            }
        }
        return false;
    }
}
