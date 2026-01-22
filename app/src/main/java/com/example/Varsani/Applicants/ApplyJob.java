package com.example.Varsani.Applicants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.VolleyMultipartRequest;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ApplyJob extends AppCompatActivity {
    private TextView tv_title, tv_company, tv_location, tv_deadline, tv_description, tv_cv_filename,
            tv_cover_filename;
    private EditText edt_expected_salary, edt_notice_period;
    private Button btn_upload_cv, btn_upload_cover, btn_apply_job;
    private ProgressBar progressBar;
    private static final int PICK_COVER_LETTER_REQUEST = 1;
    private static final int PICK_CV_REQUEST = 2;
    private Uri cvPdfUri, coverLetterUri;
    private String jobID, employerID;

    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        getSupportActionBar().setSubtitle("Apply Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_title = findViewById(R.id.tv_title);
        tv_company = findViewById(R.id.tv_company);
        tv_location = findViewById(R.id.tv_location);
        tv_deadline = findViewById(R.id.tv_deadline);
        tv_description = findViewById(R.id.tv_description);
        tv_cv_filename = findViewById(R.id.tv_cv_filename);
        tv_cover_filename = findViewById(R.id.tv_cover_filename);

        edt_expected_salary = findViewById(R.id.edt_expected_salary);
        edt_notice_period = findViewById(R.id.edt_notice_period);

        btn_upload_cv = findViewById(R.id.btn_upload_cv);
        btn_upload_cover = findViewById(R.id.btn_upload_cover);
        btn_apply_job  = findViewById(R.id.btn_apply_job);

        progressBar = findViewById(R.id.progress_bar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        Intent intent = getIntent();

        jobID = intent.getStringExtra("jobID");
        employerID = intent.getStringExtra("employerID");
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String location = intent.getStringExtra("location");
        String jobType = intent.getStringExtra("jobType");
        String salaryRange = intent.getStringExtra("salaryRange");
        String datePosted = intent.getStringExtra("datePosted");
        String deadline = intent.getStringExtra("deadline");
        String jobStatus = intent.getStringExtra("jobStatus");
        String companyName = intent.getStringExtra("companyName");
        String contacts = intent.getStringExtra("contacts");
        String email = intent.getStringExtra("email");
        String industry = intent.getStringExtra("industry");
        String employerDescription = intent.getStringExtra("employerDescription");

        tv_title.setText("Job Title: " + title);
        tv_company.setText("Company Name: " + companyName);
        tv_location.setText("Location: " + location);
        tv_deadline.setText("Deadline: " + deadline);
        tv_description.setText("Job Description: " + description);

        btn_upload_cv.setOnClickListener(v -> selectFile(PICK_CV_REQUEST));
        btn_upload_cover.setOnClickListener(v -> selectFile(PICK_COVER_LETTER_REQUEST));

        btn_apply_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertApply(v);

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

    private void selectFile(int requestCode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        } else {
            openFileChooser(requestCode);
        }
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
            intent.setType("application/pdf"); // For PDFs
            //intent.setType("image/*"); // For images
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedFileUri = data.getData();
            String fileName = getFileName(selectedFileUri);

            if (requestCode == PICK_CV_REQUEST) {

                cvPdfUri = selectedFileUri;
                tv_cv_filename.setText(fileName);
                Toast.makeText(this, "CV Selected: " + fileName, Toast.LENGTH_SHORT).show();

            } else if (requestCode == PICK_COVER_LETTER_REQUEST) {

                coverLetterUri = selectedFileUri;
                tv_cover_filename.setText(fileName);
                Toast.makeText(this, "Cover Letter Selected: " + fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void alertApply(final View v){
        AlertDialog.Builder  builder=new AlertDialog.Builder(v.getContext());
        builder.setTitle("Submit you Application ")
                .setNegativeButton("Close",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        applyNow();
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void applyNow() {

        progressBar.setVisibility(View.VISIBLE);
        btn_apply_job.setVisibility(View.GONE);

        final String salary = edt_expected_salary.getText().toString().trim();
        final String notice_period = edt_notice_period.getText().toString().trim();


        // Validate inputs
        if (TextUtils.isEmpty(salary)) {
            showToast("Please enter expected salary");
            return;
        }
        if (TextUtils.isEmpty(notice_period)) {
            showToast("Please enter your notice period");
            return;
        }

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.URL_SUBMIT_APPLICATION,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(new String(response.data));
                        String status = jsonObject.getString("status");
                        String msg = jsonObject.getString("message");

                        if ("1".equals(status)) {
                            showSuccessDialog(msg);
                        } else {
                            showToast(msg);
                            btn_apply_job.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast(e.toString());
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btn_apply_job.setVisibility(View.VISIBLE);
                    error.printStackTrace();
                    showToast(error.toString());
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", user.getClientID());
                params.put("salary", salary);
                params.put("notice_period", notice_period);
                params.put("jobID", jobID);
                //params.put("employerID", employerID);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                if (cvPdfUri != null) {
                    String idImageName = getFileName(cvPdfUri);
                    params.put("cvPdf", new DataPart(idImageName, getFileDataFromUri(cvPdfUri)));
                }
                if (coverLetterUri != null) {
                    String medicalImageName = getFileName(coverLetterUri);
                    params.put("coverLetterPdf", new DataPart(medicalImageName, getFileDataFromUri(coverLetterUri)));
                }

                return params;
            }

        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        btn_apply_job.setVisibility(View.VISIBLE);
    }

    private void showSuccessDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ApplyJob.this);
        builder.setTitle("Success")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                });
        builder.setCancelable(false).create().show();
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (fileName == null) {
            fileName = uri.getPath();
            int cut = fileName.lastIndexOf('/');
            if (cut != -1) {
                fileName = fileName.substring(cut + 1);
            }
        }
        return fileName;
    }


    private byte[] getFileDataFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}