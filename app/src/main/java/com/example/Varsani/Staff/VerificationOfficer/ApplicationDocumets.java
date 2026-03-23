package com.example.Varsani.Staff.VerificationOfficer;

import static com.example.Varsani.utils.Urls.URL_DOCUMENTS;
import static com.example.Varsani.utils.Urls.URL_RESPOND_APPLICATION;
import static com.example.Varsani.utils.Urls.URL_VERIFY_APPLICATION;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Employers.JobApplicationDetails;
import com.example.Varsani.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApplicationDocumets extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone, tvBio, tvSkills, tvEducation,
            tvSalary, tvNotice, tvDate, tvStatus;

    private Button btnViewCV, btnViewCover, btnViewCertificate;
    private Button btnShortlist, btnReject, btn_verify;

    private String cvUrl, coverLetter, certificate;
    private String applicationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_application_documets);

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
        btnViewCertificate = findViewById(R.id.btnViewCertificate);

        btnShortlist = findViewById(R.id.btnShortlist);
        btnReject = findViewById(R.id.btnReject);
        btn_verify = findViewById(R.id.btn_verify);

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
        certificate = intent.getStringExtra("certificate");

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
//View Certificate
        btnViewCertificate.setOnClickListener(v -> {

            if (certificate == null || certificate.isEmpty()) {
                Toast.makeText(this, "Certificate not available", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullCoverUrl = URL_DOCUMENTS + certificate;

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

        btn_verify.setOnClickListener(v ->
                showResponseDialog("Verified")
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void showResponseDialog(String applicationStatus) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (applicationStatus.equals("Verified")) {
            builder.setTitle("Verify Documents");
        } else {
            builder.setTitle("Reject Application");
        }

        final EditText input = new EditText(this);
        input.setHint("Enter Remarks");
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

        StringRequest request = new StringRequest(Request.Method.POST, URL_VERIFY_APPLICATION,
                response -> {

                    try {

                        JSONObject obj = new JSONObject(response);
                        int statusResponse = obj.getInt("status");
                        String message = obj.getString("message");

                        Toast toast = Toast.makeText(
                                ApplicationDocumets.this,
                                message,
                                Toast.LENGTH_SHORT
                        );
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();

                        if(statusResponse == 1){
                            finish();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },
                error -> {

                    Toast toast = Toast.makeText(
                            ApplicationDocumets.this,
                            "Server error: " + error.getMessage(),
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