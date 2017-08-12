package thanhhai.com.toeicpractice.HomeModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.R;

public class VideoYoutubeActivity extends AppCompatActivity {

    public static String API_KEY = "AIzaSyBagktDhxVnA7cua62cEQ_j9LH5nhInrT8";
    private String ID_PLAYLIST = "PLqVQ4l-_TpXp96fMqvGpmpbzoZ53RTYQy";
    private String URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + ID_PLAYLIST + "&key=" + API_KEY + "&maxResults=50";

    private ListView lv_video;
    private ArrayList<VideoYoutube> arrayVideo;
    private VideoYoutubeAdapter videoYoutubeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_youtube);
        setTitle("Video Toeic");
        lv_video = (ListView) findViewById(R.id.lv_video);
        arrayVideo = new ArrayList<>();
        videoYoutubeAdapter = new VideoYoutubeAdapter(this, R.layout.item_video, arrayVideo);
        lv_video.setAdapter(videoYoutubeAdapter);
        lv_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(VideoYoutubeActivity.this, PlayVideoActivity.class);
                intent.putExtra("idVideoYoutube", arrayVideo.get(i).getIdVideo());
                startActivity(intent);

            }
        });
        GetJsonYoutube(URL);
    }

    private void GetJsonYoutube(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    String title = "";
                    String urlThumbnail = "";
                    String videoId = "";
                    String channelTitle = "";

                    for (int i = 0; i < jsonItems.length(); i++) {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        title = jsonSnippet.getString("title");

                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                        urlThumbnail = jsonMedium.getString("url");

                        JSONObject jsonResourceID = jsonSnippet.getJSONObject("resourceId");
                        videoId = jsonResourceID.getString("videoId");

                        channelTitle = jsonSnippet.getString("channelTitle");

                        arrayVideo.add(new VideoYoutube(title, urlThumbnail, videoId, "bởi: " + channelTitle));
                    }
                    videoYoutubeAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VideoYoutubeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
