package com.example.Varsani.Applicants;

import static com.example.Varsani.utils.Urls.URL_RESPOND_OFFER;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApplicationDetails extends AppCompatActivity {

    private TextView tv_title, tv_dateApplied, tv_jobType, tv_status, tv_description, tv_company,
            tv_industry, tv_location, tv_email, tv_feedback;

    private CardView card_feedback, card_job_offer;

    private Button btn_accept_offer, btn_decline_offer;

    private String applicationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);

        getSupportActionBar().setSubtitle("Application Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_title = findViewById(R.id.tv_title);
        tv_dateApplied = findViewById(R.id.tv_dateApplied);
        tv_jobType = findViewById(R.id.tv_jobType);
        tv_status = findViewById(R.id.tv_status);
        tv_description = findViewById(R.id.tv_description);
        tv_company = findViewById(R.id.tv_company);
        tv_industry = findViewById(R.id.tv_industry);
        tv_location = findViewById(R.id.tv_location);
        tv_email = findViewById(R.id.tv_email);

        card_feedback = findViewById(R.id.card_feedback);
        tv_feedback = findViewById(R.id.tv_feedback);

        card_job_offer = findViewById(R.id.card_job_offer);

        btn_accept_offer = findViewById(R.id.btn_accept_offer);
        btn_decline_offer = findViewById(R.id.btn_decline_offer);

        Intent intent = getIntent();

        applicationID = intent.getStringExtra("applicationID");

        String dateApplied = intent.getStringExtra("dateApplied");
        String applicationStatus = intent.getStringExtra("applicationStatus");
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String location = intent.getStringExtra("location");
        String jobType = intent.getStringExtra("jobType");
        String companyName = intent.getStringExtra("companyName");
        String email = intent.getStringExtra("email");
        String industry = intent.getStringExtra("industry");
        String employerFeedback = intent.getStringExtra("employerFeedback");

        tv_title.setText("Job Title: " + title);
        tv_dateApplied.setText("Date Applied: " + dateApplied);
        tv_jobType.setText("Job Type: " + jobType);
        tv_status.setText("Status: " + applicationStatus);
        tv_description.setText("Description: " + description);
        tv_company.setText("Company: " + companyName);
        tv_industry.setText("Industry: " + industry);
        tv_location.setText("Location: " + location);
        tv_email.setText("Email: " + email);

        card_feedback.setVisibility(View.GONE);

        if (employerFeedback != null && !employerFeedback.equalsIgnoreCase("NULL")) {
            card_feedback.setVisibility(View.VISIBLE);
            tv_feedback.setText(employerFeedback);
        }

        // Show job offer card only if employer sent offer
        if (applicationStatus.equalsIgnoreCase("Job Offer")) {
            card_job_offer.setVisibility(View.VISIBLE);
        } else {
            card_job_offer.setVisibility(View.GONE);
        }

        btn_accept_offer.setOnClickListener(v -> respondToOffer("Accepted"));

        btn_decline_offer.setOnClickListener(v -> respondToOffer("Declined"));
    }

    private void respondToOffer(String responseStatus) {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL_RESPOND_OFFER,
                response -> {

                    try {

                        JSONObject obj = new JSONObject(response);

                        int status = obj.getInt("status");
                        String message = obj.getString("message");

                        Toast toast = Toast.makeText(
                                ApplicationDetails.this,
                                message,
                                Toast.LENGTH_SHORT
                        );
                        toast.setGravity(Gravity.TOP,0,250);
                        toast.show();

                        if(status == 1){

                            tv_status.setText("Status: " + responseStatus);

                            btn_accept_offer.setEnabled(false);
                            btn_decline_offer.setEnabled(false);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },
                error -> {

                    Toast.makeText(
                            ApplicationDetails.this,
                            "Network error",
                            Toast.LENGTH_SHORT
                    ).show();

                }
        ){

            @Override
            protected Map<String,String> getParams(){

                Map<String,String> params = new HashMap<>();

                params.put("application_id", applicationID);
                params.put("offer_response", responseStatus);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}