package es.source.code.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import es.source.code.R;

/**
 * Created by Hander on 16/6/18.
 * <p/>
 * Email : hander_wei@163.com
 */
public class OrderedListFragment extends Fragment {
    private Button mBtnPay;

    public interface CallBack {
        void onPay();
    }


    public OrderedListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordered_list_test, container, false);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 支付
                ((CallBack)getActivity()).onPay();
            }
        });
        return view;
    }
}
