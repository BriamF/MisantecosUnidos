package com.itsm.misantecosunidos.core

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {
    fun getFirebaseInstance() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}