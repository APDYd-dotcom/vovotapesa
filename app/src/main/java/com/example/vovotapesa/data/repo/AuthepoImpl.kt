package com.example.vovotapesa.data.repo


import android.util.Log
import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.AuthLogin
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.data.remote.dto.AuthResponse

class AuthRepositoryImpl( private val api: ApiService) : AuthRepo {

    override suspend fun register(request: AuthRegister): Result<Unit> {
        return try {
          api.register(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("Error register", "Error register ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun login(request: AuthLogin): Result<AuthResponse> {
       return try {
            val response = api.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}