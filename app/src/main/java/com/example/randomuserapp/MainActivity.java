package com.example.randomuserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , RandomUserTask.RandomUserListener {

    //TAG is voor het gebruiken in debug dat de je weet welke class de error vandaan komt
    private final String TAG = MainActivity.class.getSimpleName();
    private TextView  tvUserName;
    private ImageView imgUserImage;
    private Button btnGetRandomUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserName = findViewById(R.id.tv_user_name);
        imgUserImage = findViewById(R.id.img_user_view);
        btnGetRandomUser = findViewById(R.id.btn_get_random_user);

        btnGetRandomUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Log.d( TAG , "Onclick was clalled");
        RandomUserTask randomUserTask = new RandomUserTask(this);
        randomUserTask.execute();
    }

    public void onUserNameAvailable(String userName , String imageUrl)
    {
        Log.d(TAG ,"Username " + userName);
        tvUserName.setText(userName);

        Picasso.get().load(imageUrl).into(imgUserImage);
    }
}
