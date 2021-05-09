package com.example.smart_edu.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.Admin.QlySach.Theme.Theme_display;
import com.example.smart_edu.Admin.QlySach.Theme.Vocabulary.Vocab_adapter;
import com.example.smart_edu.Model.Book;
import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.Model.Vocab;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Study extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    RecyclerView recyclerView;

    Theme theme;
    ArrayList<Vocab> mang_vocab;
    Vocab_adapter vocab_adapter;


    String API_key = "AIzaSyDjx1v-HGRucySz351ffW3DM0NfjSPWJuc";
    int REQUEST_VIDEO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_theme_display);

        GetData_intent();
        Anhxa();

        youTubePlayerView.initialize(API_key, this);
        GetData(theme.getIdTheme());
    }

    private void GetData(int id_theme) {
        Dataservice dataservice = APIService.getService();
        Call<List<Vocab>> callback = dataservice.GetVocabulary(id_theme);
        callback.enqueue(new Callback<List<Vocab>>() {

            @Override
            public void onResponse(Call<List<Vocab>> call, Response<List<Vocab>> response) {
                mang_vocab = (ArrayList<Vocab>) response.body();
                vocab_adapter = new Vocab_adapter(getApplicationContext(), mang_vocab);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(vocab_adapter);
            }

            @Override
            public void onFailure(Call<List<Vocab>> call, Throwable t) {

                Toast.makeText(Study.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Anhxa() {
        youTubePlayerView = findViewById(R.id.playyoutube);
        recyclerView = findViewById(R.id.user_listvocab);

        mang_vocab = new ArrayList<>();
        vocab_adapter = new Vocab_adapter(Study.this, mang_vocab);
    }



    private void GetData_intent() {
        theme = (Theme) getIntent().getSerializableExtra("theme");
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if (!b) {
            youTubePlayer.cueVideo(theme.getIdLinkLesson());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(Study.this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO) {
            getYouTubePlayerProvider().initialize(API_key, Study.this);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }
}
