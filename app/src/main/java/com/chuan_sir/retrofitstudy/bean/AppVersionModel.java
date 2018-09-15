package com.chuan_sir.retrofitstudy.bean;

public class AppVersionModel {


    /**
     * versize :
     * verInfo :
     * verName : 1.0
     * verCode : 1
     */

    private String versize;
    private String verInfo;
    private String verName;
    private String verCode;

    public String getVersize() {
        return versize;
    }

    public void setVersize(String versize) {
        this.versize = versize;
    }

    public String getVerInfo() {
        return verInfo;
    }

    public void setVerInfo(String verInfo) {
        this.verInfo = verInfo;
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

    @Override
    public String toString() {
        return "AppVersionModel{" +
                "versize='" + versize + '\'' +
                ", verInfo='" + verInfo + '\'' +
                ", verName='" + verName + '\'' +
                ", verCode='" + verCode + '\'' +
                '}';
    }
}
