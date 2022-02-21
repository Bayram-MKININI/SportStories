package fr.eurosport.sportstories.feature_media.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import com.google.android.material.card.MaterialCardView
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.GOLDEN_RATIO
import fr.eurosport.sportstories.common.util.convertDpToPx
import fr.eurosport.sportstories.common.util.layoutToTopLeft
import fr.eurosport.sportstories.common.util.measureWrapContent
import kotlin.math.roundToInt

class MediaItemView(context: Context, attrs: AttributeSet?) : MaterialCardView(context, attrs) {

    private lateinit var thumbImageView: ImageView
    private lateinit var iconImageView: ImageView
    private lateinit var categoryTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    class MediaItemViewHolder(val mediaItemView: MediaItemView) : RecyclerView.ViewHolder(mediaItemView)

    data class MediaItemViewAdapter(
        val thumbUrl: String = "",
        val category: String = "",
        val title: String = "",
        val description: String = "",
        val isIconVisible: Boolean = false
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {
        thumbImageView = findViewById(R.id.thumb_image_view)
        iconImageView = findViewById(R.id.icon_image_view)
        categoryTextView = findViewById(R.id.category_text_view)
        titleTextView = findViewById(R.id.title_text_view)
        descriptionTextView = findViewById(R.id.description_text_view)
    }

    fun fillViewWithData(mediaItemViewAdapter: MediaItemViewAdapter) {

        thumbImageView.load(mediaItemViewAdapter.thumbUrl) {
            crossfade(true)
            placeholder(R.drawable.rectangle_placeholder)
            size(ViewSizeResolver(thumbImageView))
        }

        iconImageView.visibility = if (mediaItemViewAdapter.isIconVisible) VISIBLE else GONE

        categoryTextView.text = mediaItemViewAdapter.category
        titleTextView.text = mediaItemViewAdapter.title
        descriptionTextView.text = mediaItemViewAdapter.description
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        var viewHeight = viewWidth

        thumbImageView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(
                (viewWidth / GOLDEN_RATIO).roundToInt(),
                MeasureSpec.EXACTLY
            )
        )

        if (iconImageView.visibility == VISIBLE)
            iconImageView.measureWrapContent()

        categoryTextView.measureWrapContent()

        titleTextView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth * 95 / 100, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        descriptionTextView.measureWrapContent()

        viewHeight =
            thumbImageView.measuredHeight + titleTextView.measuredHeight + descriptionTextView.measuredHeight + convertDpToPx(
                40
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

        if (iconImageView.visibility == VISIBLE)
            iconImageView.layoutToTopLeft(
                (viewWidth - iconImageView.measuredWidth) / 2,
                (thumbImageView.measuredHeight - iconImageView.measuredHeight) / 2
            )

        categoryTextView.layoutToTopLeft(
            convertDpToPx(10),
            thumbImageView.bottom - categoryTextView.measuredHeight / 2
        )

        titleTextView.layoutToTopLeft(
            (viewWidth * 5 / 100) / 2,
            categoryTextView.bottom + convertDpToPx(10)
        )

        descriptionTextView.layoutToTopLeft(
            titleTextView.left,
            titleTextView.bottom + convertDpToPx(5)
        )
    }
}