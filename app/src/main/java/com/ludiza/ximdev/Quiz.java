package com.ludiza.ximdev;
public class Quiz {
    private String question;
    private String[] answers;
    private int correctAnswer;
    private int idImage;

    public Quiz(String question, String[] answers, int correctAnswer, int idImage) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.idImage = idImage;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getImageId() {
        return idImage;
    }
}
