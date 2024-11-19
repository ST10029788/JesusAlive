package com.scriptsquad.reflectionsoffaith.StartPage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.scriptsquad.reflectionsoffaith.Utilities.Utils
import com.scriptsquad.reflectionsoffaith.databinding.ActivityRegisterEmailBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

//method used from YouTube
//https://youtu.be/tbh9YaWPKKs?si=qh4THfrN5JV7ZaHL
//SmallAcademy
// Class representing the register using email activity
class Register_Using_Email_Activity : AppCompatActivity() {
    // Late-initialized variables for the activity's binding, Firebase authentication, and progress dialog
    private lateinit var binding: ActivityRegisterEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    // Companion object to hold the TAG for logging
    private companion object {
        private const val TAG = "REGISTER_TAG"
    }

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflate the activity's layout and bind it to the activity
        binding = ActivityRegisterEmailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.ToolbarBackbtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.haveAccountBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.RegisterBtn.setOnClickListener {
            validData()
        }
    }

    // Variables to store the user's input data
    private var name = ""
    private var email = ""
    private var password = ""
    private var cPassword = ""

    // Method to validate the user's input data
    private fun validData() {
        // Get user inputs and sanitize
        name = sanitizeName(binding.nameEt.text.toString().trim())
        email = sanitizeEmail(binding.emailEt.text.toString().trim())
        password = sanitizePassword(binding.passwordEt.text.toString().trim())
        cPassword = sanitizePassword(binding.ConfirmpasswordEt.text.toString().trim())

        // Mask the email and password before logging
        val maskedEmail = maskEmail(email)
        val maskedPassword = maskPassword(password)
        val maskedCPassword = maskPassword(cPassword)

        // Log the sanitized/ masked data for debugging purposes
        Log.d(TAG, "validateData: email :$maskedEmail")
        Log.d(TAG, "validateData: password :$maskedPassword")
        Log.d(TAG, "validateData: confirmPassword :$maskedCPassword")
        // Log the user's input data for debugging purposes


        // Validate email pattern
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.error = "Invalid Email Pattern"
            binding.emailEt.requestFocus()
        } else if (password.isEmpty()) {
            binding.passwordEt.error = "Enter Password"
            binding.passwordEt.requestFocus()
        } else if (cPassword.isEmpty()) {
            binding.ConfirmpasswordEt.error = "Enter Confirm Password"
            binding.ConfirmpasswordEt.requestFocus()
        } else if (password != cPassword) {
            binding.ConfirmpasswordEt.error = "Password Doesn't Match"
            binding.ConfirmpasswordEt.requestFocus()
        } else if (name.isEmpty()) {
            binding.nameEt.error = "Field Required"
            binding.nameEt.requestFocus()
        } else {
            registerUser()
        }
    }


    // Method to mask email (show only first and last few characters)
    private fun maskEmail(email: String): String {
        val parts = email.split("@")
        if (parts.size == 2) {
            val maskedLocal = parts[0].take(1) + "***" + parts[0].takeLast(1)
            return "$maskedLocal@${parts[1]}"
        }
        return "Invalid Email"
    }

    // Method to mask passwords (replace all characters with asterisks)
    private fun maskPassword(password: String): String {
        return "*".repeat(password.length)
    }

    // Method to sanitize name
    private fun sanitizeName(input: String): String {
        // Remove any unwanted characters (e.g., digits, special characters)
        return input.replace("[^\\p{L}\\s]".toRegex(), "")
    }

    // Method to sanitize email
    private fun sanitizeEmail(input: String): String {
        // Remove unwanted characters and return
        return input.replace("[^\\w@.-]".toRegex(), "")
    }

    // Method to sanitize password
    private fun sanitizePassword(input: String): String {
        // Replace unwanted characters (if necessary)
        return input.replace("[^\\w@!#$%^&*()-=_+<>?,.;:']".toRegex(), "")
    }



    // Method to register the user
    private fun registerUser() {
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.setMessage("Registered Successfully")
                MotionToast.createColorToast(
                    this,
                    "Upload Completed!",
                    "Registered Successfully",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
                )
                updateUserInfo()
                progressDialog.dismiss()
                Log.d(TAG, "register user: Register Success")

            }.addOnFailureListener { e ->
                Log.e(TAG, "register user $e")
                progressDialog.dismiss()
                Utils.toast(this, "Failed to create Account due to ${e.message}")
            }
    }

    // Method to update the user's info in the Firebase database
    private fun updateUserInfo() {
        Log.d(TAG, "updateUserInfo")
        progressDialog.setMessage("Saving User Info")

        val timeStamp = Utils.getTimestamp()
        val registerUserEmail = firebaseAuth.currentUser!!.email
        val registeredUserUid = firebaseAuth.uid

        // Create a hash map to store the user's info
        val hashMap = HashMap<String, Any>()
        hashMap["name"] = "$name"
        hashMap["phoneCode"] = ""
        hashMap["phoneNumber"] = ""
        hashMap["profileImageURl"] = ""
        hashMap["dob"] = ""
        hashMap["userType"] = "Email"
        hashMap["typingTo"] = ""
        hashMap["timestamp"] = timeStamp
        hashMap["onlineStatus"] = true
        hashMap["email"] = "$registerUserEmail"
        hashMap["uid"] = "$registeredUserUid"
        hashMap["userMode"] = "${Utils.USER_MODE}"

        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.child(registeredUserUid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                Log.d(TAG, "updateUserInfo: User registered...")
                progressDialog.dismiss()
                startActivity(Intent(this@Register_Using_Email_Activity, Log_In_Using_Email_Activity::class.java))
                finishAffinity()
            }.addOnFailureListener { e ->
                Log.e(TAG, "updateUserInfo", e)
                Utils.toast(this, "Failed to save user info due to ${e.message}")
            }
    }
}
//method used from YouTube
//https://youtu.be/TwHmrZxiPA8?si=KL-4IHmyOM0_qlol
//SmallAcademy