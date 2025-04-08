package br.com.flpbrrs.taskapp.utils

import br.com.flpbrrs.taskapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {
    companion object {
        fun getDatabase() = Firebase.database.reference
        fun getAuth() = FirebaseAuth.getInstance()
        fun getCurrentUserId() = getAuth().currentUser?.uid ?: ""
        fun isAuthenticated() = getAuth().currentUser != null
        fun validError(error: String): Int {
            return when {
                error.contains(other = "There is no user record corresponding to this identifier") -> {
                    R.string.account_not_registered_register_fragment
                }

                error.contains(other = "The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment
                }

                error.contains(other = "The password is invalid or the user does not have a password") -> {
                    R.string.invalid_password_register_fragment
                }

                error.contains(other = "The email address is already in use by another account") -> {
                    R.string.email_in_use_register_fragment
                }

                error.contains(other = "Password should be at least 6 characters") -> {
                    R.string.strong_password_register_fragment
                }

                else -> {
                    R.string.generic_message_error
                }
            }
        }
    }
}