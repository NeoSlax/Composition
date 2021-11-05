package com.neoslax.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.neoslax.composition.R

@BindingAdapter("requireAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("requireScoreAnswers")
fun bindRequiredScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requireMinPercent")
fun bindRequireMinPercent(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )

}

@BindingAdapter("requireMinPercentScore")
fun bindRequireMinPercentScore(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        count
    )

}

@BindingAdapter("setImageOfResult")
fun setImageOfResult(imageView: ImageView, winner: Boolean) {
    val smileId = if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }

    imageView.setImageResource(smileId)
}
