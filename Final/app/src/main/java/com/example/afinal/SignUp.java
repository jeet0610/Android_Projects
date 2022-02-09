package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class SignUp extends AppCompatActivity {
    EditText mFullName, mfullname2, mEmail,mPassword,musername;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar ProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().hide();//this line hides the action bar from top


        setContentView(R.layout.activity_sign_up);

        mFullName = findViewById(R.id.firstName);
        mfullname2 = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.SignIn_Email);
        mPassword = findViewById(R.id.PassWord);
        musername = findViewById(R.id.SignIn_UserName);
        mRegisterBtn = findViewById(R.id.registerbtn);
        mLoginBtn = findViewById(R.id.backToLogin);
        //ProgressBar = findViewById(R.id.progressBar);


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String username = musername.getText().toString().trim();
                String firstName = mFullName.getText().toString().trim();
                String lastName = mfullname2.getText().toString().trim();




                if (username.isEmpty()){
                    musername.setError("Mobile Number required");
                    musername.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    mEmail.setError("Mobile Number required");
                    mEmail.requestFocus();
                    return;
                }

////                if (mobile.length() < 10) {
////                    mphone.setError("Enter Valid Mobile Number");
////                    mphone.requestFocus();
////                    return;
//                }
                if (password.isEmpty()){
                    mPassword.setError("Password required");
                    mPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Length not acquired");
                    mPassword.requestFocus();
                    return;
                }



                registerUser(firstName, lastName, username, email, password);

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    private void registerUser(String firstName, String lastName, String usernme, String email, String password) {
        int a,b;
        a = 0;
        b = 1;
        String u = "https://urmilkman.com/apppanel/res/newregister1.php?" + "p1=" + firstName + "&p2=" + lastName
                + "&p3=" + usernme + "&p4=" + email + "&p5=" + password + "&p6=" + a + "&p7=" + b;
        Log.e("cs",u);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(u, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {    JSONArray user ;
                try {


                    user = response.getJSONArray("item");
                    for (int i = 0; i < user.length(); i++) {
                        JSONObject j2 = user.getJSONObject(i);

                        String result = j2.get("p1").toString();
                        if (result.equals("1")) {


                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            Toast.makeText(SignUp.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Register Not Successfull", Toast.LENGTH_SHORT).show();
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