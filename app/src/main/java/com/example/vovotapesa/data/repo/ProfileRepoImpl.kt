package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.ProfileResponse

class ProfileRepoImpl( private val api: ApiService): ProfileRepo {
    override suspend fun getProfile(token: String): Result<ProfileResponse> {
        return try {
            val response = api.getProfile(token)
            Result.success(response[0])
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}