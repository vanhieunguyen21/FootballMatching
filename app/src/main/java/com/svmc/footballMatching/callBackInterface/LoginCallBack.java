package com.svmc.footballMatching.callBackInterface;

import com.svmc.footballMatching.data.model.User;

public interface LoginCallBack {
    void onSuccess(User user, int loginRequestCode);

    void onFailure(String message);
}
