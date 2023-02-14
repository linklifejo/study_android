package com.example.my32_youtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


// Youtube를 사용하기 위해서는 반드시 jar 라이브러리 파일을 포함시켜야 함
// extends를 YouTubeBaseActivity로 변경한다

public class MainActivity extends YouTubeBaseActivity {
    private static final String TAG = "main:MainActivity";
    YouTubePlayerView playerView;
    YouTubePlayer player;
    // 구글에서 키를 생성해서 받아온다
    String API_KEY = "AIzaSyBEjILqsAYy6v007kSCv8E1CE_Nry8A2v8";
    // 유투브에서 비디오키를 갸져욘다
    String videoId = "1ZBeLZitiBQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 플레이어뷰 초기화
        initPlayer();
        // 버튼눌러서 유투부 시작하기
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Toast.makeText(MainActivity.this, "초기화 실패하였습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}