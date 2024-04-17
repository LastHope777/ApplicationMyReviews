package com.example.applicationexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

class MainMenu : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        dbHelper = DBHelper(this)
        val MostUsedGenre = dbHelper.findMostFrequentGenre()



        val myReviews: ImageButton = findViewById(R.id.MyReviewsNotActivity)
        myReviews.setOnClickListener{
            val intent = Intent(this, MyReviews::class.java)
            startActivity(intent)
        }
        val buttonPlus: ImageButton = findViewById(R.id.ButtonPlus)
        buttonPlus.setOnClickListener{
            val intent = Intent(this, AddReview::class.java)
            startActivity(intent)
        }
        val buttonFavoritegenre: ImageButton = findViewById(R.id.imageButton4)
        buttonFavoritegenre.setOnClickListener{
            val intent = Intent(this, FavoriteGenre::class.java)
            intent.putExtra("MostUsedGenre", MostUsedGenre)
            startActivity(intent)
        }
        val buttonFavoritefilm: ImageButton = findViewById(R.id.imageButton5)
        buttonFavoritefilm.setOnClickListener{
            val intent = Intent(this, FavoriteFilm::class.java)
            startActivity(intent)
        }
        if (MostUsedGenre == "Детектив")
            buttonFavoritegenre.setImageResource(R.drawable.detektiv)
        else if (MostUsedGenre == "Комедия")
            buttonFavoritegenre.setImageResource(R.drawable.comedy)
        else if (MostUsedGenre == "Приключение")
            buttonFavoritegenre.setImageResource(R.drawable.adventure)
        else if (MostUsedGenre == "Ужасы")
            buttonFavoritegenre.setImageResource(R.drawable.horror)
        else if (MostUsedGenre == "Триллер")
            buttonFavoritegenre.setImageResource(R.drawable.triller)
        else if (MostUsedGenre == "Боевик")
            buttonFavoritegenre.setImageResource(R.drawable.boewik)
        else if (MostUsedGenre == "Мультфильм")
            buttonFavoritegenre.setImageResource(R.drawable.multfilm)
        else if (MostUsedGenre == "Фантастика")
            buttonFavoritegenre.setImageResource(R.drawable.fantastic)
        else if (MostUsedGenre == "Фэнтези")
            buttonFavoritegenre.setImageResource(R.drawable.fantasy)
        else
            buttonFavoritegenre.setImageResource(R.drawable.inoygenre)


        val buttonHelp: ImageButton = findViewById(R.id.imageButton)
        val first: ImageView = findViewById(R.id.imageView8)
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
                    val second: ImageView = findViewById(R.id.imageView7)
                    second.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                    val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
                    animationSet.addAnimation(fadeInAnimation)
                    animationSet.addAnimation(shakeAnimation)
                    second.startAnimation(animationSet)
                    second.setOnTouchListener { _, event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                second.startAnimation(fadeOutAnimation)
                                second.visibility = View.INVISIBLE
                                val third: ImageView = findViewById(R.id.imageView9)
                                third.visibility = View.VISIBLE
                                val animationSet = AnimationSet(true)
                                val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                                val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
                                animationSet.addAnimation(fadeInAnimation)
                                animationSet.addAnimation(shakeAnimation)
                                third.startAnimation(animationSet)
                                third.setOnTouchListener { _, event ->
                                    when (event.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            third.startAnimation(fadeOutAnimation)
                                            third.visibility = View.INVISIBLE
                                            true
                                        }
                                        else -> false
                                }


                                    }
                                true

                            }
                            else -> false
                            }


                            }
                    true
                }
                else -> false
            }
        }
    }

}