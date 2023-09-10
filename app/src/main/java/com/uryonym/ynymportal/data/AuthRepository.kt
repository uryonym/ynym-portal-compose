package com.uryonym.ynymportal.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uryonym.ynymportal.data.model.Response
import com.uryonym.ynymportal.data.model.Response.Success
import com.uryonym.ynymportal.data.model.Response.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {
    val currentUser: FirebaseUser?

    val uid: String

    suspend fun getIdToken(): String

    suspend fun signInWithEmailAndPassword(email: String, password: String): Response<Boolean>

    fun signOut()

    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean>
}

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val auth = Firebase.auth

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override val uid: String
        get() = currentUser?.uid ?: ""

    override suspend fun getIdToken(): String {
        var token = ""
        try {
            token = currentUser?.getIdToken(true)?.await()?.token ?: ""
        } catch (e: Exception) {
            Log.d("firebase", e.toString())
        }

        return token
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Response<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean> = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}