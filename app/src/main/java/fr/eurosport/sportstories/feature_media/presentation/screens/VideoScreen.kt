@file:UnstableApi

package fr.eurosport.sportstories.feature_media.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.GOLDEN_RATIO
import fr.eurosport.sportstories.feature_media.presentation.HomeViewModel
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.VideoUI

@Composable
fun VideoScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val video = viewModel.selectedMedia.collectAsStateWithLifecycle().value as VideoUI

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (backIconRef, playerRef) = createRefs()

        IconButton(
            modifier = modifier
                .then(Modifier.size(36.dp))
                .clip(CircleShape)
                .background(Color.Black)
                .padding(10.dp)
                .constrainAs(backIconRef) {
                    start.linkTo(parent.start, margin = 15.dp)
                    top.linkTo(parent.top, margin = 15.dp)
                },
            onClick = {
                onNavigateBack.invoke()
            }) {
            Icon(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(GOLDEN_RATIO)
            .constrainAs(playerRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) {
            VideoPlayer(
                uri = video.videoUrl.toUri()
            )
        }
    }
}