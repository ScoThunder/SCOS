package es.source.code.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.source.code.R;

/**
 * Created by Hander on 16/6/18.
 * <p/>
 * Email : hander_wei@163.com
 */
public class HotDishesFragment extends Fragment {

    public HotDishesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_dishes, container, false);
    }
}
