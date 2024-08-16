package com.example.crudapp.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.hotelloginsystem.utils.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@Composable

class SharedViewModel (): ViewModel () {

    fun AddData(
        userData: UserData,
        context: Context


    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection(userData.toString())
            .document(userData.userID)

        try {
            fireStoreRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()

                }
        } catch (e: Exception) {

            Toast.makeText(
                context, e.message,

                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun retrieveData(
        userID:String,
        context: Context,
        data: (UserData) -> Unit

    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    if(it.exists()){
                        val userData = it.toObject<UserData>()!!
                        data(userData)


                    } else{
                        Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()


                    }


                }
        } catch (e: Exception) {

            Toast.makeText(
                context, e.message,

                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun deleteData(
        userID:String,
        navController: NavController,
        context: Context,
        backtoMainScreen :() -> Unit

    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("user")
            .document(userID)

        try {
            fireStoreRef.delete()
                .addOnSuccessListener{
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()


                }
        } catch (e: Exception) {

            Toast.makeText(
                context, e.message,

                Toast.LENGTH_SHORT
            ).show()
        }

    }
}