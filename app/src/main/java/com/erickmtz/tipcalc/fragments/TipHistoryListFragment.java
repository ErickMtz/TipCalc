package com.erickmtz.tipcalc.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.erickmtz.tipcalc.activities.Detail;
import com.erickmtz.tipcalc.adapters.OnItemClickListener;
import com.erickmtz.tipcalc.R;
import com.erickmtz.tipcalc.adapters.TipAdapter;
import com.erickmtz.tipcalc.models.TipRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipHistoryListFragment extends Fragment implements TipHistoryListFragmentListener, OnItemClickListener{
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    TipAdapter adapter;

    public TipHistoryListFragment()   {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tip_history_list, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        initRecyclerView();
        return view;
    }

    private void initAdapter() {
        if(adapter == null) {
            adapter = new TipAdapter(getActivity().getApplicationContext(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addToList(TipRecord record) {
        adapter.add(record);
    }

    @Override
    public void clearList() {
        adapter.clear();
    }

    @Override
    public void onItemClick(TipRecord tipRecord) {
        // TODO Implementar la lógica para llamar una actividad enviandole la información de la propina
        Intent intent = new Intent(getActivity(),Detail.class);
        intent.putExtra(Detail.detailAmount,tipRecord.getBill());
        intent.putExtra(Detail.detailTip,tipRecord.getTip());
        intent.putExtra(Detail.detailDate,tipRecord.getDateFormated());


        startActivity(intent);

        Log.v("Dinero: ",String.valueOf(tipRecord.getBill()));
        //Log.v("Propina: ",String.valueOf(tipRecord.getTip()));
        //Log.v("Fecha: ",tipRecord.getDateFormated());
    }
}
