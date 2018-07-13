package com.ziruk.oa.communitymodule.uiCommon.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.gyf.barlibrary.ImmersionBar;
import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.ZirukHttpClient;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.callback.DisposeDataListener;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.exception.ZirukHttpException;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.mdel.ResponseCls;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.request.RequestParams;
import com.ziruk.oa.communitymodule.uiCommon.bean.UserInfo;
import com.ziruk.oa.communitymodule.util.Config;
import com.ziruk.oa.communitymodule.util.CurrentUserInfoUtils;
import com.ziruk.oa.communitymodule.util.Login.KeyboardWatcher;
import com.ziruk.oa.communitymodule.util.Login.ZoomUtil;
import com.ziruk.oa.communitymodule.util.ToastUtil;
import com.ziruk.oa.communitymodule.util.bean.AccountInfo;
import com.ziruk.oa.communitymodule.view.DrawableTextView;

import org.apache.commons.lang.StringUtils;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener {

    public static String PasswordCurrent = "";

    private DrawableTextView logo;

    private EditText mUserName;
    private EditText mPassWord;
    private CheckBox checkRemember;

    private ImageView iv_clean_username;
    private ImageView clean_password;
    private ImageView iv_show_pwd;

    private Button btnLogin;

    private int screenHeight = 0; //屏幕高度
    private float scale = 0.3f;   //logo缩放比例
    private View body;

    private KeyboardWatcher keyboardWatcher;

    private boolean flag = false;

    /**
     * 初始化
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.activity_login);

        //设置沉浸式状态栏
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();

        initView();

        initData();

        initListener();

    }

    private void initView() {

        logo = (DrawableTextView) findViewById(R.id.logo);
        body = findViewById(R.id.body);

        mUserName = (EditText) findViewById(R.id.et_userName);
        mPassWord = (EditText) findViewById(R.id.et_password);
        checkRemember = (CheckBox) findViewById(R.id.cb_checkbox);

        iv_clean_username = (ImageView) findViewById(R.id.iv_clean_phone);
        clean_password = (ImageView) findViewById(R.id.clean_password);
        iv_show_pwd = (ImageView) findViewById(R.id.iv_show_pwd);

        btnLogin = (Button) findViewById(R.id.btn_login);

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度

        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this); //添加键盘监听


    }

    private void initData() {

        AccountInfo accountInfo = new AccountInfo();
        Config.LoadAccount(this, accountInfo);

        mUserName.setText(accountInfo.getUserName());

        if (accountInfo.getAutoSaveType()==true) {
            mPassWord.setText(accountInfo.getPassword());

            checkRemember.setChecked(true);
        }

    }

    private void initListener() {
        iv_clean_username.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_username.getVisibility() == View.GONE) {
                    iv_clean_username.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_username.setVisibility(View.GONE);
                }
            }
        });
        mPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    mPassWord.setSelection(s.length());
                }
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if( StringUtils.isBlank(mUserName.getText().toString())){

                    ToastUtil.showToastShort("用户名不能为空");
                    return;
                }
                if( StringUtils.isBlank(mPassWord.getText().toString())){
                    ToastUtil.showToastShort("密码不能为空");
                    return;
                }

                RequestParams params=new RequestParams();

                params.put("UserName", String.valueOf(mUserName.getText().toString()));
                params.put("Password",mPassWord.getText().toString());


                ZirukHttpClient.newBuilder()
                        .addParams(params)
                        .setContext(LoginActivity.this)
                        .post().url("SystemAccount/Authenticate").build()
                        .enqueue(new DisposeDataListener<ResponseCls<UserInfo>>() {

                            @Override
                            public void onSuccess(ResponseCls<UserInfo> response) {

                                if ( "2".equals( response.getRequestStatus( ) )
                                        || "3".equals( response.getRequestStatus( ) ) ) {

                                    ToastUtil.showToastShort( "账号或者密码错误" );
                                    return;
                                } else if ( "1".equals( response.getRequestStatus( ) ) ) {

                                    ToastUtil.showToastShort( "账号已经被锁定" );
                                    return;
                                }

                                doAfterLogin( response.getData( ) );
                            }

                            @Override
                            public void onFailure(ZirukHttpException e) {
                                super.onFailure(e);

                                ToastUtil.showToastShort("无法连接至服务器，请稍后再试！");
                            }

                        });

            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.iv_clean_phone:
//                mUserName.setText("");
//                break;
//            case R.id.clean_password:
//                mPassWord.setText("");
//                break;
//            case R.id.iv_show_pwd:
//                if(flag == true){
//                    mPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    iv_show_pwd.setImageResource(R.mipmap.pass_gone);
//                    flag = false;
//                }else{
//                    mPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    iv_show_pwd.setImageResource(R.mipmap.pass_visuable);
//                    flag = true;
//                }
//                String pwd = mPassWord.getText().toString();
//                if (!TextUtils.isEmpty(pwd))
//                    mPassWord.setSelection(pwd.length());
//                break;
//        }
//    }

    private void doAfterLogin(UserInfo user) {

        AccountInfo accountInfo = new AccountInfo(
                user.UserID,
                mUserName.getText().toString(),
                mPassWord.getText().toString(),
                user.userType,
                checkRemember.isChecked()
        );
        CurrentUserInfoUtils.SaveUserInfo(LoginActivity.this, accountInfo, user.power);

        if(!checkRemember.isChecked()){
            accountInfo.setPassword("");
        }
        Config.SaveAccount(LoginActivity.this, accountInfo);

        Intent intent=new Intent();
        intent.setClass(LoginActivity.this,MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        Log.e("log", "----->show" + keyboardSize);
        int[] location = new int[2];
        body.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
        int x = location[0];
        int y = location[1];
        Log.e("log","y = "+y+",x="+x);
        int bottom = screenHeight - (y+body.getHeight()) ;
        Log.e("log","bottom = "+bottom);
        Log.e("log","con-h = "+body.getHeight());
        if (keyboardSize > bottom){
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            ZoomUtil.ZoomIn(logo, keyboardSize - bottom,scale);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {

        Log.e("log", "----->hide");
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", body.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        ZoomUtil.ZoomOut(logo,scale);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
        ImmersionBar.with(this).destroy();
    }


    @Override
    public void onClick(View v) {

    }
}
