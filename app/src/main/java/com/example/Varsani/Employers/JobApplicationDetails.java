package com.example.Varsani.Employers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Varsani.R;
import com.google.android.material.button.MaterialButton;

public class JobApplicationDetails extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone, tvBio, tvSkills, tvEducation,
            tvSalary, tvNotice, tvDate, tvStatus;

    MaterialButton btnViewCV, btnViewCover;

    String cvUrl, coverLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_application_details);

        // Init views
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvBio = findViewById(R.id.tvBio);
        tvSkills = findViewById(R.id.tvSkills);
        tvEducation = findViewById(R.id.tvEducation);
        tvSalary = findViewById(R.id.tvSalary);
        tvNotice = findViewById(R.id.tvNotice);
        tvDate = findViewById(R.id.tvDate);
        tvStatus = findViewById(R.id.tvStatus);

        btnViewCV = findViewById(R.id.btnViewCV);
        btnViewCover = findViewById(R.id.btnViewCover);

        // Get intent data
        Intent intent = getIntent();

        tvName.setText(intent.getStringExtra("fullName"));
        tvEmail.setText(intent.getStringExtra("email"));
        tvPhone.setText(intent.getStringExtra("phone"));
        tvBio.setText(intent.getStringExtra("bio"));
        tvSkills.setText(intent.getStringExtra("skills"));
        tvEducation.setText(intent.getStringExtra("education"));
        tvSalary.setText("Expected Salary: " + intent.getStringExtra("salary"));
        tvNotice.setText("Notice Period: " + intent.getStringExtra("noticePeriod"));
        tvDate.setText("Applied On: " + intent.getStringExtra("dateApplied"));

        String status = intent.getStringExtra("applicationStatus");
        tvStatus.setText(status);

        cvUrl = intent.getStringExtra("cvUrl");
        coverLetter = intent.getStringExtra("coverLetter");

        // View CV
        btnViewCV.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(cvUrl));
            startActivity(i);
        });

        // View Cover Letter
        btnViewCover.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(coverLetter));
            startActivity(i);
        });
    }
}
