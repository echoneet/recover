package dev.echoneet.recover.data.local

import androidx.room.*
import dev.echoneet.recover.data.entity.Issue

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(issueList: List<Issue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(issue: Issue)

    @Query("SELECT * FROM issue")
    suspend fun getAll(): List<Issue>

    @Query("DELETE FROM issue")
    suspend fun deleteAll()
    
    @Update
    suspend fun update(issue: Issue)
}