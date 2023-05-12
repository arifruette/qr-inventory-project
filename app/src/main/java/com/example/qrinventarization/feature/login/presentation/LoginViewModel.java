package com.example.qrinventarization.feature.login.presentation;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrinventarization.data.repository.UsersRepository;
import com.example.qrinventarization.domain.model.users.Token;
import com.example.qrinventarization.domain.model.users.User;
import com.example.qrinventarization.feature.inventarization.presentation.InventarizationStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginPageStatus> _status = new MutableLiveData<>();
    public LiveData<LoginPageStatus> status = _status;

    private MutableLiveData<Token> _token = new MutableLiveData<>();
    public LiveData<Token> token = _token;

    public void login(User user){

        _status.setValue(LoginPageStatus.SENDING);
        UsersRepository.loginUser(user).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                _status.setValue(LoginPageStatus.SUCCESS);
//                System.out.println(response.body().getAccess_token());
                _token.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                _status.setValue(LoginPageStatus.FAILURE);
                t.printStackTrace();
            }
        });
    }
}
