package es.source.code.fragment;

import android.app.Activity;
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
    private Button mBtnLogin, mBtnReturn;

    OnBtnClicked mCallback;

    public LoginOrRegister() {

    }

    public interface OnBtnClicked {
        void onBtnClicked(String msg);
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
        return view;
    }


    @Override
    public void onClick(View view) {
        if (mCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_login:
                if (isDigitOrLetter(mEtUserName.getText().toString()) && isDigitOrLetter(mEtPswd.getText().toString())) {
                    mCallback.onBtnClicked("LoginSuccess");
                } else {
                    mCallback.onBtnClicked("LoginFailed");
                }
                break;
            case R.id.btn_return:
                mCallback.onBtnClicked("Return");
                break;
        }
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
