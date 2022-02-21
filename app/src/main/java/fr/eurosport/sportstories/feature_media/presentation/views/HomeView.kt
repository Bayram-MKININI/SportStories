package fr.eurosport.sportstories.feature_media.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.util.*
import fr.eurosport.sportstories.feature_media.presentation.adapters.MediaRecyclerAdapter
import fr.eurosport.sportstories.feature_media.presentation.views.MediaItemView.MediaItemViewAdapter

class HomeView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    var viewCallback: HomeViewCallback? by weak()

    interface HomeViewCallback {
        fun onItemClickedAtIndex(index: Int)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {

        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        recyclerView.also {

            it.layoutManager = GridLayoutManager(
                context,
                context.resources.getInteger(R.integer.number_of_columns)
            )

            it.onItemClicked(onClick = { position, _ ->
                viewCallback?.onItemClickedAtIndex(position)
            })

            val spacing = convertDpToPx(5)

            it.setPadding(spacing, spacing, spacing, spacing)
            it.clipToPadding = false
            it.clipChildren = false
            it.addItemDecoration(MarginItemDecoration(spacing, GRID))
        }
    }

    fun fillViewWithData(albumItemViewAdapters: List<MediaItemViewAdapter>) {
        recyclerView.adapter = MediaRecyclerAdapter(albumItemViewAdapters)
    }

    fun setProgressVisible(inProgress: Boolean) {
        progressBar.visibility = if (inProgress) VISIBLE else GONE
        recyclerView.visibility = if (inProgress) GONE else VISIBLE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        recyclerView.measure(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )

        if (progressBar.visibility == VISIBLE)
            progressBar.measureWrapContent()

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val viewWidth = right - left
        val viewHeight = bottom - top

        recyclerView.layoutToTopLeft(0, 0)

        if (progressBar.visibility == VISIBLE)
            progressBar.layoutToTopLeft(
                (viewWidth - progressBar.measuredWidth) / 2,
                (viewHeight - progressBar.measuredHeight) / 2
            )
    }
}