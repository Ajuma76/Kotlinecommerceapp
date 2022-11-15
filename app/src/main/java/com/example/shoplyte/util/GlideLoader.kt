package com.example.shoplyte.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shoplyte.R
import java.io.IOException

class GlideLoader (val context: Context) {

    fun loadUserPicture(imageUri: Uri, imageView: ImageView)
    {
        try {
            //load the user image in the ImageView
            Glide
                .with(context)
                .load(imageUri) //uri of the image
                .centerCrop() //scale type of image
                .placeholder(R.drawable.ic_user_placeholder) //a default placeholder if the image fails to load
                .into(imageView)

        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
    }
}