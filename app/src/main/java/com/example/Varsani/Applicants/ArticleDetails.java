package com.example.Varsani.Applicants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Varsani.R;

public class ArticleDetails extends AppCompatActivity {

    TextView tvTitle, tvContent, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        tvDate = findViewById(R.id.tvDate);

        // Get data from intent
        Intent intent = getIntent();
        String articleID = intent.getStringExtra("articleID");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String dateCreated = intent.getStringExtra("dateCreated");

        // Set data
        tvTitle.setText(title);
        tvContent.setText(content);
        tvDate.setText(dateCreated);
    }
}
