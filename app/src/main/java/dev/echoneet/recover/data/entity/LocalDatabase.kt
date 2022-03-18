package dev.echoneet.recover.data.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.echoneet.recover.data.local.IssueDao

@Database(entities = [Issue::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao
}