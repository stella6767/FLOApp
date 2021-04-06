package com.kang.floapp.view.main.frag.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kang.floapp.R;

public class FragSearch extends Fragment {

    private static final String TAG = "FragSearch";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_search_child_container, new FragSearchEngine()).commit(); //최초 화면(프레그먼트)


        return inflater.inflate(R.layout.frag_search, null);

    }
}
