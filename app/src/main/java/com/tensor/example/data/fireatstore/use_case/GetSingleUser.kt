package com.tensor.example.data.fireatstore.use_case

import com.tensor.example.data.fireatstore.repository.UserRepository


class GetSingleUser (
    private val repo: UserRepository
) {
    suspend operator fun invoke(id: String) = repo.getSingleUserFromFireStore(id)
}