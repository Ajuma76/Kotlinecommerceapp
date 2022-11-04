package com.example.shoplyte.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import com.example.shoplyte.R
import com.example.shoplyte.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
//        validateRegisterDetails()

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)

        val  actionbar = supportActionBar
        if (actionbar != null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }
        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails():Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' })  -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            binding.etFirstName.length() <= 2 -> {
                showErrorSnackBar(resources.getString(R.string.err_first_name_must_have_at_least_3_characters), true)
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            binding.etLastName.length() <= 2 -> {
                showErrorSnackBar(resources.getString(R.string.err_last_name_must_have_at_least_3_characters), true)
                false
            }


            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) ->  {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }


            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            binding.etPassword.length() <= 7 -> {
                showErrorSnackBar(resources.getString(R.string.err_password_must_have_at_least_8_characters), true)
                false
            }

            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }
            binding.etPassword.text.toString().trim{ it <= ' '} != binding.etConfirmPassword.text.toString().trim{ it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !binding.cbTermsAndConditions.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_term_and_condition), true)
                false
            }
            else -> {
//                showErrorSnackBar(resources.getString(R.string.registry_successful), false)
                true
            }
        }

    }

    private fun registerUser() {

        //check with validate function if the entries are valid or not

        if (validateRegisterDetails())
        {
            var progress: ProgressDialog = ProgressDialog(this@RegisterActivity)
            progress.setTitle("Loading")
            progress.setMessage("Please wait...")
            progress.setCanceledOnTouchOutside(false)
            progress.show()



            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            //create an instance and create and register a user with email and password

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener<AuthResult> { task->

                    progress.dismiss()

                    //if registration is successfully done

                    if (task.isSuccessful)
                    {
                        //firebase registered user

                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        showErrorSnackBar(
                            "You are registered successfully. Your user id is ${firebaseUser.uid}",
                            false)

                        FirebaseAuth.getInstance().signOut()


                    }else
                    {
                        //if registration is not successful, show error message
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                })

        }
    }

}