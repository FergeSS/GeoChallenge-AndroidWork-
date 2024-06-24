package com.company.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends SettingsApp {
    ImageButton buttonExit;
    ImageButton buttonDialogExit;
    ImageButton buttonDialogStay;
    ImageButton buttonStart;
    ImageButton buttonSettings;
    ImageButton buttonPolicy;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonExit = findViewById(R.id.exitButton);
        buttonPolicy = findViewById(R.id.buttonPolicy);
        buttonStart = findViewById(R.id.buttonStart);
        buttonSettings = findViewById(R.id.buttonSettings);

        buttonExit.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonPolicy.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.exitButton) {
            showExitMenu(this);
        }
        if (view.getId() == R.id.buttonExit) {
            finish();
        }
        if (view.getId() == R.id.buttonStay) {
            dialog.dismiss();
        }
        if (view.getId() == R.id.buttonSettings) {
            Intent actv = new Intent(this, settings.class);
            startActivity(actv);
        }
        if (view.getId() == R.id.buttonPolicy) {
            Intent actv = new Intent(this, policy.class);
            startActivity(actv);
        }
        if (view.getId() == R.id.buttonStart) {
            Intent actv = new Intent(this, game.class);
            startActivity(actv);
        }
    }

    public void showExitMenu(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_exit, null);

        buttonDialogExit = view.findViewById(R.id.buttonExit);
        buttonDialogStay = view.findViewById(R.id.buttonStay);

        buttonDialogExit.setOnClickListener(this);

        buttonDialogStay.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            window.setAttributes(layoutParams);
        }
        dialog.show();
    }

}