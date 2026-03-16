package com.example.Varsani.Staff.VerificationOfficer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Varsani.Employers.ApplicantsList;
import com.example.Varsani.Employers.MyJobDetails;
import com.example.Varsani.Employers.ShortlistedApplicants;
import com.example.Varsani.R;

public class JobInformation extends AppCompatActivity {
    private TextView tvTitle, tvCategory, tvLevel, tvDescription, tvQualifications,
            tvResponsibilities, tvLocation, tvType, tvSalary, tvDatePosted, tvDeadline, tvStatus;
    private Button btnViewApplicants, btnShortlistedApplicants;
    private String jobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_information);

        getSupportActionBar().setSubtitle("Job Details");
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
        btnViewApplicants = findViewById(R.id.btnViewApplicants);
        btnShortlistedApplicants = findViewById(R.id.btnShortlistedApplicants);

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

        btnViewApplicants.setOnClickListener(v -> {
            Intent in = new Intent(JobInformation.this, ApplicantInfo.class);
            in.putExtra("jobID", jobID);
            startActivity(in);
        });

        btnShortlistedApplicants.setOnClickListener(v -> {
            Intent in = new Intent(JobInformation.this, ShortlistedApplicants.class);
            in.putExtra("jobID", jobID);
            startActivity(in);
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}