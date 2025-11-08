package com.example.Varsani.Applicants.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Applicants.ApplyJob;
import com.example.Varsani.Applicants.Models.JobsModel;
import com.example.Varsani.Artists.Models.ArtworkModel;
import com.example.Varsani.Artists.ViewDetails;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterJobs extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<JobsModel> items;
    private List<JobsModel> searchView;

    private Context ctx;
    ProgressDialog progressDialog;
    private AdapterJobs.OnItemClickListener mOnItemClickListener;
    private AdapterJobs.OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String productID = "";
    private int count = 1;

    private EditText quantity;
    public static final String TAG = "Item_adapter";

    public void setOnItemClickListener(final AdapterJobs.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final AdapterJobs.OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterJobs(Context context, List<JobsModel> items) {
        this.items = items;
        this.searchView = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        //public ImageView artwork_image;
        public TextView txv_title, txv_company, txv_type;
        public Button btn_apply;
        //private ImageView img_details;


        public OriginalViewHolder(View v) {
            super(v);
            //artwork_image = v.findViewById( R.id.artwork_image);
            txv_title = v.findViewById(R.id.txv_title);
            txv_company = v.findViewById(R.id.txv_company);
            txv_type = v.findViewById(R.id.txv_type);
            btn_apply = v.findViewById(R.id.btn_apply);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_banner, parent, false);
        vh = new AdapterJobs.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterJobs.OriginalViewHolder) {
            final AdapterJobs.OriginalViewHolder view = (AdapterJobs.OriginalViewHolder) holder;

            final JobsModel j = items.get(position);
            //String url = Urls.ROOT_URL_ART_IMAGES;
            //Picasso.get()
                    //.load(url+p.getImgUrl())
                    //.fit()
                    //.centerCrop()
                    //.into(view.artwork_image);
            view.txv_title.setText(j.getTitle());
            view.txv_company.setText(j.getCompanyName());
            view.txv_type.setText(j.getJobType());

            session = new SessionHandler(ctx);
            user = session.getUserDetails();
            try{
                clientId= user.getClientID();
            }catch (RuntimeException e){

            }
            view.btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.isLoggedIn()) {

                        if (user.getUser_type().equals("Applicant")) {

                            String jobID = j.getJobID();
                            String employerID = j.getEmployerID();
                            String title = j.getTitle();
                            String description = j.getDescription();
                            String location = j.getLocation();
                            String jobType = j.getJobType();
                            String salaryRange = j.getSalaryRange();
                            String datePosted = j.getDatePosted();
                            String deadline = j.getDeadline();
                            String jobStatus = j.getJobStatus();
                            String companyName = j.getCompanyName();
                            String contacts = j.getContacts();
                            String email = j.getEmail();
                            String industry = j.getIndustry();
                            String employerDescription = j.getEmployerDescription();

                            Intent in = new Intent(ctx, ApplyJob.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            in.putExtra("jobID", jobID);
                            in.putExtra("employerID", employerID);
                            in.putExtra("title", title);
                            in.putExtra("description", description);
                            in.putExtra("location", location);
                            in.putExtra("jobType", jobType);
                            in.putExtra("salaryRange", salaryRange);
                            in.putExtra("datePosted", datePosted);
                            in.putExtra("deadline", deadline);
                            in.putExtra("jobStatus", jobStatus);
                            in.putExtra("companyName", companyName);
                            in.putExtra("contacts", contacts);
                            in.putExtra("email", email);
                            in.putExtra("industry", industry);
                            in.putExtra("employerDescription", employerDescription);

                            ctx.startActivity(in);

                            ctx.startActivity(in);

                        } else {

                            Toast.makeText(ctx, "Please login Apply", Toast.LENGTH_SHORT).show();

                        }
                    }else {

                        Toast.makeText(ctx, "Please login to continue", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void addToCart(){
        final String itemQty = quantity.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ADD_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response ",response);
                            String msg = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            if (status == 1){
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "error1 "+msg );
                                progressDialog.dismiss();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(ctx, "error"+e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "error2 "+e.toString());
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "error"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "error3 "+error );
                progressDialog.dismiss();


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", clientId);
                params.put("itemID", productID);
                params.put("quantity", itemQty);
                Log.e(TAG, "params is "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    public void progressShow(){
        progressDialog = new ProgressDialog( ctx);
        progressDialog.setMessage(" Please wait...");
        progressDialog.setTitle("Adding to cart... ");
        progressDialog.show();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ProductModal obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, ProductModal obj, MenuItem item);
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchView;
                } else {

                    ArrayList<JobsModel> filteredList = new ArrayList<>();

                    for (JobsModel androidVersion : items) {

                        if (androidVersion.getTitle().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (ArrayList<JobsModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
