package com.example.Varsani.Employers;

import android.content.Intent;
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
import com.example.Varsani.R;
import com.example.Varsani.utils.Urls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostJob extends AppCompatActivity {
    private TextView tv_company_name, txv_industry, txv_website;
    private EditText et_job_title, et_job_category, et_employer_type, et_entry_level,
            et_salary_range, et_deadline, et_description, et_responsibilities, et_qualifications;
    private Button btn_submit_job;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        tv_company_name = findViewById(R.id.tv_company_name);
        txv_industry = findViewById(R.id.txv_industry);

        txv_industry = findViewById(R.id.txv_industry);
        txv_website = findViewById(R.id.txv_website);
        et_job_title = findViewById(R.id.et_job_title);
        et_job_category = findViewById(R.id.et_job_category);
        et_employer_type = findViewById(R.id.et_employer_type);
        et_entry_level = findViewById(R.id.et_entry_level);
        et_salary_range = findViewById(R.id.et_salary_range);
        et_deadline = findViewById(R.id.et_deadline);
        et_description = findViewById(R.id.et_description);
        et_responsibilities = findViewById(R.id.et_responsibilities);
        et_qualifications = findViewById(R.id.et_qualifications);
        btn_submit_job = findViewById(R.id.btn_submit_job);
        progressBar=findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);

        btn_submit_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJob();
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
        final String deadline = et_deadline.getText().toString().trim();
        final String job_description = et_description.getText().toString().trim();
        final String job_responsibilities = et_responsibilities.getText().toString().trim();
        final String qualifications = et_qualifications.getText().toString().trim();


        if(TextUtils.isEmpty(company_name)){
            Toast.makeText(getApplicationContext(), "Enter company name", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(industry)){
            Toast.makeText(getApplicationContext(), "Enter industry", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(location)){
            Toast.makeText(getApplicationContext(), "Enter location", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(website)){
            Toast.makeText(getApplicationContext(), "Enter website", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(company_description)){
            Toast.makeText(getApplicationContext(), "Enter company description", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(phoneNo)){
            Toast.makeText(getApplicationContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if(phoneNo.length()>10 ||phoneNo.length()<10){
            Toast.makeText(getApplicationContext(), "Phone number should contain 10 digits", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter email address", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;

        }
        if (!email.matches(emailPattern)){
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(!password.equals(password_c)) {
            Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        //if(!role.equals(role)) {
        //    Toast.makeText(getApplicationContext(), "Select role", Toast.LENGTH_SHORT).show();
        //    registerBtn.setVisibility(View.VISIBLE);
        //    progressBar.setVisibility(View.GONE);
        //    return;
        // }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_REGISTER_COMPANY,
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
                                Intent intent=new Intent(getApplicationContext(), EmployerLogin.class);
                                startActivity(intent);
                                registerBtn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                                registerBtn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            registerBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                registerBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("company_name",company_name);
                params.put("industry",industry);
                params.put("location",location);
                params.put("website",website);
                params.put("company_description",company_description);
                params.put("username",username);
                params.put("phoneNo",phoneNo);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}