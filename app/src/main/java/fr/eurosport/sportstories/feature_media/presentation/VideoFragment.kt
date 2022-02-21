package fr.eurosport.sportstories.feature_media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.feature_media.domain.model.Video
import fr.eurosport.sportstories.feature_media.presentation.views.VideoView
import fr.eurosport.sportstories.feature_media.presentation.views.VideoView.VideoViewAdapter
import fr.eurosport.sportstories.feature_media.presentation.views.VideoView.VideoViewCallback
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VideoFragment : AppCompatDialogFragment() {

    private var videoView: VideoView? = null
    private val mediaViewModel: MediaViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialogTheme)
    }

    companion object {

        private const val SELECTED_INDEX = "selectedIndex"

        fun newInstance(selectedIndex: Int): VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle()
            args.putInt(SELECTED_INDEX, selectedIndex)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_layout, container, false).apply {
            videoView = this as VideoView
            videoView?.viewCallback = videoViewCallback
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedIndex = arguments?.getInt(SELECTED_INDEX) ?: 0
        collectFlow(selectedIndex)
    }

    private fun collectFlow(selectedIndex: Int) {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            mediaViewModel.stateFlow.collect { mediaState ->

                val selectedVideo = mediaState.mediaElements[selectedIndex] as Video

                VideoViewAdapter(
                    videoUrl = selectedVideo.videoUrl
                ).also {
                    videoView?.fillViewWithData(it)
                }
            }
        }
    }

    private val videoViewCallback: VideoViewCallback by lazy {
        object : VideoViewCallback {
            override fun onBackButtonClicked() {
                dismissAllowingStateLoss()
            }

            override fun onVideoReady() {
                videoView?.setProgressVisible(false)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        videoView?.pausePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoView?.releasePlayer()
        videoView = null
    }
}