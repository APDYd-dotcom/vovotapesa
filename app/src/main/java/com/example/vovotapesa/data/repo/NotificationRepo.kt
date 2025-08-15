package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.dto.NotificationResponse

interface NotificationRepo{
  suspend fun getNotification(token: String): Result<List<NotificationResponse>>
}