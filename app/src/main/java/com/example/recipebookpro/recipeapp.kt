package com.example.recipebookpro

import com.example.recipebookpro.database.RecipeDatabase


import android.app.Application
import androidx.room.Room

class RecipeApp : Application() {

    companion object {
        lateinit var database: RecipeDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        ).build()
    }
}