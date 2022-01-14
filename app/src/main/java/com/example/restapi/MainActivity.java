package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_sembuh, tv_positif, tv_dirawat, tv_meninggal;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_sembuh = findViewById(R.id.tv_sembuh);
        tv_positif = findViewById(R.id.tv_positif);
        tv_dirawat = findViewById(R.id.tv_dirawat);
        tv_meninggal = findViewById(R.id.tv_meninggal);

        tampilData();
    }

    private void tampilData() {
        loading = ProgressDialog.show(MainActivity.this, "Memuat Data", "Harap tunggu...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://apicovid19indonesia-v2.vercel.app/api/indonesia";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response.toString());
                    String positif = jsonObject1.getString("positif");
                    String sembuh = jsonObject1.getString("sembuh");
                    String dirawat = jsonObject1.getString("dirawat");
                    String meninggal = jsonObject1.getString("meninggal");

                    tv_positif.setText(positif);
                    tv_sembuh.setText(sembuh);
                    tv_dirawat.setText(dirawat);
                    tv_meninggal.setText(meninggal);
                    loading.cancel();
                    Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Toast.makeText(MainActivity.this, "Gagal ambil REST API " + error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(stringRequest);
    }
}