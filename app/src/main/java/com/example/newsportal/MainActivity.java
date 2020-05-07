package com.example.newsportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsportal.Model.Articles;
import com.example.newsportal.Model.HeadLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText searchNews;
    Button btn_search;
    final String API_KEY="a22bace86c0a497390928f77d119ec3d";
    String newsCatagory="health";//change here
    Adapter adapter;
    List<Articles> articles=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchNews=findViewById(R.id.edtSearch);
        btn_search=findViewById(R.id.btn_search);
        swipeRefreshLayout=findViewById(R.id.swipeFresh);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country=getCountry();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               retriveGson("",country,newsCatagory,API_KEY);//changes here
            }
        });


        retriveGson("",country,newsCatagory,API_KEY);//changes here
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchNews.getText().toString().equals(""))
                {

                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                           retriveGson(searchNews.getText().toString(),country,newsCatagory,API_KEY);//changes here
                        }
                    });
                    retriveGson(searchNews.getText().toString(),country,newsCatagory,API_KEY);//changes here
                }
                else
                {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                             retriveGson("",country,newsCatagory,API_KEY);//change here
                        }
                    });
                   retriveGson("",country,newsCatagory,API_KEY);//changes here q    
                }
            }
        });
    }

     public void retriveGson(String query,String country,String catagory,String apiKey)
    {
        swipeRefreshLayout.setRefreshing(true);
        Call<HeadLine> call;
        if (!searchNews.getText().toString().equals(""))
        {
              call=APIClient.getInstance().getApi().getsearch(query,apiKey);
        }
        else
        {
            call=APIClient.getInstance().getApi().getheadline(country,catagory,apiKey);
        }

        call.enqueue(new Callback<HeadLine>() {
            @Override
            public void onResponse(Call<HeadLine> call, Response<HeadLine> response) {
                if (response.isSuccessful() && response.body().getArticles()!=null)
                {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles= response.body().getArticles();
                    adapter=new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HeadLine> call, Throwable t) {

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry()
    {
        Locale locale=Locale.getDefault();

        String country=locale.getCountry();
        return country.toLowerCase();
    }

}
