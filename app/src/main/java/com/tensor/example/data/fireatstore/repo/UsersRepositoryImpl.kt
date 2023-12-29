package com.tensor.example.data.fireatstore.repo

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.tensor.example.data.fireatstore.FireAtStoreConstants.userName
import com.tensor.example.data.fireatstore.model.Response
import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.data.fireatstore.repository.UserRepository
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

    override suspend fun getSingleUserFromFireStore(id: String) = try {
        var user = User()
    /*    booksRef.document(id).get().addOnCompleteListener {
            if (it.isComplete) {
                val data = it.result
                if (data != null) {
                    val data = data.toObject(User::class.java)
                    Timber.tag("TAG").e(
                        "getSingleUserFromFireStore: ${
                            data
                        }"
                    )
                    user.id = id
                    user.userName = id
                    Response.Success(user)
                }


            }
        }*/
        user.let {

            var data = booksRef.document(id).get().await().toObject(User::class.java)
            if (data != null) {
                Log.e("TAG", "getSingleUserFromFireStore: "+data )
                user=data
            };
        }
        Response.Success(user)
    } catch (e: Exception) {
        Response.Failure(e)
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