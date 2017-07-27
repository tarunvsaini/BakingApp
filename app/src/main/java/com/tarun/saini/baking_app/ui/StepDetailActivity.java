package com.tarun.saini.baking_app.ui;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import static com.tarun.saini.baking_app.adapter.StepListAdapter.STEP;
import static com.tarun.saini.baking_app.adapter.StepListAdapter.STEPLIST;
import static com.tarun.saini.baking_app.ui.StepsActivity.STEP_PANES;

public class StepDetailActivity extends AppCompatActivity  {

    private static final String SAVE_STATE = "stepDetailFragment";
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Step step = getIntent().getParcelableExtra(STEP);
        ArrayList<Step> mSteps = getIntent().getParcelableArrayListExtra(STEPLIST);
        boolean mTwoPane=getIntent().getBooleanExtra(STEP_PANES,false);
        Bundle bundle = getIntent().getExtras();

        if (savedInstanceState==null)
        {
            stepDetailFragment=new StepDetailFragment();
            bundle.putBoolean(STEP_PANES,mTwoPane);
            stepDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.steps_detail_container, stepDetailFragment)
            .commit();
        }

        else
            {
                if (stepDetailFragment!=null && stepDetailFragment.isAdded())
                stepDetailFragment= (StepDetailFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState,SAVE_STATE);

            }


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        if (stepDetailFragment!=null && stepDetailFragment.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,SAVE_STATE,stepDetailFragment);

        }

    }
}
