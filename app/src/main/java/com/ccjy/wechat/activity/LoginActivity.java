package com.ccjy.wechat.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccjy.wechat.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Random;

/**
 * Created by dell on 2017/3/23.
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_login_icon;
    private EditText et_login_userName,et_login_password;
    private TextView tvHideA,tvHideB,tvHideC,tvHideD; //输入数字的文本框
    private ImageView ivNumA,ivNumB,ivNumC,ivNumD;  //获取到的数字图片
    private ImageView iv_delete_username,iv_delete_password;
    private EditText etCheck; //验证码按钮
    private TextView tvCheck; //显示随机抽取出的验证码
    private Button bt_login;  //登录按钮


    private String numStrTmp = "";   //存储每个验证码的数字
    private String numStr = "";      //存储整个验证码的数字
    private int[] numArray = new int[4];   //存储验证码的数组
    private int[] colorArray = new int[6]; //存储颜色的数组

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        iv_login_icon= (ImageView) findViewById(R.id.iv_login_icon);

        iv_delete_username= (ImageView) findViewById(R.id.iv_delete_username);
        iv_delete_password= (ImageView) findViewById(R.id.iv_delete_password);

        et_login_userName= (EditText) findViewById(R.id.et_login_userName);
        et_login_password= (EditText) findViewById(R.id.et_login_password);

        tvHideA= (TextView) findViewById(R.id.tvHideA);
        tvHideB= (TextView) findViewById(R.id.tvHideB);
        tvHideC= (TextView) findViewById(R.id.tvHideC);
        tvHideD= (TextView) findViewById(R.id.tvHideD);

        ivNumA= (ImageView) findViewById(R.id.ivNumA);
        ivNumB= (ImageView) findViewById(R.id.ivNumB);
        ivNumC= (ImageView) findViewById(R.id.ivNumC);
        ivNumD= (ImageView) findViewById(R.id.ivNumD);

        tvCheck= (TextView) findViewById(R.id.tvCheck);
        etCheck= (EditText) findViewById(R.id.etCheck);

        bt_login= (Button) findViewById(R.id.bt_login);

        ivNumA.setOnClickListener(this);
        ivNumB.setOnClickListener(this);
        ivNumC.setOnClickListener(this);
        ivNumD.setOnClickListener(this);

        bt_login.setOnClickListener(this);


    }

    private void login(String userName,String password){
        EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("onSuccess", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("onError", "登录聊天服务器失败！");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivNumA:
                break;
            case R.id.ivNumB:
                break;
            case R.id.ivNumC:
                break;
            case R.id.ivNumD:
                break;
            case R.id.iv_delete_username:
                break;
            case R.id.iv_delete_password:
                break;
            case R.id.bt_login:
                break;
        }
    }
    private void setNum() {
        initNum();
        tvHideA.setText("" + numArray[0]);
        tvHideA.setTextColor(randomColor());
        tvHideB.setText("" + numArray[1]);
        tvHideB.setTextColor(randomColor());
        tvHideC.setText("" + numArray[2]);
        tvHideC.setTextColor(randomColor());
        tvHideD.setText("" + numArray[3]);
        tvHideD.setTextColor(randomColor());


        Matrix matrixA = new Matrix();
        //重设矩阵
        matrixA.reset();
        matrixA.setRotate(randomAngle());
        Bitmap bmNumA = Bitmap.createBitmap(getBitmapFromView(tvHideA,20,50),0,0,20,50,matrixA,true);
        ivNumA.setImageBitmap(bmNumA);

        Matrix matrixB = new Matrix();
        //重设矩阵
        matrixB.reset();
        matrixB.setRotate(randomAngle());
        Bitmap bmNumB = Bitmap.createBitmap(getBitmapFromView(tvHideB,20,50),0,0,20,50,matrixB,true);
        ivNumB.setImageBitmap(bmNumB);

        Matrix matrixC = new Matrix();
        //重设矩阵
        matrixC.reset();
        matrixC.setRotate(randomAngle());
        Bitmap bmNumC = Bitmap.createBitmap(getBitmapFromView(tvHideC,20,50),0,0,20,50,matrixC,true);
        ivNumC.setImageBitmap(bmNumC);

        Matrix matrixD = new Matrix();
        //重设矩阵
        matrixD.reset();
        matrixD.setRotate(randomAngle());
        Bitmap bmNumD = Bitmap.createBitmap(getBitmapFromView(tvHideD,20,50),0,0,20,50,matrixD,true);
        ivNumD.setImageBitmap(bmNumD);

    }

    private Bitmap getBitmapFromView(View v,int width,int height ) {
        int widSpec = View.MeasureSpec.makeMeasureSpec(width,View.MeasureSpec.EXACTLY);
        int heiSpec = View.MeasureSpec.makeMeasureSpec(height,View.MeasureSpec.EXACTLY);
        //重新绘制图片大小
        v.measure(widSpec, heiSpec);
        //
        v.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        //画出图片
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        return bitmap;

    }


    //设置随机倾斜的角度
    private int randomAngle() {


        return 20*(new Random().nextInt(5)-new Random().nextInt(3));
    }



    //随机生成颜色
    private int randomColor() {
        colorArray[0]=0xFF000000; //BLACK
        colorArray[1] = 0xFFFF00FF; // MAGENTA
        colorArray[2] = 0xFFFF0000; // RED
        colorArray[3] = 0xFF00FF00; // GREEN
        colorArray[4] = 0xFF0000FF; // BLUE
        colorArray[5] = 0xFF00FFFF; // CYAN
        int randomColoId = new Random().nextInt(5);
        return colorArray[randomColoId];




    }
    //初始化验证码
    private void initNum() {
        numStr="";
        numStrTmp="";
        for (int i = 0; i < numArray.length; i++) {
            //随机生成10以内数字
            int numIntTmp = new Random().nextInt(10);
            //保存各个验证码
            numStrTmp = String.valueOf(numIntTmp);
            //保存整个验证码
            numStr = numStr+numStrTmp;
            numArray[i] = numIntTmp;
        }

    }
}
