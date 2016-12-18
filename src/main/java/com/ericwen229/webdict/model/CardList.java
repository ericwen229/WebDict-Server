package com.ericwen229.webdict.model;

import java.util.ArrayList;
import java.util.List;

public class CardList {

    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

}
