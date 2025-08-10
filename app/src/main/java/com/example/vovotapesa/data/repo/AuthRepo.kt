package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.dto.AuthLogin
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.data.remote.dto.AuthResponse

interface AuthRepo {
    suspend fun register(request: AuthRegister): Result<Unit>
    suspend fun login(request: AuthLogin): Result<AuthResponse>
}