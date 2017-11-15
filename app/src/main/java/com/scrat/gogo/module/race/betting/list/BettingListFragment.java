package com.scrat.gogo.module.race.betting.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.Betting;
import com.scrat.gogo.data.model.BettingItem;
import com.scrat.gogo.databinding.FragmentBettingListBinding;
import com.scrat.gogo.databinding.ListItemBettingItemBinding;
import com.scrat.gogo.framework.common.BaseFragment;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;
import java.util.Locale;

/**
 * Created by scrat on 2017/11/15.
 */

public class BettingListFragment extends BaseFragment {
    private FragmentBettingListBinding binding;
    private Adapter adapter;

    public static BettingListFragment newInstance() {
        Bundle args = new Bundle();

        BettingListFragment fragment = new BettingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return "BettingListFragment";
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentBettingListBinding.inflate(inflater, container, false);
        binding.list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        adapter = new Adapter();
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    public void showBetting(List<Betting> list) {
        adapter.replaceData(list);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<Betting> {

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, Betting betting) {
            holder.setText(R.id.title, betting.getTitle());
            FlowLayout layout = holder.getView(R.id.list);
            layout.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(layout.getContext());
            for (BettingItem item: betting.getItems()) {
                ListItemBettingItemBinding binding
                        = ListItemBettingItemBinding.inflate(inflater, layout, false);
                String odds = String.format(Locale.getDefault(), "%.2f", item.getOdds());
                binding.odds.setText(odds);
                binding.title.setText(item.getTitle());
                layout.addView(binding.getRoot());
            }
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_betting, parent, false);
            return new BaseRecyclerViewHolder(v);
        }
    }
}
