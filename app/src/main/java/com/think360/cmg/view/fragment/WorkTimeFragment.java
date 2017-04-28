package com.think360.cmg.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.think360.cmg.AppController;
import com.think360.cmg.R;
import com.think360.cmg.presenter.TimePresenter;
import com.think360.cmg.utils.AppConstants;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkTimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkTimeFragment extends Fragment implements TimePresenter.View, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int seconds;
    private boolean running;

    private TextView btnStart, btnFinish, btnPause;
    private AppCompatSpinner appCompatSpinner;

    private boolean isTimeRunning = false;
    private final Handler handler = new Handler();
    private Runnable runnable;

    private boolean isPausedByWorker = false;

    public WorkTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkTimeFragment newInstance(String param1, String param2) {
        WorkTimeFragment fragment = new WorkTimeFragment();
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
        return inflater.inflate(R.layout.fragment_work, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {


        AppController.getSharedPrefEditor().putInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_APP, seconds).apply();
        AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_TIME_RUNNING, isTimeRunning).apply();
        AppController.getSharedPrefEditor().putLong(AppConstants.SYSTEM_MILLIS_WHEN_PAUSED_BY_APP, System.currentTimeMillis()).apply();
        running = false;

        AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_PAUSED_BY_USER, isPausedByWorker).apply();
        AppController.getSharedPrefEditor().putInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_WORKDER, seconds).apply();


        handler.removeCallbacks(runnable);


        super.onPause();
    }

    @Override
    public void onResume() {

        if (AppController.sharedPreferencesCompat.getBoolean(AppConstants.IS_TIME_RUNNING, false)) {
            running = true;
            long system = AppController.sharedPreferencesCompat.getLong(AppConstants.SYSTEM_MILLIS_WHEN_PAUSED_BY_APP, System.currentTimeMillis());
            seconds = AppController.sharedPreferencesCompat.getInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_APP, 0) + (int) (System.currentTimeMillis() - system) / 1000;
            runTimer(btnStart);
        }
        if (AppController.sharedPreferencesCompat.getBoolean(AppConstants.IS_PAUSED_BY_USER, false)) {
            isPausedByWorker = true;
            seconds = AppController.sharedPreferencesCompat.getInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_WORKDER, 0);
            btnPause.setText("RESUME");
            setTextOnButton(btnStart, seconds);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void startTimer(View view) {
        running = true;
    }

    public void stopTimer(View view) {
        running = false;
    }

    public void resetTimer(View view) {
        running = true;
        seconds = 0;
    }

    public void runTimer(final TextView textView) {


        runnable = new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
                textView.setText(time);
                isTimeRunning = true;
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);

    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = (TextView) view.findViewById(R.id.tvStartTime);
        btnPause = (TextView) view.findViewById(R.id.btnPause);
        btnFinish = (TextView) view.findViewById(R.id.btnFinish);

        appCompatSpinner = (AppCompatSpinner) view.findViewById(R.id.spinnerTimeZone);

        btnFinish.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        try {
            Log.d("TIME", (Settings.Global.getInt(getActivity().getContentResolver(), Settings.Global.AUTO_TIME) == 1) + "");
            Log.d("TIME_ZONE", (Settings.Global.getInt(getActivity().getContentResolver(), Settings.Global.AUTO_TIME_ZONE) == 1) + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        new TimePresenter(WorkTimeFragment.this);


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
    public void onTimeZonesAdded(List<String> list) {
        appCompatSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPause:

                if (AppController.sharedPreferencesCompat.getBoolean(AppConstants.IS_PAUSED_BY_USER, false)) {
                    isPausedByWorker = false;
                    AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_PAUSED_BY_USER, isPausedByWorker).apply();
                    running = true;
                    isTimeRunning = true;
                    btnPause.setText("PAUSE");
                    seconds = AppController.sharedPreferencesCompat.getInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_WORKDER, 0);
                    runTimer(btnStart);

                } else {
                    isPausedByWorker = true;
                    running = false;
                    isTimeRunning = false;
                    handler.removeCallbacks(runnable);
                    AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_PAUSED_BY_USER, isPausedByWorker).apply();
                    AppController.getSharedPrefEditor().putInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_WORKDER, seconds).apply();
                    btnPause.setText("RESUME");
                }


                break;
            case R.id.tvStartTime:
                isTimeRunning = true;
                running = true;
                runTimer(btnStart);

                break;

            case R.id.btnFinish:
                isPausedByWorker = false;
                running = false;
                isTimeRunning = false;

                handler.removeCallbacks(runnable);
                btnStart.setText("START");

                AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_PAUSED_BY_USER, false).apply();
                AppController.getSharedPrefEditor().putInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_WORKDER, 0).apply();


                break;
        }
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

    void setTextOnButton(TextView textView, int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int sec = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
        textView.setText(time);
    }
}
