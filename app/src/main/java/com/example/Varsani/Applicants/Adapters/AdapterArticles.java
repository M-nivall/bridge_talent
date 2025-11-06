package com.example.Varsani.Applicants.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.Varsani.Applicants.Models.ArticlesMdel;
import com.example.Varsani.Artists.Adapters.AdapterExhibitions;
import com.example.Varsani.Artists.ExhibitionDetails;
import com.example.Varsani.Artists.Models.ExhibitionModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Patron.MoreExhibitionDetails;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterArticles extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ArticlesMdel> items;
    private List<ArticlesMdel> searchView;

    private Context ctx;
    ProgressDialog progressDialog;
    private AdapterArticles.OnItemClickListener mOnItemClickListener;
    private AdapterArticles.OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private int count = 1;

    private EditText quantity;
    public static final String TAG = "Item_adapter";

    public void setOnItemClickListener(final AdapterArticles.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final AdapterArticles.OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterArticles(Context context, List<ArticlesMdel> items) {
        this.items = items;
        this.searchView = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView view_details;
        public TextView article_title, article_subtitle;


        public OriginalViewHolder(View v) {
            super(v);
            //banner_img = v.findViewById( R.id.banner_img);
            article_title =v.findViewById(R.id.article_title);
            article_subtitle =v.findViewById(R.id.article_subtitle);
            view_details = v.findViewById(R.id.view_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        vh = new AdapterArticles.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterArticles.OriginalViewHolder) {
            final AdapterArticles.OriginalViewHolder view = (AdapterArticles.OriginalViewHolder) holder;

            final ArticlesMdel p = items.get(position);
            //String url = Urls.ROOT_URL_EXHIBITION_IMAGES;
            //Picasso.get()
            //        .load(url+p.getBannerImg())
            //        .fit()
            //        .centerCrop()
            //        .into(view.banner_img);
            view.article_title.setText(p.getTitle());
            view.article_subtitle.setText("Editorial Manager");
            //view.txv_venue.setText("Venue: " + p.getVenue());
            session = new SessionHandler(ctx);
            user = session.getUserDetails();

            if(session.isLoggedIn()) {

               // if (user.getUser_type().equals("Patron")) {
               //     view.btn_apply.setVisibility(View.GONE);
               //     view.btn_view_details.setVisibility(View.VISIBLE);
               // }
            }

            try{
                clientId= user.getClientID();
            }catch (RuntimeException e){

            }

            /*
            view.btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.isLoggedIn()) {

                        if (user.getUser_type().equals("Applicant")) {

                            String exhibitionID = p.getExhibitionID();
                            String title = p.getTitle();
                            String desc = p.getExhibitionDesc();
                            String startingDate = p.getStartingDate();
                            String endDate = p.getEndDate();
                            String venue = p.getVenue();
                            String exType = p.getExhibitionType();
                            String bannerImg = p.getBannerImg();
                            Intent in = new Intent(ctx, ExhibitionDetails.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            in.putExtra("exhibitionID", exhibitionID);
                            in.putExtra("title", title);
                            in.putExtra("startingDate", startingDate);
                            in.putExtra("endDate", endDate);
                            in.putExtra("venue", venue);
                            in.putExtra("exType", exType);
                            in.putExtra("bannerImg", bannerImg);
                            in.putExtra("desc", desc);
                            ctx.startActivity(in);

                        } else {

                            Toast.makeText(ctx, "Please login to continue", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Toast.makeText(ctx, "Please login to continue", Toast.LENGTH_SHORT).show();
                    }
                }
            });

             */

            view.view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (user.getUser_type().equals("Applicant")){

                        String articleID = p.getArticleID();
                        String title=p.getTitle();
                        String content=p.getContent();
                        String dateCreated=p.getDateCreated();

                        Intent in = new Intent(ctx, MoreExhibitionDetails.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("articleID", articleID);
                        in.putExtra("title", title);
                        in.putExtra("content", content);
                        in.putExtra("dateCreated", dateCreated);
                        ctx.startActivity(in);

                    }else {

                        Toast.makeText( ctx,"Please login to continue",Toast.LENGTH_SHORT ).show();

                    }
                }
            });
        }
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
        void onItemClick(View view, ExhibitionModal obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, ExhibitionModal obj, MenuItem item);
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchView;
                } else {

                    ArrayList<ArticlesMdel> filteredList = new ArrayList<>();

                    for (ArticlesMdel androidVersion : items) {

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
                items = (ArrayList<ArticlesMdel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
