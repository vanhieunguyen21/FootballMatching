package com.svmc.footballMatching.callBackInterface;

import com.svmc.footballMatching.data.enumeration.Result;

public interface RegisterCallBack {
    public void onSuccess();

    public void onFailure(String message);
}
