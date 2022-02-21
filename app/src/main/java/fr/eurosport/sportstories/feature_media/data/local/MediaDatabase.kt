package fr.eurosport.sportstories.feature_media.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.eurosport.sportstories.feature_media.data.local.entity.SportEntity
import fr.eurosport.sportstories.feature_media.data.local.entity.StoryEntity
import fr.eurosport.sportstories.feature_media.data.local.entity.VideoEntity

@Database(
    entities = [StoryEntity::class, VideoEntity::class, SportEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MediaDatabase : RoomDatabase() {
    abstract val dao: MediaDAO

    companion object {

        private const val DB_NAME = "media_db"

        @Volatile
        private var instance: MediaDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MediaDatabase::class.java,
            DB_NAME
        ).addTypeConverter(Converters(Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()))
            .fallbackToDestructiveMigration().build()
    }
}