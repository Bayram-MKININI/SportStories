package fr.eurosport.sportstories.feature_media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.STORY_FRAGMENT_TAG
import fr.eurosport.sportstories.common.VIDEO_FRAGMENT_TAG
import fr.eurosport.sportstories.common.util.DataError
import fr.eurosport.sportstories.common.util.convertSecondsToTimeString
import fr.eurosport.sportstories.common.util.inflate
import fr.eurosport.sportstories.feature_media.domain.model.Story
import fr.eurosport.sportstories.feature_media.domain.model.Video
import fr.eurosport.sportstories.feature_media.presentation.views.HomeView
import fr.eurosport.sportstories.feature_media.presentation.views.MediaItemView.MediaItemViewAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var homeView: HomeView? = null
    private val mediaViewModel: MediaViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.home_layout, false)?.apply {
            homeView = this as HomeView
            homeView?.viewCallback = homeViewCallback
        }
    }

    private val homeViewCallback: HomeView.HomeViewCallback by lazy {
        object : HomeView.HomeViewCallback {
            override fun onItemClickedAtIndex(index: Int) {

                viewLifecycleOwner.lifecycleScope.launchWhenStarted {

                    mediaViewModel.stateFlow.collect { mediaState ->

                        val mediaElement = mediaState.mediaElements[index]

                        if (mediaElement is Story) {

                            val fragmentTransaction =
                                requireActivity().supportFragmentManager.beginTransaction()
                            StoryFragment.newInstance(index)
                                .also { it.show(fragmentTransaction, STORY_FRAGMENT_TAG) }

                        } else {

                            mediaElement as Video

                            val fragmentTransaction =
                                requireActivity().supportFragmentManager.beginTransaction()
                            VideoFragment.newInstance(index)
                                .also { it.show(fragmentTransaction, VIDEO_FRAGMENT_TAG) }
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlows()
    }

    private fun collectFlows() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            mediaViewModel.eventFlow.collectLatest { sharedEvent ->

                when (sharedEvent) {

                    is MediaViewModel.UIEvent.ShowSnackBar -> {

                        val message =
                            when (sharedEvent.dataError) {
                                DataError.NETWORK_ERROR -> getString(R.string.error_no_network)
                                DataError.SYSTEM_ERROR -> getString(R.string.error_contact_support)
                                DataError.NONE -> ""
                            }

                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            mediaViewModel.stateFlow.collect { mediaState ->

                if (mediaState.mediaElements.isNotEmpty())
                    homeView?.setProgressVisible(false)

                val elementsAdapters = mediaState.mediaElements.map { mediaElement ->

                    if (mediaElement is Story) {

                        MediaItemViewAdapter(
                            thumbUrl = mediaElement.imageUrl,
                            category = mediaElement.sport.name.uppercase(),
                            title = mediaElement.title,
                            description = getString(
                                R.string.story_description,
                                mediaElement.author,
                                requireContext().convertSecondsToTimeString(
                                    before = mediaElement.dateTimestamp,
                                    now = System.currentTimeMillis()
                                )
                            ),
                            isIconVisible = false
                        )

                    } else {

                        mediaElement as Video

                        MediaItemViewAdapter(
                            thumbUrl = mediaElement.thumbUrl,
                            category = mediaElement.sport.name.uppercase(),
                            title = mediaElement.title,
                            description = getString(
                                R.string.video_description,
                                mediaElement.viewCount
                            ),
                            isIconVisible = true
                        )
                    }
                }

                homeView?.fillViewWithData(elementsAdapters)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeView = null
    }
}