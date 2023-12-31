package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Model.DataObjectUrlVideo;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.API;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayingVideo extends AppCompatActivity {
    ExoPlayer player;
    private  static  final  String SHARED_PREF_NAME = "mypref";

    private  static  final  String KEY_USERID = "userId";

    private  static  final  String KEY_PROFILEID = "profileId";

    private  static  final  String KEY_ACCESSTOKEN ="accessToken";
    String[] speed = {"0.25x", "0.5x", "Normal", "1.5x", "2x"};
    private boolean isShowingTrackSelectionDialog = false;
    private DefaultTrackSelector trackSelector;

    ImageView fullscreen;

    Boolean isfullscreen = false;
    ProgressBar progressBar;

    Boolean ispause = false;

    TextView text_speed;

    TextView text_quality;
    ImageView setiing_play;

    StyledPlayerView styledPlayerView;
    API apiserver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_video);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String userID = sharedPref.getString(KEY_USERID,"");
        String profileID = sharedPref.getString(KEY_PROFILEID,"");
        String accessToken = sharedPref.getString(KEY_ACCESSTOKEN,"");
        String m_andoid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        apiserver = RetrofitClient.getlink(profileID,userID, m_andoid,"Bearer " + accessToken).create(API.class);
        Call<DataObjectUrlVideo> data = apiserver.getlinka(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"));

        data.enqueue(new Callback<DataObjectUrlVideo>() {
            @Override
            public void onResponse(Call<DataObjectUrlVideo> call, Response<DataObjectUrlVideo> response) {
                DataObjectUrlVideo urlVideo = response.body();
                styledPlayerView = findViewById(R.id.playvideo);
                player = new ExoPlayer.Builder(PlayingVideo.this).build();

                fullscreen = styledPlayerView.findViewById(R.id.exo_fullscreen_icon);
                TextView info = styledPlayerView.findViewById(R.id.textView);
//                info.setText(urlVideo.getData().);
                progressBar = findViewById(R.id.progressBar);

                 setiing_play = styledPlayerView.findViewById(R.id.exo_settings_icon);

                ImageView play = styledPlayerView.findViewById(R.id.pause_button);

                setiing_play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openSettingPlayVideo(Gravity.BOTTOM);

                    }
                });
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ispause == false)
                        {
                            ispause = true;
                            play.setBackgroundResource(R.drawable.baseline_play_arrow_24);
                            player.setPlayWhenReady(false);
                            player.pause();
                        }
                        else
                        {
                            ispause = false;
                            play.setBackgroundResource(R.drawable.baseline_pause_24);
                            player.setPlayWhenReady(true);
                            player.play();
                        }

                    }
                });

                fullscreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isfullscreen){

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                            if(getSupportActionBar() != null){
                                getSupportActionBar().show();
                            }

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) styledPlayerView.getLayoutParams();
                            params.width = params.MATCH_PARENT;
                            params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                            styledPlayerView.setLayoutParams(params);
                            isfullscreen = false;
                        }else {

                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                            if(getSupportActionBar() != null){
                                getSupportActionBar().hide();
                            }

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) styledPlayerView.getLayoutParams();
                            params.width = params.MATCH_PARENT;
                            params.height = params.MATCH_PARENT;
                            styledPlayerView.setLayoutParams(params);
                            isfullscreen = true;
                        }
                    }
                });

                trackSelector = new DefaultTrackSelector(PlayingVideo.this);


                TrackSelectionParameters newParameters = trackSelector.getParameters()
                        .buildUpon().build();
//                        .setForceLowestBitrate(true)
//                        .build();


                trackSelector.setParameters((DefaultTrackSelector.Parameters) newParameters);
                player = new ExoPlayer.Builder(PlayingVideo.this).setTrackSelector(trackSelector).build();
                styledPlayerView.setPlayer(player);


                MediaItem mediaItem = MediaItem.fromUri(urlVideo.getData().getUrlStreaming());
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true);
                player.play();
                player.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState) {
                        Player.Listener.super.onPlaybackStateChanged(playbackState);
                        if(playbackState == Player.STATE_READY){
                            progressBar.setVisibility(View.GONE);
                            player.setPlayWhenReady(true);
                        }else if(playbackState == Player.STATE_BUFFERING){
                            progressBar.setVisibility(View.VISIBLE);
                            styledPlayerView.setKeepScreenOn(true);
                        }else {
                            progressBar.setVisibility(View.GONE);
                            player.setPlayWhenReady(true);
                        }
                    }


                });
            }

            @Override
            public void onFailure(Call<DataObjectUrlVideo> call, Throwable t) {
                call.cancel();
            }
        });
    }
    @Override
    protected void  onStop()
    {
        super.onStop();
        styledPlayerView.setPlayer(null);
        player.setPlayWhenReady(false);
        player.release();
        player = null;

    }
    private void openSettingPlayVideo(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_settting);
        Window window = dialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttribute = window.getAttributes();
        windownAttribute.gravity = gravity;
        window.setAttributes(windownAttribute);

        if(Gravity.BOTTOM == gravity)
        {
            dialog.setCancelable(true);
        }
        else
        {
            dialog.setCancelable(false);
        }
        LinearLayoutCompat speedBtn = dialog.findViewById(R.id.exo_playback_speed);

        LinearLayoutCompat qualitiesbtn = dialog.findViewById(R.id.exo_playback_quality);
        text_speed = dialog.findViewById(R.id.exo_playback_speed_content);

        text_quality = dialog.findViewById(R.id.exo_playback_quality_content);
        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSpeed();
            }
        });


        qualitiesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isShowingTrackSelectionDialog && TrackSelectionDialog.willHaveContent(trackSelector)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(trackSelector,
                            /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
                }
            }
        });
        dialog.show();
    }
    private void changeSpeed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayingVideo.this);
        builder.setTitle("Set Speed");
        builder.setItems(speed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]

                if (which == 0) {

                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("0.25X");
                    PlaybackParameters param = new PlaybackParameters(0.5f);
                    player.setPlaybackParameters(param);


                }
                if (which == 1) {

                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("0.5X");
                    PlaybackParameters param = new PlaybackParameters(0.5f);
                    player.setPlaybackParameters(param);


                }
                if (which == 2) {

                    text_speed.setVisibility(View.GONE);
                    PlaybackParameters param = new PlaybackParameters(1f);
                    player.setPlaybackParameters(param);


                }
                if (which == 3) {
                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("1.5X");
                    PlaybackParameters param = new PlaybackParameters(1.5f);
                    player.setPlaybackParameters(param);

                }
                if (which == 4) {
                    text_speed.setVisibility(View.VISIBLE);
                    text_speed.setText("2X");
                    PlaybackParameters param = new PlaybackParameters(2f);
                    player.setPlaybackParameters(param);
                }


            }
        });
        builder.show();
    }

}