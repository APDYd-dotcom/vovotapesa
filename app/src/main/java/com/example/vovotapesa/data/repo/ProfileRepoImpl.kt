package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.model.PasswordChangeRequest
import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.FantaResponse
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import com.example.vovotapesa.ui.app.components.utils.safeApiCall
import java.io.File

class ProfileRepoImpl( private val api: ApiService): ProfileRepo {
    override suspend fun getProfile(token: String): Result<ProfileResponse> {
        return try {
            val response = api.getProfile(token)
            Result.success(response[0])
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(
        id: Int,
        token: String,
        email: String?,
        fullName: String?,
        photo: File?
    ): Result<Unit> {
        return safeApiCall {
            api.updateProfile(id, token, email, fullName, photo)
            Unit
        }
    }

    override suspend fun changePassword(token: String, request: PasswordChangeRequest): Result<FantaResponse> {
        return safeApiCall { api.changePassword(token, request) }
    }
}