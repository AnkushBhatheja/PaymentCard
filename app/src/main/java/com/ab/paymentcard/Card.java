package com.ab.paymentcard;

/**
 * Created by Ankush on 01-11-2017.
 */

public class Card {
    private String CardNumber, CardHolderName, CardExpDate, CardCVV;
    private boolean isCardDefault;

    Card() {
        CardNumber = "";
        CardHolderName = "";
        CardExpDate = "";
        CardCVV = "";
        isCardDefault = false;
    }

    public boolean isCardDefault() {
        return isCardDefault;
    }

    void setCardDefault(boolean cardDefault) {
        isCardDefault = cardDefault;
    }

    public String getCardCVV() {
        return CardCVV;
    }

    void setCardCVV(String cardCVV) {
        CardCVV = cardCVV;
    }

    public String getCardExpDate() {
        return CardExpDate;
    }

    void setCardExpDate(String cardExpDate) {
        CardExpDate = cardExpDate;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }


}
