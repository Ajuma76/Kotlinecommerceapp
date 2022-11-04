package com.example.shoplyte.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.shoplyte.R
import com.example.shoplyte.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        //click event assigned to forgot password
        binding.txForgotPassword.setOnClickListener(this)
        //click event assigned to login password
        binding.btnLogin.setOnClickListener(this)
        //click event assigned to forgot password
        binding.register.setOnClickListener(this)
    }

    //In login screen, the clickable components are login button, Forgotpassword and register text

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.tx_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {

                    loginRegisteredUser()

                }

                R.id.register -> {

                    //launch the register screen when user clicks.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            else -> {
//                showErrorSnackBar(resources.getString(R.string.login_successful), false)
                true
            }
        }
    }

    private fun loginRegisteredUser()
    {
        if (validateLoginDetails())
        {
            //show progressdialog
            var progress: ProgressDialog = ProgressDialog(this@LoginActivity)
            progress.setTitle("Loading")
            progress.setMessage("Please wait...")
            progress.setCanceledOnTouchOutside(false)
            progress.show()

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            //Login Usin FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task->

                //hide progress dialog
                progress.dismiss()

                if (task.isSuccessful)
                {

                    showErrorSnackBar(
                        "You are logged in successfully.",
                        false)
                } else{
                    //if login is not successful, show error message
                    showErrorSnackBar(task.exception!!.message.toString(), true)
                }
            }
        }
    }

}