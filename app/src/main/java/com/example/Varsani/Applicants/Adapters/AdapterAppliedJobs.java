package com.example.Varsani.Applicants.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Applicants.ApplicationDetails;
import com.example.Varsani.Applicants.Models.AppliedJobsModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterAppliedJobs extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<AppliedJobsModel> items;
    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = "Orders adapter";


    public AdapterAppliedJobs(Context context, List<AppliedJobsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_jobTitle,txv_company, txv_dateApplied,txv_status;
        public Button btn_view_details;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_company =v.findViewById(R.id.txv_company);
            txv_jobTitle =v.findViewById(R.id.txv_jobTitle);
            txv_dateApplied = v.findViewById(R.id.txv_dateApplied);
            btn_view_details = v.findViewById(R.id.btn_view_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_applied_items, parent, false);
        vh = new AdapterAppliedJobs.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterAppliedJobs.OriginalViewHolder) {
            final AdapterAppliedJobs.OriginalViewHolder view = (AdapterAppliedJobs.OriginalViewHolder) holder;

            final AppliedJobsModel o= items.get(position);

            view.txv_jobTitle.setText("Job Title: " + o.getTitle());
            view.txv_company.setText("Company: " + o.getCompanyName());
            view.txv_dateApplied.setText("Date Applied: " + o.getDateApplied());
            view.txv_status.setText("Status: " + o.getApplicationStatus() );



            view.btn_view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, ApplicationDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    in.putExtra("applicationID", o.getApplicationID());
                    in.putExtra("jobID", o.getJobID());
                    in.putExtra("dateApplied", o.getDateApplied());
                    in.putExtra("applicationStatus", o.getApplicationStatus());
                    in.putExtra("title", o.getTitle());
                    in.putExtra("description", o.getDescription());
                    in.putExtra("location", o.getLocation());
                    in.putExtra("jobType", o.getJobType());
                    in.putExtra("companyName", o.getCompanyName());
                    in.putExtra("email", o.getEmail());
                    in.putExtra("industry", o.getIndustry());
                    in.putExtra("employerFeedback", o.getEmployerFeedback());

                    ctx.startActivity(in);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
