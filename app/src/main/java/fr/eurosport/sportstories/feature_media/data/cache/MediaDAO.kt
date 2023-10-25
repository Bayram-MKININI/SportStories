package fr.eurosport.sportstories.feature_media.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.eurosport.sportstories.feature_media.data.cache.entity.StoryEntity
import fr.eurosport.sportstories.feature_media.data.cache.entity.VideoEntity

@Dao
interface MediaDAO {

    @Query("SELECT * FROM StoryEntity")
    suspend fun getCachedStories(): List<StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>): List<Long>

    @Query("SELECT * FROM VideoEntity")
    suspend fun getCachedVideos(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>): List<Long>
}