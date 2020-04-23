package com.example.tab;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.logging.Logger;

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
    private   SharedPreferences prefs;
    private boolean b=true;
    private boolean isInitial=true;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        //下拉刷新
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Utility.requestData();
            }
        });
        //刷新界面
        MyMqttClient.sharedCenter().setOnServerReadStringCallback(new MyMqttClient.OnServerReadStringCallback() {
            @Override//Topic:主题  Msg.toString():接收的消息  MsgByte:16进制消息
            public void callback(String Topic, final MqttMessage Msg, byte[] MsgByte) {
                Log.e("收到新数据："," Msg"+Msg.toString() );
//                //将数据存储到SharedPreferences中
//                 prefs=getActivity().getSharedPreferences("datastore",0);
//                SharedPreferences.Editor edit=prefs.edit();
//                edit.putString("data",Msg.toString());
//                edit.commit();
//                //从SharedPreferences读取数据
//                 prefs=getActivity().getSharedPreferences("datastore",0);
//                String dataString=prefs.getString("data","");
//                Log.e("数据存储",dataString);
//                final Data newdata=Utility.handleDataResponse(dataString);
                final Data newdata=Utility.handleDataResponse(Msg.toString());
                if (newdata!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDataInfo(newdata);
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "数据请求失败！" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        //按钮点击
        SpiListener(spinner);
        BtnListener1(shangliao,"启动上料",97,"停止上料",96);
        BtnListener1(xieliao,"启动卸料",99,"停止卸料",96);
        BtnListener1(qingwu,"启动发酵",49,"停止发酵",96);
        BtnListener1(qingwu,"启动清污",145,"停止清污",147);
        BtnListener2(zidong,"自动运行",161,160);
        BtnListener2(zanting,"暂停运行",162,161);

        return view;
    }
    //展示Data实体类中的数据
    private void showDataInfo(Data newdata){
        spinner.setSelection(newdata.Id-1);
        //刷新TextView
        chenxuduan.setText(newdata.Para.get(1).toString());
        shengyushijian.setText(newdata.Para.get(2).toString());
        dangqianwendu.setText(newdata.Para.get(5).toString());
        yunxing.setText(newdata.Para.get(0).toString());
        guzhang.setText(newdata.Para.get(9).toString());
        if ((newdata.Para.get(3) & 8)==8){
            yali.setText("正常");

        }else{
            yali.setText("欠压");
        }
        //刷新Button
        if ((newdata.Para.get(0) & 2)==2){
            BtnShow(xieliao,"停止卸料","#FF0000");


        }else {
            BtnShow(xieliao,"启动卸料","#00AA44");
        }

        if ((newdata.Para.get(0) & 1)==1){
            BtnShow(shangliao,"停止上料","#FF0000");


        }else {
            BtnShow(shangliao,"启动上料","#00AA44");
        }

        if ((newdata.Para.get(0) & 16)==16){
            BtnShow(fajiao,"停止发酵","#FF0000");


        }else if ((newdata.Para.get(0) & 32)==32){

            BtnShow(fajiao,"发酵完成","#FF6600");
        }else {
            BtnShow(fajiao,"启动发酵","#00AA44");
        }
        if ((newdata.Para.get(4) & 4096)==4096){
            BtnShow(qingwu,"停止清污","#FF0000");


        }else {
            BtnShow(qingwu,"启动清污","#00AA44");
        }
        if ((newdata.Para.get(0) & 16384)!=0){
            BtnShow(zidong,"停止运行","#FF0000");


        }else {
            BtnShow(zidong,"自动运行","#00AA44");
        }
        if ((newdata.Para.get(0) & 12288)!=0){
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
                if (isInitial) {
                    isInitial = false;
                    return;
                }
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                    Toast.makeText(getActivity(), "你正在操作发酵罐：" + cardNumber, Toast.LENGTH_SHORT).show();
                    String inputx= spinner.getSelectedItem().toString();
                    Log.e("设置发酵罐：",inputx);
                    Integer x=Integer.parseInt(inputx);
                    MyMqttClient.sharedCenter().setSendData(
                           "/sys/a1S917F388O/wenxin/thing/event/property/post",
                            //"/a1yPGkxyv1q/SimuApp/user/update",
                            Utility.CommandJson(x,112,1),
                            0,
                            false);
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

                MyMqttClient.sharedCenter().setSendData(
                        "/sys/a1S917F388O/wenxin/thing/event/property/post",
                        //"/a1yPGkxyv1q/SimuApp/user/update",
                        Utility.CommandJson(x,97,para),
                        0,
                        false);
                Log.e("Btn","已发送指令"+ Utility.CommandJson(x,97,para));
            }
        });

    }
    //按钮点击事件(自动、暂停)
    private void BtnListener2(final Button btn, final String function, final int para1, final int para2){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().equals(function)) {
                    MyMqttClient.sharedCenter().setSendData(
                            "/sys/a1S917F388O/wenxin/thing/event/property/post",
                            //"/a1yPGkxyv1q/SimuApp/user/update",
                            Utility.CommandJson(1, 97,para1),
                            0,
                            false);
                } else {
                    MyMqttClient.sharedCenter().setSendData(
                            "/sys/a1S917F388O/wenxin/thing/event/property/post",
                            //"/a1yPGkxyv1q/SimuApp/user/update",
                            Utility.CommandJson(1, 97,para2),
                            0,
                            false);
                }
            }
        });
    }


}
