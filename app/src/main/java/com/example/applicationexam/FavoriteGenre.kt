package com.example.applicationexam

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class FavoriteGenre: AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private var MostUsedGenre: String = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_genre)
        dbHelper = DBHelper(this)
        MostUsedGenre = intent.getStringExtra("MostUsedGenre").toString()
        val dataList: List<Pair<String, Int>> = dbHelper.getReviews(MostUsedGenre) // Получение данных из базы данных

        val listView: ListView = findViewById(R.id.listView)
        val adapter = CustomAdapter(this, dataList)
        listView.adapter = adapter

        val buttonPlus: ImageButton = findViewById(R.id.ButtonPlus)
        buttonPlus.setOnClickListener{
            val intent = Intent(this, AddReview::class.java)
            startActivity(intent)
        }

        val buttonMain: ImageButton = findViewById(R.id.MainMenuNotActivity)
        buttonMain.setOnClickListener{
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

        val buttonReview: ImageButton = findViewById(R.id.MyReviewsActive)
        buttonReview.setOnClickListener{
            val intent = Intent(this, MyReviews::class.java)
            startActivity(intent)
        }
        val buttonHelp: ImageButton = findViewById(R.id.imageButton)
        val first: ImageView = findViewById(R.id.imageView13)
        buttonHelp.setOnClickListener {
            first.visibility = View.VISIBLE
            val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
            val animationSet = AnimationSet(true)
            animationSet.addAnimation(fadeInAnimation)
            animationSet.addAnimation(shakeAnimation)
            first.startAnimation(animationSet)
        }
        first.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
                    first.startAnimation(fadeOutAnimation)
                    first.visibility = View.INVISIBLE
                    true
                }

                else -> false
            }
        }
    }
}