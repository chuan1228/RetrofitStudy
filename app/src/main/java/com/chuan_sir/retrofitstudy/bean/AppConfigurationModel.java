package com.chuan_sir.retrofitstudy.bean;

public class AppConfigurationModel {

    private long time;
    private String verName;
    private String verCode;
    private String cardTest;
    private String fingerTest;
    private String popTral;
    private String upload;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getCardTest() {
        return cardTest;
    }

    public void setCardTest(String cardTest) {
        this.cardTest = cardTest;
    }

    public String getFingerTest() {
        return fingerTest;
    }

    public void setFingerTest(String fingerTest) {
        this.fingerTest = fingerTest;
    }

    public String getPopTral() {
        return popTral;
    }

    public void setPopTral(String popTral) {
        this.popTral = popTral;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }
}
