package com.example.Varsani.Staff.EditorialMng;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Applicants.Adapters.AdapterArticles;
import com.example.Varsani.Applicants.Models.ArticlesMdel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Articles extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AdapterArticles adapterArticles;
    private List<ArticlesMdel> list;

    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        // Action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle("Articles");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Views
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        // Session
        session = new SessionHandler(this);
        user = session.getUserDetails();

        // RecyclerView setup
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Load articles
        articles();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void articles() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Urls.URL_GET_ARTICLES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            Log.d("ARTICLES_RESPONSE", response);

                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("articles");

                                // Clear old data to avoid duplication
                                list.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);

                                    String articleID = jsn.getString("articleID");
                                    String title = jsn.getString("title");
                                    String content = jsn.getString("content");
                                    String dateCreated = jsn.getString("dateCreated");

                                    ArticlesMdel model = new ArticlesMdel(
                                            articleID,
                                            title,
                                            content,
                                            dateCreated
                                    );
                                    list.add(model);
                                }

                                adapterArticles = new AdapterArticles(Articles.this, list);
                                recyclerView.setAdapter(adapterArticles);
                            } else {
                                Toast.makeText(Articles.this,
                                        "No articles found",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Articles.this,
                                    "Parsing error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        error.printStackTrace();
                        Toast.makeText(Articles.this,
                                "Network error",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(Articles.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        articles(); // refresh safely
    }
}
