package com.example.mp5;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.enterGame);
        start.setOnClickListener(unused -> createGameClicked());
        ImageView image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.unoo);
    }

    private void createGameClicked() {
        // Set up an Intent that will launch GameActivity
        EditText name1 = findViewById(R.id.username);
        EditText name2 = findViewById(R.id.user2);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("user1", name1.getText().toString());
        intent.putExtra("user2", name2.getText().toString());
        startActivity(intent);
        finish();
    }
}
