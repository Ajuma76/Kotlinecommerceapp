package com.example.shoplyte.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.shoplyte.R
import com.example.shoplyte.databinding.ActivityUserProfileBinding
import com.example.shoplyte.model.User
import com.example.shoplyte.util.Constants
import com.example.shoplyte.util.GlideLoader
import java.io.IOException
import java.util.jar.Manifest

@Suppress("DEPRECATION")
class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
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


        //get user details
        var userDetails: User = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS))
        {
            //Get user details from intents as a ParcelableExtra
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userDetails.firstName)

        binding.etLastName.isEnabled = false
        binding.etLastName.setText(userDetails.lastName)

        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userDetails.email)

        binding.ivUserPhoto.setOnClickListener(this@UserProfileActivity)

    }

    override fun onClick(view: View?) {
        if (view != null)
        {
            when (view.id)
            {
                R.id.iv_user_photo -> {

                    //Here, we will check if permission is already allowed or we need to get a request for it
                    //First of all, we will check the READ_EXTERNAL_STORAGE permission. And if it is allowed,
                    if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
//                        showErrorSnackBar("You already have the storage permission!!", false)

                        openFileChooser(Constants.PICK_IMAGE_REQUEST_CODE)
                    }
                    else
                    {
                    //Request permission to be granted to this application.
                    // These Permission must be requested in your manifest, they should not be granted to your app
                    //and they should have protection levels

                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_PERMISSION_CODE)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE)
        {
            //if permission is granted

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Constants.showImageChooser(this@UserProfileActivity)
//                showErrorSnackBar("The storage permission is granted", false)

            }
            else
            {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, resources.getString(R.string.read_storage_permission_denied), Toast.LENGTH_LONG).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE)
            {
                if (data != null && data.data!=null)
                {
                    //the uri of the selected image from phone storage
                    val selectedImageFileUri = data.data!!

//                        binding.ivUserPhoto.setImageURI(selectedImageFileUri)
                    Glide.with(this).load(selectedImageFileUri).into(binding.ivUserPhoto)

                }else{
                    Toast.makeText(this@UserProfileActivity, resources.getString(R.string.image_selection_failed),
                        Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@UserProfileActivity, "Codes dont match",
                    Toast.LENGTH_SHORT).show()
            }
        }

        else if (resultCode == Activity.RESULT_CANCELED)
        {
            //A log is printed when user close or cancel the image selection
            Log.e("Request cancelled", "Image selection cancelled")
            Toast.makeText(this@UserProfileActivity, "Image selection cancelled",
                Toast.LENGTH_SHORT).show()
        }

    }

    fun openFileChooser(PICK_IMAGE_REQUEST_CODE:Int){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,PICK_IMAGE_REQUEST_CODE)
    }




}