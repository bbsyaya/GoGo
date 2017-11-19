package com.scrat.gogo.module.race.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestOptions;
import com.scrat.gogo.R;
import com.scrat.gogo.data.model.Race;
import com.scrat.gogo.data.model.RaceGroupItem;
import com.scrat.gogo.databinding.FragmentRaceListBinding;
import com.scrat.gogo.databinding.ListItemRaceBinding;
import com.scrat.gogo.framework.common.BaseFragment;
import com.scrat.gogo.framework.common.BaseOnItemClickListener;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;
import com.scrat.gogo.framework.common.BaseRecyclerViewOnScrollListener;
import com.scrat.gogo.framework.glide.GlideApp;
import com.scrat.gogo.framework.glide.GlideRequests;
import com.scrat.gogo.framework.glide.GlideRoundTransform;
import com.scrat.gogo.module.race.betting.BettingActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by scrat on 2017/11/14.
 */

public class RaceListFragment extends BaseFragment implements RaceListContract.View {
    private FragmentRaceListBinding binding;
    private RaceListContract.Presenter presenter;
    private BaseRecyclerViewOnScrollListener loadMoreListener;
    private Adapter adapter;

    public static RaceListFragment newInstance() {
        Bundle args = new Bundle();
        RaceListFragment fragment = new RaceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return "RaceListFragment";
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentRaceListBinding.inflate(inflater, container, false);

        binding.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(true);
            }
        });
        binding.list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        loadMoreListener = new BaseRecyclerViewOnScrollListener(
                layoutManager, new BaseRecyclerViewOnScrollListener.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadData(false);
            }
        });
        binding.list.addOnScrollListener(loadMoreListener);
        GlideRequests glideRequests = GlideApp.with(this);
        adapter = new Adapter(glideRequests, new BaseOnItemClickListener<Race>() {
            @Override
            public void onItemClick(Race race) {
                BettingActivity.show(getActivity(), race);
            }
        });
        binding.list.setAdapter(adapter);

        new RaceListPresenter(this);
        presenter.loadData(true);

        return binding.getRoot();
    }

    @Override
    public void setPresenter(RaceListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingList() {
        showLoading();
    }

    @Override
    public void showListData(List<RaceGroupItem> list, boolean replace) {
        hideLoading();
        adapter.setData(list, replace);
    }

    @Override
    public void showNoMoreListData() {
        hideLoading();
    }

    @Override
    public void showEmptyList() {
        hideLoading();
    }

    @Override
    public void showLoadingListError(String e) {
        hideLoading();
        showMsg(e);
    }

    private void showLoading() {
        binding.srl.setRefreshing(true);
    }

    private void hideLoading() {
        binding.srl.setRefreshing(false);
        loadMoreListener.setLoading(false);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<RaceGroupItem> {
        private GlideRequests request;
        private SimpleDateFormat sdf;
        private BaseOnItemClickListener<Race> onItemClickListener;
        private RequestOptions options;

        private Adapter(GlideRequests requests, BaseOnItemClickListener<Race> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            request = requests;
            sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            options = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(4));
        }

        private String formatGroupTitle(String dt) {
            try {
                Date date = sdf.parse(dt);
                return DateFormat.format("M月d日", date).toString();
            } catch (ParseException e) {
                return dt;
            }
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, RaceGroupItem raceGroupItem) {
            LinearLayout layout = holder.getView(R.id.group_list);
            holder.setText(R.id.group_name, formatGroupTitle(raceGroupItem.getDt()));
            layout.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(layout.getContext());
            for (final Race race : raceGroupItem.getItems()) {
                ListItemRaceBinding binding = ListItemRaceBinding.inflate(inflater, layout, false);
                request.load(race.getTeamA().getLogo()).apply(options).into(binding.logoA);
                binding.date.setText(DateFormat.format("H:mm", race.getRaceTs()));
                request.load(race.getTeamB().getLogo()).apply(options).into(binding.logoB);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(race);
                    }
                });
                layout.addView(binding.getRoot());
            }

        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_group_item_race, parent, false);
            return new BaseRecyclerViewHolder(v);
        }
    }
}
