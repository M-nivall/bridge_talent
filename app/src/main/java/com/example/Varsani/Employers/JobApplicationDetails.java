package com.example.Varsani.Employers;

import static com.example.Varsani.utils.Urls.URL_APPROVE_PAYMENT;
import static com.example.Varsani.utils.Urls.URL_DOCUMENTS;
import static com.example.Varsani.utils.Urls.URL_RESPOND_APPLICATION;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class JobApplicationDetails extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone, tvBio, tvSkills, tvEducation,
            tvSalary, tvNotice, tvDate, tvStatus;

    private Button btnViewCV, btnViewCover;
    private Button btnShortlist, btnReject;

    private String cvUrl, coverLetter;
    private String applicationID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_application_details);

        //getSupportActionBar().setSubtitle("Applicant Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        btnShortlist = findViewById(R.id.btnShortlist);
        btnReject = findViewById(R.id.btnReject);

        // Get intent data
        Intent intent = getIntent();

        applicationID = intent.getStringExtra("applicationID");

        tvName.setText(intent.getStringExtra("fullName"));
        tvEmail.setText("Email: " + intent.getStringExtra("email"));
        tvPhone.setText("Phone: " + intent.getStringExtra("phone"));
        tvBio.setText(intent.getStringExtra("bio"));
        tvSkills.setText(intent.getStringExtra("skills"));
        tvEducation.setText(intent.getStringExtra("education"));
        tvSalary.setText("Expected Salary: Ksh " + intent.getStringExtra("salary"));
        tvNotice.setText("Notice Period: " + intent.getStringExtra("noticePeriod"));
        tvDate.setText("Applied On: " + intent.getStringExtra("dateApplied"));

        String status = intent.getStringExtra("applicationStatus");
        tvStatus.setText(status);

        cvUrl = intent.getStringExtra("cvUrl");
        coverLetter = intent.getStringExtra("coverLetter");

// View CV
        btnViewCV.setOnClickListener(v -> {

            if (cvUrl == null || cvUrl.isEmpty()) {
                Toast.makeText(this, "CV not available", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullCvUrl = URL_DOCUMENTS + cvUrl;

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(fullCvUrl));
            startActivity(i);
        });

// View Cover Letter
        btnViewCover.setOnClickListener(v -> {

            if (coverLetter == null || coverLetter.isEmpty()) {
                Toast.makeText(this, "Cover letter not available", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullCoverUrl = URL_DOCUMENTS + coverLetter;

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(fullCoverUrl));
            startActivity(i);
        });


        btnShortlist.setOnClickListener(v ->
                showResponseDialog("Shortlisted")
        );

        btnReject.setOnClickListener(v ->
                showResponseDialog("Rejected")
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * =============================
     * EMPLOYER RESPONSE DIALOG
     * =============================
     */
    private void showResponseDialog(String applicationStatus) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (applicationStatus.equals("Shortlisted")) {
            builder.setTitle("Shortlist Applicant");
        } else {
            builder.setTitle("Reject Application");
        }

        final EditText input = new EditText(this);
        input.setHint("Feedback message to applicant");
        input.setMinLines(3);
        input.setGravity(Gravity.TOP | Gravity.START);
        input.setPadding(24, 24, 24, 24);

        builder.setView(input);

        builder.setPositiveButton("CONFIRM", (dialog, which) -> {
            String note = input.getText().toString().trim();
            submitEmployerResponse(applicationStatus, note);
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void submitEmployerResponse(String status, String note) {

        StringRequest request = new StringRequest(Request.Method.POST, URL_RESPOND_APPLICATION,
                response -> {
                    Toast toast = Toast.makeText(
                            JobApplicationDetails.this,
                            "Application updated successfully",
                            Toast.LENGTH_SHORT
                    );
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    finish();
                },
                error -> {
                    Toast toast = Toast.makeText(
                            JobApplicationDetails.this,
                            "Failed to update application",
                            Toast.LENGTH_SHORT
                    );
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("application_id", applicationID);
                params.put("status", status);
                params.put("employer_note", note);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
