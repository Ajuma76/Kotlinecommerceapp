package com.example.shoplyte.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.shoplyte.activities.LoginActivity
import com.example.shoplyte.activities.RegisterActivity
import com.example.shoplyte.model.User
import com.example.shoplyte.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        //The "user" is a collection name. If the collection is already created, then it will not create the same.

        mFireStore.collection(Constants.USERS)

            //Document ID for user fields. Here Document is the user id
            .document(userInfo.id)

            //Here the userInfo are Fields and The SetOptions is set to merge. It is for if we want to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge()).addOnSuccessListener {

                //Here call a function of base activity for transferring the results to it
                activity.userRegistrationSuccess()

                //coinbase
            }
            .addOnFailureListener {
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user"
                )
            }
    }

    fun getCurrentUserID(): String {

        //get currentUser using FirebaseAuth Instance

        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign currentUserID if it is not null or else it will be blank
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        //Here we pass the collection name from which we want the data
        mFireStore.collection(Constants.USERS)

            //The document Id to get the field of the user
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener{ document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                //Here, we have received the document snapshot which is converted into the User Data model Object
                val user = document.toObject(User::class.java)!!


                //3:41:30
                 val sharedPreferences = activity.getSharedPreferences(
                    Constants.MYSHOPLYTE_PREFERENCE, Context.MODE_PRIVATE)

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                //key: LOGGED_IN_USER
                //Value: Allan Juma
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )

                editor.apply()

                when (activity) {
                    is LoginActivity -> {

                        //call a function of Base Activity for transferring the data to it
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details"
                )
            }
    }
}