package com.ludiza.ximdev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class lose extends SettingsApp {
    ImageButton again;
    ImageButton menu;
    SharedPreferences questionSh;
    SharedPreferences.Editor editorCountQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);
        Intent intent = getIntent();
        int value = intent.getIntExtra("correct", 0);

        TextView text = findViewById(R.id.text);
        text.setText(value+"/20");

        again = findViewById(R.id.buttonTryAgain);
        menu = findViewById(R.id.buttonExitToMenu);

        again.setOnClickListener(this);
        menu.setOnClickListener(this);

        questionSh = getSharedPreferences("count", MODE_PRIVATE);
        editorCountQuestion = questionSh.edit();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (R.id.buttonExitToMenu == view.getId()) {
            int countOfQuestion = 20;
            editorCountQuestion.putInt("count", countOfQuestion);
            editorCountQuestion.apply();
            finish();
        }
        if (view.getId() == R.id.buttonTryAgain) {
            int countOfQuestion = 20;
            editorCountQuestion.putInt("count", countOfQuestion);
            editorCountQuestion.apply();
            finish();
            Intent actv = new Intent(this, game.class);
            startActivity(actv);
        }
    }

}