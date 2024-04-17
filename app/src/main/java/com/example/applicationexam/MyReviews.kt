package com.example.applicationexam

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MyReviews : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reviews)


        dbHelper = DBHelper(this)
        val dataList: List<Pair<String, Int>> = dbHelper.getAllReviews() // Получение данных из базы данных

        val listView: ListView = findViewById(R.id.listView)
        val adapter = CustomAdapter(this, dataList)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val reviewTitle = (listView.adapter as CustomAdapter).getItem(position) as Pair<String, Int>
            val reviewId = dbHelper.getReviewIdByTitle(reviewTitle.first)
            val intent = Intent(this, ChangeReview::class.java)
            intent.putExtra("reviewId", reviewId)
            startActivity(intent)
        }
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
        val buttonHelp: ImageButton = findViewById(R.id.imageButton)
        val first: ImageView = findViewById(R.id.imageView10)
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

        //dbHelper.clearDatabase()
    }
    private fun refreshData(){
        dbHelper.deleteReview(1)
        val dataList: List<Pair<String, Int>> = dbHelper.getAllReviews() // Получение данных из базы данных
    }
}

