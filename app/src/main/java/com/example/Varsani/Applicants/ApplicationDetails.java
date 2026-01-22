package com.example.Varsani.Applicants;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Varsani.R;

public class ApplicationDetails extends AppCompatActivity {
    private TextView tv_title, tv_dateApplied, tv_jobType, tv_status, tv_description, tv_company,
            tv_industry, tv_location, tv_email, tv_feedback;
    private CardView card_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
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

        Intent intent = getIntent();

        String applicationID = intent.getStringExtra("applicationID");
        String jobID = intent.getStringExtra("jobID");
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
        tv_description.setText("Description: " + title);
        tv_company.setText("Company: " + companyName);
        tv_industry.setText("Industry: " + industry);
        tv_location.setText("Location: " + location);
        tv_email.setText("Location: " + email);

        card_feedback.setVisibility(View.GONE);

        if (!employerFeedback.equalsIgnoreCase("NULL")) {
            card_feedback.setVisibility(View.VISIBLE);
            tv_feedback.setText(employerFeedback);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}