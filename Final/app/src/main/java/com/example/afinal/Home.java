package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    TextView Mainuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Mainuser = findViewById(R.id.Username1);
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        if(bundle!=null){
            Mainuser.setText("Welcome "+bundle.getString("username"));
        }


    }
}