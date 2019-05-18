package com.nqc.tracuukaraoke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nqc.adapter.SingAdapter;
import com.nqc.model.Sing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HatOnlineFragment extends Fragment {
    View view;
    public static String API_KEY = "AIzaSyD9PrqM5MxSa-CKW2KGhnmNbeMG07ZJem0";

    String tenbai = ChiTietBaiHatActivity.song.getTenBH();
    ListView lvSing;
    ArrayList<Sing> arrSing;
    SingAdapter adapterSing;
    String ulrGetJson = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&q="+tenbai+"karaoke"+"&key=" + API_KEY;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_hat_online, container, false);
        addControls();
        addEvents();
        GetJsonYouTube(ulrGetJson);
        return view;
    }
    private void addEvents() {
        lvSing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), PlayVideoActivity.class);
                intent.putExtra("idVideoSing", arrSing.get(i).getIdVideo());
                startActivity(intent);
            }
        });
    }
    private void addControls() {
        lvSing = view.findViewById(R.id.lvSing);
        arrSing = new ArrayList<Sing>();
        adapterSing = new SingAdapter(view.getContext(), R.layout.item_sing, arrSing);
        lvSing.setAdapter(adapterSing);
    }

    private void GetJsonYouTube(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonItems = response.getJSONArray("items");

                    String title = "";
                    String url = "";
                    String idVideo = "";


                    for (int i = 0; i < jsonItems.length(); i++) {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);

                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        title = jsonSnippet.getString("title");
                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                        url = jsonMedium.getString("url");
                        JSONObject jsonId = jsonItem.getJSONObject("id");
                        idVideo = jsonId.getString("videoId");

                        arrSing.add(new Sing(title,url,idVideo));
                    }

                    adapterSing.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Loi", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
