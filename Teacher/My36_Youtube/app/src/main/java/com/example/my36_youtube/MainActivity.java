package com.example.my36_youtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {
    private static final String TAG = "main:MainActivity";

    YouTubePlayerView playerView;
    YouTubePlayer player;

    // 구글에서 키를 생성해서 받아온다
    String API_KEY = "AIzaSyDX5ZInNPI2acRkjhvlSApMI50BvN6zPSM";
    // 유튜브에서 비디오키를 가져온다
    String videoId = "cuhrJ0JjwCI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 플레이어뷰 초기화
        initPlayer();

        // 유튜브 시작하기
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });

    }

    private void playVideo() {
        if(player != null){
            // 만약 플레이어가 진행중이면 일시정지
            if(player.isPlaying()){
                player.pause();
            }
            player.cueVideo(videoId);
        }
    }

    private void initPlayer() {
        playerView = findViewById(R.id.playerView);

        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;

                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String id) {
                        Log.d(TAG, "onLoaded: " + id);

                        player.play();
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
}