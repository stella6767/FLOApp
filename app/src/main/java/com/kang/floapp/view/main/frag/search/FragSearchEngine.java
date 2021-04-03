package com.kang.floapp.view.main.frag.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kang.floapp.R;
import com.kang.floapp.view.main.MainActivity;

public class FragSearchEngine extends Fragment {

    private static final String TAG = "FragSearchEngine";
    private EditText inputSearch;
    private Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_engine, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();

        inputSearch = view.findViewById(R.id.input_search);
        btnSearch = view.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(v -> {
            String keyword = inputSearch.getText().toString().trim();
            Log.d(TAG, "onCreateView: keyword: "+keyword);
            getFragmentManager().beginTransaction().replace(R.id.fragment_search_child_container, new FragSearchResult(keyword)).commit();
        });



        return view;
    }
}
