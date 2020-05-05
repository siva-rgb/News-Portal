package com.example.newsportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    TextView tvTitle,tvSource,tvTime,tvDeasc;
    ImageView imageView;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvTitle=findViewById(R.id.tvTitle1);
        tvSource=findViewById(R.id.tvSource1);
        tvTime=findViewById(R.id.tvDate1);
        tvDeasc=findViewById(R.id.tvDesc);
        webView=findViewById(R.id.webView);
        imageView=findViewById(R.id.imageView);

        Intent intent= getIntent();
        String title=intent.getStringExtra("title");
        String source=intent.getStringExtra("source");
        String time=intent.getStringExtra("time");
        String desc=intent.getStringExtra("desc");
        String imageUrl=intent.getStringExtra("imageUrl");
        String url=intent.getStringExtra("url");

        tvTitle.setText(title);
        tvSource.setText(source);
        tvTime.setText(time);
        tvDeasc.setText(desc);
        Picasso.with(DetailedActivity.this).load(imageUrl).into(imageView);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


    }
}
