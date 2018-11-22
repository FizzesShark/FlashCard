package com.example.xbug2.flashcard;

import java.util.HashMap;

public class CardSet {

    HashMap<String, Card> cards;
    private String id;
    private String name;
    private String color;

    public CardSet() {
    }

    public CardSet(HashMap<String, Card> cards, String id, String name, String color) {

        this.cards = cards;
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public HashMap<String, Card> getCards() {
        return cards;
    }

    public void setCards(HashMap<String, Card> cards) {
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
