package fr.eurosport.sportstories.feature_media.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.eurosport.sportstories.feature_media.data.local.entity.SportEntity
import fr.eurosport.sportstories.feature_media.data.local.entity.StoryEntity
import fr.eurosport.sportstories.feature_media.data.local.entity.VideoEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MediaDAOTest {

    private lateinit var database: MediaDatabase
    private lateinit var dao: MediaDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MediaDatabase::class.java
        ).allowMainThreadQueries()
            .addTypeConverter(
                Converters(
                    Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    private val stories = listOf(
        StoryEntity(
            id = 7738009,
            title = "Tour de France, arrêt de la L1, Agassi-Sampras, 8 secondes : le plateau du jour",
            author = "John doe",
            dateTimestamp = 1588222335007,
            imageUrl = "https://i.eurosport.com/2020/04/30/2812653-58018086-640-220.jpg",
            sport = SportEntity(id = 95, name = "Omnisport"),
            teaser = "Ce soir, c'est le weekend ! En ce 46e jour de confinement"
        ),
        StoryEntity(
            id = 7738025,
            title = "Mourad de Toulon : \"Tillous-Borde mérite le respect. J'espère que le RCT s'en souviendra",
            author = "Mike Swiss",
            dateTimestamp = 1588224705843,
            imageUrl = "https://i.eurosport.com/2020/04/29/2812626-58017546-640-220.png",
            sport = SportEntity(id = 44, name = "Rugby"),
            teaser = "Toujours confiné à son domicile, Mourad Boudjellal est un spectateur attentif du monde du rugby."
        ),
        StoryEntity(
            id = 7737839,
            title = "Malgré l'arrêt de la L1, le PSG et Lyon pourraient jouer en C1 selon un membre exécutif de l'UEFA",
            author = "Rémy De Souza",
            dateTimestamp = 1588112029433,
            imageUrl = "https://i.eurosport.com/2009/10/28/554648-6892499-128-96.jpg",
            sport = SportEntity(id = 22, name = "Football"),
            teaser = "Andre Agassi fête mercredi son 50e anniversaire. Pour l'occasion, on vous propose un quiz sur un aspect important de l'oeuvre du \"Kid de Las Vegas\" : le style."
        )
    )
    private val videos = listOf(
        VideoEntity(
            id = 777788,
            title = "CHRONIQUE FRITSCH",
            videoUrl = "https://vod-eurosport.akamaized.net/nogeo/2019/10/22/CHRONIQUE_FRITSCH_22102019_V1_22040825-1254400-2300-1024-576.mp4",
            dateTimestamp = 1588224445007,
            sport = SportEntity(id = 22, name = "Football"),
            thumbUrl = "https://i.eurosport.com/2020/01/27/2763745-57096910-2560-1440.jpg",
            viewCount = 12333
        ),
        VideoEntity(
            id = 777799,
            title = "Snooker finish",
            videoUrl = "https://vod-eurosport.akamaized.net/ebu-au/2019/12/08/snookfinish-1269077-700-512-288.mp4",
            dateTimestamp = 1588104445007,
            sport = SportEntity(id = 33, name = "Snooker"),
            thumbUrl = "https://i.eurosport.com/2018/09/14/2418542-50269610-2560-1440.jpg",
            viewCount = 444
        ),
        VideoEntity(
            id = 777689,
            title = "Snooker Trohpey",
            videoUrl = "https://vod-eurosport.akamaized.net/ebu-au/2019/12/08/trophey-1269106-700-512-288.mp4",
            dateTimestamp = 1488133445007,
            sport = SportEntity(id = 33, name = "Snooker"),
            thumbUrl = "https://i.eurosport.com/2019/12/08/2732922-56480450-2560-1440.jpg",
            viewCount = 1023
        )
    )

    @Test
    fun writeAndReadStoriesInDB() = runBlocking {

        dao.insertStories(stories)
        val returnedStories = dao.getCachedStories()

        Assert.assertEquals(3, returnedStories.size)
        Assert.assertTrue(returnedStories.containsAll(stories))
    }

    @Test
    fun writeAndReadVideosInDB() = runBlocking {

        dao.insertVideos(videos)
        val returnedVideos = dao.getCachedVideos()

        Assert.assertEquals(3, returnedVideos.size)
        Assert.assertTrue(returnedVideos.containsAll(videos))
    }
}