package com.foozenergy.news;

import android.net.Uri;

import com.foozenergy.news.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryClass {

    public static URL createUrl(String url){
        URL myUrl = null;
        try {
            myUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return myUrl;
    }

    //make a http request and return a string json response
    public static String makeHttpRequest(URL url) throws IOException {

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String jResponse = null;
        //make http request
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //get input stream and return json response
            inputStream = httpURLConnection.getInputStream();
            jResponse = readInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return jResponse;
    }

    //get an input stream
    public static String readInputStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String news = "";
        String message;

        while ((message=bufferedReader.readLine()) != null){
            news += message;
        }

        return news;
    }

    public static List<News> fetchNewsData(String jsonResponse){
        //an array list to hold the news information
        ArrayList<News> newsArrayList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(jsonResponse);

            JSONArray jsonArray = jsonObject.optJSONArray("articles");

            //loop through the array
            for(int i = 0; i<jsonArray.length(); i++){

                JSONObject aJsonObject = jsonArray.optJSONObject(i);
                String title = aJsonObject.optString("title");
                String category = aJsonObject.optString("description");
                String webUrl = aJsonObject.optString("url");
                String imageUrl = aJsonObject.optString("urlToImage");

                newsArrayList.add(new News(title,category,webUrl,imageUrl));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsArrayList;
    }

    public static List<News> fetchNews(String url){
        URL myUrl = createUrl(url);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(myUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<News> news = fetchNewsData(jsonResponse);

        return news;
    }


}
