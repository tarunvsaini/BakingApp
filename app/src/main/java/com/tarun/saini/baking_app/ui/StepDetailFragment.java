package com.tarun.saini.baking_app.ui;


import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarun.saini.baking_app.adapter.StepListAdapter.STEPLIST;
import static com.tarun.saini.baking_app.ui.StepsActivity.STEP_DESCRIPTION;
import static com.tarun.saini.baking_app.ui.StepsActivity.STEP_PANES;
import static com.tarun.saini.baking_app.ui.StepsActivity.STEP_POSITION;
import static com.tarun.saini.baking_app.ui.StepsActivity.STEP_THUMBNAIL;
import static com.tarun.saini.baking_app.ui.StepsActivity.STEP_VIDEO;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener, View.OnClickListener {

    private static final String SAVE_DETAILS = "stepDetail";
    private static final String SAVE_URL = "stepUrl";
    private static final String SAVE_POSITION = "stepPosition";
    private static final String SAVE_LIST = "stepList";
    private static final String SAVE_INDEX = "index";
    private static final String SAVE_RESUME_POSITION = "save_resume_position";
    private static final String SAVE_WINDOW = "save_resume_window";

    private Step mStep;
    @BindView(R.id.recipe_description)
    TextView description;
    @BindView(R.id.text_previous)
    TextView previousText;
    @BindView(R.id.text_next)
    TextView nextText;
    @BindView(R.id.previous_button)
    ImageButton previous;
    @BindView(R.id.next_button)
    ImageButton next;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.thumbnail_image)
    ImageView thumbnailImage;
    private TrackSelector trackSelector;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;
    private String videoUrl;
    private String thumbnailUrl;
    private ArrayList<Step> arrayList;
    private int mIndex;
    private int position;
    private int resumeWindow;
    private long resumePosition;
    private boolean shouldAutoPlay;


    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private String recipeDetail;


    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        shouldAutoPlay = true;
        // clearResumePosition();


        description.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "lato_regular.ttf"));
        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video);


        Bundle bundle = this.getArguments();

        Boolean mTwoPaneLayout = bundle.getBoolean(STEP_PANES);

        if (savedInstanceState == null) {
            if (mTwoPaneLayout) {
                previous.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                previousText.setVisibility(View.GONE);
                nextText.setVisibility(View.GONE);
                mToolbar.setVisibility(View.GONE);
                tabView();
            } else {
                phoneView();
            }

        } else {
            recipeDetail = savedInstanceState.getString(SAVE_DETAILS);
            videoUrl = savedInstanceState.getString(SAVE_URL);
            position = savedInstanceState.getInt(SAVE_POSITION);
            arrayList = savedInstanceState.getParcelableArrayList(SAVE_LIST);
            mIndex = savedInstanceState.getInt(SAVE_INDEX);
            resumePosition = savedInstanceState.getLong(SAVE_RESUME_POSITION);
            resumeWindow = savedInstanceState.getInt(SAVE_WINDOW);


            if (mTwoPaneLayout) {
                previous.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                previousText.setVisibility(View.GONE);
                nextText.setVisibility(View.GONE);
                mToolbar.setVisibility(View.GONE);
                showDataTab();
                player.seekTo(resumePosition);
            } else {
                description.setText(arrayList.get(position).getDescription());
                showDataPhone(position);
                playerView.setPlayer(player);
                player.seekTo(resumePosition);
            }

        }

        return rootView;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    private void phoneView() {


        arrayList = getActivity().getIntent().getParcelableArrayListExtra(STEPLIST);
        position = getArguments().getInt(STEP_POSITION);
        showDataPhone(position);
    }

    private void tabView() {

        recipeDetail = getArguments().getString(STEP_DESCRIPTION);
        videoUrl = getArguments().getString(STEP_VIDEO);
        position = getArguments().getInt(STEP_POSITION);
        thumbnailUrl = getArguments().getString(STEP_THUMBNAIL);
        showDataTab();


    }

    private void showDataTab() {
        description.setText(recipeDetail);
        if (!videoUrl.isEmpty() && thumbnailUrl.isEmpty()) {
            initializePlayer(Uri.parse(videoUrl));
            thumbnailImage.setVisibility(View.GONE);

        } else if (!thumbnailUrl.isEmpty() && thumbnailUrl.endsWith(".mp4")) {
            playerView.setVisibility(View.VISIBLE);
            playerView.requestFocus();
            initializePlayer(Uri.parse(thumbnailUrl));
        } else if (!thumbnailUrl.isEmpty() && !thumbnailUrl.endsWith(".mp4")) {
            playerView.setVisibility(View.GONE);
            Glide.with(getActivity()).load(thumbnailUrl).into(thumbnailImage);
        } else {
            playerView.setVisibility(View.GONE);
            thumbnailImage.setVisibility(View.GONE);
        }
    }


    private void showDataPhone(int index) {

        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        playerView.setVisibility(View.VISIBLE);
        playerView.requestFocus();
        recipeDetail = arrayList.get(index).getDescription();
        videoUrl = arrayList.get(index).getVideoURL();
        description.setText(recipeDetail);
        mIndex = arrayList.get(position).getId();
        thumbnailUrl = arrayList.get(position).getThumbnailURL();

        if (!videoUrl.isEmpty() && thumbnailUrl.isEmpty()) {
            initializePlayer(Uri.parse(videoUrl));
            thumbnailImage.setVisibility(View.GONE);

        } else if (!thumbnailUrl.isEmpty() && thumbnailUrl.endsWith(".mp4")) {
            playerView.setVisibility(View.VISIBLE);
            playerView.requestFocus();
            initializePlayer(Uri.parse(thumbnailUrl));
        } else if (!thumbnailUrl.isEmpty() && !thumbnailUrl.endsWith(".mp4")) {
            playerView.setVisibility(View.GONE);
            Glide.with(getActivity()).load(thumbnailUrl).into(thumbnailImage);
        } else {
            playerView.setVisibility(View.GONE);
            thumbnailImage.setVisibility(View.GONE);
        }

        if (position < arrayList.size() - 1)
            mToolbar.setTitle(arrayList.get(position).getShortDescription());
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setFitsSystemWindows(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));


        if (position == 0) {
            previous.setVisibility(View.GONE);
            previousText.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
            nextText.setVisibility(View.VISIBLE);

        }
    }


    private void initializePlayer(Uri mediaUri) {

        if (player == null) {
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getActivity(),
                    null, DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);

            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);


            player.addListener(this);

            playerView.setPlayer(player);
            player.setPlayWhenReady(true);


            String userAgent = Util.getUserAgent(getActivity(), "Baking App");

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getActivity(), BANDWIDTH_METER,
                            new DefaultHttpDataSourceFactory(userAgent, BANDWIDTH_METER)),
                    new DefaultExtractorsFactory(), null, null);

            player.prepare(mediaSource);


        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {

        if (player != null) {
            player.stop();
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.next_button:
                if (mIndex < arrayList.size() - 1) {

                    next.setVisibility(View.VISIBLE);
                    previous.setVisibility(View.VISIBLE);
                    previousText.setVisibility(View.VISIBLE);
                    nextText.setVisibility(View.VISIBLE);
                    int i = ++mIndex;
                    position += 1;
                    releasePlayer();
                    showDataPhone(i);

                } else {

                    Toast.makeText(getActivity(), "Last Step", Toast.LENGTH_LONG).show();
                    next.setVisibility(View.GONE);
                    nextText.setVisibility(View.INVISIBLE);

                }
                break;
            case R.id.previous_button:
                if (mIndex > 0) {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    previousText.setVisibility(View.VISIBLE);
                    nextText.setVisibility(View.VISIBLE);
                    int index = --mIndex;
                    position -= 1;
                    releasePlayer();
                    showDataPhone(index);


                } else {

                    Toast.makeText(getActivity(), "First Step", Toast.LENGTH_LONG).show();
                    previous.setVisibility(View.GONE);
                    previousText.setVisibility(View.GONE);

                }
                break;

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(SAVE_DETAILS, recipeDetail);
        outState.putString(SAVE_URL, videoUrl);
        outState.putInt(SAVE_POSITION, position);
        outState.putParcelableArrayList(SAVE_LIST, arrayList);
        outState.putInt(SAVE_INDEX, mIndex);
        outState.putInt(SAVE_WINDOW, resumeWindow);
        outState.putLong(SAVE_RESUME_POSITION, resumePosition);


    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.getCurrentPosition();
    }


}


