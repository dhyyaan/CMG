package com.think360.cmg.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.think360.cmg.AppController;
import com.think360.cmg.R;
import com.think360.cmg.adapter.WeatherAdapter;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.work.Data;
import com.think360.cmg.model.work.WorkHistory;
import com.think360.cmg.presenter.TimePresenter;
import com.think360.cmg.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;


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
    @Inject
    ApiService apiService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private int seconds;
    private boolean running;
    private TextView btnStart, btnFinish, btnPause, tvProjectName, tvTimezone;
    private Spinner appCompatSpinner, spinnerProject;
    private boolean isTimeRunning = false;
    private Handler handler;
    private Runnable runnable;
    private boolean isPausedByWorker = false;
    private String projectId = "";
    private String projectName = "";

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

        ((AppController) getActivity().getApplication()).getComponent()
                .inject(this);
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

        if (handler != null) {
            handler.removeCallbacks(runnable);
        }

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

        handler = new Handler();
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

        btnStart = (TextView) view.findViewById(R.id.btnStart);
        btnPause = (TextView) view.findViewById(R.id.btnPause);
        btnFinish = (TextView) view.findViewById(R.id.btnFinish);


        tvProjectName = (TextView) view.findViewById(R.id.tvProjectName);
        tvProjectName.setOnClickListener(this);
        tvTimezone = (TextView) view.findViewById(R.id.tvTimezone);
        tvTimezone.setOnClickListener(this);


        spinnerProject = (Spinner) view.findViewById(R.id.spinnerProject);
        appCompatSpinner = (Spinner) view.findViewById(R.id.spinnerTimeZone);


        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnFinish.setOnClickListener(this);


        try {
            Log.d("TIME", (Settings.Global.getInt(getActivity().getContentResolver(), Settings.Global.AUTO_TIME) == 1) + "");
            Log.d("TIME_ZONE", (Settings.Global.getInt(getActivity().getContentResolver(), Settings.Global.AUTO_TIME_ZONE) == 1) + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        new TimePresenter(WorkTimeFragment.this, apiService, 1);


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
    public void onTimeZonesAdded(List<Data> list) {
        appCompatSpinner.setAdapter(new WeatherAdapter(getActivity(), list));

        appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0) {
                    Data data = (Data) parent.getAdapter().getItem(position);
                    projectId = data.getProjectId();
                    projectName = data.getProjectName();
                    tvProjectName.setText(projectName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void OnProjectLoadComplete(WorkHistory collection) {
        Data data = new Data();
        data.setProjectName("Select Project");
        data.setProjectId("");

        collection.getData().add(0, data);
        spinnerProject.setAdapter(new WeatherAdapter(getActivity(), collection.getData()));
        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0) {
                    Data data = (Data) parent.getAdapter().getItem(position);
                    projectId = data.getProjectId();
                    projectName = data.getProjectName();
                    tvProjectName.setText(projectName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btnStart:
                if (!TextUtils.isEmpty(projectId)) {
                    if (seconds == 0) {
                        isTimeRunning = true;
                        running = true;
                        runTimer(btnStart);
                    } else {
                        Toast.makeText(getActivity(), "Finish Current Job First", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Select Any project To Start With", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.btnPause:
                if (seconds > 0) {


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
                } else {
                    Toast.makeText(getActivity(), "Start a Job First", Toast.LENGTH_SHORT).show();

                }


                break;


            case R.id.btnFinish:
                if (seconds > 0) {
                    handler.removeCallbacks(runnable);
                    isPausedByWorker = false;
                    running = false;
                    isTimeRunning = false;
                    seconds = 0;

                    btnStart.setText("START");

                    AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_PAUSED_BY_USER, false).apply();
                    AppController.getSharedPrefEditor().putInt(AppConstants.TIME_ELAPSED_WHEN_PAUSED_BY_WORKDER, 0).apply();

                } else {
                    Toast.makeText(getActivity(), "Start a Job First", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvProjectName:
                spinnerProject.performClick();
                break;

            case R.id.tvTimezone:
                appCompatSpinner.performClick();
                break;
        }
    }

    void setTextOnButton(TextView textView, int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int sec = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
        textView.setText(time);
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
