package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerFragment extends Fragment implements View.OnClickListener{

    Button scanBtn;
    Button addBtn;
    TextView messageText;
    FragmentTransaction fTrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        scanBtn = view.findViewById(R.id.scanBtn);
        addBtn = view.findViewById(R.id.addBtn);
        messageText = view.findViewById(R.id.textContent);
        //messageFormat = view.findViewById(R.id.textFormat);

        // adding listener to the button
        scanBtn.setOnClickListener(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment fragment = new AddFragment();
                fTrans = getFragmentManager().beginTransaction();

                ScannerFragment fragment1 = new ScannerFragment();

                fTrans.addToBackStack(null);
                fTrans.replace(R.id.navHostFragment, fragment);

                fTrans.setReorderingAllowed(true);



                fTrans.commit();
            }

        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        // we need to create the object
        // of IntentIntegrator class
        // which is the class of QR library
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);
        intentIntegrator.setPrompt("Сканируйте QR-код");
        intentIntegrator.setOrientationLocked(false);

        //intentIntegrator.setTorchEnabled(true);//включает фонарик
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
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
                messageText.setText(intentResult.getContents());
                System.out.println(0 + intentResult.getContents() + intentResult.getFormatName());
            }
        }
    }
}


