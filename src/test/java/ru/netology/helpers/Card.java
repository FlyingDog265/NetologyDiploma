package ru.netology.helpers;

public class Card {
    private String cardNumber;
    private String cardMonth;
    private String cardYear;
    private String cardOwner;
    private String cardCvcCvv;

    public Card(String cardNumber, String cardMonth, String cardYear, String cardOwner, String cardCvcCvv) {
        this.cardNumber = cardNumber;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        this.cardOwner = cardOwner;
        this.cardCvcCvv = cardCvcCvv;
    }

    public Card() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardMonth() {
        return cardMonth;
    }

    public String getCardYear() {
        return cardYear;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public String getCardCvcCvv() {
        return cardCvcCvv;
    }
}
