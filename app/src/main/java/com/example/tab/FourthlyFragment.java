package com.example.tab;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.tab.Login.ScanMessage;

import org.json.JSONArray;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static com.example.tab.Utility.SetCommandJson;
import static com.example.tab.Utility.fabutopic;
import static com.example.tab.Utility.mynum;
import static com.example.tab.Utility.username;

public class FourthlyFragment extends Fragment {
    private int P1=0;
    private SwipeRefreshLayout swipeRefresh;
    private SharedPreferences prefs;
    private Spinner spinner;
    private Data newdata;
    private EditText wdsx;
    private EditText wdxx;
    private EditText fwsx;
    private EditText fwxx;
    private EditText flxz;
    private EditText jzxz;
    private EditText flxs;
    private EditText jzxs;
    private EditText slxs;
    private EditText xlxs;
    private EditText kjxs;
    private EditText jbxs;
    private EditText cxds;
    private EditText ydkj;
    private EditText ydgj;
    private EditText edkj;
    private EditText edgj;
    private CheckBox qingwu;
    private CheckBox fenwu;
    private CheckBox fuliao;
    private CheckBox junzhong;
    private Button canshu;
    private String dataString;
    private List<CharSequence> eduList = null;
    private ArrayAdapter<CharSequence> eduAdapter = null;
    private int num;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fourthly, null);
        swipeRefresh=view.findViewById(R.id.swipe_refresh4);
        spinner=view.findViewById(R.id.spin6);
        wdsx=view.findViewById(R.id.et_wdsx);
        wdxx=view.findViewById(R.id.et_wdxx);
        fwsx=view.findViewById(R.id.et_fwsx);
        fwxx=view.findViewById(R.id.et_fwxx);
        flxz=view.findViewById(R.id.et_flxz);
        jzxz=view.findViewById(R.id.et_jzxz);
        flxs=view.findViewById(R.id.et_flxs);
        jzxs=view.findViewById(R.id.et_jzxs);
        slxs=view.findViewById(R.id.et_slxs);
        xlxs=view.findViewById(R.id.et_xlxs);
        kjxs=view.findViewById(R.id.et_kjxs);
        jbxs=view.findViewById(R.id.et_jbxs);
        cxds=view.findViewById(R.id.et_cxds);
        ydkj=view.findViewById(R.id.et_1dkj);
        ydgj=view.findViewById(R.id.et_1gj);
        edkj=view.findViewById(R.id.et_2dkj);
        edgj=view.findViewById(R.id.et_2gj);
        qingwu=view.findViewById(R.id.cb_1);
        fenwu=view.findViewById(R.id.cb_2);
        fuliao=view.findViewById(R.id.cb_3);
        junzhong=view.findViewById(R.id.cb_4);
        canshu=view.findViewById(R.id.btn_cspz);

        ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
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
                Utility.szrequestData();//发送请求
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("设置-数据读取",dataString);
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
        SpiListener(spinner);
        qingwu.setOnCheckedChangeListener(listener);
        fenwu.setOnCheckedChangeListener(listener);
        fuliao.setOnCheckedChangeListener(listener);
        junzhong.setOnCheckedChangeListener(listener);
        BtnListener(canshu);
        return view;
    }
    //展示Data实体类中的数据
    private void showDataInfo(Data newdata){
        spinner.setSelection(newdata.Id%256);
        wdsx.setText(newdata.Para.get(3).toString());
        wdxx.setText(newdata.Para.get(4).toString());
        fwsx.setText(newdata.Para.get(5).toString());
        fwxx.setText(newdata.Para.get(6).toString());
        flxz.setText(newdata.Para.get(7).toString());
        jzxz.setText(newdata.Para.get(8).toString());
        flxs.setText(newdata.Para.get(12).toString());
        jzxs.setText(newdata.Para.get(13).toString());
        slxs.setText(newdata.Para.get(10).toString());
        xlxs.setText(newdata.Para.get(11).toString());
        kjxs.setText(newdata.Para.get(14).toString());
        jbxs.setText(newdata.Para.get(9).toString());
        cxds.setText(newdata.Para.get(2).toString());
        ydkj.setText(newdata.Para.get(16).toString());
        ydgj.setText(newdata.Para.get(15).toString());
        edkj.setText(newdata.Para.get(18).toString());
        edgj.setText(newdata.Para.get(17).toString());
        if ((newdata.Para.get(1) & 1)==1){
            qingwu.setChecked(true);
        }else {
            qingwu.setChecked(false);
        }
        if ((newdata.Para.get(1) & 2)==2){
            fenwu.setChecked(true);
        }
        else {
            fenwu.setChecked(false);
        }
        if ((newdata.Para.get(1) & 4)==4){
            fuliao.setChecked(true);
        }else {
            fuliao.setChecked(false);
        }
        if ((newdata.Para.get(1) & 8)==8){
            junzhong.setChecked(true);
        }else {
            junzhong.setChecked(false);
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
                Integer Id=username*256+x;
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("设置（Spinner）返回数据读取",dataString);
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
    //CheckBox监听事件
    private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_1:
                    if (qingwu.isChecked()){
                        P1+=1;
                    }else{
                        P1-=1;
                    }
                    break;

                case R.id.cb_2:
                    if (fenwu.isChecked()){
                        P1+=2;
                    }else{
                        P1-=2;
                    }
                    break;
                case R.id.cb_3:
                    if (fuliao.isChecked()){
                        P1+=4;
                    }else{
                        P1-=4;
                    }
                    break;
                case R.id.cb_4:
                    if (junzhong.isChecked()){
                        P1+=8;
                    }else{
                        P1-=8;
                    }
                    break;
                default:
                    break;
            }
            Log.e("参数设置","P1值："+P1);

        }
    };
    //按钮点击事件
    private void BtnListener(final Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputx= spinner.getSelectedItem().toString();
                Integer x=Integer.parseInt(inputx);
               // JSONArray jsonArray=new JSONArray();
                //判断是否有参数为空
                if(TextUtils.isEmpty(cxds.getText())||TextUtils.isEmpty(wdsx.getText())||TextUtils.isEmpty(wdxx.getText())
                ||TextUtils.isEmpty(fwsx.getText())||TextUtils.isEmpty(fwxx.getText())||TextUtils.isEmpty(flxz.getText())
                ||TextUtils.isEmpty(jzxz.getText())||TextUtils.isEmpty(jbxs.getText())||TextUtils.isEmpty(slxs.getText())
                ||TextUtils.isEmpty(xlxs.getText())||TextUtils.isEmpty(flxs.getText())||TextUtils.isEmpty(jzxs.getText())
                ||TextUtils.isEmpty(kjxs.getText())||TextUtils.isEmpty(ydgj.getText())||TextUtils.isEmpty(ydkj.getText())
                ||TextUtils.isEmpty(edgj.getText())||TextUtils.isEmpty(edkj.getText())||(P1==0)){
                    Toast.makeText(getActivity(), "有参数未设置，参数配置失败！" , Toast.LENGTH_SHORT).show();
                    return;

                }
                Integer P2=Integer.parseInt(cxds.getText().toString().trim());
                Integer P3=Integer.parseInt(wdsx.getText().toString().trim());
                Integer P4=Integer.parseInt(wdxx.getText().toString().trim());
                Integer P5=Integer.parseInt(fwsx.getText().toString().trim());
                Integer P6=Integer.parseInt(fwxx.getText().toString().trim());
                Integer P7=Integer.parseInt(flxz.getText().toString().trim());
                Integer P8=Integer.parseInt(jzxz.getText().toString().trim());
                Integer P9=Integer.parseInt(jbxs.getText().toString().trim());
                Integer P10=Integer.parseInt(slxs.getText().toString().trim());
                Integer P11=Integer.parseInt(xlxs.getText().toString().trim());
                Integer P12=Integer.parseInt(flxs.getText().toString().trim());
                Integer P13=Integer.parseInt(jzxs.getText().toString().trim());
                Integer P14=Integer.parseInt(kjxs.getText().toString().trim());
                Integer P15=Integer.parseInt(ydgj.getText().toString().trim());
                Integer P16=Integer.parseInt(ydkj.getText().toString().trim());
                Integer P17=Integer.parseInt(edgj.getText().toString().trim());
                Integer P18=Integer.parseInt(edkj.getText().toString().trim());
                Integer Id=username*256+x;
                Integer Cmd=mynum*256+27;
                JSONArray jsonArray=new JSONArray();
                int C0=Utility.MySecret(65535,Id);
                int C1=Utility.MySecret(C0,Cmd);
                int C2=Utility.MySecret(C1,P1);
                int C3=Utility.MySecret(C2,P2);
                int C4=Utility.MySecret(C3,P3);
                int C5=Utility.MySecret(C4,P4);
                int C6=Utility.MySecret(C5,P5);
                int C7=Utility.MySecret(C6,P6);
                int C8=Utility.MySecret(C7,P7);
                int C9=Utility.MySecret(C8,P8);
                int C10=Utility.MySecret(C9,P9);
                int C11=Utility.MySecret(C10,P10);
                int C12=Utility.MySecret(C11,P11);
                int C13=Utility.MySecret(C12,P12);
                int C14=Utility.MySecret(C13,P13);
                int C15=Utility.MySecret(C14,P14);
                int C16=Utility.MySecret(C15,P15);
                int C17=Utility.MySecret(C16,P16);
                int C18=Utility.MySecret(C17,P17);
                int P0=Utility.MySecret(C18,P18);
                jsonArray.put(P0);
                jsonArray.put(P1);
                jsonArray.put(P2);
                jsonArray.put(P3);
                jsonArray.put(P4);
                jsonArray.put(P5);
                jsonArray.put(P6);
                jsonArray.put(P7);
                jsonArray.put(P8);
                jsonArray.put(P9);
                jsonArray.put(P10);
                jsonArray.put(P11);
                jsonArray.put(P12);
                jsonArray.put(P13);
                jsonArray.put(P14);
                jsonArray.put(P15);
                jsonArray.put(P16);
                jsonArray.put(P17);
                jsonArray.put(P18);

                MyMqttClient.sharedCenter().setSendData(
                    fabutopic,
                    Utility.SetCommandJson(Id,Cmd,jsonArray),
                    0,
                    false);
                Log.e("EditText","发送数据："+Utility.SetCommandJson(Id,Cmd,jsonArray));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("发酵罐按钮数据读取",dataString);
                            newdata=Utility.handleDataResponse(dataString);
                            showDataInfo(newdata);//刷新界面
                            prefs.edit().clear().commit();//清除SharedPreferences数据
                        }else {
                            Toast.makeText(getActivity(), "获取数据失败，请检查设备是否上线！", Toast.LENGTH_SHORT).show();
                        }
                    }
                },500);
            }
        });
    }

}
