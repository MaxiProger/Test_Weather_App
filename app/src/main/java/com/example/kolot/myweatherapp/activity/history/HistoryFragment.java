package com.example.kolot.myweatherapp.activity.history;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kolot.myweatherapp.R;
import com.example.kolot.myweatherapp.activity.adapters.RvAdapter;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.example.kolot.myweatherapp.activity.database.MyApplication;
import com.example.kolot.myweatherapp.activity.database.MyDataBase;
import com.example.kolot.myweatherapp.activity.helpers.CallableDeleteDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class HistoryFragment extends Fragment implements HistoryView{

    private MyDataBase dataBase = MyApplication.getInstance().getMyDataBase();
    private RvAdapter adapter;
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private HistoryPresenter presenter;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.history_constraint);

        presenter = new HistoryPresenter(this);
        adapter = new RvAdapter(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();
    }

    @Override
    public void loadList() {
        disposable.add( dataBase.dao().getAll().observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<DbDto>, List<DbDto>>() {
                    @Override
                    public List<DbDto> apply(List<DbDto> dbDtos) throws Exception {

                        Collections.sort(dbDtos, new Comparator<DbDto>() {
                            @Override
                            public int compare(DbDto dbDto, DbDto t1) {
                                if (dbDto.getTime() > t1.getTime())
                                    return -1;
                                else if (dbDto.getTime() < t1.getTime())
                                    return 1;
                                return 0;
                            }
                        });

                        return dbDtos;
                    }
                })
                .subscribe(new Consumer<List<DbDto>>() {
                    @Override
                    public void accept(List<DbDto> dbDtos) throws Exception {
                        if(dbDtos.size()>=40)
                            presenter.trimList(dbDtos.size(), dbDtos);
                        adapter.setData(dbDtos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Snackbar.make(constraintLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                })
        );
    }

    //для экономия места на устройстве, максимальное число запросов в базе данных не будет превышать 40

    @Override
    public void deleteItems(int size, List<DbDto> dbDtos) {

        List<DbDto> newDbDtos = new ArrayList<>();

        for (int i = --size; i>=40; i--){
            newDbDtos.add(dbDtos.get(i));
        }

        disposable.add(Observable.fromCallable(new CallableDeleteDao(newDbDtos))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        adapter.notifyDataSetChanged();
                    }
                }));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) {
            disposable.dispose();
            disposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
            disposable.clear();
        }
    }
}
