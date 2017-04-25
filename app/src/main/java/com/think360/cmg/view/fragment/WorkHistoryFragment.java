package com.think360.cmg.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.think360.cmg.AppController;
import com.think360.cmg.BR;
import com.think360.cmg.R;
import com.think360.cmg.adapter.RecyclerBindingAdapter;
import com.think360.cmg.databinding.FragmentWorkHistoryBinding;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.work.Data;
import com.think360.cmg.model.work.WorkHistory;
import com.think360.cmg.presenter.ProjectPresenter;

import java.util.AbstractList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkHistoryFragment extends Fragment implements ProjectPresenter.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Inject
    ApiService apiService;

    private OnFragmentInteractionListener mListener;

    public WorkHistoryFragment() {
        // Required empty public constructor
    }

    private FragmentWorkHistoryBinding fragmentWorkHistoryBinding;
    private ProjectPresenter projectPresenter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkHistoryFragment newInstance(String param1, String param2) {
        WorkHistoryFragment fragment = new WorkHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentWorkHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_history, container, false);
        return fragmentWorkHistoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((AppController) getActivity().getApplication()).getComponent()
                .inject(this);

        fragmentWorkHistoryBinding.rvWorkHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentWorkHistoryBinding.rvWorkHistory.setHasFixedSize(true);
        fragmentWorkHistoryBinding.rvWorkHistory.setItemAnimator(new DefaultItemAnimator());


        projectPresenter = new ProjectPresenter(this, apiService, 1);


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showGithubInfo(WorkHistory collection) {
        fragmentWorkHistoryBinding.rvWorkHistory.setAdapter(new RecyclerBindingAdapter<>(R.layout.single_item_project, BR.project, (AbstractList<Data>) collection.getData()));
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable t) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
