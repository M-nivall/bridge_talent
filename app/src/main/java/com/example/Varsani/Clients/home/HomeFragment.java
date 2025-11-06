package com.example.Varsani.Clients.home;

import static java.sql.Types.NULL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.Varsani.Applicants.Models.JobsModel;
import com.example.Varsani.Applicants.Adapters.AdapterJobs;
import com.example.Varsani.Artists.Adapters.AdapterExhibitions;
import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.Clients.About;
import com.example.Varsani.Clients.CompleteProfile;
import com.example.Varsani.Clients.Profile;
import com.example.Varsani.MainActivity;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SessionHandler session;
    private UserModel user;
    private List<ArticlesMdel> list;
    private List<JobsModel> list2;
    private AdapterArticles adapterArticles;
    private AdapterJobs adapterArtWork;
    private TextView btn_complete_profile;
    private ImageView icon_home,icon_about,icon_profile;
    private RecyclerView rv_articles ,rv_jobs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rv_articles=root.findViewById(R.id.rv_articles);
        rv_jobs=root.findViewById(R.id.rv_jobs);

        icon_home = root.findViewById(R.id.icon_home);
        icon_about= root.findViewById(R.id.icon_about);
        icon_profile = root.findViewById(R.id.icon_profile);
        btn_complete_profile = root.findViewById(R.id.btn_complete_profile);

        btn_complete_profile.setVisibility(View.GONE);



        icon_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        icon_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ght = new Intent(getContext(), About.class);
                startActivity(ght);
            }
        });
        icon_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ght = new Intent(getContext(), Profile.class);
                startActivity(ght);
            }
        });

        rv_articles.setLayoutManager( new LinearLayoutManager( getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rv_articles.setLayoutManager(mLayoutManager);

        rv_jobs.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rv_jobs.setLayoutManager(nLayoutManager);

        session=new SessionHandler(getContext());
        user=session.getUserDetails();
        list= new ArrayList<>();
        list2= new ArrayList<>();

        if (user.getSkills().equalsIgnoreCase("NULL") || user.getSkills().isEmpty()) {
            btn_complete_profile.setVisibility(View.VISIBLE);
        }

        btn_complete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CompleteProfile.class);
                intent.putExtra("role", "surrogate_mother");
                startActivity(intent);
            }
        });

        articles();
        getJobs();

        return root;
    }
    public void articles(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_ARTICLES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("articles");
                                for (int i=0; i < jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String articleID=jsn.getString("articleID");
                                    String title=jsn.getString("title");
                                    String content=jsn.getString("content");
                                    String dateCreated=jsn.getString("dateCreated");

                                    ArticlesMdel articlesMdel=new ArticlesMdel(articleID, title, content, dateCreated);
                                    list.add(articlesMdel);
                                }
                                //progressBar.setVisibility(View.GONE);
                                adapterArticles=new AdapterArticles(getContext(),list);
                                rv_articles.setAdapter(adapterArticles);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getJobs(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_JOBS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");

                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("jobs");
                                for (int i=0; i < jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String jobID = jsn.getString("job_id");
                                    String employerID = jsn.getString("employer_id");
                                    String title = jsn.getString("title");
                                    String description = jsn.getString("description");
                                    String location = jsn.getString("location");
                                    String jobType = jsn.getString("job_type");
                                    String salaryRange = jsn.getString("salary_range");
                                    String datePosted = jsn.getString("date_posted");
                                    String deadline = jsn.getString("deadline");
                                    String jobStatus = jsn.getString("job_status");

                                    String companyName = jsn.getString("company_name");
                                    String contacts = jsn.getString("contacts");
                                    String email = jsn.getString("email");
                                    String industry = jsn.getString("industry");
                                    String employerDescription = jsn.getString("employer_description");

                                    JobsModel jb=new JobsModel( jobID, employerID, title, description, location, jobType,
                                            salaryRange, datePosted, deadline, jobStatus, companyName, contacts, email, industry, employerDescription);
                                    list2.add(jb);
                                }
                                //progressBar.setVisibility(View.GONE);
                                adapterArtWork=new AdapterJobs(getContext(),list2);
                                rv_jobs.setAdapter(adapterArtWork);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
