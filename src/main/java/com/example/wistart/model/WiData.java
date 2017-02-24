package com.example.wistart.model;

import java.util.Date;
import java.util.List;

/**
 * Created by pdomokos on 2/15/17.
 */
public class WiData {

    long id;
    int interval;
    int[] userIds;
    Date dateInterest;
    List<List<Object>> oneDayWiData;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int[] getUserIds() {
        return userIds;
    }

    public void setUserIds(int[] userIds) {
        this.userIds = userIds;
    }

    public Date getDateInterest() {
        return dateInterest;
    }

    public void setDateInterest(Date dateInterest) {
        this.dateInterest = dateInterest;
    }

    public List<List<Object>> getOneDayWiData() {
        return oneDayWiData;
    }

    public void setOneDayWiData(List<List<Object>> oneDayWiData) {
        this.oneDayWiData = oneDayWiData;
    }
}
