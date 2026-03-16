package com.example.Varsani.Employers;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Varsani.R;

public class PaymentReceipt extends AppCompatActivity {
    private TextView tvRequestID, tvItems, tvRequestDate, tvRequestStatus, tvQuantity,tv_request_amount;
    private String requestID, items, requestDate, requestStatus, quantity,amount;
    private ImageView btn_printfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_receipt);

    }
}