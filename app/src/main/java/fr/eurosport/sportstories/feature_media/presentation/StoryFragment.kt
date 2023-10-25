package fr.eurosport.sportstories.feature_media.presentation

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.util.convertSecondsToTimeString
import fr.eurosport.sportstories.feature_media.domain.model.Story
import fr.eurosport.sportstories.feature_media.presentation.views.StoryView
import fr.eurosport.sportstories.feature_media.presentation.views.StoryView.StoryViewAdapter
import fr.eurosport.sportstories.feature_media.presentation.views.StoryView.StoryViewCallback

@AndroidEntryPoint
class StoryFragment : AppCompatDialogFragment() {

    private var storyView: StoryView? = null
    private val mediaViewModel by activityViewModels<MediaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialogTheme)
    }

    companion object {

        private const val SELECTED_INDEX = "selectedIndex"

        fun newInstance(selectedIndex: Int): StoryFragment {
            val fragment = StoryFragment()
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
        return inflater.inflate(R.layout.story_layout, container, false).apply {
            storyView = this as StoryView
            storyView?.viewCallback = storyViewCallback
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

                val selectedStory = mediaState.mediaElements[selectedIndex] as Story

                StoryViewAdapter(
                    thumbUrl = selectedStory.imageUrl,
                    category = selectedStory.sport.name.uppercase(),
                    title = selectedStory.title,
                    author = resolveAuthorColored(selectedStory),
                    time = getString(
                        R.string.story_time,
                        requireContext().convertSecondsToTimeString(
                            before = selectedStory.dateTimestamp,
                            now = System.currentTimeMillis()
                        )
                    ),
                    content = selectedStory.teaser

                ).also {

                    storyView?.fillViewWithData(it)
                }
            }
        }
    }

    private fun resolveAuthorColored(selectedStory: Story): SpannableStringBuilder {

        val byAuthor = SpannableStringBuilder()

        byAuthor.append(getString(R.string.buy))
        byAuthor.append(" ")

        val foregroundColorSpan = ForegroundColorSpan(Color.CYAN)

        val start = byAuthor.length

        byAuthor.append(selectedStory.author)

        byAuthor.setSpan(
            foregroundColorSpan,
            start,
            byAuthor.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return byAuthor
    }

    private val storyViewCallback: StoryViewCallback by lazy {
        object : StoryViewCallback {
            override fun onBackButtonClicked() {
                dismissAllowingStateLoss()
            }

            override fun onShareButtonClicked() {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        storyView = null
    }
}