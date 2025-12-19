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

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Employers.JobApplicationDetails;
import com.example.Varsani.Employers.Models.ApplicantsModel;
import com.example.Varsani.Employers.Models.MyJobsModel;
import com.example.Varsani.Employers.MyJobDetails;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterApplication extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ApplicantsModel> items;
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


    public AdapterApplication(Context context, List<ApplicantsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_applicant_name,txv_salary,txv_dateApplied,txv_status;
        public Button btn_job_details;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_salary =v.findViewById(R.id.txv_salary);
            txv_applicant_name =v.findViewById(R.id.txv_applicant_name);
            txv_dateApplied = v.findViewById(R.id.txv_dateApplied);
            btn_job_details = v.findViewById(R.id.btn_job_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_job_applications, parent, false);
        vh = new AdapterApplication.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterApplication.OriginalViewHolder) {
            final AdapterApplication.OriginalViewHolder view = (AdapterApplication.OriginalViewHolder) holder;

            final ApplicantsModel o= items.get(position);

            view.txv_applicant_name.setText("Applicant Name: " + o.getFullName());
            view.txv_salary.setText("Expected Salary: Ksh " + o.getSalary());
            view.txv_dateApplied.setText("Date Applied: " + o.getDateApplied() );
            view.txv_status.setText("Status: " + o.getApplicationStatus());


            view.btn_job_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, JobApplicationDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    in.putExtra("applicationID", o.getApplicationId());
                    in.putExtra("applicantId", o.getApplicantId());
                    in.putExtra("cvUrl", o.getCvUrl());
                    in.putExtra("coverLetter", o.getCoverLater());
                    in.putExtra("salary", o.getSalary());
                    in.putExtra("noticePeriod", o.getNoticePeriod());
                    in.putExtra("dateApplied", o.getDateApplied());
                    in.putExtra("fullName", o.getFullName());
                    in.putExtra("email", o.getEmail());
                    in.putExtra("phone", o.getPhone());
                    in.putExtra("bio", o.getBio());
                    in.putExtra("skills", o.getSkills());
                    in.putExtra("education", o.getEducation());
                    in.putExtra("profileUrl", o.getProfileUrl());
                    in.putExtra("applicationStatus", o.getApplicationStatus());

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
