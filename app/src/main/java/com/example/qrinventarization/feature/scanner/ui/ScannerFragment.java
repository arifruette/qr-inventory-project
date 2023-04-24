package com.example.qrinventarization.feature.scanner.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrinventarization.R;
import com.example.qrinventarization.databinding.FragmentScannerBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerFragment extends Fragment {


    private FragmentScannerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScannerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);
                intentIntegrator.setPrompt("Сканируйте QR-код");
                intentIntegrator.setOrientationLocked(false);

                //intentIntegrator.setTorchEnabled(true);//включает фонарик
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            Log.d("TESTQR", " " + intentResult.getContents() + " " + intentResult.getFormatName());
            if (intentResult.getContents() == null) {
                Toast.makeText(getContext(), "Отмена", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                System.out.println(intentResult.getContents());
                System.out.println(0 + intentResult.getContents() + intentResult.getFormatName());
            }
        }
    }

}