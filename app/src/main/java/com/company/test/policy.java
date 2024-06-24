package com.company.test;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

public class policy extends SettingsApp {
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        findViewById(R.id.buttonBack).setOnClickListener(this);

        WebView policy = findViewById(R.id.textPolicy);
        policy.loadUrl("https://telegra.ph/Geo-Challenge-06-17");

        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.buttonBack) {
            finish();
        }
    }
}