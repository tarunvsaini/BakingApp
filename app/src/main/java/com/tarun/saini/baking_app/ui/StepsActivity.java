package com.tarun.saini.baking_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tarun.saini.baking_app.adapter.StepListAdapter;
import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarun.saini.baking_app.adapter.StepListAdapter.STEP;
import static com.tarun.saini.baking_app.adapter.StepListAdapter.STEPLIST;

public class StepsActivity extends AppCompatActivity implements StepListAdapter.OnStepClickListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Boolean mTwoPaneLayout;
    public static final String STEP_POSITION = "step_position";
    public static final String STEP_PANES = "step_panes";
    public static final String STEP_VIDEO = "step_video";
    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_ID = "step_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setFitsSystemWindows(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));

        if (mTwoPaneLayout = findViewById(R.id.step_linear_layout) != null) {
            mTwoPaneLayout = true;
        } else {
            mTwoPaneLayout = false;

        }


    }

    @Override
    public void onStepSelected(int position, ArrayList<Step> steps) {

//        Toast.makeText(this, mTwoPaneLayout+" Item Clicked", Toast.LENGTH_SHORT).show();

        Bundle bun = new Bundle();
        if (mTwoPaneLayout) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            bun.putString(STEP_VIDEO, steps.get(position).getVideoURL());
            bun.putString(STEP_DESCRIPTION, steps.get(position).getDescription());
            bun.putBoolean(STEP_PANES, mTwoPaneLayout);
            bun.putInt(STEP_POSITION, position);
            stepDetailFragment.setArguments(bun);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.steps_detail_container, stepDetailFragment);
            transaction.commit();

        } else {
            Intent stepDetails = new Intent(this, StepDetailActivity.class);
            bun.putInt(STEP_POSITION, position);
            bun.putBoolean(STEP_PANES, mTwoPaneLayout);
            stepDetails.putExtra(STEP, steps.get(position));
            //stepDetails.putExtra(STEP_POSITION, position);
            stepDetails.putParcelableArrayListExtra(STEPLIST, steps);
            stepDetails.putExtras(bun);
            startActivity(stepDetails);
        }

    }
}
