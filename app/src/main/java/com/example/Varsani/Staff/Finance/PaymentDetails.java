package com.example.Varsani.Staff.Finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Employers.ApplicantsList;
import com.example.Varsani.Employers.MyJobDetails;
import com.example.Varsani.Staff.Adapters.AdapterQuotItems;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterClientItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_APPROVE_PAYMENT;
import static com.example.Varsani.utils.Urls.URL_APPROVE_SERV_PAYMENTS;
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;
import static com.example.Varsani.utils.Urls.URL_QUOTATION_ITEMS;

public class PaymentDetails extends AppCompatActivity {
    private TextView tvTitle, tvCategory, tvLevel, tvDescription, tvQualifications,
            tvResponsibilities, tvLocation, tvType, tvSalary, tvDatePosted, tvDeadline,
            tvStatus, tvEmployer, tvAmount, tvPaymentCode, tvPaymentStatus;
    private Button btnApprovePayment;
    private String jobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        getSupportActionBar().setSubtitle("Payment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle = findViewById(R.id.tvTitle);
        tvCategory = findViewById(R.id.tvCategory);
        tvLevel = findViewById(R.id.tvLevel);
        tvDescription = findViewById(R.id.tvDescription);
        tvQualifications = findViewById(R.id.tvQualifications);
        tvResponsibilities = findViewById(R.id.tvResponsibilities);
        tvLocation = findViewById(R.id.tvLocation);
        tvType = findViewById(R.id.tvType);
        tvSalary = findViewById(R.id.tvSalary);
        tvDatePosted = findViewById(R.id.tvDatePosted);
        tvDeadline = findViewById(R.id.tvDeadline);
        tvStatus = findViewById(R.id.tvStatus);
        btnApprovePayment = findViewById(R.id.btnApprovePayment);

        tvEmployer = findViewById(R.id.tvEmployer);
        tvAmount = findViewById(R.id.tvAmount);
        tvPaymentCode = findViewById(R.id.tvPaymentCode);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);

        Intent intent=getIntent();

        jobID=intent.getStringExtra("jobID");
        String jobTitle=intent.getStringExtra("jobTitle");
        String jobCategory=intent.getStringExtra("jobCategory");
        String jobLevel=intent.getStringExtra("jobLevel");
        String description=intent.getStringExtra("description");
        String qualifications=intent.getStringExtra("qualifications");
        String jobResponsibilities=intent.getStringExtra("jobResponsibilities");
        String location=intent.getStringExtra("location");
        String jobType=intent.getStringExtra("jobType");
        String salaryRange=intent.getStringExtra("salaryRange");
        String datePosted=intent.getStringExtra("datePosted");
        String deadline=intent.getStringExtra("deadline");
        String jobStatus=intent.getStringExtra("jobStatus");

        String companyName=intent.getStringExtra("companyName");
        String contacts=intent.getStringExtra("contacts");
        String amount=intent.getStringExtra("amount");
        String paymentMode=intent.getStringExtra("paymentMode");
        String transactionCode=intent.getStringExtra("transactionCode");
        String paymentStatus=intent.getStringExtra("paymentStatus");

        tvEmployer.setText("Employer: " + companyName);
        tvTitle.setText("Job Title: " + jobTitle);
        tvCategory.setText("Category: " + jobCategory);
        tvLevel.setText("Level: " + jobLevel);


        tvDescription.setText(description);
        tvQualifications.setText(qualifications);
        tvResponsibilities.setText(jobResponsibilities);
        tvLocation.setText(location);
        tvType.setText(jobType);
        tvSalary.setText(salaryRange);
        tvDatePosted.setText(datePosted);
        tvDeadline.setText(deadline);
        tvStatus.setText(jobStatus);

        tvAmount.setText("Ksh " + amount);
        tvPaymentCode.setText(transactionCode);
        tvPaymentStatus.setText(paymentStatus);

        btnApprovePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertApprove();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void approvePayment(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APPROVE_PAYMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(PaymentDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(PaymentDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(PaymentDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(PaymentDetails.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("jobID",jobID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void alertApprove(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Confirm Payment");
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Approve ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                approvePayment();
                return;
            }
        });
        alertDialog.show();
    }

}
