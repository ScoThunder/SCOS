package es.source.code.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import es.source.code.R;

/**
 * Created by Hander on 16/6/11.
 * <p/>
 * Email : hander_wei@163.com
 */
public class LoginOrRegister extends Fragment implements View.OnClickListener {

    private EditText mEtUserName, mEtPswd;
    private Button mBtnLogin, mBtnReturn, mBtnSignUp;

    OnBtnClicked mCallback;

    public static final String PREFS_NAME = "SCOS";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_LOGIN_STATE = "loginState";

    public LoginOrRegister() {

    }

    public interface OnBtnClicked {
        void onReturnBtnClicked(String msg);

        void onLoginBtnClicked(String msg, String userName, String password);

        void onSignUpBtnClicked(String msg, String userName, String password);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnBtnClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_or_register, container, false);
        mEtUserName = (EditText) view.findViewById(R.id.et_user_name);
        mEtPswd = (EditText) view.findViewById(R.id.et_password);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        mBtnReturn = (Button) view.findViewById(R.id.btn_return);
        mBtnSignUp = (Button) view.findViewById(R.id.btn_sign_up);

        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userName = prefs.getString(KEY_USER_NAME, null);
        if (userName != null) {
            mBtnSignUp.setVisibility(View.GONE);
            mEtUserName.setText(userName);
            mBtnLogin.setVisibility(View.VISIBLE);
        } else {
            mBtnLogin.setVisibility(View.GONE);
            mBtnSignUp.setVisibility(View.VISIBLE);
        }

        mEtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                for (int i = 0; i < input.length(); i++) {
                    if (!(Character.isDigit(input.charAt(i)) || Character.isLetter(input.charAt(i)))) {
                        mEtUserName.setError("Your input is not correct!");
                        return;
                    }
                }
            }
        });
        mEtPswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                for (int i = 0; i < input.length(); i++) {
                    if (!(Character.isDigit(input.charAt(i)) || Character.isLetter(input.charAt(i)))) {
                        mEtUserName.setError("Your input is not correct!");
                        return;
                    }
                }
            }
        });
        mBtnLogin.setOnClickListener(this);
        mBtnReturn.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (mCallback == null) {
            return;
        }

        String userName = mEtUserName.getText().toString();
        String password = mEtPswd.getText().toString();
        switch (view.getId()) {
            case R.id.btn_login:
                if (userName != null && password != null && isDigitOrLetter(userName) && isDigitOrLetter(password)) {
                    mCallback.onLoginBtnClicked("LoginSuccess", userName, password);
                    saveLoginState(userName);
                } else {
                    mCallback.onReturnBtnClicked("Return");
                }
                break;
            case R.id.btn_return:
                clearLoginState();
                mCallback.onReturnBtnClicked("Return");
                break;
            case R.id.btn_sign_up:
                if (userName != null && password != null && isDigitOrLetter(userName) && isDigitOrLetter(password)) {
                    mCallback.onSignUpBtnClicked("RegisterSuccess", userName, password);
                    saveLoginState(userName);
                } else {
                    mCallback.onReturnBtnClicked("Return");
                }
                break;
        }
    }

    /**
     * 保存用户登录状态
     * <p/>
     * 当用户点击登录，注册时触发
     *
     * @param userName
     */
    private void saveLoginState(String userName) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.putInt(KEY_LOGIN_STATE, 1);
    }

    /**
     * 清除用户登录状态
     */
    private void clearLoginState() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_LOGIN_STATE, 0);
    }

    private boolean isDigitOrLetter(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!(Character.isDigit(input.charAt(i)) || Character.isLetter(input.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
