package com.example.Varsani.Employers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.CheckOut2;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Employers.Models.EmployerModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PostJob extends AppCompatActivity {
    private TextView tv_company_name, txv_industry, txv_website;
    private EditText et_job_title, et_job_category, et_employer_type, et_entry_level,et_job_location,
            et_salary_range, et_deadline, et_description, et_responsibilities, et_qualifications,
            et_payment_code;
    private Button btn_submit_job;
    private ProgressBar progressBar;

    private SessionHandler session;
    private EmployerModel user;

    final String job_fee = "3500"; // job posting fee in Ksh


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    DatePickerDialog datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        getSupportActionBar().setSubtitle("Job Applications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_company_name = findViewById(R.id.tv_company_name);
        txv_industry = findViewById(R.id.txv_industry);
        txv_website = findViewById(R.id.txv_website);
        et_job_title = findViewById(R.id.et_job_title);
        et_job_category = findViewById(R.id.et_job_category);
        et_employer_type = findViewById(R.id.et_employer_type);
        et_entry_level = findViewById(R.id.et_entry_level);
        et_salary_range = findViewById(R.id.et_salary_range);
        et_job_location = findViewById(R.id.et_job_location);
        et_deadline = findViewById(R.id.et_deadline);
        et_description = findViewById(R.id.et_description);
        et_responsibilities = findViewById(R.id.et_responsibilities);
        et_qualifications = findViewById(R.id.et_qualifications);
        et_payment_code = findViewById(R.id.et_payment_code);
        btn_submit_job = findViewById(R.id.btn_submit_job);
        progressBar=findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails_2();


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        et_deadline.setText("Set Deadline");

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(PostJob.this);

        tv_company_name.setText("Company Name: " + user.getCompanyName());
        txv_industry.setText("Industry: " + user.getIndustry());
        txv_website.setText("Website: " + user.getWebsite());

        et_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(PostJob.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        et_deadline.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        btn_submit_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog
                new androidx.appcompat.app.AlertDialog.Builder(PostJob.this)
                        .setTitle("Confirm Submission")
                        .setMessage("Are you sure you want to submit this job?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                // User clicked Yes, proceed to post job
                            postJob();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                // User clicked No, dismiss dialog
                            dialog.dismiss();
                        })
                        .show();
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

    public void postJob(){
        btn_submit_job.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        final String job_title = et_job_title.getText().toString().trim();
        final String job_category = et_job_category.getText().toString().trim();
        final String employer_type = et_employer_type.getText().toString().trim();
        final String entry_level = et_entry_level.getText().toString().trim();
        final String salary_range = et_salary_range.getText().toString().trim();
        final String job_location = et_job_location.getText().toString().trim();
        final String deadline = et_deadline.getText().toString().trim();
        final String job_description = et_description.getText().toString().trim();
        final String job_responsibilities = et_responsibilities.getText().toString().trim();
        final String qualifications = et_qualifications.getText().toString().trim();
        final String payment_code = et_payment_code.getText().toString().trim();


        if(TextUtils.isEmpty(job_title)){
            Toast.makeText(getApplicationContext(), "Enter Job Title", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(job_category)){
            Toast.makeText(getApplicationContext(), "Enter job category", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(employer_type)){
            Toast.makeText(getApplicationContext(), "Enter employer type", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(entry_level)){
            Toast.makeText(getApplicationContext(), "Enter entry level", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(salary_range)){
            Toast.makeText(getApplicationContext(), "Enter salary range", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(deadline)){
            Toast.makeText(getApplicationContext(), "Select deadline", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(job_description)){
            Toast.makeText(getApplicationContext(), "Enter job description", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(job_location)){
            Toast.makeText(getApplicationContext(), "Enter job location", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(job_responsibilities)){
            Toast.makeText(getApplicationContext(), "Fill job responsibilities", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(qualifications)) {
            Toast.makeText(getApplicationContext(), "Enter qualifications", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(payment_code)) {
            Toast.makeText(getApplicationContext(), "Enter payment_code", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(payment_code.length()>10 ||payment_code.length()<10){
            Toast.makeText(getApplicationContext(), "Payment code  should contain 10 digits", Toast.LENGTH_SHORT).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(!payment_code.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            Toast.makeText(getApplicationContext(), "Payment code should have  characters and digit",
                    Toast.LENGTH_LONG).show();
            btn_submit_job.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_POST_JOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response","is" + response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                btn_submit_job.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                                btn_submit_job.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            btn_submit_job.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                btn_submit_job.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("job_title",job_title);
                params.put("job_category",job_category);
                params.put("employer_type",employer_type);
                params.put("entry_level",entry_level);
                params.put("salary_range",salary_range);
                params.put("job_location",job_location);
                params.put("deadline",deadline);
                params.put("job_description",job_description);
                params.put("job_responsibilities",job_responsibilities);
                params.put("qualifications",qualifications);
                params.put("payment_code",payment_code);
                params.put("employerID",user.getClientID());
                params.put("job_fee", job_fee);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}