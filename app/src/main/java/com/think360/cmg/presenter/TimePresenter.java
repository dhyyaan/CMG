package com.think360.cmg.presenter;

import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.work.Data;
import com.think360.cmg.model.work.WorkHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by think360 on 28/04/17.
 */

public class TimePresenter {

    private View view;

    private ApiService api;

    public interface View {
        void onTimeZonesAdded(List<Data> list);


        void OnProjectLoadComplete(WorkHistory collection);

        void onCompleted();

        void onError(Throwable t);

    }


    public TimePresenter(View view, ApiService api, int id) {
        this.api = api;
        this.view = view;
        createTimerZoneList();
        getGithubInfo(id);
    }


    private void getGithubInfo(int id) {

        api.getWorkHistoryWithCall(id).enqueue(new Callback<WorkHistory>() {
            @Override
            public void onResponse(Call<WorkHistory> call, Response<WorkHistory> response) {


                view.OnProjectLoadComplete(response.body());
            }

            @Override
            public void onFailure(Call<WorkHistory> call, Throwable t) {

                view.onError(t);

            }
        });
    }

    private void createTimerZoneList() {
        Observable.create(new ObservableOnSubscribe<List<Data>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Data>> e) throws Exception {
                List<Data> stringArrayList = new ArrayList<>();

                for (String id : TimeZone.getAvailableIDs()) {
                    Data data = new Data();
                    data.setProjectName(displayTimeZone(TimeZone.getTimeZone(id)));
                    stringArrayList.add(data);
                }
                Data data = new Data();
                data.setProjectName("Select Zone");
                data.setProjectId("");
                stringArrayList.add(0, data);
                e.onNext(stringArrayList);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<List<Data>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(final List<Data> strings) {




                view.onTimeZonesAdded(strings);


            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });

    }


    private static String displayTimeZone(TimeZone tz) {

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        if (hours > 0) {
            result = String.format("%s (GMT+%d:%02d)", tz.getID(), hours, minutes);
        } else {
            result = String.format("%s (GMT%d:%02d)", tz.getID(), hours, minutes);
        }

        return result;

    }

}
