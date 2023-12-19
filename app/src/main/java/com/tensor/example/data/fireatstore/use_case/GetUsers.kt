package com.tensor.example.data.fireatstore.use_case

import com.tensor.example.data.fireatstore.repository.UserRepository


class GetUsers (
    private val repo: UserRepository
) {
    operator fun invoke() = repo.getUsersFromFireStore()
}