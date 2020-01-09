package net.sahal.capstone_stage2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;

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

        setTitle(exercise.getExercise());
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
                    fullscreen = false;

                    thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ((AppCompatActivity) thisActivity).getSupportActionBar().show();
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(thisActivity, R.drawable.ic_fullscreen_open));

                } else {
                    fullscreen = true;

                    thisActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    ((AppCompatActivity) thisActivity).getSupportActionBar().hide();
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(thisActivity, R.drawable.ic_fullscreen_close));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ExerciseDetail.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Sign Out Successfully", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
