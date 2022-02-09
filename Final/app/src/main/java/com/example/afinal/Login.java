package com.example.afinal;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText etmobile,passWord;
    Button btnLogin , signUp;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etmobile = findViewById(R.id.Email);
        passWord = findViewById(R.id.PassWord);
        btnLogin = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpButton);
        settings = getSharedPreferences("test",0);
        editor = settings.edit();


        String Username = settings.getString("username","abc");
        String password = settings.getString("password","abc");

//        if(!mob.equals("abc"))
//        {
//            checklogin(mob,password);
//        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etmobile.getText().toString().trim();
                String password = passWord.getText().toString().trim();

                checklogin(mobile,password);



            }
        });

    }


    public void checklogin(String mobile,String pass)
    {
        String u = "https://urmilkman.com/apppanel/res/wsloginnew.php?" + "p1=" + mobile + "&p2=" + pass;
        Log.e("cs","url=>"+u);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(u, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {    JSONArray user;
                try {

                    Log.e("cs","response =>"+response.toString());

                    user = response.getJSONArray("item");
                    for (int i = 0; i < user.length(); i++) {
                        JSONObject j2 = user.getJSONObject(i);

                        String result = j2.get("p3").toString();

                        if (!result.isEmpty()) {

                            String name = j2.get("p4").toString();
                            String mobileno = j2.get("p5").toString();
                            String etmobileadrs = j2.get("p6").toString();



                            editor.putString("name",name).apply();
                            editor.putString("mobileno",mobileno).apply();
                            editor.putString("etmobileadrs",etmobileadrs).apply();
                            editor.putString("password",pass).apply();


                            Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            intent.putExtra("username",mobileno);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(Login.this, "invalid username/password", Toast.LENGTH_SHORT).show();
                            //Log.e("cs","View Not Updated");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonObjectRequest);
    }
}