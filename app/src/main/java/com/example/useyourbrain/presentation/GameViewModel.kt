package com.example.useyourbrain.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.useyourbrain.R
import com.example.useyourbrain.data.GameRepositoryImpl
import com.example.useyourbrain.domain.entity.GameResult
import com.example.useyourbrain.domain.entity.GameSettings
import com.example.useyourbrain.domain.entity.Level
import com.example.useyourbrain.domain.entity.Question
import com.example.useyourbrain.domain.usecases.GenerateQuestionUseCase
import com.example.useyourbrain.domain.usecases.GetGameSettingUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    //1
    private lateinit var gameSettings: GameSettings

    //2
    private lateinit var level: Level

    private val context = application
    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingUseCase = GetGameSettingUseCase(repository)

    //3
    private var timer: CountDownTimer? = null

    //2
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    //4
    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    //6
    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    //7
    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers


    //8
    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    //9
    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers


    //10
    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent


    //11
    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    //6
    private var countOfRightAnswer = 0
    private var countOfQuestion = 0


    //1
    fun startGame(level: Level) {
        //2
        getGameSettings(level)
        //3
        startTimer()
        //4
        generateQuestion()
        updateProgress()

    }


    fun chooseAnswer(number: Int) {
        //5
        checkAnswer(number)
        //6
        updateProgress()
        //4
        generateQuestion()
    }

    //8
    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswer.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswersForWinner
        )
        //9
        _enoughCountOfRightAnswers.value =
            countOfRightAnswer >= gameSettings.minCountOfRightAnswersForWinner
        _enoughPercentOfRightAnswers.value =
            percent >= gameSettings.minPercentOfRightAnswersForWinner

    }

    //8
    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestion ==0){
            return 0
        }
        return ((countOfRightAnswer / countOfQuestion.toDouble()) * 100).toInt()
    }

    //5
    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswer++
        }
        countOfQuestion++
    }

    //2
    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswersForWinner
    }

    //3
    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    //4
    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }


    //3
    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    //10
    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswers.value == true && enoughPercent.value == true,
            countOfRightAnswers = countOfRightAnswer,
            countOfQuestions = countOfQuestion,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        // 3
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}