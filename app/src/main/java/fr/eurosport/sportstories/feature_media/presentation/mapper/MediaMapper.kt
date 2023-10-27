package fr.eurosport.sportstories.feature_media.presentation.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.util.convertSecondsToTimeString
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement.Story
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement.Video
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.StoryUI
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.VideoUI
import javax.inject.Inject

class MediaMapper @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun map(media: MediaElement) = when (media) {
        is Story -> StoryUI(
            id = media.id,
            title = media.title,
            date = context.convertSecondsToTimeString(
                media.dateTimestamp,
                System.currentTimeMillis()
            ),
            category = media.sport.name,
            author = media.author,
            thumbUrl = media.imageUrl,
            teaser = media.teaser,
            isIconVisible = false
        )

        is Video -> VideoUI(
            id = media.id,
            title = media.title,
            category = media.sport.name,
            videoUrl = media.videoUrl,
            thumbUrl = media.thumbUrl,
            viewCount = context.getString(
                R.string.video_description,
                media.viewCount
            ),
            isIconVisible = true
        )
    }
}