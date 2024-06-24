package com.company.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class win extends SettingsApp {

    ImageButton exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        exit = findViewById(R.id.buttonExitToMenu);
        exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.buttonExitToMenu) {
            finish();
        }
    }
}