package com.ab.paymentcard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

/**
 * Created by Ankush on 29-10-2017.
 */

public class PaymentCard extends LinearLayout {
    private static final String BUNDLE_KEY_CARD_NAME = "BUNDLE_KEY_CARD_NAME";
    private static final String BUNDLE_KEY_CARD_NUMBER = "BUNDLE_KEY_CARD_NUMBER";
    private static final String BUNDLE_KEY_CARD_EXPIRY = "BUNDLE_KEY_CARD_EXPIRY";
    private static final String BUNDLE_KEY_CARD_CVV = "BUNDLE_KEY_CARD_CVV";
    private static final String BUNDLE_KEY_CARD_IS_DEFAULT = "BUNDLE_KEY_CARD_IS_DEFAULT";

    private Context context;
    private AttributeSet attributeSet;
    private LinearLayout llCard;
    private AppCompatEditText etCardHolder, etCardNumber, etExpiry, etCVV;
    private TypedArray typedArray;
    private CardsBrand cardBrand;
    private CheckBox cbDefault;

    public PaymentCard(Context context) {
        super(context);
        this.context = context;
//        if (!isInEditMode()) {
        initialize();
//        }
    }


    public PaymentCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attributeSet = attrs;
//        if (!isInEditMode()) {
        initialize();
//        }
    }

    public PaymentCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attributeSet = attrs;
//        if (!isInEditMode()) {
        initialize();
//        }
    }

    public void setTextColor(int color) {
        etCardNumber.setTextColor(color);
        etCardHolder.setTextColor(color);
        etCVV.setTextColor(color);
        etExpiry.setTextColor(color);
    }

    public void setBackgroundColor(int color) {
        GradientDrawable drawable = (GradientDrawable) llCard.getBackground();
        drawable.setColor(color);
    }

    /**
     * set card holder name
     *
     * @param name name of Cardholder
     */
    public void setCardHolder(String name) {
        etCardHolder.setText(name);
    }

    /**
     * set card number
     *
     * @param number Card number
     */
    public void setCardNumber(String number) {
        etCardNumber.setText(number);
    }


    private void initialize() {
        if (context != null) {
            inflate(getContext(), R.layout.payment_card, this);

            llCard = (LinearLayout) findViewById(R.id.llCard);
            etCardHolder = (AppCompatEditText) findViewById(R.id.etCardHolder);
            etCardNumber = (AppCompatEditText) findViewById(R.id.etCardNumber);
            etExpiry = (AppCompatEditText) findViewById(R.id.etExpriy);
            etCVV = (AppCompatEditText) findViewById(R.id.etCVV);
            cbDefault = (CheckBox) findViewById(R.id.cbDefault);
            cardBrand = CardsBrand.UNKNOWN;
            setExpiryDateListener();


            if (attributeSet != null) {
                @ColorInt int textColor, backgroundColor;
                String cardHolder, cardNumber;

                typedArray = context.getTheme().obtainStyledAttributes(attributeSet,
                        R.styleable.PaymentCard, 0, 0);
                textColor = typedArray.getColor(R.styleable.PaymentCard_textColor,
                        Color.parseColor("#000000"));
                backgroundColor = typedArray.getColor(R.styleable.PaymentCard_backgroundColor,
                        Color.parseColor("#000000"));
                cardHolder = typedArray.getString(R.styleable.PaymentCard_cardHolder);
                cardNumber = typedArray.getString(R.styleable.PaymentCard_cardNumber);

                typedArray.recycle();

                if (cardHolder != null && !cardHolder.isEmpty()) {
                    setCardHolder(cardHolder);
                }

                if (cardNumber != null && !cardNumber.isEmpty()) {
                    setCardNumber(cardNumber);
                }

                if (textColor != -1) {
                    setTextColor(textColor);
                }

                if (backgroundColor != -1) {
                    setBackgroundColor(backgroundColor);
                }

            }
            recogniseCardBrand();
        }
    }

    private void recogniseCardBrand() {
        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cardBrand = CardsBrand.detect(editable.toString());
                int drawable = R.drawable.debit_card;
                switch (cardBrand) {
                    case VISA:
                        drawable = R.drawable.visa;
                        break;
                    case MASTERCARD:
                        drawable = R.drawable.mastercard;
                        break;
                    case AMERICAN_EXPRESS:
                        drawable = R.drawable.amex;
                        break;
                    case MAESTRO:
                        drawable = R.drawable.maestro_card;
                        break;
                    case DISCOVER:
                        drawable = R.drawable.discover;
                        break;
                    case JCB:
                        drawable = R.drawable.jcb;
                        break;
                    case DINERS_CLUB:
                        drawable = R.drawable.diners_club;
                        break;
                    default:

                }
                etCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable, 0);
            }
        });
    }

    private void setExpiryDateListener() {
        if (etCVV != null) {
            etExpiry.addTextChangedListener(new TextWatcher() {
                int preLength, postLength;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    preLength = charSequence.length();
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    postLength = charSequence.length();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 2) {
                        if (postLength > preLength) {
                            editable.insert(2, "/");
                        } else {
                            editable.delete(1, 2);
                        }
                    }


                }
            });
        }
    }


    /**
     * get card details in a bundle
     *
     * @return a bundle with keys
     * BUNDLE_KEY_CARD_NAME,
     * BUNDLE_KEY_CARD_NUMBER,
     * BUNDLE_KEY_CARD_EXPIRY,
     * BUNDLE_KEY_CARD_CVV,
     * BUNDLE_KEY_CARD_IS_DEFAULT
     */
    public Card getCardDetails() {
        Bundle bundle = new Bundle();
        Card card = new Card();
        card.setCardHolderName(etCardHolder.getText().toString());
        card.setCardNumber(etCardNumber.getText().toString());
        card.setCardExpDate(etExpiry.getText().toString());
        card.setCardCVV(etCVV.getText().toString());
        card.setCardDefault(cbDefault.isChecked());
//        bundle.putString(BUNDLE_KEY_CARD_NAME, etCardHolder.getText().toString());
//        bundle.putString(BUNDLE_KEY_CARD_NUMBER, etCardNumber.getText().toString());
//        bundle.putString(BUNDLE_KEY_CARD_EXPIRY, etExpiry.getText().toString());
//        bundle.putString(BUNDLE_KEY_CARD_CVV, etCVV.getText().toString());
//        bundle.putBoolean(BUNDLE_KEY_CARD_IS_DEFAULT, cbDefault.isChecked());
        return card;
    }

}
