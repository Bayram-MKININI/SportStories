package fr.eurosport.sportstories.feature_media.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import fr.eurosport.sportstories.common.components.AnimatedButton
import fr.eurosport.sportstories.common.theme.DarkBlue
import fr.eurosport.sportstories.common.theme.Grey
import fr.eurosport.sportstories.common.theme.Teal_200
import fr.eurosport.sportstories.feature_media.presentation.HomeViewModel
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.StoryUI

@Composable
fun StoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigateBack: () -> Unit = {},
    onShare: () -> Unit = {}
) {
    val story = viewModel.selectedMedia.collectAsStateWithLifecycle().value as StoryUI
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (imageRef, backIconRef, shareIconRef, categoryRef, titleRef, authorRef, timeRef, bodyRef) = createRefs()
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(imageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(story.thumbUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.rectangle_placeholder),
            contentDescription = story.title,
            contentScale = ContentScale.FillWidth
        )

        IconButton(
            modifier = modifier
                .then(Modifier.size(24.dp))
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

        IconButton(
            modifier = modifier
                .then(Modifier.size(24.dp))
                .constrainAs(shareIconRef) {
                    end.linkTo(parent.end, margin = 15.dp)
                    top.linkTo(parent.top, margin = 15.dp)
                },
            onClick = {
                onShare.invoke()
            }) {
            Icon(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.share),
                contentDescription = "Share",
                tint = Color.White
            )
        }

        Text(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
                .background(DarkBlue)
                .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
                .constrainAs(categoryRef) {
                    start.linkTo(parent.start, margin = 10.dp)
                    top.linkTo(imageRef.bottom)
                    bottom.linkTo(imageRef.bottom)
                },
            text = story.category.uppercase(),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = modifier
                .padding(end = 10.dp)
                .constrainAs(titleRef) {
                    start.linkTo(categoryRef.start)
                    top.linkTo(categoryRef.bottom, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
            text = story.title,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = modifier
                .constrainAs(authorRef) {
                    start.linkTo(titleRef.start)
                    top.linkTo(titleRef.bottom, margin = 10.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = context.getString(R.string.buy),
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    modifier = modifier.padding(start = 5.dp),
                    text = story.author,
                    color = Teal_200,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            AnimatedButton(
                modifier = modifier.padding(top = 5.dp)
            )
        }

        Text(
            modifier = modifier.constrainAs(timeRef) {
                start.linkTo(titleRef.start)
                top.linkTo(authorRef.bottom, margin = 10.dp)
            },
            text = story.date,
            fontSize = 13.sp,
            color = Grey
        )

        Text(
            modifier = modifier
                .constrainAs(bodyRef) {
                    start.linkTo(titleRef.start)
                    top.linkTo(timeRef.bottom, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
            text = story.teaser,
            fontSize = 12.sp
        )
    }
}