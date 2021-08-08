package com.tcscorp.forwardsmstoemail.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.tcscorp.forwardsmstoemail.data.MessageDao
import com.tcscorp.forwardsmstoemail.data.MessageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun providesWorkManager(@ApplicationContext appContext: Context): WorkManager =
        WorkManager.getInstance(appContext)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): MessageDatabase = Room
        .databaseBuilder(app, MessageDatabase::class.java, "message_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideMessageDao(db: MessageDatabase): MessageDao = db.messageDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope