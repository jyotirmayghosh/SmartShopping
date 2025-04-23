package com.jyotirmay.smartshopping

import android.app.Application
import androidx.room.Room
import com.jyotirmay.smartshopping.data.local.AppDatabase
import com.jyotirmay.smartshopping.data.local.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = AppDatabase::class.java,
            name = "shopping_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase): ProductDao = appDatabase.productDao

}