package com.tensor.example.data.fireatstore.repo

import com.google.firebase.firestore.CollectionReference
import com.tensor.example.data.fireatstore.FireAtStoreConstants.userName
import com.tensor.example.data.fireatstore.model.Response
import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.data.fireatstore.repository.UserRepository
import com.tensor.example.data.fireatstore.repository.UserResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val booksRef: CollectionReference
) : UserRepository {
    override fun getUsersFromFireStore() = callbackFlow {
        val snapshotListener = booksRef.orderBy(userName)
            .addSnapshotListener { snapshot, e ->
                val booksResponse = if (snapshot != null) {
                    val books = snapshot.toObjects(User::class.java)
                    Response.Success(books)
                } else {
                    Response.Failure(e)
                }
                trySend(booksResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getSingleUserFromFireStore(id: String): UserResponse {
        val user = User()
        booksRef.document(id).get().addOnCompleteListener {
            if (it.isComplete) {
                val data = it.result
                if (data != null) {
                    Timber.tag("TAG").e(
                        "getSingleUserFromFireStore: ${
                            data.data
                        }"
                    )
                    user.id = id
                    user.userName = id


                } else {
                }
            } else if (it.isCanceled) {
                Response.Failure(it.exception)
            }


        }
        return Response.Success(user)

    }

    override suspend fun addUserToFireStore(user: User) = try {
        user.id?.let { booksRef.document(it).set(user).await() }
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteUserFromFireStore(id: String) = try {
        booksRef.document(id).delete().await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}