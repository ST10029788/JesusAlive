package com.scriptsquad.reflectionsoffaith.StartPage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.scriptsquad.reflectionsoffaith.Account.Forgot_Password_Activity
import com.scriptsquad.reflectionsoffaith.Utilities.Utils
import com.scriptsquad.reflectionsoffaith.databinding.ActivityLogInEmailBinding

// Class representing the email login activity
class Log_In_Using_Email_Activity : AppCompatActivity() {
    // Late-initialized variables for the activity's binding and Firebase authentication
    private lateinit var binding:ActivityLogInEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Companion object to hold the TAG for logging
    private companion object{
        private const val TAG = "LOGIN_TAG"
    }
    // Late-initialized variable for the progress dialog
    private lateinit var progressDialog: ProgressDialog
    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
        binding=ActivityLogInEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
     progressDialog.setTitle("Please wait....")
     progressDialog.setCanceledOnTouchOutside(false)

        // Set up button click listeners
        // Back button click listener
     binding.ToolbarBackbtn.setOnClickListener {
         // Go back to the previous activity
         onBackPressed()

     }
        // Register button click listener
        binding.registerBtn.setOnClickListener {
            val intent = Intent(this@Log_In_Using_Email_Activity, Register_Using_Email_Activity::class.java)
            startActivity(intent)
        }
        // Login button click listener
        binding.logInBtn.setOnClickListener {
            validateData()
        }
        // Forgot password text view click listener
        binding.forgotPasswordTv.setOnClickListener {
       startActivity(Intent(this@Log_In_Using_Email_Activity, Forgot_Password_Activity::class.java))
        }



    }
    // Variables to store the user's email and password
    private var email = ""
    private var password = ""

    // Method to validate the user's input data
    private fun validateData() {
        // Get the user's email and password from the input fields
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        // Log the user's email for debugging purposes
        Log.d(TAG, "validate email: $email")

        // Check if the email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.error = "Invalid Email Format"
            binding.emailEt.requestFocus()
        }
        // Check for password strength (length and character checks)
        else if (password.length < 6) { // Minimum length check, adjust as needed
            binding.passwordEt.error = "Password must be at least 6 characters"
            binding.passwordEt.requestFocus()
        } else {
            // If the data is valid, sanitize inputs before login
            email = sanitizeEmail(email)
            password = sanitizePassword(password)

            // If inputs are sanitized, proceed to login
            loginUser()
        }
    }

    // Method to sanitize email
    private fun sanitizeEmail(input: String): String {
        // Replace unwanted characters and return
        return input.replace("[^\\w@.-]".toRegex(), "")
    }

    // Method to sanitize password (you can add more rules if needed)
    private fun sanitizePassword(input: String): String {
        // Replace unwanted characters (if necessary)
        return input.replace("[^\\w@!#$%^&*()-=_+<>?,.;:']".toRegex(), "")
    }

    // Method to login the user
    private fun loginUser(){
        // Log the login attempt for debugging purposes
        Log.d(TAG,"loginUser")
        progressDialog.setTitle("Logging In...")
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                Log.e(TAG, "loginUser: Logged In...")
                // Dismiss the progress dialog and start the main home screen activity
                progressDialog.dismiss()
                startActivity(Intent(this@Log_In_Using_Email_Activity, Main_Home_Screen::class.java))
                finishAffinity()
            }.addOnFailureListener { e ->
                // Log the failed login for debugging purposes
                Log.e(TAG, "logInUser", e)
                progressDialog.dismiss()

                // Dismiss the progress dialog and show an error message
                Utils.toast(this, "Unable to Login due to ${e.message}")
            }
    }

}
//method used from YouTube
//https://youtu.be/hcEqX5lcuUc?si=am4dOV8R-L12w0Hk
//channel: Code With Arvind