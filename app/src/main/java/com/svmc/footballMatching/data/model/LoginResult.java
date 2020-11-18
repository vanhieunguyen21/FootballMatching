package com.svmc.footballMatching.data.model;

import com.svmc.footballMatching.data.enumeration.Result;

public class LoginResult {
    int loginRequestCode;
    private Result result;
    private User user;

    public LoginResult(int loginRequestCode, Result result, User user) {
        this.loginRequestCode = loginRequestCode;
        this.result = result;
        this.user = user;
    }

    public int getLoginRequestCode() {
        return loginRequestCode;
    }

    public Result getResult() {
        return result;
    }

    public User getUser() {
        return user;
    }
}
