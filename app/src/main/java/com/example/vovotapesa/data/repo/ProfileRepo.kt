package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.dto.ProfileResponse

interface ProfileRepo {
    suspend fun getProfile(token: String): Result<ProfileResponse>
}