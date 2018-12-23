package com.example.rza.socialmedialogins;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Pool extends Activity {

    SimpleAdapter adapter;
    private ProgressDialog pDialog;
    public TextView x_DateText;
    public TextView x_DegreeText;
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    private static String url_all_values = "https://thingspeak.com/channels/436068/feed.json";

    private static final String TAG_FEEDS = "feeds";
    private static final String TAG_ENTRYID = "entry_id";
    private static final String TAG_CREATEDAT = "created_at";
    private static final String TAG_FIELD = "field1";

    JSONArray values = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pool);
        if (Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        x_DateText = findViewById(R.id.poolDate_text);
        x_DegreeText = findViewById(R.id.poolDegree_text);
        productsList = new ArrayList<HashMap<String, String>>();
        new LoadAllProducts().execute();

    }
    class LoadAllProducts extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Pool.this);
            pDialog.setMessage("Please Wait a Moment ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_all_values, "GET", params);
            Log.d("All Values: ", json.toString());

            try {

                values = json.getJSONArray(TAG_FEEDS);
                for (int i = 0; i < values.length(); i++) {
                    JSONObject c = values.getJSONObject(i);

                    String id = c.getString(TAG_ENTRYID);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(c.getString(TAG_CREATEDAT));
                    String created_at = dateFormat.format(date);

                    String field = c.getString(TAG_FIELD);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ENTRYID, id);
                    map.put(TAG_CREATEDAT, created_at);
                    map.put(TAG_FIELD, field);
                    productsList.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            x_DateText.setText(productsList.get(productsList.size() - 1).get(TAG_CREATEDAT));
            String x_ListItem = productsList.get(productsList.size() - 1).get(TAG_FIELD);
            int x_Degree =  Integer.parseInt(x_ListItem);
            String x_StringDegree = Integer.toString(x_Degree) + (char) 0x00B0;
            x_DegreeText.setText(x_StringDegree);
        }

    }
}
