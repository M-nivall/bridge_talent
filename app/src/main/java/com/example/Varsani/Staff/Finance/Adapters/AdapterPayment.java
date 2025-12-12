package com.example.Varsani.Staff.Finance.Adapters;

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
import com.example.Varsani.Employers.Adapters.AdapterMyJobs;
import com.example.Varsani.Employers.Models.MyJobsModel;
import com.example.Varsani.Employers.MyJobDetails;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Finance.Models.PaymentModel;
import com.example.Varsani.Staff.Finance.PaymentDetails;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterPayment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PaymentModel> items;
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


    public AdapterPayment(Context context, List<PaymentModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_jobTitle,txv_employer, txv_amount,txv_datePaid,txv_status;
        public Button btn_job_details;


        public OriginalViewHolder(View v) {
            super(v);

            txv_status =v.findViewById(R.id.txv_status);
            txv_employer =v.findViewById(R.id.txv_employer);
            txv_jobTitle =v.findViewById(R.id.txv_jobTitle);
            txv_amount = v.findViewById(R.id.txv_amount);
            txv_datePaid = v.findViewById(R.id.txv_datePaid);
            btn_job_details = v.findViewById(R.id.btn_job_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_payment_items, parent, false);
        vh = new AdapterPayment.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterPayment.OriginalViewHolder) {
            final AdapterPayment.OriginalViewHolder view = (AdapterPayment.OriginalViewHolder) holder;

            final PaymentModel o= items.get(position);

            view.txv_jobTitle.setText("Job Title: " + o.getTitle());
            view.txv_employer.setText("Employer: " + o.getCompanyName());
            view.txv_amount.setText("Amount: Ksh " + o.getAmount());
            view.txv_datePaid.setText("Date: " + o.getDatePosted() );
            view.txv_status.setText("Status: " + o.getJobStatus() );


            view.btn_job_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, PaymentDetails.class);
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

                    in.putExtra("companyName", o.getCompanyName());
                    in.putExtra("contacts", o.getContacts());
                    in.putExtra("amount", o.getAmount());
                    in.putExtra("paymentMode", o.getPaymentMethod());
                    in.putExtra("transactionCode", o.getTransactionCode());
                    in.putExtra("paymentStatus", o.getPaymentStatus());

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
