package com.example.vipul.weathermap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.vipul.weathermap.QueryUtils.fetchWeatherData;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    EditText editText;
    Button button;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.weather_Text);
        editText = findViewById(R.id.city_name);
        button = findViewById(R.id.search_button);


    }

    public void findWeather(View view) {

        String city = editText.getText().toString();
        Log.i("city name", city);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Start the AsyncTask to fetch the earthquake data
            DownloadTask task = new DownloadTask();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+",in&appid=461f85ece67361ee91b1a5dfaa00b9bb");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No Internet!",Toast.LENGTH_SHORT).show();
        }

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return fetchWeatherData(urls[0]);

        }

        @Override
        protected void onPostExecute(String jsonResult) {
            super.onPostExecute(jsonResult);

            try {

                JSONObject jsonObject = new JSONObject(jsonResult);

                JSONObject main = jsonObject.getJSONObject("main");

                result = "Current Temp : " + main.getString("temp")
                        + "\nPressure : " + main.getString("pressure")
                        + "\nMin Temp: "+ main.getString("temp_min")
                        + "\nMax Temp: "+ main.getString("temp_max");

                if(result!="");
                {
                    textView.setText(result);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
