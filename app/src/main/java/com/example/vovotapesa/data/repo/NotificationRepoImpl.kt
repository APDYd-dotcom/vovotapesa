package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.NotificationResponse

class NotificationRepoImpl(private val api: ApiService): NotificationRepo {
  override suspend fun getNotification(token: String): Result<List<NotificationResponse>> {
    return try {
      val response: List<NotificationResponse> = api.getNotification(token)
      Result.success(response)
    } catch (e: Exception){
      Result.failure(e)
    }
  }

}