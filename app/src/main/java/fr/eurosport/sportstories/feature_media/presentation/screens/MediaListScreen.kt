package fr.eurosport.sportstories.feature_media.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.GOLDEN_RATIO
import fr.eurosport.sportstories.common.theme.ColorAccent
import fr.eurosport.sportstories.common.theme.DarkBlue
import fr.eurosport.sportstories.common.theme.Grey
import fr.eurosport.sportstories.common.util.ViewState.DataState
import fr.eurosport.sportstories.common.util.ViewState.LoadingState
import fr.eurosport.sportstories.feature_media.presentation.HomeViewModel
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.StoryUI
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.VideoUI

@Composable
fun MediaListScreen(
    viewModel: HomeViewModel,
    onMediaClick: (MediaElementUI) -> Unit = {}
) {

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    when (state) {
        is LoadingState -> {
            LoadingScreen()
        }

        is DataState -> {
            val medias = (state as DataState<List<MediaElementUI>>).data
            LazyVerticalGrid(
                columns = GridCells.Fixed(integerResource(id = R.integer.number_of_columns)),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(medias?.count() ?: 0) { index ->
                        medias?.get(index)?.let { mediaElementUI ->
                            MediaItemScreen(
                                media = mediaElementUI,
                                onMediaClick = onMediaClick
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = ColorAccent,
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun MediaItemScreen(
    modifier: Modifier = Modifier,
    media: MediaElementUI,
    onMediaClick: (MediaElementUI) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { onMediaClick(media) },
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            val (imageRef, iconRef, categoryRef, titleRef, timeRef) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(GOLDEN_RATIO)
                    .constrainAs(imageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.thumbUrl)
                    .build(),
                placeholder = painterResource(id = R.drawable.rectangle_placeholder),
                contentDescription = media.title,
                contentScale = ContentScale.Crop
            )

            if (media.isIconVisible) {
                Icon(
                    modifier = Modifier
                        .size(size = 60.dp)
                        .constrainAs(iconRef) {
                            start.linkTo(imageRef.start)
                            top.linkTo(imageRef.top)
                            end.linkTo(imageRef.end)
                            bottom.linkTo(imageRef.bottom)
                        },
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "Play",
                    tint = Color.White
                )
            }

            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(DarkBlue)
                    .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
                    .constrainAs(categoryRef) {
                        start.linkTo(parent.start, margin = 10.dp)
                        top.linkTo(imageRef.bottom)
                        bottom.linkTo(imageRef.bottom)
                    },
                text = media.category.uppercase(),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .constrainAs(titleRef) {
                        start.linkTo(categoryRef.start)
                        top.linkTo(categoryRef.bottom, margin = 10.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                        width = Dimension.fillToConstraints
                    },
                text = media.title,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                modifier = Modifier.constrainAs(timeRef) {
                    start.linkTo(titleRef.start)
                    top.linkTo(titleRef.bottom, margin = 10.dp)
                },
                text = when (media) {
                    is StoryUI -> media.date
                    is VideoUI -> media.viewCount
                },
                fontSize = 13.sp,
                color = Grey
            )
        }
    }
}