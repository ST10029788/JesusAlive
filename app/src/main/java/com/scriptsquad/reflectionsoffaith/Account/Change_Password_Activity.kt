package com.scriptsquad.reflectionsoffaith.Account

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.scriptsquad.reflectionsoffaith.Utilities.Utils
import com.scriptsquad.reflectionsoffaith.databinding.ActivityChangePasswordBinding

class Change_Password_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    private companion object {
        private const val TAG = "CHANGE_PASSWORD_TAG"
    }

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.toolbarBackBtn.setOnClickListener {
            onBackPressed()
        }
        binding.changePasswordBtn.setOnClickListener {
            validateData()
        }
    }

    private var currentPassword = ""
    private var newPassword = ""
    private var confirmNewPassword = ""

    private fun validateData() {
        currentPassword = sanitizePassword(binding.currentPasswordEt.text.toString().trim())
        newPassword = sanitizePassword(binding.newPasswordEt.text.toString().trim())
        confirmNewPassword = sanitizePassword(binding.newConfirmPasswordEt.text.toString().trim())

        Log.d(TAG, "validateData: currentPassword: $currentPassword")
        Log.d(TAG, "validateData: newPassword: $newPassword")
        Log.d(TAG, "validateData: confirmNewPassword: $confirmNewPassword")

        when {
            currentPassword.isEmpty() -> {
                binding.currentPasswordEt.error = "Enter Current Password"
                binding.currentPasswordEt.requestFocus()
            }
            newPassword.isEmpty() -> {
                binding.newPasswordEt.error = "Enter New Password"
                binding.newPasswordEt.requestFocus()
            }
            confirmNewPassword.isEmpty() -> {
                binding.newConfirmPasswordEt.error = "Enter Confirm Password"
                binding.newConfirmPasswordEt.requestFocus()
            }
            newPassword != confirmNewPassword -> {
                binding.newConfirmPasswordEt.error = "Password Mismatch"
                binding.newConfirmPasswordEt.requestFocus()
            }
            else -> {
                authenticateUserForUpdatePassword()
            }
        }
    }

    private fun authenticateUserForUpdatePassword() {
        progressDialog.setMessage("Authenticating user")
        progressDialog.show()

        val authCredential = EmailAuthProvider.getCredential(firebaseUser.email.toString(), currentPassword)
        firebaseUser.reauthenticate(authCredential)
            .addOnSuccessListener {
                Log.d(TAG, "authenticateUserForUpdatePassword: Auth success")
                updatePassword() // Call updatePassword on successful re-authentication
            }.addOnFailureListener { e ->
                Log.e(TAG, "authenticateUserForUpdatePassword: ", e)
                progressDialog.dismiss()
                Utils.toast(this, "Failed to authenticate: ${e.message}")
            }
    }

    private fun updatePassword() {
        progressDialog.setMessage("Updating Password")
        progressDialog.show()

        firebaseUser.updatePassword(newPassword).addOnSuccessListener {
            Log.d(TAG, "updatePassword: Password Updated")
            progressDialog.dismiss()
            Utils.toast(this, "Password updated successfully!")
        }.addOnFailureListener { e ->
            Log.d(TAG, "updatePassword: ", e)
            progressDialog.dismiss()
            Utils.toast(this, "Failed to update password: ${e.message}")
        }
    }

    // Method to sanitize passwords (for demonstration, could enhance further)
    private fun sanitizePassword(input: String): String {
        // Strip leading/trailing whitespace and check length
        return input.trim().takeIf { it.length >= 6 } ?: ""
    }
}

//method used from YouTube
//https://youtu.be/NW-k3Mbp0tk?si=Y-xLDEW8ha62MrL7
//channel: Code Solutions
