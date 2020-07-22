package com.example.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.tab.Login.ScanMessage;

import org.json.JSONArray;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static com.example.tab.Utility.BtnShow;

public class FristFragment extends Fragment {
    private Spinner  spinner;
    private TextView dangqianwendu;
    private TextView chenxuduan;
    private TextView yali;
    private TextView shengyushijian;
    private TextView yunxing;
    private TextView guzhang;
    private Button   shangliao;
    private Button   xieliao;
    private Button   fajiao;
    private Button   qingwu;
    private Button   zidong;
    private Button   zanting;
    private SwipeRefreshLayout swipeRefresh;
    private SharedPreferences prefs;
    private Data newdata;
    private String dataString;
    private static final int UPDATE_TEXT=1;
    private static Context mContext =null;

    private List<CharSequence> eduList = null;
    private ArrayAdapter<CharSequence> eduAdapter = null;
    private int num;
    private String fabutopic;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        View view=inflater.inflate(R.layout.fragment_frist, null);
        spinner=view.findViewById(R.id.Spi);
        dangqianwendu=view.findViewById(R.id.Tv_DangqiqnWendu);
        chenxuduan=view.findViewById(R.id.Tv_ChengXuDuan);
        yali=view.findViewById(R.id.TV_YaLi);
        shengyushijian=view.findViewById(R.id.Tv_ShengyuShijian);
        yunxing=view.findViewById(R.id.TV_Yunxing);
        guzhang=view.findViewById(R.id.Tv_GuZhang);
        shangliao=view.findViewById(R.id.btn2);
        xieliao=view.findViewById(R.id.btn3);
        fajiao= view.findViewById(R.id.btn4);
        qingwu=view.findViewById(R.id.btn5);
        zidong=view.findViewById(R.id.btn6);
        zanting=view.findViewById(R.id.btn7);
        swipeRefresh=view.findViewById(R.id.swipe_refresh);
        //Topic
        ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
        fabutopic=lastmessage.getFabuTopic().trim();
        //设置spinner
        eduList = new ArrayList<CharSequence>();
        num=Integer.valueOf(lastmessage.getFanum().trim());
        for (int i=0;i<=num;i++){
            eduList.add(String.valueOf(i));
        }
        eduAdapter = new ArrayAdapter<CharSequence>(this.getActivity(),android.R.layout.simple_spinner_item,eduList);
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(eduAdapter);
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
                        if (dataString !=""){
                            Log.e("自动数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                            showDataInfo(newdata);
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                },500);
            }
        });

        //按钮点击
        SpiListener(spinner);
        BtnListener1(shangliao,"启动上料",97,"停止上料",96);
        BtnListener1(xieliao,"启动卸料",99,"停止卸料",96);
        BtnListener1(fajiao,"启动发酵",49,"停止发酵",96);
        BtnListener1(qingwu,"启动清污",145,"停止清污",147);
        BtnListener2(zidong,"自动运行",161,160);
        BtnListener2(zanting,"暂停运行",162,161);

        return view;
    }
    //展示Data实体类中的数据
    private void showDataInfo(Data newdata){
        spinner.setSelection(newdata.Id%256);
        //刷新TextView
        chenxuduan.setText(newdata.Para.get(2).toString());
        shengyushijian.setText(newdata.Para.get(3).toString());
        dangqianwendu.setText(newdata.Para.get(6).toString());
        yunxing.setText(newdata.Para.get(1).toString());
        guzhang.setText(newdata.Para.get(10).toString());
        if ((newdata.Para.get(4) & 8)==8){
            yali.setText("正常");

        }else{
            yali.setText("欠压");
        }
        //刷新Button
        if ((newdata.Para.get(1) & 2)==2){
            BtnShow(xieliao,"停止卸料","#FF0000");


        }else {
            BtnShow(xieliao,"启动卸料","#00AA44");
        }

        if ((newdata.Para.get(1) & 1)==1){
            BtnShow(shangliao,"停止上料","#FF0000");


        }else {
            BtnShow(shangliao,"启动上料","#00AA44");
        }

        if ((newdata.Para.get(1) & 16)==16){
            BtnShow(fajiao,"停止发酵","#FF0000");


        }else if ((newdata.Para.get(1) & 32)==32){

            BtnShow(fajiao,"发酵完成","#FF6600");
        }else {
            BtnShow(fajiao,"启动发酵","#00AA44");
        }
        if ((newdata.Para.get(5) & 4096)==4096){
            BtnShow(qingwu,"停止清污","#FF0000");


        }else {
            BtnShow(qingwu,"启动清污","#00AA44");
        }
        if ((newdata.Para.get(1) & 16384)!=0){
            BtnShow(zidong,"停止运行","#FF0000");


        }else {
            BtnShow(zidong,"自动运行","#00AA44");
        }
        if ((newdata.Para.get(1) & 12288)!=0){
            BtnShow(zanting,"暂停恢复","#FF6600");


        }else {
            BtnShow(zanting,"暂停运行","#00AA44");
        }


    }
    //Spinner点击事件
    private void SpiListener(final  Spinner spi){
        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                if (cardNumber.equals("0")) {
                    return;
                }
                    Toast.makeText(getActivity(), "你正在操作发酵罐：" + cardNumber, Toast.LENGTH_SHORT).show();
                    String inputx= spinner.getSelectedItem().toString();
                    Log.e("设置发酵罐：",inputx);
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
                            //"/a1gZWTRWzGi/P:0001:01/user/update",
                            fabutopic,
                            Utility.SetCommandJson(Id,Cmd,jsonArray),
                            0,
                            false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //从SharedPreferences读取数据
                            prefs=getActivity().getSharedPreferences("datastore",0);
                            dataString=prefs.getString("data","");
                            if (dataString!=""){
                                Log.e("自动（Spinner）返回数据读取",dataString);
                                newdata=Utility.handleDataResponse(dataString);
                              //  showDataInfo(newdata);//刷新界面
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
    //按钮点击事件（上料、卸料、发酵、清污）
    private void BtnListener1 (final Button btn, final String function1, final int f1para,final String function2,final int f2parn){
        btn.setOnClickListener(new View.OnClickListener() {
            int para;
            @Override
            public void onClick(View v) {
                String inputx= spinner.getSelectedItem().toString();
                Log.e("选择发酵罐：",inputx);
                Integer x=Integer.parseInt(inputx);
                if (btn.getText().equals(function1)){
                    para=f1para;
                }else if (btn.getText().equals(function2)){para=f2parn;}
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+x;
                Integer Cmd=1*256+97;
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
                        //Utility.CommandJson(x,97,para),
                        Utility.SetCommandJson(Id,Cmd,jsonArray),
                        0,
                        false);
                Log.e("Btn","已发送指令"+Utility.SetCommandJson(Id,Cmd,jsonArray));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("自动（按钮1）数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                          //  showDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请先刷新界面或检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }

                    }
                },500);
            }
        });

    }
    //按钮点击事件(自动、暂停)
    private void BtnListener2(final Button btn, final String function, final int para1, final int para2){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+1;
                Integer Cmd=1*256+97;
                if (btn.getText().equals(function)) {
                    JSONArray jsonArray=new JSONArray();
                    int P10=Utility.MySecret(65535,Id);
                    int P11=Utility.MySecret(P10,Cmd);
                    int P1=Utility.MySecret(P11,para1);
                    jsonArray.put(P1);
                    jsonArray.put(para1);
                    MyMqttClient.sharedCenter().setSendData(
                            //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                            //"/a1S917F388O/P:0001:01/user/update",
                            fabutopic,
                            //Utility.CommandJson(1, 97,para1),
                            Utility.SetCommandJson(Id,Cmd,jsonArray),
                            0,
                            false);
                } else {
                    JSONArray jsonArray=new JSONArray();
                    int P10=Utility.MySecret(65535,Id);
                    int P11=Utility.MySecret(P10,Cmd);
                    int P1=Utility.MySecret(P11,para2);
                    jsonArray.put(P1);
                    jsonArray.put(para2);
                    MyMqttClient.sharedCenter().setSendData(
                            //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                            //"/a1yPGkxyv1q/SimuApp/user/update",
                            fabutopic,
                            //Utility.CommandJson(1, 97,para2),
                            Utility.SetCommandJson(Id,Cmd,jsonArray),
                            0,
                            false);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("自动（按钮2）数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                           // showDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请先刷新界面或检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }

                    }
                },500);
            }
        });
    }
    public static Handler handler0=new Handler(){
        public  void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT:
                    Toast.makeText(mContext,"已连接到服务器！",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
