package com.tensor.example.data.fireatstore.repository
import androidx.lifecycle.MutableLiveData
import com.tensor.example.data.fireatstore.model.Response
import com.tensor.example.data.fireatstore.model.User
import kotlinx.coroutines.flow.Flow

typealias UserResponse = Response<User>
typealias AddUserResponse = Response<Boolean>
typealias DeleteUserResponse = Response<Boolean>
interface UserRepository {
    fun getUsersFromFireStore(): Flow<Response<MutableList<User>>>
    suspend fun getSingleUserFromFireStore(id :String): UserResponse

    suspend fun addUserToFireStore(user: User): AddUserResponse

    suspend fun deleteUserFromFireStore(id: String): DeleteUserResponse
}