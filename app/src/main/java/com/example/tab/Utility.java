package com.example.tab;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.tab.Login.ScanMessage;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Utility {

    static ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
    static String fabutopic=lastmessage.getFabuTopic().trim();
    static Integer username=lastmessage.getUserNum();
    static Integer mynum=lastmessage.getMyNum();
    static SharedPreferences prefs;

    public static Data handleDataResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            return new Gson().fromJson(String.valueOf(jsonObject),Data.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static  void requestData(){
        Integer Id=username*256+2;
        Integer Cmd=mynum*256+112;
        JSONArray jsonArray=new JSONArray();
        int P10=Utility.MySecret(65535,Id);
        int P11=Utility.MySecret(P10,Cmd);
        int P1=Utility.MySecret(P11,1);
        jsonArray.put(P1);
        jsonArray.put(1);
        MyMqttClient.sharedCenter().setSendData(
                //"/a1yPGkxyv1q/SimuApp/user/update",
                fabutopic,
                //"{\"method\":\"thing.event.property.post\",\"id\":\"1111\",\"params\":{\"Id\":1,\"Cmd\":112,\"Para\":[1]},\"version\":\"1.0.0\"}",
                Utility.SetCommandJson(Id,Cmd,jsonArray),
                0,
                false);
        Log.e("下拉刷新","已发送查询指令:"+ Utility.SetCommandJson(Id,Cmd,jsonArray));
    }
    public static  void szrequestData(){
        Integer Id=username*256+1;
        Integer Cmd=mynum*256+43;
        JSONArray jsonArray=new JSONArray();
        int P10=Utility.MySecret(65535,Id);
        int P11=Utility.MySecret(P10,Cmd);
        int P1=Utility.MySecret(P11,1);
        jsonArray.put(P1);
        jsonArray.put(1);
        MyMqttClient.sharedCenter().setSendData(
                fabutopic,
                Utility.SetCommandJson(Id,Cmd,jsonArray),
                0,
                false);
        Log.e("下拉刷新","已发送查询指令:"+ Utility.SetCommandJson(Id,Cmd,jsonArray));

    }
    public static  void xtrequestData(){
        Integer Id=username*256+1;
        Integer Cmd=mynum*256+42;
        JSONArray jsonArray=new JSONArray();
        int P10=Utility.MySecret(65535,Id);
        int P11=Utility.MySecret(P10,Cmd);
        int P1=Utility.MySecret(P11,1);
        jsonArray.put(P1);
        jsonArray.put(1);
        MyMqttClient.sharedCenter().setSendData(
            fabutopic,
            Utility.SetCommandJson(Id,Cmd,jsonArray),
            0,
            false);
        Log.e("下拉刷新","已发送查询指令:"+ Utility.SetCommandJson(Id,Cmd,jsonArray));
    }
    public static void BtnShow(final Button btn, final String BtnMsg, final String color){
        btn.setText(BtnMsg);
        btn.setTextColor(Color.parseColor(color));
    }
    //改变shape背景色
    public static void BtnBgShowColor(final Button btn, final String color){
        GradientDrawable gd = (GradientDrawable) btn.getBackground();
        gd.setColor(Color.parseColor(color));
    }
    //构建指令报文
    public static String CommandJson(Integer id,int cmd,int para){
        //创建JSON
        JSONObject jsonObject = new JSONObject();
        JSONObject object_1 = new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            jsonArray.put(para);
            object_1.put("Para",jsonArray);
            object_1.put("Id",id);
            object_1.put("Cmd",cmd);
            jsonObject.put("method", "thing.event.property.post");
            jsonObject.put("params",  object_1);
            jsonObject.put("version", "1.0.0");
        } catch (JSONException e){
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
    //构建指令报文
    public static String SetCommandJson(Integer id,Integer cmd,JSONArray jsonArray){
        //创建JSON
        JSONObject jsonObject = new JSONObject();
        JSONObject object_1 = new JSONObject();
        try {
            object_1.put("Para",jsonArray);
            object_1.put("Id",id);
            object_1.put("Cmd",cmd);
            jsonObject.put("method", "thing.event.property.post");
            jsonObject.put("params",  object_1);
            jsonObject.put("version", "1.0.0");
        } catch (JSONException e){
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
    /**
     * HMACSHA1加密
     *
     */
    public static String encryptHMAC(String signMethod,String content, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), signMethod);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] data = mac.doFinal(content.getBytes("utf-8"));
        return bytesToHexString(data);
    }

    public static final String bytesToHexString(byte[] bArray) {

        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    //构造校验字ChkWord
    public static int  MySecret(int Start, int Append) {
        int i,j;
        int ApdByt;

        Append <<= 4;
        for(i=0;i<3;i++){
            ApdByt = Append % 256;
            Append /= 256;
            Start = Start ^ ApdByt;
            for(j=0;j<8;j++){
                if((Start & 0x01)==1)
                    Start = (Start >> 1)^0xA001;
                else Start = Start >> 1;
            }
        }
        return(Start);
    }

}
