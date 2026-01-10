package com.example.Varsani.Applicants;

import static com.example.Varsani.utils.Urls.URL_DISABLE_ARTICLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.PaymentDetails;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ArticleDetails extends AppCompatActivity {

    private TextView tvTitle, tvContent, tvDate;
    private Button btnDisableArticle;
    private String articleID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        tvDate = findViewById(R.id.tvDate);
        btnDisableArticle = findViewById(R.id.btnDisableArticle);

        // Get data from intent
        Intent intent = getIntent();
        articleID = intent.getStringExtra("articleID");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String dateCreated = intent.getStringExtra("dateCreated");

        // Set data
        tvTitle.setText(title);
        tvContent.setText(content);
        tvDate.setText(dateCreated);

        btnDisableArticle.setOnClickListener(v -> {
            new AlertDialog.Builder(ArticleDetails.this)
                    .setTitle("Disable Article")
                    .setMessage("Are you sure you want to disable this article? It will no longer be visible to users.")
                    .setPositiveButton("Disable", (dialog, which) -> {
                        // Call API to disable article
                        disableArticle();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void disableArticle(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_DISABLE_ARTICLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(ArticleDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(ArticleDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(ArticleDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(ArticleDetails.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("articleID",articleID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
