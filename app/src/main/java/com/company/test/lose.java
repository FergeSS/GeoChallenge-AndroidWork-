package com.company.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class lose extends SettingsApp {
    ImageButton again;
    ImageButton menu;

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
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (R.id.buttonExitToMenu == view.getId()) {
            finish();
        }
        if (view.getId() == R.id.buttonTryAgain) {
            Intent actv = new Intent(this, game.class);
            startActivity(actv);
            finish();
        }
    }

}