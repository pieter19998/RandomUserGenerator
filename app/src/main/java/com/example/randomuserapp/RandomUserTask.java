package com.example.randomuserapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RandomUserTask extends AsyncTask<Void , Void , String> {

    private final String TAG = RandomUserTask.class.getSimpleName();
    private String mRandomUserApi = "https://randomuser.me/api";
    private RandomUserListener listener;

    public RandomUserTask(RandomUserListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        {

            Log.d(TAG, "getRandomUser was called");

            String response = null;

            try {
                URL mURL = new URL(mRandomUserApi);
                URLConnection urlConnection = mURL.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == 200) {
                    InputStream in = httpURLConnection.getInputStream();

                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");

                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        response = scanner.next();
                    }
                    Log.d(TAG, response);

                } else {
                    Log.e(TAG, "Error occured " + responseCode);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }
    }

    @Override
    protected void onPostExecute(String response)
    {
        super.onPostExecute(response);
        Log.d(TAG , "onPostExecute was callded");
        Log.d(TAG , "response = " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");

            JSONObject user = results.getJSONObject(0);
            String title =user.getJSONObject("name").getString("title");
            String firstname = user.getJSONObject("name").getString("first");
            String lastname = user.getJSONObject("name").getString("last");


            String name = title + " " + firstname + " " + lastname;
            Log.d(TAG , "Person = " + name );

            String imageUrl = user.getJSONObject("picture").getString("large");
            Log.d(TAG , "imageurl " + imageUrl);

            listener.onUserNameAvailable(name , imageUrl);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface RandomUserListener
    {
        public void onUserNameAvailable(String username , String imageUrl);
    }
}
