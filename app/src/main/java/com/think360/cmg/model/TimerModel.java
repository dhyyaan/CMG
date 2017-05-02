package com.think360.cmg.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.think360.cmg.db.AppDatabase;

/**
 * Created by think360 on 27/04/17.
 */

@Table(database = AppDatabase.class)
public class TimerModel {


    @PrimaryKey(autoincrement = true)
    long id;


    @Column(defaultValue = "0")
    int projectID;

    @Column(defaultValue = "0")
    private long sytemTimeAtPause;

    @Column(defaultValue = "0")
    private int systemTimeAtResume;

    @Column(defaultValue = "false")
    private boolean isTimerRunning;


    @Column(defaultValue = "0")
    private String gmtZone;


    public TimerModel() {
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public long getSytemTimeAtPause() {
        return sytemTimeAtPause;
    }

    public void setSytemTimeAtPause(long sytemTimeAtPause) {
        this.sytemTimeAtPause = sytemTimeAtPause;
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        isTimerRunning = timerRunning;
    }

    public int getSystemTimeAtResume() {
        return systemTimeAtResume;
    }

    public void setSystemTimeAtResume(int systemTimeAtResume) {
        this.systemTimeAtResume = systemTimeAtResume;
    }

    public String getGmtZone() {
        return gmtZone;
    }

    public void setGmtZone(String gmtZone) {
        this.gmtZone = gmtZone;
    }

}
