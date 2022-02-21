package fr.eurosport.sportstories.feature_media.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.GOLDEN_RATIO
import fr.eurosport.sportstories.common.util.*
import kotlin.math.roundToInt

class VideoView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var backIconImageView: ImageView
    private lateinit var playerView: PlayerView
    private lateinit var progressBar: ProgressBar
    private var exoPlayer: ExoPlayer? = null

    var viewCallback: VideoViewCallback? by weak()

    interface VideoViewCallback {
        fun onBackButtonClicked()
        fun onVideoReady()
    }

    data class VideoViewAdapter(
        val videoUrl: String = "",
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {

        backIconImageView = findViewById(R.id.back_icon_image_view)
        backIconImageView.setOnClickListener {
            viewCallback?.onBackButtonClicked()
        }

        playerView = findViewById(R.id.player_view)
        progressBar = findViewById(R.id.progress_bar)

        exoPlayer = ExoPlayer.Builder(context).build().also {
            it.playWhenReady = true
            it.addListener(playerListener)
            playerView.player = it
        }
    }

    private val playerListener: Player.Listener by lazy {
        object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY)
                    viewCallback?.onVideoReady()
            }
        }
    }

    fun fillViewWithData(videoViewAdapter: VideoViewAdapter) {
        val mediaItem = MediaItem.fromUri(videoViewAdapter.videoUrl)
        exoPlayer?.run {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    fun setProgressVisible(inProgress: Boolean) {
        progressBar.visibility = if (inProgress) VISIBLE else GONE
    }

    fun pausePlayer() {
        exoPlayer?.pause()
    }

    fun releasePlayer() {
        exoPlayer?.run {
            playWhenReady = this.playWhenReady
            release()
        }
        exoPlayer = null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        backIconImageView.measureWrapContent()

        playerView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(
                (viewWidth / GOLDEN_RATIO).roundToInt(),
                MeasureSpec.EXACTLY
            )
        )

        progressBar.measureWrapContent()

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        val viewWidth = right - left
        val viewHeight = bottom - top

        backIconImageView.layoutToTopLeft(
            convertDpToPx(15),
            convertDpToPx(15)
        )

        playerView.layoutToTopLeft(
            (viewWidth - playerView.measuredWidth) / 2,
            (viewHeight - playerView.measuredHeight) / 2
        )

        progressBar.layoutToTopLeft(
            (viewWidth - progressBar.measuredWidth) / 2,
            (viewHeight - progressBar.measuredHeight) / 2
        )
    }
}