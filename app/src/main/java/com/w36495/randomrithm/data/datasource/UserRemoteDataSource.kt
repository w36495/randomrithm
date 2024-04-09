package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.data.service.UserService
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
){
    suspend fun checkUserAccount(query: String): Response<UserDTO> = userService.checkUserAccount(query)
}