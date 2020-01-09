package net.sahal.capstone_stage2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExerciseDetail extends AppCompatActivity {

    private Exercises exercise;
    private SimpleExoPlayer mExoPlayer;
    private boolean playWhenReady = true;
    private long position = -1;
    private boolean fullscreen = false;

    private final String INFO_DETAIL_PLAYER_POSITION = "Info_Detail_Player_position";
    private final String INFO_DETAIL_EXERCISES = "Info_Detail_EXERCISES";
    private final String INFO_DETAIL_PLAY_WHEN_READY = "Info_Detail_Play_When_Ready";

    public ExerciseDetail() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_exercise_video);

        Intent intent = getIntent();
        if (intent != null) {
            exercise = (Exercises) intent.getSerializableExtra("EXERCISE");
        }

        if (savedInstanceState != null) {
            exercise = (Exercises) savedInstanceState.getSerializable(INFO_DETAIL_EXERCISES);
            position = savedInstanceState.getLong(INFO_DETAIL_PLAYER_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(INFO_DETAIL_PLAY_WHEN_READY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializerPlayer(Uri.parse(exercise.getVideos()));
    }

    private void initializerPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            SimpleExoPlayerView player = findViewById(R.id.step_player);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            player.setPlayer(mExoPlayer);

            player.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(this, "Capstone_Stage2");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);

            fullScreen(player);
        }
    }

    public void fullScreen(final SimpleExoPlayerView player) {
        final ImageView fullscreenButton = player.findViewById(R.id.exo_fullscreen_icon);
        final FragmentActivity thisActivity = this;

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(thisActivity, R.drawable.ic_fullscreen_open));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (((AppCompatActivity) thisActivity).getSupportActionBar() != null) {
                        ((AppCompatActivity) thisActivity).getSupportActionBar().show();
                    }

                    thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    fullscreen = false;

                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(thisActivity, R.drawable.ic_fullscreen_close));
                    thisActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (((AppCompatActivity) thisActivity).getSupportActionBar() != null) {
                        ((AppCompatActivity) thisActivity).getSupportActionBar().hide();
                    }
                    thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    fullscreen = true;
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(INFO_DETAIL_EXERCISES, exercise);
        outState.putLong(INFO_DETAIL_PLAYER_POSITION, position);
        outState.putBoolean(INFO_DETAIL_PLAY_WHEN_READY, playWhenReady);
        super.onSaveInstanceState(outState);
    }
}
