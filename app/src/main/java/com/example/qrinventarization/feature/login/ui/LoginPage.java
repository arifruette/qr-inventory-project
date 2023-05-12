package com.example.qrinventarization.feature.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrinventarization.databinding.ActivityLoginPageBinding;
import com.example.qrinventarization.domain.model.users.Token;
import com.example.qrinventarization.domain.model.users.User;
import com.example.qrinventarization.feature.login.presentation.LoginPageStatus;
import com.example.qrinventarization.feature.login.presentation.LoginViewModel;
import com.example.qrinventarization.feature.main.ui.MainActivity;

public class LoginPage extends AppCompatActivity {

    private ActivityLoginPageBinding binding;
    private boolean email_correct = false;
    private boolean password_correct = false;
    private LoginViewModel viewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(view);

        sharedPreferences = getSharedPreferences("mysettings", MODE_PRIVATE);

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_correct = Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches();
                binding.loginPageButton.setEnabled(email_correct && password_correct);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_correct = binding.password.getText().toString().length() >= 1;
                binding.loginPageButton.setEnabled(email_correct && password_correct);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(binding.email.getText().toString(), binding.password.getText().toString());
                viewModel.token.observe(LoginPage.this, LoginPage.this::saveToken);
                viewModel.status.observe(LoginPage.this, LoginPage.this::renderStatus);

                viewModel.login(user);
            }
        });
    }

    private void renderStatus(LoginPageStatus status){
        switch (status){
            case SENDING:
                binding.loginPageButton.setVisibility(View.INVISIBLE);
                binding.constraintLayout.setVisibility(View.INVISIBLE);
                binding.constraintLayout2.setVisibility(View.INVISIBLE);

                binding.loginTitle.setVisibility(View.INVISIBLE);
                binding.loginSubtitle.setVisibility(View.INVISIBLE);

                binding.loginFailed.setVisibility(View.INVISIBLE);

                binding.sendingProgressBar.setVisibility(View.VISIBLE);

                break;

            case FAILURE:
                binding.loginPageButton.setVisibility(View.INVISIBLE);
                binding.constraintLayout.setVisibility(View.INVISIBLE);
                binding.constraintLayout2.setVisibility(View.INVISIBLE);

                binding.loginTitle.setVisibility(View.INVISIBLE);
                binding.loginSubtitle.setVisibility(View.INVISIBLE);

                binding.loginFailed.setVisibility(View.VISIBLE);

                binding.sendingProgressBar.setVisibility(View.INVISIBLE);
                break;


            case SUCCESS:
                if(sharedPreferences.getBoolean("is_admin", false)){
                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    //TODO new class for user
                }

                break;
        }
    }

    private void saveToken(Token token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token.getAccess_token());
        editor.putBoolean("is_admin", token.isIs_admin());
        editor.apply();
    }

}