package com.Distributed.account_service.service;

import com.Distributed.account_service.dto.auth.AuthResponse;
import com.Distributed.account_service.dto.auth.LoginRequest;
import com.Distributed.account_service.dto.auth.SignupRequest;

public interface AuthService {


    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
