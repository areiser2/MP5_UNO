package com.example.mp5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        TextView theWinner = findViewById(R.id.theWinner);
        TextView theLoser = findViewById(R.id.theLoser);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        ImageView image = findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.thumbup);
        ImageView image2 = findViewById(R.id.imageView3);
        image2.setImageResource(R.drawable.thumbdown);
        String p1Name = intent.getStringExtra("p1Name");
        String p2Name = intent.getStringExtra("p2Name");
        Boolean p1Winner = intent.getBooleanExtra("p1Wins", false);

        if (p1Winner) {
            theWinner.setText(p1Name);
            theLoser.setText(p2Name);
        } else {
            theWinner.setText(p2Name);
            theLoser.setText(p1Name);
        }
    }
}
