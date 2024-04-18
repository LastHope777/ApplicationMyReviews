package com.example.applicationexam

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.AdapterView
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog


class AddReview : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)
        dbHelper = DBHelper(this)
        val buttonreviews: ImageButton = findViewById(R.id.MyReviewsNotActivity)
        buttonreviews.setOnClickListener {
            val intent = Intent(this, MyReviews::class.java)
            startActivity(intent)
        }
        val home: ImageButton = findViewById(R.id.Home)
        home.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
        val spinner: Spinner = findViewById(R.id.spinner2)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.genres, // массив строк из ресурсов
            R.layout.spinner_layout
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Применение адаптера к спиннеру
        spinner.adapter = adapter

// Обработчик выбора элемента спиннера
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
// Действия при выборе элемента
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    applicationContext,
                    "Выбран элемент: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
// Действия при отсутствии выбранного элемента
            }
        }

        //Сюжет
        var sugetcheck: Boolean = false
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

        //Диалоги
        var dialogcheck: Boolean = false
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
        var syomkacheck: Boolean = false
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
        var actorcheck: Boolean = false
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
        var genrecheck: Boolean = false
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
        var origincheck: Boolean = false
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


        //EditText (Название)
        val name: EditText = findViewById(R.id.editTextText)
        var flag: Int = 0
        name.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && flag == 0) {
                name.setText("")
                flag++
            }
        }

        //EditText (Эмоции)
        val emotion: EditText = findViewById(R.id.editText)
        val EmotionText = emotion.text


        //Сохранение
        val savebutton: ImageButton = findViewById(R.id.imageButton2)
        savebutton.setOnClickListener {
            if (name.text.toString() == "" || name.text.toString() == "Введите название") {
                Toast.makeText(this, "Необходимо ввести название!", Toast.LENGTH_SHORT).show()

            }
            else {
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

                dbHelper.addReview(
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
// После добавления рецензии вы можете выполнить другие действия, например, показать сообщение об успешном сохранении или перезагрузить список рецензий
            }
        }
        val buttonHelp: ImageButton = findViewById(R.id.imageButton)
        val first: ImageView = findViewById(R.id.imageView0)
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