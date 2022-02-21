package fr.eurosport.sportstories.feature_media.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.eurosport.sportstories.feature_media.data.local.entity.StoryEntity
import fr.eurosport.sportstories.feature_media.data.local.entity.VideoEntity

@Dao
interface MediaDAO {

    @Query("SELECT * FROM StoryEntity")
    suspend fun getCachedStories(): List<StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Query("SELECT * FROM VideoEntity")
    suspend fun getCachedVideos(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)
}