package com.example.Varsani.Employers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Varsani.R;

public class MyJobDetails extends AppCompatActivity {

    private TextView tvTitle, tvCategory, tvLevel, tvDescription, tvQualifications,
            tvResponsibilities, tvLocation, tvType, tvSalary, tvDatePosted, tvDeadline, tvStatus;
    private Button btnViewApplicants;
    private String jobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_details);

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
            Intent in = new Intent(MyJobDetails.this, ApplicantsList.class);
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
