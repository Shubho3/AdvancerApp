package com.tensor.example.data.fireatstore.use_case

import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.data.fireatstore.repository.UserRepository

class AddUser(
    private val repo: UserRepository
) {
    suspend operator fun invoke(
        user:User

    ) = repo.addUserToFireStore(user)
}