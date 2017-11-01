package com.ab.paymentcard;

import java.util.regex.Pattern;

/**
 * Created by Ankush on 29-10-2017.
 */

public enum CardsBrand {

    UNKNOWN,
    VISA("^4[0-9]+"),
    MASTERCARD("^5[1-5][0-9]*"),
    AMERICAN_EXPRESS("^3[47][0-9]*"),
    MAESTRO("^(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)"),
    DINERS_CLUB("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
    DISCOVER("^6(?:011|5[0-9]{2})[0-9]*"),
    JCB("^(?:2131|1800|35\\d{3})+");

    private Pattern pattern;

    CardsBrand() {
        this.pattern = null;
    }

    CardsBrand(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public static CardsBrand detect(String cardNumber) {

        for (CardsBrand cardType : CardsBrand.values()) {
            if (null == cardType.pattern) continue;
            if (cardType.pattern.matcher(cardNumber).matches()) return cardType;
        }

        return UNKNOWN;
    }

}