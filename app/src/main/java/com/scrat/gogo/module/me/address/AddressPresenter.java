package com.scrat.gogo.module.me.address;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.Address;

/**
 * Created by scrat on 2017/11/17.
 */

public class AddressPresenter implements AddressContract.Presenter {
    private AddressContract.View view;

    public AddressPresenter(AddressContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadAddress() {
        view.showLoadingAddress();
        DataRepository.getInstance().getApi().getAddress(
                new DefaultLoadObjCallback<Address, Res.AddressRes>() {
                    @Override
                    protected void onSuccess(Address address) {
                        view.showAddress(address);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadingAddressError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.AddressRes> getResClass() {
                        return Res.AddressRes.class;
                    }
                });
    }

    @Override
    public void updateAddress(String receiver, String tel, String location, String detail) {
        view.showUpdatingAddress();
        DataRepository.getInstance().getApi().updateAddress(
                receiver,
                tel,
                location,
                detail,
                new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                    @Override
                    protected void onSuccess(String s) {
                        view.showUpdateAddressSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showUpdateAddressError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.DefaultStrRes> getResClass() {
                        return Res.DefaultStrRes.class;
                    }
                });
    }
}
