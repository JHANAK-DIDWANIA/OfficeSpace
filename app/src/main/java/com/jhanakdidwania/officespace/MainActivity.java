package com.jhanakdidwania.officespace;

import android.app.Application;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity{

    private String LOGIN_EXTRA;
    private String PASSWORD_EXTRA;
    private String LOGIN_URL_EXTRA;
    private EditText mLogin;
    private EditText mPassword;
    private static final int LOADER_ID = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mLogin = (EditText) findViewById(R.id.ID);
        mPassword = (EditText) findViewById(R.id.PASSWORD);

        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(getIntent().hasExtra("EXIT")) {
            boolean result = getIntent().getBooleanExtra("EXIT", false);
            if (result) {
                finish();
            }
        }
        if(savedInstanceState!= null){
            String id = savedInstanceState.getString(LOGIN_EXTRA);
            String url = savedInstanceState.getString(LOGIN_URL_EXTRA);
            String password = savedInstanceState.getString(PASSWORD_EXTRA);
            mLogin.setText(id);
            mPassword.setText(password);
        }
//        getSupportLoaderManager().initLoader(LOADER_ID, null, (android.support.v4.app.LoaderManager.LoaderCallbacks<Object>) this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onRestart();
    }

    public void LoginIntoAccount(View view) throws IOException {
        String id = mLogin.getText().toString();
        String password = mPassword.getText().toString();
        Intent intent = new Intent(MainActivity.this, ApplicationSelection.class);
        startActivity(intent);
        //URL url = NetworkUtils.buildUrl(id,password);
        // new LoginTask().execute(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class LoginTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String LoginResults = null;
            try {
                LoginResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return LoginResults;
        }

        @Override
        protected void onPostExecute(String queryResults) {
            if (queryResults != null && !queryResults.equals("")) {
                Log.d("officespace", queryResults);
            } else{
                Log.d("officespace","No result fetched");
            }
        }
    }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

        String login = mLogin.getText().toString();
        String password = mPassword.getText().toString();
        outState.putString(LOGIN_EXTRA, login);
        outState.putString(PASSWORD_EXTRA, password);
    }
}
