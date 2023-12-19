package com.tensor.example.data.fireatstore.use_case

import com.tensor.example.data.fireatstore.repository.UserRepository


class DeleteUser(
    private val repo: UserRepository
) {
    suspend operator fun invoke(id: String) = repo.deleteUserFromFireStore(id)
}