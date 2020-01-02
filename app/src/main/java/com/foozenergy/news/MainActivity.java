package com.foozenergy.news;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView newsListView;
    NewsAdapter newsAdapter;
    //public final String JSON_STRING_URL = "https://content.guardianapis.com/search?api-key=test";
    public final String JSON_STRING_URL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=4dbc17e007ab436fb66416009dfb59a8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference to the list view
        newsListView = findViewById(R.id.news_list_view);
        newsAdapter = new NewsAdapter(getApplicationContext(),new ArrayList<News>());

        newsListView.setAdapter(newsAdapter);

        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
        newsAsyncTask.execute(JSON_STRING_URL);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                News news = newsAdapter.getItem(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getWebUrl()));

                //resolve activity with android package manager
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }

    private class NewsAsyncTask extends AsyncTask<String,Void, List<News>>{

        @Override
        protected List<News> doInBackground(String... url) {
            if(url[0] == null && TextUtils.isEmpty(url[0])){
                return null;
            }
            List<News> news = QueryClass.fetchNews(url[0]);

            return news;
        }

        @Override
        protected void onPostExecute(List<News> news) {

            //clear the adapter of any data
            newsAdapter.clear();

            if(news != null){
                newsAdapter.addAll(news);
            }

        }
    }
}
