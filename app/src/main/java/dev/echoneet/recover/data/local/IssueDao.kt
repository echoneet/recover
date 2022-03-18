package dev.echoneet.recover.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.echoneet.recover.data.entity.Issue

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(issueList: List<Issue>)

    @Query("SELECT * FROM issue")
    suspend fun getAll(): List<Issue>

    @Query("DELETE FROM issue")
    suspend fun deleteAll();
}