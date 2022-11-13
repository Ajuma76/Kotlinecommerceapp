package com.example.shoplyte.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoplyte.R
import com.example.shoplyte.databinding.ActivityForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : BaseActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar()
    {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)

        val actionBar = supportActionBar
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }

        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }

        binding.btnSubmit.setOnClickListener {
            val email: String = binding.etEmailForgotPw.text.toString().trim { it <= ' ' }
            if (email.isEmpty())
            {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            }else{
                var progress: ProgressDialog = ProgressDialog(this@ForgotPassword)
                progress.setTitle("Loading")
                progress.setMessage("Please wait...")
                progress.setCanceledOnTouchOutside(false)
                progress.show()

                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{ task->
                    progress.dismiss()

                    if (task.isSuccessful)
                    {
                        //show the toast message and finish the forgot password activity to go back to the login activity
                        Toast.makeText(this@ForgotPassword, resources.getString(R.string.email_sent_success),
                            Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
            }
        }
    }
}