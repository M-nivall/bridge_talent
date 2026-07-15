package com.example.Varsani.Employers;

import static com.example.Varsani.utils.Urls.URL_PAYMENT_DETAILS;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.print.PrintHelper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentReceipt extends AppCompatActivity {
    private TextView tv_payment_id, tv_name, tv_job_posted, tv_payment_code, tv_payment_date,tv_amount;
    private String jobID, datePosted, payment_id, transaction_code, jobTitle, amount, company_name;
    private ImageView btn_printfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_receipt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize TextViews
        tv_payment_id = findViewById(R.id.tv_payment_id);
        tv_name = findViewById(R.id.tv_name);
        tv_job_posted = findViewById(R.id.tv_job_posted);
        tv_payment_code = findViewById(R.id.tv_payment_code);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        btn_printfile = findViewById(R.id.btn_printfile);
        tv_amount = findViewById(R.id.tv_amount);

        // Get the jobID from the Intent
        jobID = getIntent().getStringExtra("jobID");
        jobTitle = getIntent().getStringExtra("jobTitle");
        datePosted = getIntent().getStringExtra("datePosted");

        tv_job_posted.setText("Job: " + jobTitle);
        tv_payment_date.setText("Date: " + datePosted);

        btn_printfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
            }
        });

        // Call API to fetch payment details
        requests();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void requests() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PAYMENT_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);
                                    //requestID = jsn.getString("requestID");
                                    payment_id = jsn.getString("payment_id");
                                    company_name = jsn.getString("company_name");
                                    amount = jsn.getString("amount");
                                    transaction_code = jsn.getString("transaction_code");


                                    // Set the values in the TextViews
                                    tv_payment_id.setText("Payment ID: " + payment_id);
                                    tv_name.setText("Employer: " + company_name);
                                    tv_payment_code.setText("Payment Code: " + transaction_code);
                                    tv_amount.setText("Amount: KSH " + amount);
                                }
                            } else {
                                showToast(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToast(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                showToast(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jobID", jobID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void print(){
        btn_printfile.setVisibility(View.GONE);

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View. MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        PrintHelper photoPrinter = new PrintHelper(this); // Assume that 'this' is your activity
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("print", bitmap);

        btn_printfile.setVisibility(View.VISIBLE);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }
}