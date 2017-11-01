package com.ab.paymentcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ab.paymentcard.Card;
import com.ab.paymentcard.PaymentCard;
import com.ab.paymentcard.R;

public class SampleActivity extends AppCompatActivity {

    private PaymentCard paymentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentCard = (PaymentCard) findViewById(R.id.paymentCard);
        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card card = paymentCard.getCardDetails();
//                if (bundle != null) {
//                    Log.d("CardHolder : ", bundle.getString("BUNDLE_KEY_CARD_NAME"));
//                    Log.d("Card Number : ", bundle.getString("BUNDLE_KEY_CARD_NUMBER"));
//                    Log.d("Card CVV : ", bundle.getString("BUNDLE_KEY_CARD_CVV"));
//                    Log.d("Card EXPIRY : ", bundle.getString("BUNDLE_KEY_CARD_EXPIRY"));
//                }
                Log.e("Card Name: ", card.getCardHolderName());
                Log.e("Card Number: ", card.getCardNumber());
                Log.e("Card Exp: ", card.getCardExpDate());
                Log.e("Card CVV: ", card.getCardCVV());
                Log.e("Card Default: ", card.isCardDefault()+"");
            }
        });
    }
}
