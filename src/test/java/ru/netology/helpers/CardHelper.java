package ru.netology.helpers;

public class CardHelper {

    private CardHelper() {
    }

    private static final String approvedCardNumber = "4444 4444 4444 4441";
    private static final String declinedCardNumber = "4444 4444 4444 4442";
    private static final String validCardMonth = "08";
    private static final String validCardYear = "22";
    private static final String validCardOwner = "Ivanov Ivan";
    private static final String validCardCvcCvv = "999";

    public static Card getCardInfoWithApprovedCardNumber() {
        return new Card(
                approvedCardNumber, validCardMonth, validCardYear, validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithDeclinedCardNumber() {
        return new Card(
                declinedCardNumber, validCardMonth, validCardYear, validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithUnknownCardNumber() {
        return new Card(
                "5555 5555 5555 5555", validCardMonth, validCardYear, validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithTooSmallYear() {
        return new Card(
                approvedCardNumber, validCardMonth, "12", validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithTooBigYear() {
        return new Card(
                approvedCardNumber, validCardMonth, "55", validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithTooSmallMonth() {
        return new Card(
                approvedCardNumber, "00", validCardYear, validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithTooBigMonth() {
        return new Card(
                approvedCardNumber, "13", validCardYear, validCardOwner, validCardCvcCvv);
    }

    public static Card getCardInfoWithIncorrectCardNumberAndCvc() {
        return new Card(
                "5555 5555", "13", validCardYear, validCardOwner, "0");
    }

    public static Card getCardInfoWithIncorrectOwnerByCyrillic() {
        return new Card(
                approvedCardNumber, validCardMonth, validCardYear, "Иванов Иван", validCardCvcCvv);
    }
}
