package com.svmc.footballMatching.data.model;

public class Photo {
    private String id;
    private String photoUrl;
    private int showOrder;

    public Photo() {
    }

    public Photo(String id, String photoUrl, int showOrder) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.showOrder = showOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }
}
