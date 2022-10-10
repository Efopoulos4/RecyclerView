package com.example.recyclerviewfinal;

public class Item {
    private String word;
    private boolean clicked;

    public Item(String word, boolean clicked) {
        this.word = word;
        this.clicked = clicked;
    }

    public String getWord() {
        return word;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
