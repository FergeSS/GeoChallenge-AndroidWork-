package com.company.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class settings extends SettingsApp {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton ok = findViewById(R.id.buttonOk);
        ok.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        super.onClick(view);
        if (view.getId() == R.id.buttonOk) {
            finish();
        }
    }
}