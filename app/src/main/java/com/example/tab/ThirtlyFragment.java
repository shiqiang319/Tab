package com.example.tab;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private  boolean js_tag=false;
    private  boolean fjg_tag=false;

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
                Log.e("下拉刷新","状态—已发送查询指令!");
                //从SharedPreferences读取数据
                prefs=getActivity().getSharedPreferences("datastore",0);
                String dataString=prefs.getString("data","");
                Log.e("状态-数据读取",dataString);
                newdata=Utility.handleDataResponse(dataString);
                showDataInfo(newdata);
                swipeRefresh.setRefreshing(false);
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
        if (newdata.Id<=12){
            spin_fjg.setSelection(newdata.Id);
            spin_js.setSelection(0);
            js_tag=true;
        }else {
            spin_fjg.setSelection(0);
            fjg_tag=true;
            spin_js.setSelection(newdata.Id-16);
        }
        //刷新TextView
        chengzhong.setText(newdata.Para.get(7).toString());
        chanliang.setText(newdata.Para.get(11).toString());
        zhuangtai.setText(newdata.Para.get(0).toString());
        cuowu.setText(newdata.Para.get(9).toString());
        //刷新Button
        if ((newdata.Para.get(4) & 1)==1){
            BtnBgShowColor(O1,"#FF0000");
        }else {
            BtnBgShowColor(O1,"#00AA44");
        }
        if ((newdata.Para.get(4) & 2)==2){
            BtnBgShowColor(O2,"#FF0000");
        }else {
            BtnBgShowColor(O2,"#00AA44");
        }
        if ((newdata.Para.get(4) & 4)==4){
            BtnBgShowColor(O3,"#FF0000");
        }else {
            BtnBgShowColor(O3,"#00AA44");
        }
        if ((newdata.Para.get(4) & 8)==8){
            BtnBgShowColor(O4,"#FF0000");
        }else {
            BtnBgShowColor(O4,"#00AA44");
        }
        if ((newdata.Para.get(4) & 16)==16){
            BtnBgShowColor(O5,"#FF0000");
        }else {
            BtnBgShowColor(O5,"#00AA44");
        }
        if ((newdata.Para.get(4) & 32)==32){
            BtnBgShowColor(O6,"#FF0000");
        }else {
            BtnBgShowColor(O6,"#00AA44");
        }
        if ((newdata.Para.get(4) & 64)==64){
            BtnBgShowColor(O7,"#FF0000");
        }else {
            BtnBgShowColor(O7,"#00AA44");
        }
        if ((newdata.Para.get(4) & 128)==128){
            BtnBgShowColor(O8,"#FF0000");
        }else {
            BtnBgShowColor(O8,"#00AA44");
        }
        if ((newdata.Para.get(4) & 256)==256){
            BtnBgShowColor(O9,"#FF0000");
        }else {
            BtnBgShowColor(O9,"#00AA44");
        }
        if ((newdata.Para.get(4) & 512)==512){
            BtnBgShowColor(O10,"#FF0000");
        }else {
            BtnBgShowColor(O10,"#00AA44");
        }
        if ((newdata.Para.get(4) & 1024)==1024){
            BtnBgShowColor(O11,"#FF0000");
        }else {
            BtnBgShowColor(O11,"#00AA44");
        }
        if ((newdata.Para.get(4) & 2048)==2048){
            BtnBgShowColor(O12,"#FF0000");
        }else {
            BtnBgShowColor(O12,"#00AA44");
        }
        if ((newdata.Para.get(4) & 4096)==4096){
            BtnBgShowColor(O13,"#FF0000");
        }else {
            BtnBgShowColor(O13,"#00AA44");
        }
        if ((newdata.Para.get(4) & 8192)==8192){
            BtnBgShowColor(O14,"#FF0000");
        }else {
            BtnBgShowColor(O14,"#00AA44");
        }
        if ((newdata.Para.get(4) & 16384)==16384){
            BtnBgShowColor(O15,"#FF0000");
        }else {
            BtnBgShowColor(O15,"#00AA44");
        }
        if ((newdata.Para.get(4) & 32768)==32768){
            BtnBgShowColor(O16,"#FF0000");
        }else {
            BtnBgShowColor(O16,"#00AA44");
        }
        if ((newdata.Para.get(3) & 1)==1){
            BtnBgShowColor(i1,"#FF0000");
        }else {
            BtnBgShowColor(i1,"#00AA44");
        }
        if ((newdata.Para.get(3) & 2)==2){
            BtnBgShowColor(i2,"#FF0000");
        }else {
            BtnBgShowColor(i2,"#00AA44");
        }
        if ((newdata.Para.get(3) & 4)==4){
            BtnBgShowColor(i3,"#FF0000");
        }else {
            BtnBgShowColor(i3,"#00AA44");
        }
        if ((newdata.Para.get(3) & 8)==8){
            BtnBgShowColor(i4,"#FF0000");
        }else {
            BtnBgShowColor(i4,"#00AA44");
        }
        if ((newdata.Para.get(3) & 16)==16){
            BtnBgShowColor(i5,"#FF0000");
        }else {
            BtnBgShowColor(i5,"#00AA44");
        }
        if ((newdata.Para.get(3) & 32)==32){
            BtnBgShowColor(i6,"#FF0000");
        }else {
            BtnBgShowColor(i6,"#00AA44");
        }
        if ((newdata.Para.get(3) & 64)==64){
            BtnBgShowColor(i7,"#FF0000");
        }else {
            BtnBgShowColor(i7,"#00AA44");
        }
        if ((newdata.Para.get(3) & 128)==128){
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
                if (isInitial||fjg_tag) {
                    isInitial = false;
                    fjg_tag=false;
                    return;
                }
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                Toast.makeText(getActivity(), "你正在操作发酵罐：" + cardNumber, Toast.LENGTH_SHORT).show();
                String inputx= spin_fjg.getSelectedItem().toString();
                Log.e("状态-设置发酵罐：",inputx);
                Integer x=Integer.parseInt(inputx);
                MyMqttClient.sharedCenter().setSendData(
                        "/sys/a1S917F388O/wenxin/thing/event/property/post",
                        //"/a1yPGkxyv1q/SimuApp/user/update",
                        Utility.CommandJson(x,112,1),
                        0,
                        false);
                //从SharedPreferences读取数据
                prefs=getActivity().getSharedPreferences("datastore",0);
                String dataString=prefs.getString("data","");
                Log.e("（状态）发酵罐Spinner返回数据读取",dataString);
                newdata=Utility.handleDataResponse(dataString);
                showDataInfo(newdata);//刷新界面
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitial2||js_tag) {
                    isInitial2 = false;
                    js_tag=false;
                    return;
                }
                String cardNumber = getActivity().getResources().getStringArray(R.array.ctype)[position];
                Toast.makeText(getActivity(), "你正在操作圈舍：" + cardNumber, Toast.LENGTH_SHORT).show();
                String inputx= spin_js.getSelectedItem().toString();
                Log.e("状态-设置圈舍：",inputx);
                Integer z=Integer.parseInt(inputx);
                MyMqttClient.sharedCenter().setSendData(
                        "/sys/a1S917F388O/wenxin/thing/event/property/post",
                        //"/a1yPGkxyv1q/SimuApp/user/update",
                        Utility.CommandJson(z+16,112,1),
                        0,
                        false);
                //从SharedPreferences读取数据
                prefs=getActivity().getSharedPreferences("datastore",0);
                String dataString=prefs.getString("data","");
                Log.e("状态-圈舍Spinner返回数据读取",dataString);
                newdata=Utility.handleDataResponse(dataString);
                showDataInfo(newdata);//刷新界面
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
