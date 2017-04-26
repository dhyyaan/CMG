package com.think360.cmg.model.user;

/**
 * Created by think360 on 20/04/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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


    public class Data {

        @SerializedName("worker_id")
        @Expose
        private Integer workerId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("worker_name")
        @Expose
        private String workerName;
        @SerializedName("worker_pic")
        @Expose
        private String workerPic;

        public Integer getWorkerId() {
            return workerId;
        }

        public void setWorkerId(Integer workerId) {
            this.workerId = workerId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public String getWorkerPic() {
            return workerPic;
        }

        public void setWorkerPic(String workerPic) {
            this.workerPic = workerPic;
        }

    }
}


