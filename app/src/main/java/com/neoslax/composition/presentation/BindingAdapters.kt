package com.neoslax.composition.presentation

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.neoslax.composition.R
import com.neoslax.composition.domain.entity.Question

interface OnOptionClickListener{
    fun onOptionClick(position: Int)
}

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

@BindingAdapter("setQuestionSum")
fun setQuestionSum(textView: TextView, question: Question){
    textView.text = question.sum.toString()
}
@BindingAdapter("setVisibleNumber")
fun setVisibleNumber(textView: TextView, question: Question){
    textView.text = question.visibleNumber.toString()
}

private fun getStateProgressColor(view: View, isEnough: Boolean): Int {
    val colorId = if (isEnough) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(view.context, colorId)
}

@BindingAdapter("setTVProgressColor")
fun setProgressColor(textView: TextView, enoughColor: Boolean){
    val color = getStateProgressColor(textView, enoughColor)
    textView.setTextColor(color)

}

@BindingAdapter("setProgressBarColor")
fun setProgressBarColor(progressBar: ProgressBar, enoughPercent: Boolean){
    val color = getStateProgressColor(progressBar, enoughPercent)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("setPercentOfRightAnswers")
fun setPercentOfRightAnswers(progressBar: ProgressBar, progress: Int){
    progressBar.setProgress(progress, true)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, operation: OnOptionClickListener) {
    textView.setOnClickListener {
        operation.onOptionClick(textView.text.toString().toInt())
    }
}