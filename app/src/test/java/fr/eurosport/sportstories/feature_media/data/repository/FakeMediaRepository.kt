package fr.eurosport.sportstories.feature_media.data.repository

import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.feature_media.domain.model.Media
import fr.eurosport.sportstories.feature_media.domain.model.Sport
import fr.eurosport.sportstories.feature_media.domain.model.Story
import fr.eurosport.sportstories.feature_media.domain.model.Video
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMediaRepository : MediaRepository {

    private val stories = listOf(
        Story(
            id = 7738009,
            title = "Tour de France, arrêt de la L1, Agassi-Sampras, 8 secondes : le plateau du jour",
            author = "John doe",
            dateTimestamp = 1588222335007,
            imageUrl = "https://i.eurosport.com/2020/04/30/2812653-58018086-640-220.jpg",
            sport = Sport(id = 95, name = "Omnisport"),
            teaser = "Ce soir, c'est le weekend ! En ce 46e jour de confinement"
        ),
        Story(
            id = 7738025,
            title = "Mourad de Toulon : \"Tillous-Borde mérite le respect. J'espère que le RCT s'en souviendra",
            author = "Mike Swiss",
            dateTimestamp = 1588224705843,
            imageUrl = "https://i.eurosport.com/2020/04/29/2812626-58017546-640-220.png",
            sport = Sport(id = 44, name = "Rugby"),
            teaser = "Toujours confiné à son domicile, Mourad Boudjellal est un spectateur attentif du monde du rugby."
        ),
        Story(
            id = 7737839,
            title = "Malgré l'arrêt de la L1, le PSG et Lyon pourraient jouer en C1 selon un membre exécutif de l'UEFA",
            author = "Rémy De Souza",
            dateTimestamp = 1588112029433,
            imageUrl = "https://i.eurosport.com/2009/10/28/554648-6892499-128-96.jpg",
            sport = Sport(id = 22, name = "Football"),
            teaser = "Andre Agassi fête mercredi son 50e anniversaire. Pour l'occasion, on vous propose un quiz sur un aspect important de l'oeuvre du \"Kid de Las Vegas\" : le style."
        )
    )
    private val videos = listOf(
        Video(
            id = 777788,
            title = "CHRONIQUE FRITSCH",
            videoUrl = "https://vod-eurosport.akamaized.net/nogeo/2019/10/22/CHRONIQUE_FRITSCH_22102019_V1_22040825-1254400-2300-1024-576.mp4",
            dateTimestamp = 1588224445007,
            sport = Sport(id = 22, name = "Football"),
            thumbUrl = "https://i.eurosport.com/2020/01/27/2763745-57096910-2560-1440.jpg",
            viewCount = 12333
        ),
        Video(
            id = 777799,
            title = "Snooker finish",
            videoUrl = "https://vod-eurosport.akamaized.net/ebu-au/2019/12/08/snookfinish-1269077-700-512-288.mp4",
            dateTimestamp = 1588104445007,
            sport = Sport(id = 33, name = "Snooker"),
            thumbUrl = "https://i.eurosport.com/2018/09/14/2418542-50269610-2560-1440.jpg",
            viewCount = 444
        ),
        Video(
            id = 777689,
            title = "Snooker Trohpey",
            videoUrl = "https://vod-eurosport.akamaized.net/ebu-au/2019/12/08/trophey-1269106-700-512-288.mp4",
            dateTimestamp = 1488133445007,
            sport = Sport(id = 33, name = "Snooker"),
            thumbUrl = "https://i.eurosport.com/2019/12/08/2732922-56480450-2560-1440.jpg",
            viewCount = 1023
        )
    )

    override fun getMedia(): Flow<Resource<Media>> = flow {
        emit(Resource.Success(data = Media(stories, videos)))
    }
}