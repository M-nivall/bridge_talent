package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Applicants.Adapters.AdapterArticles;
import com.example.Varsani.Applicants.Adapters.AdapterJobs;
import com.example.Varsani.Applicants.Models.ArticlesMdel;
import com.example.Varsani.Applicants.Models.JobsModel;
import com.example.Varsani.Artists.Adapters.AdapterExhibitions;
import com.example.Varsani.Artists.Models.ArtworkModel;
import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private SessionHandler session;
    private UserModel user;
    private List<JobsModel> list;
    private List<ArticlesMdel> list2;
    private AdapterJobs adapterJobs;
    private AdapterArticles adapterArticles;

    private RecyclerView recyclerViewArt, recyclerViewExhibition;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = findViewById(R.id.progressBar);
        recyclerViewArt = findViewById(R.id.recyclerViewArt);
        recyclerViewExhibition = findViewById(R.id.recyclerViewExhibition);

        recyclerViewArt.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewExhibition.setLayoutManager(new GridLayoutManager(this, 1));

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        list = new ArrayList<>();
        list2 = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        getArtWork();
        getExhibition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterJobs != null) {
                    adapterJobs.getFilter().filter(newText);
                }
                if (adapterArticles != null) {
                    adapterArticles.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    public void getArtWork() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_JOBS,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("jobs");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                list.add(new JobsModel(
                                        jsn.getString("job_id"),
                                        jsn.getString("employer_id"),
                                        jsn.getString("title"),
                                        jsn.getString("description"),
                                        jsn.getString("location"),
                                        jsn.getString("job_type"),
                                        jsn.getString("salary_range"),
                                        jsn.getString("date_posted"),
                                        jsn.getString("deadline"),
                                        jsn.getString("job_status"),
                                        jsn.getString("company_name"),
                                        jsn.getString("contacts"),
                                        jsn.getString("email"),
                                        jsn.getString("industry"),
                                        jsn.getString("employer_description")

                                ));
                            }
                            adapterJobs = new AdapterJobs(getApplicationContext(), list);
                            recyclerViewArt.setAdapter(adapterJobs);
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getExhibition() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_GET_ARTICLES,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                list2.add(new ArticlesMdel(
                                        jsn.getString("articleID"),
                                        jsn.getString("title"),
                                        jsn.getString("content"),
                                        jsn.getString("dateCreated")
                                ));
                            }
                            adapterArticles = new AdapterArticles(getApplicationContext(), list2);
                            recyclerViewExhibition.setAdapter(adapterArticles);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
