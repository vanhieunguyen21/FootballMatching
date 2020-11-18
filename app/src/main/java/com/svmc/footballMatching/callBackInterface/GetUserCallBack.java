package com.svmc.footballMatching.callBackInterface;

import com.svmc.footballMatching.data.model.User;

public interface GetUserCallBack {
    void onSuccess(User user);
    void onFailure(String message);
}
