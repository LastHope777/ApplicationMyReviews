package com.example.applicationexam

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.Pair


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ReviewsDB"
        private const val TABLE_REVIEWS = "reviews"
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_GENRE = "genre"
        private const val KEY_PLOT = "plot"
        private const val KEY_DIALOGUES = "dialogues"
        private const val KEY_CINEMATOGRAPHY = "cinematography"
        private const val KEY_ACTING = "acting"
        private const val KEY_GENRE_COMPLIANCE = "genre_compliance"
        private const val KEY_ORIGINALITY = "originality"
        private const val KEY_ITOG = "itog"
        private const val KEY_ADDITIONAL_COMMENTS = "additional_comments"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_REVIEWS " +
        "($KEY_ID INTEGER PRIMARY KEY," +
        "$KEY_TITLE TEXT," +
        "$KEY_GENRE TEXT," +
        "$KEY_PLOT INTEGER," +
        "$KEY_DIALOGUES INTEGER," +
        "$KEY_CINEMATOGRAPHY INTEGER," +
        "$KEY_ACTING INTEGER," +
        "$KEY_GENRE_COMPLIANCE INTEGER," +
        "$KEY_ORIGINALITY INTEGER," +
        "$KEY_ITOG INTEGER," +
        "$KEY_ADDITIONAL_COMMENTS TEXT)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_REVIEWS")
        onCreate(db)
    }
    fun clearDatabase()
    {
        val db = this.writableDatabase
        db.delete(TABLE_REVIEWS, null, null)
        db.close()
    }

    fun addReview(
        title: String,
        genre: String,
        plot: Boolean,
        dialogues: Boolean,
        cinematography: Boolean,
        acting: Boolean,
        genreCompliance: Boolean,
        originality: Boolean,
        itog: String,
        additionalComments: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_GENRE, genre)
        values.put(KEY_PLOT, if (plot) 1 else 0)
        values.put(KEY_DIALOGUES, if (dialogues) 1 else 0)
        values.put(KEY_CINEMATOGRAPHY, if (cinematography) 1 else 0)
        values.put(KEY_ACTING, if (acting) 1 else 0)
        values.put(KEY_GENRE_COMPLIANCE, if (genreCompliance) 1 else 0)
        values.put(KEY_ORIGINALITY, if (originality) 1 else 0)
        values.put(KEY_ITOG, itog)
        values.put(KEY_ADDITIONAL_COMMENTS, additionalComments)
        return db.insert(TABLE_REVIEWS, null, values)
    }
    fun updateReview(
        id: Long,
        title: String,
        genre: String,
        plot: Boolean,
        dialogues: Boolean,
        cinematography: Boolean,
        acting: Boolean,
        genreCompliance: Boolean,
        originality: Boolean,
        itog: String,
        additionalComments: String
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_TITLE, title)
            put(KEY_GENRE, genre)
            put(KEY_PLOT, if (plot) 1 else 0)
            put(KEY_DIALOGUES, if (dialogues) 1 else 0)
            put(KEY_CINEMATOGRAPHY, if (cinematography) 1 else 0)
            put(KEY_ACTING, if (acting) 1 else 0)
            put(KEY_GENRE_COMPLIANCE, if (genreCompliance) 1 else 0)
            put(KEY_ORIGINALITY, if (originality) 1 else 0)
            put(KEY_ITOG, itog)
            put(KEY_ADDITIONAL_COMMENTS, additionalComments)
        }
        val rowsUpdated = db.update(TABLE_REVIEWS, values, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsUpdated > 0
    }

    fun getAllReviews(): List<Pair<String, Int>> {
        val reviewList = mutableListOf<Pair<String, Int>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_TITLE, $KEY_ITOG FROM $TABLE_REVIEWS", null)
        try {
            while (cursor.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val itog = cursor.getInt(cursor.getColumnIndex(KEY_ITOG))
                val pair = Pair(title, itog)
                reviewList.add(pair)
            }
        } finally {
            cursor.close()
        }
        return reviewList
    }
    fun findMostFrequentGenre(): String {
        val db = this.readableDatabase
        var mostFrequentGenre = "nothing"
        val genreCounts = mutableMapOf<String, Int>()

        val cursor = db.rawQuery("SELECT $KEY_GENRE FROM $TABLE_REVIEWS", null)
        try {
            while (cursor.moveToNext()) {
                val genre = cursor.getString(cursor.getColumnIndex(KEY_GENRE))
                val count = genreCounts.getOrDefault(genre, 0)
                genreCounts[genre] = count + 1
            }
        } finally {
            cursor.close()
        }

        if (genreCounts.isNotEmpty()) {
            mostFrequentGenre = genreCounts.maxByOrNull { it.value }?.key ?: "nothing"
        }

        return mostFrequentGenre
    }
    fun getHighRatedMovies(): List<Pair<String, Int>> {
        val highRatedMovies = mutableListOf<Pair<String, Int>>()
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT $KEY_TITLE, $KEY_ITOG FROM $TABLE_REVIEWS WHERE $KEY_ITOG > 69 ORDER BY $KEY_ITOG DESC", null)
        try {
            while (cursor.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val itog = cursor.getInt(cursor.getColumnIndex(KEY_ITOG))
                val pair = Pair(title, itog)
                highRatedMovies.add(pair)
            }
        } finally {
            cursor.close()
        }

        return highRatedMovies
    }
    fun getReviews(genre: String): List<Pair<String, Int>> {
        val reviewList = mutableListOf<Pair<String, Int>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_TITLE, $KEY_ITOG, $KEY_GENRE FROM $TABLE_REVIEWS", null)
        try {
            while (cursor.moveToNext()) {

                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val itog = cursor.getInt(cursor.getColumnIndex(KEY_ITOG))
                val genres = cursor.getString(cursor.getColumnIndex(KEY_GENRE))
                val pair = Pair(title, itog)
                if (genres == genre)
                    reviewList.add(pair)
            }
        } finally {
            cursor.close()
        }
        return reviewList
    }
    fun deleteReview(id: Long): Boolean {
        val db = this.writableDatabase
        return db.delete(TABLE_REVIEWS, "$KEY_ID = ?", arrayOf(id.toString())) > 0
    }
    fun getReviewIdByTitle(title: String): Long {
        val db = this.readableDatabase
        var reviewId: Long = -1

        val cursor = db.query(
            TABLE_REVIEWS,
            arrayOf(KEY_ID),
            "$KEY_TITLE = ?",
            arrayOf(title),
            null,
            null,
            null
        )

        try {
            if (cursor.moveToFirst()) {
                reviewId = cursor.getLong(cursor.getColumnIndex(KEY_ID))
            }
        } finally {
            cursor.close()
        }

        return reviewId
    }
    fun getReviewTitleById(reviewId: Long): String? {
        val db = this.readableDatabase
        var reviewTitle: String? = null
        val cursor = db.query(
            TABLE_REVIEWS, // Таблица, из которой получаем данные
            arrayOf(KEY_TITLE), // Список столбцов для выборки
            "$KEY_ID = ?", // Условие выборки
            arrayOf(reviewId.toString()), // Значение аргумента условия выборки
            null, // Группировка данных (не используется)
            null, // Условие ограничения группировки (не используется)
            null // Сортировка результатов (не используется)
        )
        cursor.use { // Использование try-with-resources для закрытия курсора
            if (it.moveToFirst()) { // Если курсор содержит данные
                reviewTitle = it.getString(it.getColumnIndex(KEY_TITLE)) // Получить заголовок
            }
        }
        return reviewTitle
    }

    fun getReviewPlotById(reviewId: Long): Boolean {
        val db = this.readableDatabase
        var plot = false
        val cursor = db.rawQuery("SELECT $KEY_PLOT FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                plot = cursor.getInt(cursor.getColumnIndex(KEY_PLOT)) == 1
            }
        } finally {
            cursor.close()
        }
        return plot
    }
    fun getReviewDialogById(reviewId: Long): Boolean {
        val db = this.readableDatabase
        var dialogues = false
        val cursor = db.rawQuery("SELECT $KEY_DIALOGUES FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                dialogues = cursor.getInt(cursor.getColumnIndex(KEY_DIALOGUES)) == 1
            }
        } finally {
            cursor.close()
        }
        return dialogues
    }
    fun getReviewCinematographyById(reviewId: Long): Boolean {
        val db = this.readableDatabase
        var cinematography = false
        val cursor = db.rawQuery("SELECT $KEY_CINEMATOGRAPHY FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                cinematography = cursor.getInt(cursor.getColumnIndex(KEY_CINEMATOGRAPHY)) == 1
            }
        } finally {
            cursor.close()
        }
        return cinematography
    }
    fun getReviewActorById(reviewId: Long): Boolean {
        val db = this.readableDatabase
        var acting = false
        val cursor = db.rawQuery("SELECT $KEY_ACTING FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                acting = cursor.getInt(cursor.getColumnIndex(KEY_ACTING)) == 1
            }
        } finally {
            cursor.close()
        }
        return acting
    }
    fun getReviewGenreComplianceById(reviewId: Long): Boolean {
        val db = this.readableDatabase
        var genreCompliance = false
        val cursor = db.rawQuery("SELECT $KEY_GENRE_COMPLIANCE FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                genreCompliance = cursor.getInt(cursor.getColumnIndex(KEY_GENRE_COMPLIANCE)) == 1
            }
        } finally {
            cursor.close()
        }
        return genreCompliance
    }
    fun getReviewOriginById(reviewId: Long): Boolean {
        val db = this.readableDatabase
        var originality = false
        val cursor = db.rawQuery("SELECT $KEY_ORIGINALITY FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                originality = cursor.getInt(cursor.getColumnIndex(KEY_ORIGINALITY)) == 1
            }
        } finally {
            cursor.close()
        }
        return originality
    }
    fun getReviewItogById(reviewId: Long): String? {
        val db = this.readableDatabase
        var itog: String? = null
        val cursor = db.rawQuery("SELECT $KEY_ITOG FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                itog = cursor.getString(cursor.getColumnIndex(KEY_ITOG))
            }
        } finally {
            cursor.close()
        }
        return itog
    }
    fun getReviewCommenttsById(reviewId: Long): String? {
        val db = this.readableDatabase
        var additionalComments: String? = null
        val cursor = db.rawQuery("SELECT $KEY_ADDITIONAL_COMMENTS FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                additionalComments = cursor.getString(cursor.getColumnIndex(KEY_ADDITIONAL_COMMENTS))
            }
        } finally {
            cursor.close()
        }
        return additionalComments
    }
    fun getReviewGenreById(reviewId: Long): String? {
        val db = this.readableDatabase
        var genre: String? = null
        val cursor = db.rawQuery("SELECT $KEY_GENRE FROM $TABLE_REVIEWS WHERE $KEY_ID = ?", arrayOf(reviewId.toString()))
        try {
            if (cursor.moveToFirst()) {
                genre = cursor.getString(cursor.getColumnIndex(KEY_GENRE))
            }
        } finally {
            cursor.close()
        }
        return genre
    }
}
