package com.example.tab.Login;

import org.litepal.crud.LitePalSupport;

public class ScanMessage extends LitePalSupport {
    private int id;
    private String  ProductKey;
    private String  DeviceNam;
    private String  DeviceSecret;
    private String  DingyueTopic;
    private String  FabuTopic;
    private String  Fanum;
    private String  Juannum;
    private int     UserNum;
    private int     MyNum;

    public int getUserNum() {
        return UserNum;
    }

    public void setUserNum(int userNum) {
        UserNum = userNum;
    }

    public int getMyNum() {
        return MyNum;
    }

    public void setMyNum(int myNum) {
        MyNum = myNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductKey() {
        return ProductKey;
    }

    public void setProductKey(String productKey) {
        ProductKey = productKey;
    }

    public String getDeviceNam() {
        return DeviceNam;
    }

    public void setDeviceNam(String deviceNam) {
        DeviceNam = deviceNam;
    }

    public String getDeviceSecret() {
        return DeviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        DeviceSecret = deviceSecret;
    }

    public String getFanum() {
        return Fanum;
    }

    public void setFanum(String fanum) {
        Fanum = fanum;
    }

    public String getJuannum() {
        return Juannum;
    }

    public void setJuannum(String juannum) {
        Juannum = juannum;
    }

    public String getDingyueTopic() {
        return DingyueTopic;
    }

    public void setDingyueTopic(String dingyueTopic) {
        DingyueTopic = dingyueTopic;
    }

    public String getFabuTopic() {
        return FabuTopic;
    }

    public void setFabuTopic(String fabuTopic) {
        FabuTopic = fabuTopic;
    }
}
