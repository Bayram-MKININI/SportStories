package fr.eurosport.sportstories.feature_media.presentation.modelUi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MediaElementUI : Parcelable {
    abstract val id: Int
    abstract val title: String
    abstract val category: String
    abstract val date: String
    abstract val isIconVisible: Boolean
    abstract val thumbUrl: String

    data class StoryUI(
        override val id: Int = 0,
        override val title: String = "",
        override val date: String = "",
        override val category: String = "",
        override val thumbUrl: String = "",
        val author: String = "",
        val teaser: String = "",
        override val isIconVisible: Boolean = false
    ) : MediaElementUI()

    data class VideoUI(
        override val id: Int = 0,
        override val title: String = "",
        override val date: String = "",
        override val category: String = "",
        override val thumbUrl: String = "",
        val videoUrl: String = "",
        val viewCount: String = "",
        override val isIconVisible: Boolean = false
    ) : MediaElementUI()
}