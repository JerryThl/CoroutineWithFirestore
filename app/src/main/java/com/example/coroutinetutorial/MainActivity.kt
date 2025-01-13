package com.example.coroutinetutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinetutorial.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class Person(
    val name:String = "",
    val age:Int = 1
)



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tutorialDocument = Firebase.firestore
            .collection("coroutine")
            .document("tutorial")

        val peter = Person("Peter", 25)

        GlobalScope.launch(Dispatchers.IO) {
            tutorialDocument.set(peter).await()
            val personDocument = tutorialDocument.get().await().toObject(Person::class.java)

            withContext(Dispatchers.Main){
                binding.textView.text = personDocument.toString()
            }
        }

    }

}