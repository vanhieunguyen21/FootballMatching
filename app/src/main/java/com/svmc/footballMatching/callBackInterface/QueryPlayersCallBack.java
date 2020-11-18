package com.svmc.footballMatching.callBackInterface;

import com.svmc.footballMatching.data.model.User;

import java.util.List;

public interface QueryPlayersCallBack {
    void onSuccess(List<User> players);
    void onFailure(String message);
}
