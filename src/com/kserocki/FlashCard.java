package com.kserocki;

public class FlashCard {
    // Variables
    private String question;
    private String answer;

    // Constructor of FlashCard class
    public FlashCard(String q, String a) {
        question = q;
        answer = a;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
