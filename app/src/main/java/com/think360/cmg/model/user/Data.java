package com.think360.cmg.model.user;

/**
 * Created by think360 on 20/04/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("worker_id")
    @Expose
    private String workerId;
    @SerializedName("first_name")
    @Expose
    private String firstName;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public class User {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }
}


