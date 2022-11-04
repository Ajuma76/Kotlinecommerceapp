package com.example.shoplyte.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.shoplyte.R
import com.example.shoplyte.databinding.ActivityBaseBinding
import com.example.shoplyte.databinding.ProgressDialogueBinding
import com.google.android.material.snackbar.Snackbar
import java.util.logging.ErrorManager

open class BaseActivity : AppCompatActivity() {

//    private lateinit var mProgressDialog: Dialog

    var tv_progress_text: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        tv_progress_text = findViewById(R.id.tv_progress_text)

//        showErrorSnackBar()
    }

     fun showErrorSnackBar(message: String, errorMessage: Boolean) {
         val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
         val snackBarView = snackBar.view

         if (errorMessage) {
             snackBarView.setBackgroundColor(
                 ContextCompat.getColor(
                     this@BaseActivity, R.color.colorSnackBarError
                 )
             )
         }else
         {
             snackBarView.setBackgroundColor(
                 ContextCompat.getColor(
                     this@BaseActivity, R.color.colorSnackBarSuccess
                 )
             )
         }
         snackBar.show()
    }

//    fun showProgressDialog(text: String){
//
//        mProgressDialog = Dialog(this)
//
//        //set the screen content from a layout resource.-->
//        //the resource would be inflated , adding all top level views to the screen-->
//
//        mProgressDialog.setContentView(R.layout.progress_dialogue)
//
//        mProgressDialog.tv_progress_text.text = text
//
//        mProgressDialog.setCancelable(false)
//        mProgressDialog.setCanceledOnTouchOutside(false)
//
//        //start dialog and display on screen-->
//        mProgressDialog.show()
//
//    }
}