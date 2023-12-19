/*
* Copyright 2023 TensorIotExample
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.tensor.example.di

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tensor.example.data.fireatstore.FireAtStoreConstants.USERS
import com.tensor.example.data.fireatstore.repo.UsersRepositoryImpl
import com.tensor.example.data.fireatstore.repository.UserRepository
import com.tensor.example.data.fireatstore.use_case.AddUser
import com.tensor.example.data.fireatstore.use_case.DeleteUser
import com.tensor.example.data.fireatstore.use_case.GetSingleUser
import com.tensor.example.data.fireatstore.use_case.GetUsers
import com.tensor.example.data.fireatstore.use_case.UseCases
import com.tensor.example.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context)

    @Provides
    fun provideBooksRef() = Firebase.firestore.collection(USERS)

    @Provides
    fun provideBooksRepository(
        booksRef: CollectionReference
    ): UserRepository = UsersRepositoryImpl(booksRef)

    @Provides
    fun provideUseCases(
        repo: UserRepository
    ) = UseCases(
        getUsers = GetUsers(repo),
        adduser = AddUser(repo),
        deleteUser = DeleteUser(repo),
        getSingleUser = GetSingleUser(repo)
    )
}
