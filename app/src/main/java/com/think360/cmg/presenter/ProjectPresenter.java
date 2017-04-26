package com.think360.cmg.presenter;

import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.work.WorkHistory;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by think360 on 24/04/17.
 */

public class ProjectPresenter {

    private CompositeDisposable _disposables;
    private ApiService api;
    private ProjectPresenter.View view;

    public interface View {
        void showGithubInfo(WorkHistory collection);

        void onCompleted();

        void onError(Throwable t);
    }

    public ProjectPresenter(ProjectPresenter.View view, ApiService api, int id) {
        this.view = view;
        this.api = api;
        _disposables = new CompositeDisposable();
        getGithubInfo(id);
    }

    public void getGithubInfo(int id) {

        api.getWorkHistoryWithCall(id).enqueue(new Callback<WorkHistory>() {
            @Override
            public void onResponse(Call<WorkHistory> call, Response<WorkHistory> response) {
                view.showGithubInfo(response.body());
            }

            @Override
            public void onFailure(Call<WorkHistory> call, Throwable t) {

                view.onError(t);

            }
        });
    /*    _disposables.add(api.getWorkHistory(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableObserver<WorkHistory>() {

                            @Override
                            public void onComplete() {
                               // Timber.d("Retrofit call 1 completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                               // Timber.e(e, "woops we got an error while getting the list of contributors");
                            }

                            @Override
                            public void onNext(WorkHistory contributors) {
                                        view.showGithubInfo(contributors);
                               // Timber.d("%s has made %d contributions to %s", );
                            }

                        }));*/

    }

    public void onStop() {
        _disposables.dispose();

    }


}
