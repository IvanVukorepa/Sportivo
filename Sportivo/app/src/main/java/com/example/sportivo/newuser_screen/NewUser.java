package com.example.sportivo.newuser_screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sportivo.R;
import com.example.sportivo.Singleton;
import com.example.sportivo.login_screen.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NewUser extends AppCompatActivity {

    Button back_btn, createuser_btn;
    TextView username_tv, password_tv, password2_tv, newOwner_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nus_new_user_layout);

        back_btn = (Button) findViewById(R.id.back_btn);
        username_tv = (TextView) findViewById(R.id.username_tv);
        createuser_btn = (Button) findViewById(R.id.createuser_btn);
        password_tv = (TextView) findViewById(R.id.createpassword_tv);
        password2_tv = (TextView) findViewById(R.id.confirmpass_tv);
        newOwner_tv = (TextView) findViewById(R.id.newOwner_tv);

        newOwner_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CompanyRegistration.class);
                startActivity(intent);
            }
        });

        username_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String url = getString(R.string.baseURL) + getString(R.string.userURL) + "checkusername?username=" + username_tv.getText().toString();

                    StringRequest checkUsernameAvailable = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("false")){
                                        Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                                        username_tv.setTextColor(Color.rgb(255,0,0));
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    Singleton.getInstance(getApplicationContext()).addToRequestQueue(checkUsernameAvailable);

                }
                else{
                    username_tv.setTextColor(Color.rgb(0,0,0));
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createuser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllRequiredFieldsFilled()){
                    final String username = username_tv.getText().toString();
                    final String password = password2_tv.getText().toString();
                    JSONObject user = new JSONObject();
                    try{
                        user.put("Username", username);
                        user.put("Password", password);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    NewUser.createUser(getApplicationContext(), user);

                }
            }
        });
    }

    private boolean CheckAllRequiredFieldsFilled(){
        try{
            if(username_tv.getText().toString().equals("") || password_tv.getText().toString().equals("")
                    || password2_tv.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Not all required fields are filled", Toast.LENGTH_LONG).show();
                return false;
            }
            else if(password2_tv.getText().toString().equals(password_tv.getText().toString()))
                return true;

            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
            return false;
        }catch(Error error){return false;}

    }

    public static void createUser(final Context context, final JSONObject user){
        String url = context.getString(R.string.baseURL) + context.getString(R.string.userURL) + "register";

        StringRequest registerUser = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "User " + user.optString("Username", "") + " successfully registered", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                return user.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Singleton.getInstance(context).addToRequestQueue(registerUser);
    }
}
