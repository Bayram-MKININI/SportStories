package fr.eurosport.sportstories.feature_media.presentation

import fr.eurosport.sportstories.feature_media.domain.model.MediaElement

data class MediaVMState(
    val mediaElements: List<MediaElement> = listOf(),
)