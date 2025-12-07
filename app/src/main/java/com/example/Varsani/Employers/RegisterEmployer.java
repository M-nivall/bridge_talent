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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Login;
import com.example.Varsani.R;
import com.example.Varsani.utils.Urls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterEmployer extends AppCompatActivity {
    private Button registerBtn;
    private ProgressBar progressBar;
    private EditText edt_company_name,edt_industry,edt_email,edt_phone_number,edt_username,
            edt_location,edt_website,edt_company_description,edt_password,edt_password_c;
    //private Spinner spinner_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        getSupportActionBar().setSubtitle("Company Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edt_company_name=findViewById(R.id.edt_company_name);
        edt_username=findViewById(R.id.edt_username);
        edt_industry=findViewById(R.id.edt_industry);
        edt_phone_number=findViewById(R.id.edt_phone_number);
        edt_email=findViewById(R.id.edt_email);
        edt_location=findViewById(R.id.edt_location);
        edt_website=findViewById(R.id.edt_website);
        edt_company_description=findViewById(R.id.edt_company_description);
        edt_password=findViewById(R.id.edt_password);
        edt_password_c=findViewById(R.id.edt_password_c);
        progressBar=findViewById(R.id.progress_bar);
        registerBtn=findViewById(R.id.btn_register);
        //spinner_role = findViewById(R.id.spinner_role);

        progressBar.setVisibility(View.GONE);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCompany();
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

    public void registerCompany(){
        registerBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        final String company_name = edt_company_name.getText().toString().trim();
        final String industry = edt_industry.getText().toString().trim();
        final String username = edt_username.getText().toString().trim();
        final String phoneNo = edt_phone_number.getText().toString().trim();
        final String email = edt_email.getText().toString().trim();
        final String password = edt_password.getText().toString().trim();
        final String password_c = edt_password_c.getText().toString().trim();
        final String location = edt_location.getText().toString().trim();
        final String website = edt_website.getText().toString().trim();
        final String company_description = edt_company_description.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


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