package com.example.applicationexam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ImageView

class ChangeReview : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private var reviewId: Long = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_review)

        dbHelper = DBHelper(this)
        val spinner: Spinner = findViewById(R.id.spinner2)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.genres, // массив строк из ресурсов
            R.layout.spinner_layout
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Применение адаптера к спиннеру
        spinner.adapter = adapter
// Получить reviewId из предыдущей активности
        reviewId = intent.getLongExtra("reviewId", 0)

        val deleteReviewButton: ImageButton = findViewById(R.id.imageButton_delete)
        deleteReviewButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val buttonreviews: ImageButton = findViewById(R.id.MyReviewsNotActivity)
        buttonreviews.setOnClickListener{
            val intent = Intent(this, MyReviews::class.java)
            startActivity(intent)
        }
        val home: ImageButton = findViewById(R.id.Home)
        home.setOnClickListener{
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
        val adding: ImageButton = findViewById(R.id.ButtonPlus)
        adding.setOnClickListener{
            val intent = Intent(this, AddReview::class.java)
            startActivity(intent)
        }


        val Name: EditText = findViewById(R.id.editTextText)
        val reviewTitle = dbHelper.getReviewTitleById(reviewId)
        Name.setText(reviewTitle)

        val plott = dbHelper.getReviewPlotById(reviewId)
        val Plot: ImageButton = findViewById(R.id.SugetTrue)
        if (plott == true)
            Plot.visibility = View.VISIBLE
        val dialogg = dbHelper.getReviewDialogById(reviewId)
        val Dialog: ImageButton = findViewById(R.id.DialogTrue)
        if (dialogg == true)
            Dialog.visibility = View.VISIBLE
        val cinema = dbHelper.getReviewCinematographyById(reviewId)
        val Cinema: ImageButton = findViewById(R.id.SyomkaTrue)
        if (cinema == true)
            Cinema.visibility = View.VISIBLE
        val actor = dbHelper.getReviewActorById(reviewId)
        val Actor: ImageButton = findViewById(R.id.ActorTrue)
        if (actor == true)
            Actor.visibility = View.VISIBLE
        val genreg = dbHelper.getReviewGenreComplianceById(reviewId)
        val GEnreg: ImageButton = findViewById(R.id.GenreTrue)
        if (genreg == true)
            GEnreg.visibility = View.VISIBLE
        val origin = dbHelper.getReviewOriginById(reviewId)
        val Origin: ImageButton = findViewById(R.id.OriginTrue)
        if (origin == true)
            Origin.visibility = View.VISIBLE
        val itog = dbHelper.getReviewItogById(reviewId)
        val Itog: EditText = findViewById(R.id.editTextNumber)
        Itog.setText(itog)
        val comment = dbHelper.getReviewCommenttsById(reviewId)
        val Comment: EditText = findViewById(R.id.editText)
        Comment.setText(comment)

        val genre = dbHelper.getReviewGenreById(reviewId)
        if (genre != null) {
            val genreIndex = adapter.getPosition(genre)
            spinner.setSelection(genreIndex)
        }


        var sugetcheck = dbHelper.getReviewPlotById(reviewId)
        val sugetbuttonf: ImageButton = findViewById(R.id.SugetFalse)
        val sugetbuttont: ImageButton = findViewById(R.id.SugetTrue)
        sugetbuttonf.setOnClickListener {
            sugetcheck = true
            sugetbuttonf.visibility = View.INVISIBLE
            sugetbuttont.visibility = View.VISIBLE
        }
        sugetbuttont.setOnClickListener {
            sugetcheck = false
            sugetbuttonf.visibility = View.VISIBLE
            sugetbuttont.visibility = View.INVISIBLE
        }
        var dialogcheck = dbHelper.getReviewDialogById(reviewId)
        val dialogbuttonf: ImageButton = findViewById(R.id.DialogFalse)
        val dialogbuttont: ImageButton = findViewById(R.id.DialogTrue)
        dialogbuttonf.setOnClickListener {
            dialogcheck = true
            dialogbuttonf.visibility = View.INVISIBLE
            dialogbuttont.visibility = View.VISIBLE
        }
        dialogbuttont.setOnClickListener {
            dialogcheck = false
            dialogbuttonf.visibility = View.VISIBLE
            dialogbuttont.visibility = View.INVISIBLE
        }

        //Съёмка
        var syomkacheck = dbHelper.getReviewCinematographyById(reviewId)
        val syomkabuttonf: ImageButton = findViewById(R.id.SyomkaFalse)
        val syomkabuttont: ImageButton = findViewById(R.id.SyomkaTrue)
        syomkabuttonf.setOnClickListener {
            syomkacheck = true
            syomkabuttonf.visibility = View.INVISIBLE
            syomkabuttont.visibility = View.VISIBLE
        }
        syomkabuttont.setOnClickListener {
            syomkacheck = false
            syomkabuttonf.visibility = View.VISIBLE
            syomkabuttont.visibility = View.INVISIBLE
        }

        //Актёрская игра
        var actorcheck = dbHelper.getReviewActorById(reviewId)
        val actorbuttonf: ImageButton = findViewById(R.id.ActorFalse)
        val actorbuttont: ImageButton = findViewById(R.id.ActorTrue)
        actorbuttonf.setOnClickListener {
            actorcheck = true
            actorbuttonf.visibility = View.INVISIBLE
            actorbuttont.visibility = View.VISIBLE
        }
        actorbuttont.setOnClickListener {
            actorcheck = false
            actorbuttonf.visibility = View.VISIBLE
            actorbuttont.visibility = View.INVISIBLE
        }

        //Соответствие жанру
        var genrecheck = dbHelper.getReviewGenreComplianceById(reviewId)
        val genrebuttonf: ImageButton = findViewById(R.id.GenreFalse)
        val genrebuttont: ImageButton = findViewById(R.id.GenreTrue)
        genrebuttonf.setOnClickListener {
            genrecheck = true
            genrebuttonf.visibility = View.INVISIBLE
            genrebuttont.visibility = View.VISIBLE
        }
        genrebuttont.setOnClickListener {
            genrecheck = false
            genrebuttonf.visibility = View.VISIBLE
            genrebuttont.visibility = View.INVISIBLE
        }

        //Оригинальность
        var origincheck = dbHelper.getReviewOriginById(reviewId)
        val originbuttonf: ImageButton = findViewById(R.id.OriginFalse)
        val originbuttont: ImageButton = findViewById(R.id.OriginTrue)
        originbuttonf.setOnClickListener {
            origincheck = true
            originbuttonf.visibility = View.INVISIBLE
            originbuttont.visibility = View.VISIBLE
        }
        originbuttont.setOnClickListener {
            origincheck = false
            originbuttonf.visibility = View.VISIBLE
            originbuttont.visibility = View.INVISIBLE
        }
        //EditText (Эмоции)
        val emotion: EditText = findViewById(R.id.editText)
        val EmotionText = emotion.text

        //Название
        val name: EditText = findViewById(R.id.editTextText)



        val savebutton: ImageButton = findViewById(R.id.imageButton2)
        savebutton.setOnClickListener {
            //Итоговая оценка
            val itognumber: EditText = findViewById(R.id.editTextNumber)
            val rating = itognumber.getText().toString()
            val ratingInt = rating.toInt()
            if (ratingInt < 0)
                itognumber.setText("0")
            if (ratingInt > 100)
                itognumber.setText("100")

            val title = name.text.toString()
            val genre = spinner.selectedItem.toString()
            val plot = sugetcheck // Значение для сюжета
            val dialogues = dialogcheck // Значение для диалогов
            val cinematography = syomkacheck // Значение для съемки
            val acting = actorcheck // Значение для актерской игры
            val genreCompliance = genrecheck // Значение для соответствия жанру
            val originality = origincheck // Значение для оригинальности
            val itog = itognumber.text.toString()
            val additionalComments = EmotionText.toString()// Дополнительные комментарии


                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setMessage("Вы действительно хотите внести изменения?")
                    .setCancelable(false)
                    .setPositiveButton("Да") { dialog, _ ->
                        dbHelper.updateReview(
                            reviewId,
                            title,
                            genre,
                            plot,
                            dialogues,
                            cinematography,
                            acting,
                            genreCompliance,
                            originality,
                            itog,
                            additionalComments
                        )
                        dialog.dismiss()
                        Toast.makeText(this, "Изменения успешно сохранены!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MyReviews::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton("Нет") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()



        }

        val buttonHelp: ImageButton = findViewById(R.id.imageButton)
        val first: ImageView = findViewById(R.id.imageView11)
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

    private fun showDeleteConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Вы точно хотите удалить рецензию?")
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, _ ->
// Удалить рецензию

                deleteReview(reviewId)
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteReview(reviewId: Long) {
        val rowsDeleted = dbHelper.deleteReview(reviewId)
            val intent = Intent(this, MyReviews::class.java)
            intent.putExtra("message", "Рецензия успешно удалена!")
            startActivity(intent)

    }


}
