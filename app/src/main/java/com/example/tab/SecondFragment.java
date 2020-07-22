package com.example.tab;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tab.Login.ScanMessage;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static com.example.tab.Utility.BtnShow;
import static com.example.tab.Utility.fabutopic;

public class SecondFragment extends Fragment {
    private SwipeRefreshLayout swipeRefresh;
    private SharedPreferences prefs;
    private Data newdata;
    private Button jiaoban;
    private Button paosui;
    private Button xieliao;
    private Button yure;
    private Button honggan;
    private Button chuwei;
    private Button junzhong;
    private Button fuliao;
    private Button bengzhan;
    private Button shusong;
    private Button tuichu;
    private Button jieru;
    private Button fudai;
    private Button pingjiao;
    private Button baojing;
    private Button qingwu1;
    private Button qingwu2;
    private Button qingwu3;
    private Button qingwu4;
    private Button qingwu5;
    private Button qingwu6;
    private Button qingwu7;
    private Button qingwu8;
    private Button jiebodai;
    private Spinner spin_fajiao;
    private Spinner spin_juanshe;
    private String dataString;
    private boolean isGetData = false;
    private List<CharSequence> faeduList = null;
    private ArrayAdapter<CharSequence> faeduAdapter = null;
    private List<CharSequence> juaneduList = null;
    private ArrayAdapter<CharSequence> juaneduAdapter = null;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_second, null);
        swipeRefresh=view.findViewById(R.id.swipe_refresh2);
        jiaoban=view.findViewById(R.id.btn_jiaobanguan);
        paosui=view.findViewById(R.id.btn_posuiguan);
        xieliao=view.findViewById(R.id.btn_xieliaoguan);
        yure=view.findViewById(R.id.btn_yureguan);
        honggan=view.findViewById(R.id.btn_hongganguan);
        chuwei=view.findViewById(R.id.btn_chuweiguan);
        junzhong=view.findViewById(R.id.btn_junzhongguan);
        fuliao=view.findViewById(R.id.btn_fuliaoguan);
        bengzhan=view.findViewById(R.id.btn_bengzhanguan);
        shusong=view.findViewById(R.id.btn_shusongguan);
        tuichu=view.findViewById(R.id.btn_tuichuguan);
        jieru=view.findViewById(R.id.btn_jieruguan);
        fudai=view.findViewById(R.id.btn_fudaiguan);
        pingjiao=view.findViewById(R.id.btn_pingjiaoguan);
        baojing=view.findViewById(R.id.btn_baojingguan);
        qingwu1=view.findViewById(R.id.btn_1);
        qingwu2=view.findViewById(R.id.btn_2);
        qingwu3=view.findViewById(R.id.btn_3);
        qingwu4=view.findViewById(R.id.btn_4);
        qingwu5=view.findViewById(R.id.btn_5);
        qingwu6=view.findViewById(R.id.btn_6);
        qingwu7=view.findViewById(R.id.btn_7);
        qingwu8=view.findViewById(R.id.btn_8);
        jiebodai=view.findViewById(R.id.btn_9);
        spin_fajiao=view.findViewById(R.id.spin2);
        spin_juanshe=view.findViewById(R.id.spin3);
        //Topic
        ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
        String fabutopic=lastmessage.getFabuTopic().trim();
        //设置发酵罐spinner
        faeduList = new ArrayList<CharSequence>();
        int fanum=Integer.valueOf(lastmessage.getFanum().trim());
        for (int i=0;i<=fanum;i++){
            faeduList.add(String.valueOf(i));
        }
        faeduAdapter = new ArrayAdapter<CharSequence>(this.getActivity(),android.R.layout.simple_spinner_item,faeduList);
        faeduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_fajiao.setAdapter(faeduAdapter);
        //设置圈舍spinner
        juaneduList = new ArrayList<CharSequence>();
        int juannum=Integer.valueOf(lastmessage.getJuannum().trim());
        for (int i=0;i<=juannum;i++){
            juaneduList.add(String.valueOf(i));
        }
        juaneduAdapter = new ArrayAdapter<CharSequence>(this.getActivity(),android.R.layout.simple_spinner_item,juaneduList);
        juaneduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_juanshe.setAdapter(juaneduAdapter);

        //下拉刷新
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               Utility.requestData();//发送请求
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("手动数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                            showFjgDataInfo(newdata);
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                },500);
            }
        });
        //发酵罐控制部分按钮点击
        FjgSpiListener(spin_fajiao);
        FjgBtnListener(jiaoban,1);
        FjgBtnListener(xieliao,2);
        FjgBtnListener(paosui,3);
        FjgBtnListener(yure,4);
        FjgBtnListener(bengzhan,5);
        FjgBtnListener(chuwei,6);
        FjgBtnListener(honggan,7);
        FjgBtnListener(fuliao,9);
        FjgBtnListener(junzhong,10);
        FjgBtnListener(fudai,11);
        FjgBtnListener(pingjiao,12);
        FjgBtnListener(shusong,13);
        FjgBtnListener(jieru,14);
        FjgBtnListener(tuichu,15);
        FjgBtnListener(baojing,16);
        //圈舍控制部分按钮点击
        JsSpiListener(spin_juanshe);
        JsBtnListener(qingwu1,1);
        JsBtnListener(qingwu2,2);
        JsBtnListener(qingwu3,3);
        JsBtnListener(qingwu4,4);
        JsBtnListener(qingwu5,5);
        JsBtnListener(qingwu6,6);
        JsBtnListener(qingwu7,7);
        JsBtnListener(qingwu8,8);
        JsbtnJbdListener(jiebodai);
        return view;
    }
    //展示Data实体类中的数据
    //发酵罐控制部分数据展示
    private void showFjgDataInfo(Data newdata){
        spin_fajiao.setSelection(newdata.Id%256);
        //刷新Button
        if ((newdata.Para.get(5) & 1)==1){
            BtnShow(jiaoban,"搅拌关","#FF0000");


        }else {
            BtnShow(jiaoban,"搅拌开","#00AA44");
        }
        if ((newdata.Para.get(5) & 2)==2){
            BtnShow(xieliao,"卸料关","#FF0000");


        }else {
            BtnShow(xieliao,"卸料开","#00AA44");
        }
        if ((newdata.Para.get(5) & 4)==4){
            BtnShow(paosui,"破碎关","#FF0000");


        }else {
            BtnShow(paosui,"破碎开","#00AA44");
        }
        if ((newdata.Para.get(5) & 8)==8){
            BtnShow(yure,"预热关","#FF0000");


        }else {
            BtnShow(yure,"预热开","#00AA44");
        }
        if ((newdata.Para.get(5) & 16)==16){
            BtnShow(bengzhan,"泵站关","#FF0000");


        }else {
            BtnShow(bengzhan,"泵站开","#00AA44");
        }
        if ((newdata.Para.get(5) & 32)==32){
            BtnShow(chuwei,"除味关","#FF0000");


        }else {
            BtnShow(chuwei,"除味开","#00AA44");
        }
        if ((newdata.Para.get(5) & 64)==64){
            BtnShow(honggan,"烘干关","#FF0000");


        }else {
            BtnShow(honggan,"烘干开","#00AA44");
        }
        if ((newdata.Para.get(5) & 256)==256){
            BtnShow(fuliao,"辅料关","#FF0000");


        }else {
            BtnShow(fuliao,"辅料开","#00AA44");
        }
        if ((newdata.Para.get(5) & 512)==512){
            BtnShow(junzhong,"菌种关","#FF0000");


        }else {
            BtnShow(junzhong,"菌种开","#00AA44");
        }
        if ((newdata.Para.get(5) & 1024)==1024){
            BtnShow(fudai,"辅带关","#FF0000");


        }else {
            BtnShow(fudai,"辅带开","#00AA44");
        }
        if ((newdata.Para.get(5) & 2048)==2048){
            BtnShow(pingjiao,"平绞关","#FF0000");


        }else {
            BtnShow(pingjiao,"平绞开","#00AA44");
        }
        if ((newdata.Para.get(5) & 4096)==4096){
            BtnShow(shusong,"输送关","#FF0000");


        }else {
            BtnShow(shusong,"输送开","#00AA44");
        }
        if ((newdata.Para.get(5) & 8192)==8192){
            BtnShow(jieru,"接入关","#FF0000");


        }else {
            BtnShow(jieru,"接入开","#00AA44");
        }
        if ((newdata.Para.get(5) & 16384)==16384){
            BtnShow(tuichu,"退出关","#FF0000");


        }else {
            BtnShow(tuichu,"退出开","#00AA44");
        }
        if ((newdata.Para.get(5) & 32768)==32768){
            BtnShow(baojing,"报警关","#FF0000");


        }else {
            BtnShow(baojing,"报警开","#00AA44");
        }
    }
    //圈舍控制部分数据展示
    private void showJsDataInfo(Data newdata) {
        spin_juanshe.setSelection((newdata.Id - 16)%256);
        //刷新Button
        if ((newdata.Para.get(5) & 1)==1){
            BtnShow(qingwu1,"清污关","#FF0000");


        }else {
            BtnShow(qingwu1,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 2)==2){
            BtnShow(qingwu2,"清污关","#FF0000");


        }else {
            BtnShow(qingwu2,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 4)==4){
            BtnShow(qingwu3,"清污关","#FF0000");


        }else {
            BtnShow(qingwu3,"清污开","#00AA44");
        }
        if ((newdata.Para.get(9) & 8)==8){
            BtnShow(qingwu4,"清污关","#FF0000");


        }else {
            BtnShow(qingwu4,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 16)==16){
            BtnShow(qingwu5,"清污关","#FF0000");


        }else {
            BtnShow(qingwu5,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 32)==32){
            BtnShow(qingwu6,"清污关","#FF0000");


        }else {
            BtnShow(qingwu6,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 64)==64){
            BtnShow(qingwu7,"清污关","#FF0000");


        }else {
            BtnShow(qingwu7,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 128)==128){
            BtnShow(qingwu8,"清污关","#FF0000");


        }else {
            BtnShow(qingwu8,"清污开","#00AA44");
        }
        if ((newdata.Para.get(5) & 32768)==32768){
            BtnShow(jiebodai,"接驳带关","#FF0000");


        }else {
            BtnShow(jiebodai,"接驳带开","#00AA44");
        }
    }
    //发酵罐Spinner点击事件
    private void FjgSpiListener(final  Spinner spi){
        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                if (cardNumber.equals("0")) {
                    return;
                }
                Toast.makeText(getActivity(), "你正在操作发酵罐：" + cardNumber, Toast.LENGTH_SHORT).show();
                String inputx= spin_fajiao.getSelectedItem().toString();
                Log.e("手动设置发酵罐：",inputx);
                Integer x=Integer.parseInt(inputx);
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+x;
                Integer Cmd=1*256+112;
                JSONArray jsonArray=new JSONArray();
                int P10=Utility.MySecret(65535,Id);
                int P11=Utility.MySecret(P10,Cmd);
                int P1=Utility.MySecret(P11,1);
                jsonArray.put(P1);
                jsonArray.put(1);
                MyMqttClient.sharedCenter().setSendData(
                        //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                        //"/a1yPGkxyv1q/SimuApp/user/update",
                        //"/a1S917F388O/P:0001:01/user/update",
                        fabutopic,
                       // Utility.CommandJson(x,112,1),
                        Utility.SetCommandJson(Id,Cmd,jsonArray),
                        0,
                        false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("（手动）发酵罐Spinner返回数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                           // showFjgDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    //发酵罐控制部分按钮点击事件
    private void FjgBtnListener(final Button btn,final int para){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputx= spin_fajiao.getSelectedItem().toString();
                Integer x=Integer.parseInt(inputx);
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+x;
                Integer Cmd=1*256+67;
                JSONArray jsonArray=new JSONArray();
                int P10=Utility.MySecret(65535,Id);
                int P11=Utility.MySecret(P10,Cmd);
                int P1=Utility.MySecret(P11,para);
                jsonArray.put(P1);
                jsonArray.put(para);
                MyMqttClient.sharedCenter().setSendData(
                        //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                        //"/a1yPGkxyv1q/SimuApp/user/update",
                        //"/a1S917F388O/P:0001:01/user/update",
                        fabutopic,
                       // Utility.CommandJson(x,67,para),
                        Utility.SetCommandJson(Id,Cmd,jsonArray),
                        0,
                        false);
                Log.e("Btn","发酵罐已发送指令"+Utility.SetCommandJson(Id,Cmd,jsonArray));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("发酵罐按钮数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                          //  showFjgDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请先刷新界面或检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },500);
            }
        });
    }
    //圈舍Spinner点击事件
    private void JsSpiListener(final  Spinner spi){
        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                if (cardNumber.equals("0")) {
                    return;
                }
                Toast.makeText(getActivity(), "你正在操作圈舍：" + cardNumber, Toast.LENGTH_SHORT).show();
                String inputx= spin_juanshe.getSelectedItem().toString();
                Log.e("手动设置圈舍：",inputx);
                Integer z=Integer.parseInt(inputx);
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+z+16;
                Integer Cmd=1*256+112;
                JSONArray jsonArray=new JSONArray();
                int P10=Utility.MySecret(65535,Id);
                int P11=Utility.MySecret(P10,Cmd);
                int P1=Utility.MySecret(P11,1);
                jsonArray.put(P1);
                jsonArray.put(1);
                MyMqttClient.sharedCenter().setSendData(
                        //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                       // "/a1yPGkxyv1q/SimuApp/user/update",
                        //"/a1S917F388O/P:0001:01/user/update",
                        fabutopic,
                       // Utility.CommandJson(z+16,112,1),
                        Utility.SetCommandJson(Id,Cmd,jsonArray),
                        0,
                        false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("（手动）圈舍Spinner返回数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                           showJsDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }

                    }
                },500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    //圈舍控制部分按钮点击事件
    private void JsBtnListener(final Button btn,final int para){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputz= spin_juanshe.getSelectedItem().toString();
                Integer z=Integer.parseInt(inputz);
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+z+16;
                Integer Cmd=1*256+67;
                JSONArray jsonArray=new JSONArray();
                int P10=Utility.MySecret(65535,Id);
                int P11=Utility.MySecret(P10,Cmd);
                int P1=Utility.MySecret(P11,para);
                jsonArray.put(P1);
                jsonArray.put(para);
                MyMqttClient.sharedCenter().setSendData(
                        //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                       // "/a1yPGkxyv1q/SimuApp/user/update",
                       // "/a1S917F388O/P:0001:01/user/update",
                        fabutopic,
                       // Utility.CommandJson(z+16,67,para),
                        Utility.SetCommandJson(Id,Cmd,jsonArray),
                        0,
                        false);
                Log.e("Btn","圈舍已发送指令"+ Utility.SetCommandJson(Id,Cmd,jsonArray));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("圈舍按钮数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                         //   showJsDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请先刷新界面或检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },500);
            }
        });
    }
    //圈舍控制部分接驳带按钮点击事件
    private void JsbtnJbdListener(final Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+1;
                Integer Cmd=1*256+67;
                JSONArray jsonArray=new JSONArray();
                int P10=Utility.MySecret(65535,Id);
                int P11=Utility.MySecret(P10,Cmd);
                int P1=Utility.MySecret(P11,13);
                jsonArray.put(P1);
                jsonArray.put(13);
                MyMqttClient.sharedCenter().setSendData(
                //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                //"/a1yPGkxyv1q/SimuApp/user/update",
                //"/a1gZWTRWzGi/P:0001:01/user/update",
                fabutopic,
                //"{\"method\":\"thing.event.property.post\",\"id\":\"1111\",\"params\":{\"Id\":1,\"Cmd\":67,\"Para\":[13]},\"version\":\"1.0.0\"}",
                Utility.SetCommandJson(Id,Cmd,jsonArray),
                0,
                false);
                Log.e("Btn","圈舍已发送指令:"+Utility.SetCommandJson(Id,Cmd,jsonArray));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("圈舍按钮数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                            showJsDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请先刷新界面或检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }

                    }
                },500);
            }
        });

    }
}
