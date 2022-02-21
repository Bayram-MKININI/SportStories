package fr.eurosport.sportstories.common.util

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.eurosport.sportstories.R

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun View.measureWrapContent() {
    measure(
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
    )
}

fun View.layoutToTopLeft(left: Int, top: Int) {
    val right = left + measuredWidth
    val bottom = top + measuredHeight
    layout(left, top, right, bottom)
}

fun View.layoutToTopRight(right: Int, top: Int) {
    val left = right - measuredWidth
    val bottom = top + measuredHeight
    layout(left, top, right, bottom)
}

fun View.layoutToBottomLeft(left: Int, bottom: Int) {
    val right = left + measuredWidth
    val top = bottom - measuredHeight
    layout(left, top, right, bottom)
}

fun View.layoutToBottomRight(right: Int, bottom: Int) {
    val left = right - measuredWidth
    val top = bottom - measuredHeight
    layout(left, top, right, bottom)
}

fun View.convertDpToPx(dpValue: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dpValue.toFloat(),
    context.resources.displayMetrics
).toInt()

fun RecyclerView.onItemClicked(
    onClick: ((position: Int, view: View) -> Unit)? = null,
    onLongClick: ((position: Int, view: View) -> Unit)? = null
) {
    this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
}

fun <T> List<T>.mixAlternate(other: List<T>): List<T> {
    val first = iterator()
    val second = other.iterator()
    val list = ArrayList<T>(minOf(this.size, other.size))

    while (first.hasNext() || second.hasNext()) {

        if (first.hasNext())
            list.add(first.next())

        if (second.hasNext())
            list.add(second.next())
    }

    return list
}

fun Context.convertSecondsToTimeString(before: Long, now: Long): String {

    val diff: Long = now - before

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val years = days / 365

    return when {
        years > 0 -> {
            val remainingDays = days % (years * 365)
            "$years ${getString(R.string.years)} $remainingDays ${getString(R.string.days)}"
        }
        days > 0 -> {
            val remainingHours = hours % (days * 24)
            "$days ${getString(R.string.days)} $remainingHours ${getString(R.string.hours)}"
        }
        hours > 0 -> {
            val remainingMinutes = minutes % (hours * 60)
            "$hours ${getString(R.string.hours)} $remainingMinutes ${getString(R.string.minutes)}"
        }
        minutes > 0 -> {
            val remainingSeconds = seconds % (minutes * 60)
            "$minutes ${getString(R.string.minutes)} $remainingSeconds ${getString(R.string.seconds)}"
        }
        else -> {
            "$seconds ${getString(R.string.seconds)}"
        }
    }
}

fun String.parseTimestamp(): Long {

    val temp = this.split(".")

    val builder = StringBuilder()
    builder.append(temp[1])

    repeat(3 - temp[1].length) {
        builder.append("0")
    }

    return (temp[0] + builder.toString()).toLong()
}