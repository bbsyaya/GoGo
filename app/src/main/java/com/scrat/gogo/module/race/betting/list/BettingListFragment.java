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
import com.scrat.gogo.framework.common.BaseOnItemClickListener;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;
import java.util.Locale;

/**
 * Created by scrat on 2017/11/15.
 */

public class BettingListFragment extends BaseFragment implements BettingListContract.View {
    private FragmentBettingListBinding binding;
    private BettingPopupWindow popupWindow;
    private Adapter adapter;
    private BettingListContract.Presenter presenter;

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
        adapter = new Adapter(new BaseOnItemClickListener<BettingItem>() {
            @Override
            public void onItemClick(BettingItem bettingItem) {
                presenter.loadCoinInfo();
                popupWindow.showBettingItem(binding.list, bettingItem);
            }
        });
        binding.list.setAdapter(adapter);
        popupWindow = new BettingPopupWindow(
                getContext(), new BettingPopupWindow.OnBettingClickListener() {
            @Override
            public void onBettingClick(BettingItem item, int coin) {
                presenter.betting(item.getBettingItemId(), coin);
            }
        });

        new BettingListPresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        super.onDestroy();
    }

    public void showBetting(List<Betting> list) {
        adapter.replaceData(list);
    }

    @Override
    public void setPresenter(BettingListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showBettingExecuting() {

    }

    @Override
    public void showBettingExecuteSuccess() {
        popupWindow.dismiss();
        showToast("参与成功");
        // TODO
    }

    @Override
    public void showBettingExecuteError(String e) {
        popupWindow.dismiss();
        showMsg(e);
    }

    @Override
    public void showLoadingCoin() {
        if (isFinish()) {
            return;
        }

        popupWindow.showUserCoinLoading();
    }

    @Override
    public void showLoadingCoinError(String e) {

    }

    @Override
    public void showUserCoin(long coin) {
        if (isFinish()) {
            return;
        }

        popupWindow.showUserCoin(coin);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<Betting> {
        private BaseOnItemClickListener<BettingItem> onItemClickListener;

        private Adapter(BaseOnItemClickListener<BettingItem> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, Betting betting) {
            holder.setText(R.id.title, betting.getTitle());
            FlowLayout layout = holder.getView(R.id.list);
            layout.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(layout.getContext());
            for (final BettingItem item : betting.getItems()) {
                ListItemBettingItemBinding binding
                        = ListItemBettingItemBinding.inflate(inflater, layout, false);
                String odds = String.format(Locale.getDefault(), "%.2f", item.getOdds());
                binding.odds.setText(odds);
                binding.title.setText(item.getTitle());
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(item);
                    }
                });
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
