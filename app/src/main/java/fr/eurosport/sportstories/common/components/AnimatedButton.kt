package fr.eurosport.sportstories.common.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.eurosport.sportstories.common.components.AnimatedButtonState.IDLE
import fr.eurosport.sportstories.common.components.AnimatedButtonState.PRESSED

@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit = {}
) {
    var buttonState by remember { mutableStateOf(IDLE) }
    // Button shape
    val shape = RoundedCornerShape(corner = CornerSize(12.dp))
    // Button background
    val transition = updateTransition(
        targetState = buttonState,
        label = "AnimatedButtonTransition"
    )
    val duration = 480
    val buttonBackgroundColor by transition.animateColor(
        transitionSpec = { tween(duration) },
        label = "Button Background Color"
    ) { state ->
        when (state) {
            IDLE -> Color.Blue
            PRESSED -> Color.White
        }
    }
    val buttonWidth by transition.animateDp(
        transitionSpec = { tween(duration) },
        label = "Button Width"
    ) { state ->
        when (state) {
            IDLE -> 70.dp
            PRESSED -> 32.dp
        }
    }
    val textMaxWidth by transition.animateDp(
        transitionSpec = { tween(duration) },
        label = "Text Max Width"
    ) { state ->
        when (state) {
            IDLE -> 40.dp
            PRESSED -> 0.dp
        }
    }
    // Button icon
    val iconAsset = if (buttonState == PRESSED)
        Icons.Default.Check
    else
        Icons.Default.Add
    val iconTintColor by transition.animateColor(
        transitionSpec = { tween(duration) },
        label = "Icon Tint Color"
    ) { state ->
        when (state) {
            IDLE -> Color.White
            PRESSED -> Color.Blue
        }
    }
    Box(
        modifier = modifier
            .clip(shape)
            .border(width = 1.dp, color = Color.Blue, shape = shape)
            .background(color = buttonBackgroundColor)
            .size(width = buttonWidth, height = 24.dp)
            .clickable(onClick = {
                buttonState = if (buttonState == IDLE) {
                    onClick.invoke(true)
                    PRESSED
                } else {
                    onClick.invoke(false)
                    IDLE
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconAsset,
                contentDescription = "Plus Icon",
                tint = iconTintColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Click",
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 1,
                modifier = Modifier.widthIn(
                    min = 0.dp,
                    max = textMaxWidth
                )
            )
        }
    }
}

enum class AnimatedButtonState {
    IDLE,
    PRESSED
}

@Preview
@Composable
fun JoinButtonPreview() {
    AnimatedButton(onClick = {})
}