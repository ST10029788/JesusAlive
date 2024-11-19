package com.scriptsquad.reflectionsoffaith.StartPage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.scriptsquad.reflectionsoffaith.R
import com.scriptsquad.reflectionsoffaith.Utilities.Utils
import com.scriptsquad.reflectionsoffaith.databinding.ActivityLogInBinding
import java.lang.Exception

//method used from YouTube
//https://youtu.be/H_maapn4Q3Q?si=_1siEM622Nqtcr-s
//channel: TECH_WORLD
class Log_In_Screen : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private companion object {
        private const val TAG = "LOGIN_OPTIONS_TAG"
    }

    // Late-initialized variables for Google sign-in client, progress dialog, and Firebase authentication
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    // Late-initialized variable for the activity's binding
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        // Inflate the activity's layout and bind it to the activity
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()


        // Configure Google sign-in options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        // Initialize BiometricPrompt
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e(TAG, "Authentication error: $errString")
                Utils.toast(this@Log_In_Screen, "Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Authentication succeeded")
                // Proceed to the main screen after successful authentication
                startActivity(Intent(this@Log_In_Screen, Main_Home_Screen::class.java))
                finishAffinity()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.e(TAG, "Authentication failed")
                Utils.toast(this@Log_In_Screen, "Authentication failed")
            }
        })

        // Create prompt info
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Set click listener for biometric button
        binding.LogInBiometricBtn.setOnClickListener {
            authenticateUser ()
        }

        binding.LogInEmailBtn.setOnClickListener {
            startActivity(Intent(this@Log_In_Screen, Log_In_Using_Email_Activity::class.java))
        }

        binding.LogInGoogleBtn.setOnClickListener {
            beginGoogleLogin()
        }
        binding.LogInPhoneBtn.setOnClickListener {
            startActivity(Intent(this@Log_In_Screen, Log_In_Using_Phone_Activity::class.java))
        }

        binding.LogInGuestBtn.setOnClickListener {
            continueAsGuest()
        }
        // Redirect to sign-up screen when "Sign up here" is clicked
        binding.signupredirect.setOnClickListener {
            startActivity(Intent(this@Log_In_Screen, Register_Using_Email_Activity::class.java)) // Replace SignUpActivity with your actual sign-up activity class
        }



    }

    //method used from YouTube
    //https://youtu.be/KJ3ChWp0Qd0?si=IVsBM_mGkUYfSmAF
    //channel: Coding Meet

    private fun authenticateUser () {
        // Check if biometric authentication is available
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Utils.toast(this, "No biometric features available on this device.")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Utils.toast(this, "Biometric features are currently unavailable.")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Utils.toast(this, "No biometric credentials enrolled.")
            }
        }
    }

    private val email: String = "guestuser@gmail.com"
    private val password: String = "1234guest"

    // Method to continue as a guest
    private fun continueAsGuest() {
        Log.d(TAG, "loginUser")
        progressDialog.setTitle("Logging In...")
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        // Sign in as the guest using Firebase authentication
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                Log.e(TAG, "loginUser: Logged In...")
                progressDialog.dismiss()
                startActivity(Intent(this@Log_In_Screen, Main_Home_Screen::class.java))
                finishAffinity()
            }.addOnFailureListener { e ->
                Log.e(TAG, "logInUser", e)
                progressDialog.dismiss()

                Utils.toast(this, "Unable to Login due to ${e.message}")
            }
    }

    // Method to begin the Google login process
    private fun beginGoogleLogin() {
        Log.d(TAG, "beginGoogleLogin:")
        val googleSignInIntent = mGoogleSignInClient.signInIntent
        googleSignnInARL.launch(googleSignInIntent)
    }

    // Activity result launcher for the Google sign-in activity
    private val googleSignnInARL = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> // Check if the result is OK
        Log.d(TAG, "googleSignInARL: ")
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "googleSignInARL: Account ID: ${account.id}")
                firebaseAuthWithGoogleAccount(account.idToken)
            } catch (e: Exception) {
                Log.d(TAG, "googleSignInARL", e)
                Utils.toast(this@Log_In_Screen, "${e.message}")
            }

        } else {
            Utils.toast(this@Log_In_Screen, "Cancelled...!")
        }

    }
    //method used from YouTube
    //https://youtu.be/pP7quzFmWBY?si=WfuxedAC0XF6HfuF
    //channel: Firebase
    private fun firebaseAuthWithGoogleAccount(idToken: String?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: idToken : $idToken")

        // Sign in with the credential
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                if (authResult.additionalUserInfo!!.isNewUser) {
                    Log.d(TAG, "firebaseAuthWithGoogle: New User, Account created....")
                    updateUserInfoDb()
                } else {
                    Log.d(TAG, "firebaseAuthWithGoogle: Existing User, Logged In....")
                    startActivity(Intent(this@Log_In_Screen, Main_Home_Screen::class.java))
                    finishAffinity()
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "firebaseAuthWithGoogleAccount: ", e)
                Utils.toast(this@Log_In_Screen, "${e.message}")

            }
    }

    // Method to update the user's info in the Firebase database
    private fun updateUserInfoDb() {
        Log.d(TAG, "Saving User Info")

        progressDialog.setTitle("Saving User Info")
        progressDialog.show()

        // Get the current timestamp
        val timestamp = Utils.getTimestamp()
        val registerUserEmail = firebaseAuth.currentUser?.email
        val registerUserUid = firebaseAuth.uid
        val name = firebaseAuth.currentUser?.displayName

        val hashMap = HashMap<String, Any?>()

        // Add the user's info to the hash map
        hashMap["name"] = "$name"
        hashMap["phoneCode"] = ""
        hashMap["phoneNumber"] = ""
        hashMap["profileImageURl"] = ""
        hashMap["dob"] = ""
        hashMap["userType"] = "Google"
        hashMap["typingTo"] = ""
        hashMap["timestamp"] = timestamp
        hashMap["onlineStatus"] = true
        hashMap["email"] = "$registerUserEmail"
        hashMap["uid"] = "$registerUserUid"
        hashMap["userMode"] = Utils.USER_MODE

        // set data to firebase

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(registerUserUid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                Log.d(TAG, "updatedUserInfoDb: User info saved")
                progressDialog.dismiss()
                startActivity(Intent(this, Main_Home_Screen::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "updateUserInfoDb", e)
                Utils.toast(this, "Failed to save user info due to ${e.message}")
            }


    }

}