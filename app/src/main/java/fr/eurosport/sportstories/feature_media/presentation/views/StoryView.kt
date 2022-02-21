package fr.eurosport.sportstories.feature_media.presentation.views

import android.content.Context
import android.text.Spannable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import coil.size.ViewSizeResolver
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.GOLDEN_RATIO
import fr.eurosport.sportstories.common.util.*
import kotlin.math.roundToInt

class StoryView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var thumbImageView: ImageView
    private lateinit var backIconImageView: ImageView
    private lateinit var shareIconImageView: ImageView
    private lateinit var categoryTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var contentTextView: TextView

    var viewCallback: StoryViewCallback? by weak()

    interface StoryViewCallback {
        fun onBackButtonClicked()
        fun onShareButtonClicked()
    }

    data class StoryViewAdapter(
        val thumbUrl: String = "",
        val category: String = "",
        val title: String = "",
        val author: Spannable,
        val time: String = "",
        val content: String = "",
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {

        thumbImageView = findViewById(R.id.thumb_image_view)

        backIconImageView = findViewById(R.id.back_icon_image_view)
        backIconImageView.setOnClickListener {
            viewCallback?.onBackButtonClicked()
        }

        shareIconImageView = findViewById(R.id.share_icon_image_view)
        shareIconImageView.setOnClickListener {
            viewCallback?.onShareButtonClicked()
        }

        categoryTextView = findViewById(R.id.category_text_view)
        titleTextView = findViewById(R.id.title_text_view)
        authorTextView = findViewById(R.id.author_text_view)
        timeTextView = findViewById(R.id.time_text_view)
        contentTextView = findViewById(R.id.content_text_view)
    }

    fun fillViewWithData(storyViewAdapter: StoryViewAdapter) {

        thumbImageView.load(storyViewAdapter.thumbUrl) {
            crossfade(true)
            placeholder(R.drawable.rectangle_placeholder)
            size(ViewSizeResolver(thumbImageView))
        }

        categoryTextView.text = storyViewAdapter.category
        titleTextView.text = storyViewAdapter.title
        authorTextView.text = storyViewAdapter.author
        timeTextView.text = storyViewAdapter.time
        contentTextView.text = storyViewAdapter.content
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        thumbImageView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(
                (viewWidth / GOLDEN_RATIO).roundToInt(),
                MeasureSpec.EXACTLY
            )
        )

        backIconImageView.measureWrapContent()

        shareIconImageView.measureWrapContent()

        categoryTextView.measureWrapContent()

        titleTextView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth * 90 / 100, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        authorTextView.measureWrapContent()

        timeTextView.measureWrapContent()

        contentTextView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth * 90 / 100, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        val viewWidth = right - left
        val viewHeight = bottom - top

        thumbImageView.layoutToTopLeft(0, 0)

        backIconImageView.layoutToTopLeft(
            convertDpToPx(15),
            convertDpToPx(15)
        )

        shareIconImageView.layoutToTopRight(
            viewWidth - convertDpToPx(15),
            backIconImageView.top
        )

        categoryTextView.layoutToTopLeft(
            convertDpToPx(10),
            thumbImageView.bottom - categoryTextView.measuredHeight / 2
        )

        titleTextView.layoutToTopLeft(
            viewWidth * 5 / 100,
            categoryTextView.bottom + convertDpToPx(10)
        )

        authorTextView.layoutToTopLeft(
            titleTextView.left,
            titleTextView.bottom + convertDpToPx(5)
        )

        timeTextView.layoutToTopLeft(
            titleTextView.left,
            authorTextView.bottom + convertDpToPx(5)
        )

        contentTextView.layoutToTopLeft(
            titleTextView.left,
            timeTextView.bottom + convertDpToPx(10)
        )
    }
}