package com.example.Varsani.Staff.Finance;

import static com.example.Varsani.utils.Urls.URL_APPROVED_PAYMENTS;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.Adapters.AdapterPayment;
import com.example.Varsani.Staff.Finance.Models.PaymentModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovedPayments extends AppCompatActivity {
    private List<PaymentModel> list;
    private AdapterPayment adapterPayment;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_payments);

        getSupportActionBar().setSubtitle("Approved Payments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        payments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void payments() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APPROVED_PAYMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);

                                    PaymentModel paymentModel = new PaymentModel(
                                            jsn.getString("job_id"),
                                            jsn.getString("title"),
                                            jsn.getString("job_category"),
                                            jsn.getString("job_level"),
                                            jsn.getString("description"),
                                            jsn.getString("qualifications"),
                                            jsn.getString("job_responsibilities"),
                                            jsn.getString("job_location"),
                                            jsn.getString("job_type"),
                                            jsn.getString("salary_range"),
                                            jsn.getString("date_posted"),
                                            jsn.getString("deadline"),
                                            jsn.getString("job_status"),

                                            jsn.getString("company_name"),
                                            jsn.getString("contacts"),
                                            jsn.getString("amount"),
                                            jsn.getString("payment_method"),
                                            jsn.getString("transaction_code"),
                                            jsn.getString("payment_status")
                                    );

                                    list.add(paymentModel);
                                }

                                adapterPayment=new AdapterPayment(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterPayment);
                                progressBar.setVisibility(View.GONE);

                            }else{
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                            Log.e("ERROR E ", e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                Log.e("ERROR E ", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("userID",user.getClientID());
                Log.e("PARAMS","" +params);
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