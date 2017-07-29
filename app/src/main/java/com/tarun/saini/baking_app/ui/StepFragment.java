package com.tarun.saini.baking_app.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarun.saini.baking_app.adapter.StepListAdapter;
import com.tarun.saini.baking_app.adapter.StepListAdapter.OnStepClickListener;
import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarun.saini.baking_app.ui.DetailActivity.STEPS;


public class StepFragment extends Fragment {

    @BindView(R.id.step_list_recyclerView)
    RecyclerView stepListRecyclerView;
    private OnStepClickListener mCallBack;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallBack = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public StepFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        stepListRecyclerView.setNestedScrollingEnabled(false);
        stepListRecyclerView.setHasFixedSize(true);
        stepListRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        stepListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Step> arrayList;
        arrayList = getActivity().getIntent().getParcelableArrayListExtra(STEPS);
        StepListAdapter step = new StepListAdapter(arrayList, mCallBack);
        stepListRecyclerView.setAdapter(step);
        return rootView;
    }


}
