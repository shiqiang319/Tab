package com.example.tab;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;

import static com.example.tab.Utility.SetCommandJson;

public class FifthlyFragment extends Fragment {
    private SwipeRefreshLayout swipeRefresh;
    private SharedPreferences prefs;
    private Data newdata;
    private EditText passwordEdit;
    private EditText fjgsl;
    private EditText qssl;
    private EditText qwfc;
    private EditText qwzq;
    private EditText qwsj;
    private EditText cwys;
    private EditText zdys;
    private EditText sbbm;
    private EditText yhdm;
    private EditText sysq;
    private Button   btnsq;
    private Button   canshu;
    private int chedkedItem = 0;
    private String dataString;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fifthly, null);
        swipeRefresh=view.findViewById(R.id.swipe_refresh5);
        fjgsl=view.findViewById(R.id.xt_fjgsl);
        qssl=view.findViewById(R.id.xt_qssl);
        qwfc=view.findViewById(R.id.xt_qwfc);
        qwzq=view.findViewById(R.id.xt_qwzq);
        qwsj=view.findViewById(R.id.xt_qwsj);
        cwys=view.findViewById(R.id.xt_cwys);
        zdys=view.findViewById(R.id.xt_zdys);
        sbbm=view.findViewById(R.id.xt_sbbm);
        yhdm=view.findViewById(R.id.xt_yhdm);
        sysq=view.findViewById(R.id.xt_sysq);
        canshu=view.findViewById(R.id.btn_cspz2);
        btnsq=view.findViewById(R.id.btn_sysq);
        //下拉刷新
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Utility.xtrequestData();//发送请求
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
        //使用授权为不可编辑
        sysq.setFocusable(false);
        //sysq.setFocusableInTouchMode(false);
        //按钮点击
        BtnListener(canshu);
        btnsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView();
            }
        });
        return view;
    }
    //展示Data实体类中的数据
    private void showDataInfo(Data newdata) {
        fjgsl.setText(newdata.Para.get(1).toString());
        qssl.setText(newdata.Para.get(2).toString());
        qwfc.setText(newdata.Para.get(3).toString());
        qwzq.setText(newdata.Para.get(4).toString());
        qwsj.setText(newdata.Para.get(5).toString());
        cwys.setText(newdata.Para.get(6).toString());
        zdys.setText(newdata.Para.get(7).toString());
        sbbm.setText(newdata.Para.get(9).toString());
        yhdm.setText(newdata.Para.get(10).toString());
        if (newdata.Para.get(8) == 1) {
            sysq.setText("正常");
        } else sysq.setText("停机");
    }
    //按钮点击事件
    private void BtnListener(final Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int P8;
                //JSONArray jsonArray=new JSONArray();
                if (TextUtils.isEmpty(fjgsl.getText())||TextUtils.isEmpty(qssl.getText())||TextUtils.isEmpty(qwfc.getText())
                        ||TextUtils.isEmpty(qwzq.getText())||TextUtils.isEmpty(qwsj.getText())||TextUtils.isEmpty(cwys.getText())
                        ||TextUtils.isEmpty(zdys.getText())||TextUtils.isEmpty(sbbm.getText())||TextUtils.isEmpty(yhdm.getText())
                        ||TextUtils.isEmpty(sysq.getText())){
                    Toast.makeText(getActivity(), "有参数未设置，参数配置失败！" , Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer P1=Integer.parseInt(fjgsl.getText().toString().trim());
                Integer P2=Integer.parseInt(qssl.getText().toString().trim());
                Integer P3=Integer.parseInt(qwfc.getText().toString().trim());
                Integer P4=Integer.parseInt(qwzq.getText().toString().trim());
                Integer P5=Integer.parseInt(qwsj.getText().toString().trim());
                Integer P6=Integer.parseInt(cwys.getText().toString().trim());
                Integer P7=Integer.parseInt(zdys.getText().toString().trim());
                Integer P9=Integer.parseInt(sbbm.getText().toString().trim());
                Integer P10=Integer.parseInt(yhdm.getText().toString().trim());
                if (sysq.getText().toString().trim().equals("正常")){
                     P8=1;
                }else P8=0;
//                ScanMessage lastmessage= LitePal.findLast(ScanMessage.class);
//                Integer username=lastmessage.getUserNum();
//                Integer mynum=lastmessage.getMyNum();
                Integer Id=1*256+1;
                Integer Cmd=1*256+26;
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
                int P0=Utility.MySecret(C10,P10);
                jsonArray.put(P0);
                jsonArray.put(P1);
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
                MyMqttClient.sharedCenter().setSendData(
                        //"/sys/a1S917F388O/wenxin/thing/event/property/post",
                        //"/a1yPGkxyv1q/SimuApp/user/update",
                        "/a1gZWTRWzGi/P:0001:01/user/update",
                       // SetCommandJson(1,26,jsonArray),
                        Utility.SetCommandJson(Id,Cmd,jsonArray),
                        0,
                        false);
                Log.e("EditText","发送数据："+SetCommandJson(1,26,jsonArray));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //从SharedPreferences读取数据
                        prefs=getActivity().getSharedPreferences("datastore",0);
                        String dataString=prefs.getString("data","");
                        if (dataString!=""){
                            Log.e("数据读取",dataString);
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
    /**
     * 自定义对话框
     */
    public void customView( )
    {
        // 加载\res\layout\login.xml界面布局文件
        final TableLayout loginForm = (TableLayout) getLayoutInflater().inflate(R.layout.login, null);
        new AlertDialog.Builder(getActivity())
                // 设置对话框的图标
                // .setIcon(R.drawable.tools)
                // 设置对话框的标题
                .setTitle("设备需要远程授权？")
                // 设置对话框显示的View对象
                .setView(loginForm)
                // 为对话框设置一个“确定”按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passwordEdit=loginForm.findViewById(R.id.password);
                        String password=passwordEdit.getText().toString();
                        // 此处可执行登录处理
                         if (password.equals("123456")){
                           dialog.dismiss();
                           singleChoiceDialog();
                         }else {
                             Toast.makeText(getActivity(), "你输入的密码不正确！", Toast.LENGTH_SHORT).show();
                             dialog.dismiss();
                         }
                    }
                })
                // 为对话框设置一个“取消”按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 取消登录，不做任何事情
                        dialog.dismiss();
                    }
                })
                // 创建并显示对话框
                .create().show();
    }
    /**
     * 单选对话框
     */
    public void singleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("请选择：");
        final String[] cities = {" ","正常", "停机"};

        builder.setSingleChoiceItems(cities, chedkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), "你选择了" + cities[which], Toast.LENGTH_SHORT).show();
                chedkedItem = which;
                if (cities[which].equals("正常")){
                    sysq.setText("正常");
                }else sysq.setText("停机");
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }
}
