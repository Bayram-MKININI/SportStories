package fr.eurosport.sportstories.feature_media.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.eurosport.sportstories.R
import fr.eurosport.sportstories.common.util.inflate
import fr.eurosport.sportstories.feature_media.presentation.views.MediaItemView
import fr.eurosport.sportstories.feature_media.presentation.views.MediaItemView.MediaItemViewAdapter
import fr.eurosport.sportstories.feature_media.presentation.views.MediaItemView.MediaItemViewHolder

class MediaRecyclerAdapter(private val mediaItemViewAdapters: List<MediaItemViewAdapter>) : RecyclerView.Adapter<MediaItemViewHolder>() {

    override fun getItemCount(): Int = mediaItemViewAdapters.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaItemViewHolder {
        val inflatedView = parent.inflate(R.layout.media_item_layout, false)
        return MediaItemViewHolder(inflatedView as MediaItemView)
    }

    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) = holder.mediaItemView.fillViewWithData(mediaItemViewAdapters[position])
}