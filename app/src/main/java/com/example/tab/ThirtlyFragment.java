package com.example.tab;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONArray;

import static com.example.tab.Utility.BtnBgShowColor;

public class ThirtlyFragment extends Fragment {
    private SwipeRefreshLayout swipeRefresh;
    private SharedPreferences prefs;
    private boolean isInitial=true;
    private boolean isInitial2=true;
    private Data newdata;
    private Button i1;
    private Button i2;
    private Button i3;
    private Button i4;
    private Button i5;
    private Button i6;
    private Button i7;
    private Button i8;
    private Button O1;
    private Button O2;
    private Button O3;
    private Button O4;
    private Button O5;
    private Button O6;
    private Button O7;
    private Button O8;
    private Button O9;
    private Button O10;
    private Button O11;
    private Button O12;
    private Button O13;
    private Button O14;
    private Button O15;
    private Button O16;
    private TextView chengzhong;
    private TextView chanliang;
    private TextView zhuangtai;
    private TextView cuowu;
    private Spinner spin_fjg;
    private Spinner spin_js;
    private  String dataString;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thirtly, null);
        swipeRefresh=view.findViewById(R.id.swipe_refresh3);
        spin_fjg=view.findViewById(R.id.spin4);
        spin_js=view.findViewById(R.id.spin5);
        i1=view.findViewById(R.id.btn_I1);
        i2=view.findViewById(R.id.btn_I2);
        i3=view.findViewById(R.id.btn_I3);
        i4=view.findViewById(R.id.btn_I4);
        i5=view.findViewById(R.id.btn_I5);
        i6=view.findViewById(R.id.btn_I6);
        i7=view.findViewById(R.id.btn_I7);
        i8=view.findViewById(R.id.btn_I8);
        O1=view.findViewById(R.id.btn01);
        O2=view.findViewById(R.id.btn02);
        O3=view.findViewById(R.id.btn03);
        O4=view.findViewById(R.id.btn04);
        O5=view.findViewById(R.id.btn05);
        O6=view.findViewById(R.id.btn06);
        O7=view.findViewById(R.id.btn07);
        O8=view.findViewById(R.id.btn08);
        O9=view.findViewById(R.id.btn09);
        O10=view.findViewById(R.id.btn010);
        O11=view.findViewById(R.id.btn011);
        O12=view.findViewById(R.id.btn012);
        O13=view.findViewById(R.id.btn013);
        O14=view.findViewById(R.id.btn014);
        O15=view.findViewById(R.id.btn015);
        O16=view.findViewById(R.id.btn016);
        chengzhong=view.findViewById(R.id.tv_chengzhong);
        chanliang=view.findViewById(R.id.tv_chanliang);
        zhuangtai=view.findViewById(R.id.tv_zhuangtai);
        cuowu=view.findViewById(R.id.tv_cuowu);
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
                            Log.e("状态-数据读取",dataString);
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
        //发酵罐控制
        FjgSpiListener(spin_fjg);
        //圈舍控制
        JsSpiListener(spin_js);
        return view;
    }
    //展示Data实体类中的数据
    private void showDataInfo(Data newdata){
        //if (newdata.Id<=12){
//        ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//        Integer username=lastmessage.getUserNum();
        if (newdata.Id<=(12+256)){
            spin_fjg.setSelection(newdata.Id%256);
            spin_js.setSelection(0);
        }else {
            spin_fjg.setSelection(0);
            spin_js.setSelection((newdata.Id-16)%256);
        }
        //刷新TextView
        chengzhong.setText(newdata.Para.get(8).toString());
        chanliang.setText(newdata.Para.get(12).toString());
        zhuangtai.setText(newdata.Para.get(1).toString());
        cuowu.setText(newdata.Para.get(10).toString());
        //刷新Button
        if ((newdata.Para.get(5) & 1)==1){
            BtnBgShowColor(O1,"#FF0000");
        }else {
            BtnBgShowColor(O1,"#00AA44");
        }
        if ((newdata.Para.get(5) & 2)==2){
            BtnBgShowColor(O2,"#FF0000");
        }else {
            BtnBgShowColor(O2,"#00AA44");
        }
        if ((newdata.Para.get(5) & 4)==4){
            BtnBgShowColor(O3,"#FF0000");
        }else {
            BtnBgShowColor(O3,"#00AA44");
        }
        if ((newdata.Para.get(5) & 8)==8){
            BtnBgShowColor(O4,"#FF0000");
        }else {
            BtnBgShowColor(O4,"#00AA44");
        }
        if ((newdata.Para.get(5) & 16)==16){
            BtnBgShowColor(O5,"#FF0000");
        }else {
            BtnBgShowColor(O5,"#00AA44");
        }
        if ((newdata.Para.get(5) & 32)==32){
            BtnBgShowColor(O6,"#FF0000");
        }else {
            BtnBgShowColor(O6,"#00AA44");
        }
        if ((newdata.Para.get(5) & 64)==64){
            BtnBgShowColor(O7,"#FF0000");
        }else {
            BtnBgShowColor(O7,"#00AA44");
        }
        if ((newdata.Para.get(5) & 128)==128){
            BtnBgShowColor(O8,"#FF0000");
        }else {
            BtnBgShowColor(O8,"#00AA44");
        }
        if ((newdata.Para.get(5) & 256)==256){
            BtnBgShowColor(O9,"#FF0000");
        }else {
            BtnBgShowColor(O9,"#00AA44");
        }
        if ((newdata.Para.get(5) & 512)==512){
            BtnBgShowColor(O10,"#FF0000");
        }else {
            BtnBgShowColor(O10,"#00AA44");
        }
        if ((newdata.Para.get(5) & 1024)==1024){
            BtnBgShowColor(O11,"#FF0000");
        }else {
            BtnBgShowColor(O11,"#00AA44");
        }
        if ((newdata.Para.get(5) & 2048)==2048){
            BtnBgShowColor(O12,"#FF0000");
        }else {
            BtnBgShowColor(O12,"#00AA44");
        }
        if ((newdata.Para.get(5) & 4096)==4096){
            BtnBgShowColor(O13,"#FF0000");
        }else {
            BtnBgShowColor(O13,"#00AA44");
        }
        if ((newdata.Para.get(5) & 8192)==8192){
            BtnBgShowColor(O14,"#FF0000");
        }else {
            BtnBgShowColor(O14,"#00AA44");
        }
        if ((newdata.Para.get(5) & 16384)==16384){
            BtnBgShowColor(O15,"#FF0000");
        }else {
            BtnBgShowColor(O15,"#00AA44");
        }
        if ((newdata.Para.get(5) & 32768)==32768){
            BtnBgShowColor(O16,"#FF0000");
        }else {
            BtnBgShowColor(O16,"#00AA44");
        }
        if ((newdata.Para.get(4) & 1)==1){
            BtnBgShowColor(i1,"#FF0000");
        }else {
            BtnBgShowColor(i1,"#00AA44");
        }
        if ((newdata.Para.get(4) & 2)==2){
            BtnBgShowColor(i2,"#FF0000");
        }else {
            BtnBgShowColor(i2,"#00AA44");
        }
        if ((newdata.Para.get(4) & 4)==4){
            BtnBgShowColor(i3,"#FF0000");
        }else {
            BtnBgShowColor(i3,"#00AA44");
        }
        if ((newdata.Para.get(4) & 8)==8){
            BtnBgShowColor(i4,"#FF0000");
        }else {
            BtnBgShowColor(i4,"#00AA44");
        }
        if ((newdata.Para.get(4) & 16)==16){
            BtnBgShowColor(i5,"#FF0000");
        }else {
            BtnBgShowColor(i5,"#00AA44");
        }
        if ((newdata.Para.get(4) & 32)==32){
            BtnBgShowColor(i6,"#FF0000");
        }else {
            BtnBgShowColor(i6,"#00AA44");
        }
        if ((newdata.Para.get(4) & 64)==64){
            BtnBgShowColor(i7,"#FF0000");
        }else {
            BtnBgShowColor(i7,"#00AA44");
        }
        if ((newdata.Para.get(4) & 128)==128){
            BtnBgShowColor(i8,"#FF0000");
        }else {
            BtnBgShowColor(i8,"#00AA44");
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
                String inputx= spin_fjg.getSelectedItem().toString();
                Log.e("状态-设置发酵罐：",inputx);
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
                        "/a1S917F388O/P:0001:01/user/update",
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
                            Log.e("（状态）发酵罐Spinner返回数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                            showDataInfo(newdata);//刷新界面
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
    //圈舍Spinner点击事件
    private void JsSpiListener(final  Spinner spi){
        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, final long id) {
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                if (cardNumber.equals("0")) {
                    return;
                }
                Toast.makeText(getActivity(), "你正在操作圈舍：" + cardNumber, Toast.LENGTH_SHORT).show();
                String inputx= spin_js.getSelectedItem().toString();
                Log.e("状态-设置圈舍：",inputx);
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
                       //"/a1yPGkxyv1q/SimuApp/user/update",
                        "/a1S917F388O/P:0001:01/user/update",
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
                            Log.e("状态-圈舍Spinner返回数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                            showDataInfo(newdata);//刷新界面
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
}
