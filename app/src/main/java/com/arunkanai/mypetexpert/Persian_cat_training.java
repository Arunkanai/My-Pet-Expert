package com.arunkanai.mypetexpert;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Persian_cat_training extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persian_cat_training);

        YouTubePlayerView youTubePlayerView1 = findViewById(R.id.youtube_player_view1);
        YouTubePlayerView youTubePlayerView2 = findViewById(R.id.youtube_player_view2);
        YouTubePlayerView youTubePlayerView3 = findViewById(R.id.youtube_player_view3);

        getLifecycle().addObserver(youTubePlayerView1);
        getLifecycle().addObserver(youTubePlayerView2);

        youTubePlayerView1.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "ozxwS8Rs0X4";
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "0XSTQZQmy6E";
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        youTubePlayerView3.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "IGb_7yQDxVQ";
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
}
