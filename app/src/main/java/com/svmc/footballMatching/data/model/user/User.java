package com.svmc.footballMatching.data.model.user;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.data.model.Photo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class User {
    protected String id;
    protected String email;
    protected String fullName;
    protected List<Photo> photos = new ArrayList<>();
    protected String address;
    protected Timestamp birthday;
    protected String phone;
    protected Timestamp joinedTimestamp;
    protected boolean accountActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getJoinedTimestamp() {
        return joinedTimestamp;
    }

    public void setJoinedTimestamp(Timestamp joinedTimestamp) {
        this.joinedTimestamp = joinedTimestamp;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public String getBirthdayString() {
        if (birthday == null) return "";
        Date birthdayDate = birthday.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(birthdayDate);
    }

    public String getJoinedDateString() {
        Date joinedDateDate = joinedTimestamp.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(joinedDateDate);
    }
}
