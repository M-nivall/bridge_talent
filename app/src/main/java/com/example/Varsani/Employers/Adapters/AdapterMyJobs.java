package com.example.Varsani.Employers.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Applicants.Adapters.AdapterAppliedJobs;
import com.example.Varsani.Applicants.ApplicationDetails;
import com.example.Varsani.Applicants.Models.AppliedJobsModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Employers.Models.MyJobsModel;
import com.example.Varsani.Employers.MyJobDetails;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterMyJobs extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<MyJobsModel> items;
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


    public AdapterMyJobs(Context context, List<MyJobsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_jobTitle,txv_jobCategory, txv_jobType,txv_datePosted,txv_status;
        public Button btn_view_details;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_jobCategory =v.findViewById(R.id.txv_jobCategory);
            txv_jobTitle =v.findViewById(R.id.txv_jobTitle);
            txv_jobType = v.findViewById(R.id.txv_jobType);
            txv_datePosted = v.findViewById(R.id.txv_datePosted);
            btn_view_details = v.findViewById(R.id.btn_view_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_job_items, parent, false);
        vh = new AdapterMyJobs.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterMyJobs.OriginalViewHolder) {
            final AdapterMyJobs.OriginalViewHolder view = (AdapterMyJobs.OriginalViewHolder) holder;

            final MyJobsModel o= items.get(position);

            view.txv_jobTitle.setText("Job Title: " + o.getTitle());
            view.txv_jobCategory.setText("Job Category: " + o.getJobCategory());
            view.txv_jobType.setText("Employment Type: " + o.getJobType());
            view.txv_datePosted.setText("Date Posted: " + o.getDatePosted() );
            view.txv_status.setText("Status: " + o.getJobStatus() );


            view.btn_view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, MyJobDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    in.putExtra("jobID", o.getJobID());
                    in.putExtra("jobTitle", o.getTitle());
                    in.putExtra("jobCategory", o.getJobCategory());
                    in.putExtra("jobLevel", o.getJobLevel());
                    in.putExtra("description", o.getDescription());
                    in.putExtra("qualifications", o.getQualifications());
                    in.putExtra("jobResponsibilities", o.getJobResponsibilities());
                    in.putExtra("location", o.getLocation());
                    in.putExtra("jobType", o.getJobType());
                    in.putExtra("salaryRange", o.getSalaryRange());
                    in.putExtra("datePosted", o.getDatePosted());
                    in.putExtra("deadline", o.getDeadline());
                    in.putExtra("jobStatus", o.getJobStatus());

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
