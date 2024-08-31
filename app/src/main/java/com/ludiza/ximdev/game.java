package com.ludiza.ximdev;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class game extends SettingsApp {
    boolean checkTime = true;
    long currentTime = System.currentTimeMillis();
    long heartTime;
    int countOfQuestion;
    ProgressBar progressBar;
    boolean isStop = false;
    TextView timer;
    Handler handlerTimer = new Handler();
    final int startTime = 300;
    int timeRemaining = startTime;
    Runnable timerRunnable;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton skip;
    ImageButton back;
    ImageButton buttonDialogContinue;
    ImageButton buttonDialogSettings;
    ImageButton buttonDialogStay;
    ImageButton buttonDialogExitToMenu;
    ImageButton buttonDialogOk;
    ImageButton pause;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView questionText;
    TextView counter;
    ImageView img;
    AlertDialog dialog;
    SharedPreferences countOfLive;
    SharedPreferences.Editor editorLive;

    SharedPreferences currentQuest;
    SharedPreferences.Editor editorQuest;

    SharedPreferences heartsTime;
    SharedPreferences.Editor editorHeartsTime;

    SharedPreferences questionSh;
    SharedPreferences.Editor editorCountQuestion;
    ImageView[] hearts;
    int countLive;
    int id;
    List<Quiz> question = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fillQuiz(question);
        hearts = new ImageView[]{findViewById(R.id.heart1), findViewById(R.id.heart2), findViewById(R.id.heart3), findViewById(R.id.heart4), findViewById(R.id.heart5)};

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        questionText = findViewById(R.id.question);
        img = findViewById(R.id.photo);
        counter = findViewById(R.id.countOfQuestion);
        skip = findViewById(R.id.buttonSkip);
        back = findViewById(R.id.buttonBack);
        pause = findViewById(R.id.buttonPause);
        timer = findViewById(R.id.timer);
        progressBar = findViewById(R.id.progressBar);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        skip.setOnClickListener(this);
        back.setOnClickListener(this);
        pause.setOnClickListener(this);

        currentQuest = getSharedPreferences("quest", MODE_PRIVATE);
        editorQuest = currentQuest.edit();
        id = currentQuest.getInt("quest", 0);

        heartsTime = getSharedPreferences("heartsTime", MODE_PRIVATE);
        editorHeartsTime = heartsTime.edit();
        heartTime = heartsTime.getLong("heartsTime", System.currentTimeMillis());

        countOfLive = getSharedPreferences("live", MODE_PRIVATE);
        editorLive = countOfLive.edit();
        countLive = countOfLive.getInt("live", 5);
        countLive += (currentTime - heartTime) / 3600000; //каждый час восстанавливается жизнь
        if (countLive > 5) {
            countLive = 5;
        }
        if (countLive <= 0) {
            showHeartsMenu(this);
        }
        editorLive.putInt("live", countLive);
        editorLive.apply();

        questionSh = getSharedPreferences("count", MODE_PRIVATE);
        editorCountQuestion = questionSh.edit();
        countOfQuestion = questionSh.getInt("count", 20);

        if (id >= 19) {
            id = 0;
            editorQuest.putInt("quest", id);
            editorQuest.apply();
        }
        questionText.setText(question.get(id).getQuestion());
        text1.setText(question.get(id).getAnswers()[0]);
        text2.setText(question.get(id).getAnswers()[1]);
        text3.setText(question.get(id).getAnswers()[2]);
        text4.setText(question.get(id).getAnswers()[3]);
        counter.setText((id + 1) + "/20");
        img.setImageResource(question.get(id).getImageId());
        for (int i = 0; i < countLive; ++i) {
            hearts[i].setImageResource(R.drawable.heart);
        }
        startCountdown();
    }

    public void nextQuest(ImageButton correct) {
        timeRemaining = startTime;
        if (correct != null) {
            correct.setBackgroundResource(R.drawable.variant_true);
        }
        else {
            --countLive;
            editorLive.putInt("live", countLive);
            editorLive.apply();
            if (checkTime) {
                editorHeartsTime.putLong("heartsTime", System.currentTimeMillis());
                editorHeartsTime.apply();
                checkTime = false;
            }
            if (countLive > 0) {
                hearts[countLive].setImageResource(R.drawable.heart_no);
            }
            else {
                hearts[0].setImageResource(R.drawable.heart_no);
                showHeartsMenu(this);
            }
        }
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        button4.setClickable(false);
        id++;
        editorQuest.putInt("quest", id);
        editorQuest.apply();

        Handler ms = new Handler();
        ms.postDelayed(new Runnable() {
            @Override
            public void run() {
                questionText.setText(question.get(id).getQuestion());
                text1.setText(question.get(id).getAnswers()[0]);
                text2.setText(question.get(id).getAnswers()[1]);
                text3.setText(question.get(id).getAnswers()[2]);
                text4.setText(question.get(id).getAnswers()[3]);
                img.setImageResource(question.get(id).getImageId());
                counter.setText((id + 1) + "/20");
                button1.setBackgroundResource(R.drawable.variant);
                button2.setBackgroundResource(R.drawable.variant);
                button3.setBackgroundResource(R.drawable.variant);
                button4.setBackgroundResource(R.drawable.variant);
                button1.setClickable(true);
                button2.setClickable(true);
                button3.setClickable(true);
                button4.setClickable(true);
            }
        }, 500);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.button1) {
            if (question.get(id).getCorrectAnswer() == 1) {
                nextQuest(button1);
            }
            else {
                button1.setBackgroundResource(R.drawable.variant_fall);
                button1.setClickable(false);
                --countLive;
                editorLive.putInt("live", countLive);
                editorLive.apply();
                editorHeartsTime.putLong("heartsTime", System.currentTimeMillis());
                editorHeartsTime.apply();
                if (countLive > 0) {
                    hearts[countLive].setImageResource(R.drawable.heart_no);
                }
                else {
                    hearts[0].setImageResource(R.drawable.heart_no);
                    showHeartsMenu(this);
                }
            }
        }
        else if (view.getId() == R.id.button2) {
            if (question.get(id).getCorrectAnswer() == 2) {
                nextQuest(button2);
            }
            else {
                button2.setBackgroundResource(R.drawable.variant_fall);
                button2.setClickable(false);
                editorHeartsTime.putLong("heartsTime", System.currentTimeMillis());
                editorHeartsTime.apply();
                --countLive;
                editorLive.putInt("live", countLive);
                editorLive.apply();
                if (countLive > 0) {
                    hearts[countLive].setImageResource(R.drawable.heart_no);
                }
                else {
                    hearts[0].setImageResource(R.drawable.heart_no);
                    showHeartsMenu(this);
                }
            }
        }
        else if (view.getId() == R.id.button3) {
            if (question.get(id).getCorrectAnswer() == 3) {
                nextQuest(button3);
            }
            else {
                button3.setBackgroundResource(R.drawable.variant_fall);
                button3.setClickable(false);
                editorHeartsTime.putLong("heartsTime", System.currentTimeMillis());
                editorHeartsTime.apply();
                --countLive;
                editorLive.putInt("live", countLive);
                editorLive.apply();
                if (countLive > 0) {
                    hearts[countLive].setImageResource(R.drawable.heart_no);
                }
                else {
                    hearts[0].setImageResource(R.drawable.heart_no);
                    showHeartsMenu(this);
                }
            }
        }
        else if (view.getId() == R.id.button4) {
            if (question.get(id).getCorrectAnswer() == 4) {
                nextQuest(button4);
            }
            else {
                button4.setBackgroundResource(R.drawable.variant_fall);
                button4.setClickable(false);
                editorHeartsTime.putLong("heartsTime", System.currentTimeMillis());
                editorHeartsTime.apply();
                --countLive;
                editorLive.putInt("live", countLive);
                editorLive.apply();
                if (countLive > 0) {
                    hearts[countLive].setImageResource(R.drawable.heart_no);
                }
                else {
                    hearts[0].setImageResource(R.drawable.heart_no);
                    showHeartsMenu(this);
                }
            }
        }
        else if (view.getId() == R.id.buttonBack) {
            showExitMenu(this);
        }
        else if (view.getId() == R.id.buttonSkip) {
            --countOfQuestion;
            editorCountQuestion.putInt("count", countOfQuestion);
            editorCountQuestion.apply();
            nextQuest(null);
        }
        else if (view.getId() == R.id.buttonPause) {
            showPauseMenu(this);
        }
        else if (view.getId() == R.id.buttonContinue || view.getId() == R.id.buttonStay){
            isStop = false;
            dialog.dismiss();
        }
        else if (view.getId() == R.id.buttonSettings) {
            Intent actv = new Intent(this, settings.class);
            startActivity(actv);
        }
        else if (view.getId() == R.id.buttonExitToMenu) {
            editorQuest.putInt("quest", id);
            editorQuest.apply();
            finish();
        }
        else if (view.getId() == R.id.buttonOk) {
            finish();
        }

        if (id > 19) {
            id = 0;
            editorQuest.putInt("quest", id);
            editorQuest.apply();
            editorLive.putInt("live", countLive);
            editorLive.apply();
            finish();
            if (countOfQuestion == 20) {
                Intent actv = new Intent(this, win.class);
                startActivity(actv);
            }
            else {
                Intent actv = new Intent(this, lose.class);
                actv.putExtra("correct", countOfQuestion);
                startActivity(actv);
            }
        }

    }
    private void startCountdown() {
        timeRemaining = startTime;
        timer.setText("30 sec");

        timerRunnable = new Runnable() {
            @Override
            public void run() {

                if (timeRemaining >= 0) {
                    if (!isStop) {
                        --timeRemaining;
                    }
                    timer.setText((timeRemaining / 10 + 1) + " sec");
                    handlerTimer.postDelayed(this, 100);
                    progressBar.setProgress(timeRemaining);
                } else {
                    --countOfQuestion;
                    nextQuest(null);
                    handlerTimer.postDelayed(this, 100);
                }
            }
        };
        timer.postDelayed(timerRunnable, 0);
    }

    void fillQuiz(List<Quiz> items) {
        items.add(new Quiz("Which city is the largest in terms of population?", new String[] {"Moscow", "Tokyo", "New York", "Shanghai"}, 2, R.drawable.img1)); // 1
        items.add(new Quiz("Which country has the tallest volcano in the world?", new String[] {"Indonesia", "Japan", "Philippines", "Chile"}, 4, R.drawable.img2)); // 2
        items.add(new Quiz("Which river is the longest in the world?", new String[] {"Nile", "Amazon", "Yangtze", "Mississippi"}, 2, R.drawable.img3)); // 3
        items.add(new Quiz("Which continent has the most countries?", new String[] {"Africa", "Asia", "Europe", "North America"}, 1, R.drawable.img4)); // 4
        items.add(new Quiz("What is the name of the highest mountain in Russia and where is it located?", new String[] {"Elbrus, Caucasus", "Altai, Altai Mountains", "Kazbek, North Ossetia", "Shan, Ingushetia"}, 1, R.drawable.img5)); // 5
        items.add(new Quiz("Which lake is the deepest on Earth?", new String[] {"Baikal", "Tanganyika", "Victoria", "Ladoga"}, 1, R.drawable.img6)); // 6
        items.add(new Quiz("In which ocean is the Mariana Trench located?", new String[] {"Atlantic", "Quiet", "Indian", "Arctic"}, 2, R.drawable.img7)); // 7
        items.add(new Quiz("What is the name of the largest lake in the world by area?", new String[] {"Caspian Sea", "Upper", "Ladoga", "Baikal"}, 1, R.drawable.img8)); // 8
        items.add(new Quiz("Where is the longest strait in the world?", new String[] {"Bering Strait", "Mozambique Strait", "Drake Passage", "Strait of Gibraltar"}, 2, R.drawable.img9)); // 9
        items.add(new Quiz("How many oceans are there on Earth?", new String[] {"3", "4", "5", "6"}, 2, R.drawable.img10)); // 10
        items.add(new Quiz("In which part of the world is the longest river in the world located?", new String[] {"Europe", "Asia", "Africa", "Australia"}, 3, R.drawable.img11)); // 11
        items.add(new Quiz("Name the highest point in North America", new String[] {"McKinley", "Denali", "Logan", "Foraker"}, 1, R.drawable.img12)); // 12
        items.add(new Quiz("In which country is the Grand Canyon located?", new String[] {"America", "Venezuela", "Argentina", "Peru"}, 1, R.drawable.img13)); // 13
        items.add(new Quiz("Where is Mount Vesuvius located?", new String[] {"America", "Venezuela", "Italy", "Peru"}, 3, R.drawable.img14)); // 14
        items.add(new Quiz("In which country is the Thar Desert located?", new String[] {"America", "Venezuela", "India", "Peru"}, 3, R.drawable.img15)); // 15
        items.add(new Quiz("Where is the tallest waterfall in the world?", new String[] {"South America", "Venezuela", "India", "Peru"}, 1, R.drawable.img16)); // 16
        items.add(new Quiz("In what country is the Colosseum located?", new String[] {"America", "Venezuela", "Italy", "Peru"}, 3, R.drawable.img17)); // 17
        items.add(new Quiz("In which city is the Eiffel Tower located?", new String[] {"America", "Venezuela", "Italy", "Paris"}, 4, R.drawable.img18)); // 18
        items.add(new Quiz("In which country is the Great Wall of China located?", new String[] {"South America", "Venezuela", "China", "Peru"}, 3, R.drawable.img19)); // 19
        items.add(new Quiz("What mountains separate Europe and Asia?", new String[] {"Mount Everest", "Ural Mountains", "Kilimanjaro", "Mont Blanс"}, 2, R.drawable.img20)); // 20
    }
    public void showExitMenu(Context context) {
        isStop = true;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_exit_game, null);

        buttonDialogStay = view.findViewById(R.id.buttonStay);
        buttonDialogExitToMenu = view.findViewById(R.id.buttonExitToMenu);

        buttonDialogStay.setOnClickListener(this);
        buttonDialogExitToMenu.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        dialog = builder.create();
        Window window = dialog.getWindow();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                isStop = false;
            }
        });
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            window.setAttributes(layoutParams);
        }
        try {
            dialog.show();
        }
        catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public void showHeartsMenu(Context context) {
        isStop = true;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_hearts, null);

        buttonDialogOk= view.findViewById(R.id.buttonOk);

        buttonDialogOk.setOnClickListener(this);

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
        try {
            dialog.show();
        }
        catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public void showPauseMenu(Context context) {
        isStop = true;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_menu, null);

        buttonDialogContinue = view.findViewById(R.id.buttonContinue);
        buttonDialogExitToMenu = view.findViewById(R.id.buttonExitToMenu);
        buttonDialogSettings = view.findViewById(R.id.buttonSettings);

        buttonDialogContinue.setOnClickListener(this);
        buttonDialogExitToMenu.setOnClickListener(this);
        buttonDialogSettings.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        dialog = builder.create();
        Window window = dialog.getWindow();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                isStop = false;
            }
        });
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            window.setAttributes(layoutParams);
        }
        try {
            dialog.show();
        }
        catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }
}