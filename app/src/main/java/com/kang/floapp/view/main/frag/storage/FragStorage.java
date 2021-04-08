package com.kang.floapp.view.main.frag.storage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kang.floapp.R;
import com.kang.floapp.model.dto.StorageSaveReqDto;
import com.kang.floapp.view.main.MainActivity;
import com.kang.floapp.view.main.MainActivityViewModel;
import com.kang.floapp.view.main.adapter.StorageAdapter;

public class FragStorage extends Fragment {

    private static final String TAG = "FragStorage";

    private RecyclerView rvStorage;
    private StorageAdapter storageAdapter;
    private MainActivityViewModel mainViewModel;
    //private LinearLayout storageLayout;

    // 보관함에서 띄우는 다이얼 객체들
    private AppCompatImageView ivStorageAdd;
    private TextView tvDialogCancel, tvDialogComfirm;
    private TextInputEditText tiDialogTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_storage, container, false);

        MainActivity mainActivity = (MainActivity) container.getContext();
        mainViewModel = mainActivity.mainViewModel;
        ivStorageAdd = view.findViewById(R.id.iv_storage_add);
        rvStorage = view.findViewById(R.id.rv_storage);
        //storageLayout = view.findViewById(R.id.storage_layout);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvStorage.setLayoutManager(manager);
        storageAdapter = mainActivity.storageAdapter;
        rvStorage.setAdapter(storageAdapter);



        ivStorageAdd.setOnClickListener(v -> {
            View dialog = v.inflate(v.getContext(), R.layout.item_dialog_storage, null);
            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
            dlg.setView(dialog);

            tiDialogTitle = dialog.findViewById(R.id.ti_dialog_title);
            tvDialogCancel = dialog.findViewById(R.id.tv_dialog_cancel);

            final AlertDialog alertDialog = dlg.create();
            alertDialog.show();

            tvDialogCancel.setOnClickListener(v1 -> {
                Log.d(TAG, "다이얼 취소 버튼 클릭 됨.");
                alertDialog.dismiss();
            });

            tvDialogComfirm = dialog.findViewById(R.id.tv_dialog_confirm);
            tvDialogComfirm.setOnClickListener(v1 -> {
                Log.d(TAG, "다이얼 확인 버튼 클릭 됨.");
                String title = tiDialogTitle.getText().toString();
                StorageSaveReqDto storageSaveReqDto = new StorageSaveReqDto();
                storageSaveReqDto.setTitle(title);

                mainViewModel.addStorage(storageSaveReqDto, mainActivity);
                alertDialog.dismiss();
            });

        });

        swipeListner();

        return view;
    }

    private void swipeListner(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(TAG, "onSwiped: " + viewHolder.getAdapterPosition());

                int id = storageAdapter.getStorageId(viewHolder.getAdapterPosition());

                if(id != 0) {
                    mainViewModel.deleteByStorageId(id);
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvStorage);
    }

}
