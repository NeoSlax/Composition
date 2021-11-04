package com.neoslax.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neoslax.composition.R
import com.neoslax.composition.data.RepositoryImpl
import com.neoslax.composition.domain.entity.GameResult
import com.neoslax.composition.domain.entity.GameSettings
import com.neoslax.composition.domain.entity.Level
import com.neoslax.composition.domain.entity.Question
import com.neoslax.composition.domain.usecases.GenerateQuestionUseCase
import com.neoslax.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {

    private val repositoryImpl = RepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repositoryImpl)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repositoryImpl)

    private lateinit var gameSettings: GameSettings

    private var countDownTimer: CountDownTimer? = null
    private var countOfRightAnswer = 0
    private var countOfQuestions = 0
    private var percent = 0

    private val _timer = MutableLiveData<String>()
    val timer: LiveData<String>
        get() = _timer

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _isFinished = MutableLiveData<Unit>()
    val isFinished: LiveData<Unit>
        get() = _isFinished

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        getNewQuestion()
        startTimer()
        updateProgress()
    }

    private fun updateProgress() {
        percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswers
        )

        _enoughCount.value = countOfRightAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) return 0
        return ((countOfRightAnswer / countOfQuestions.toDouble()) * FULL_PERCENT).toInt()
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }


    private fun getNewQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }


    fun takeAnswer(answer: Int) {
        checkAnswer(answer)
        countOfQuestions++
        updateProgress()
        getNewQuestion()
    }

    private fun checkAnswer(answer: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (answer == rightAnswer) {
            countOfRightAnswer++
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                val timeRemain = secondsConverter(millisUntilFinished)
                _timer.value = timeRemain
            }

            override fun onFinish() {
                finishGame()
            }
        }.start()
    }

    private fun secondsConverter(millis: Long): String {

        val ms = millis % 1000
        var millis = (millis - ms) / 1000
        val secs = millis % 60
        millis = (millis - secs) / 60
        val mins = millis % 60

        return "$mins:$secs"
    }

    private fun finishGame() {
        val gameResult = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswer,
            countOfQuestions,
            percent,
            gameSettings
        )
        _gameResult.value = gameResult
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val FULL_PERCENT = 100
    }
}