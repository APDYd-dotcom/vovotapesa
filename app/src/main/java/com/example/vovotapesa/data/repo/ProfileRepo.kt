package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.model.PasswordChangeRequest
import com.example.vovotapesa.data.remote.dto.FantaResponse
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import java.io.File

interface ProfileRepo {
    suspend fun getProfile(token: String): Result<ProfileResponse>

    suspend fun updateProfile(
        id: Int,
        token: String,
        email: String? = null,
        fullName: String? = null,
        photo: File? = null
    ): Result<Unit>
    suspend fun changePassword(token: String, request: PasswordChangeRequest): Result<FantaResponse>

}