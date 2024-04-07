package com.leaf.hearingtest.ui.hearing

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf.hearingtest.audioplayer.AudioPlayerImpl
import com.leaf.hearingtest.mapper.UserModelToUserDetails
import com.leaf.hearingtest.ui.hearing.model.Digit
import com.leaf.hearingtest.ui.hearing.model.Noise
import com.leaf.hearingtest.ui.hearing.model.Question
import com.leaf.hearingtest.ui.hearing.model.createQuestionnaire
import com.leaf.hearingtest.ui.user.UserModel
import com.leaf.repository.HearingTestRepository
import com.leaf.repository.helper.DataState
import com.leaf.repository.model.HearingTest
import com.leaf.repository.model.QuestionnaireAnswers
import com.leaf.repository.model.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HearingTestViewModel @Inject constructor(
    private val repository: HearingTestRepository,
    private val userModelToUserDetails: UserModelToUserDetails
) : ViewModel() {

    private val questionMutableLiveData: MutableLiveData<Question> = MutableLiveData()
    val questionLiveData: LiveData<Question> = questionMutableLiveData

    var userAnswers = MutableLiveData<String>()

    private val difficultyMutableLiveData: MutableLiveData<Int> = MutableLiveData(5)
    val difficultyAnsweredLiveData: LiveData<Int> = difficultyMutableLiveData

    private val totalQuestionsAnsweredMutableLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val totalQuestionsAnsweredLiveData: LiveData<Int> = totalQuestionsAnsweredMutableLiveData

    private val scoreMutableLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val scoreLiveData: LiveData<Int> = scoreMutableLiveData

    private val showErrorMutableLiveData = MutableLiveData<Boolean>()
    val showErrorLiveData: LiveData<Boolean> = showErrorMutableLiveData

    private val showLoadingMutableLiveData = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean> = showLoadingMutableLiveData

    private val showAlertDialogMutableLiveData = MutableLiveData<DialogData>()
    val showAlertDialogLiveData: LiveData<DialogData> = showAlertDialogMutableLiveData

    private val questionsAnswersList = ArrayList<QuestionnaireAnswers>()

    private var currentQuestionIndex = CURRENT_QUESTION_START_INDEX
    private var totalPoints: Int = 0

    private var questions = createQuestionnaire().toMutableList()

    private var userDetails: UserDetails? = null

    private lateinit var audioPlayerImpl: AudioPlayerImpl

    /**
     * loadQuestion
     * loads question into the view.
     */
    fun loadQuestion() {
        updateViewOnQuestionChanged()
    }

    /**
     * fetchUserDetails
     * gets information passed through with safeargs from [UserDetailedFragment]
     * @param userModel [UserModel]
     */
    fun fetchUserDetails(userModel: UserModel?) {
        userDetails = userModel?.let { userModelToUserDetails.mapToModel(it) }
    }

    /**
     * onSubmitClicked
     * function that triggers when the submit button on the view is clicked.
     * Its the primary driving factor its responsibility
     * is iterate and process what the user has inputted.
     * it gets the current question in the test.
     * captures it in the [QuestionnaireAnswers][List]
     * checks if the the user answered is correct.
     * calculates the total score.
     */
    fun onSubmitClicked() {

        val currentQuestion = getCurrentQuestion()

        captureCurrentQuestionsAnswer(currentQuestion)

        if (isUserAnswerCorrect(currentQuestion)) {
            calculateTotalScore(currentQuestion)
            nextQuestion()
            updateViewWithTotalQuestionsAnswered()
            updateViewQuestionDifficulty()

        } else {
            previousQuestion()
        }
    }

    /**
     * nextQuestion
     * steps to the next question in the test.
     * if theres no questions remaining it will submit the test.
     */
    private fun nextQuestion() {
        if (hasMoreQuestions()) {
            incrementQuestion()
            updateViewOnQuestionChanged()
        } else if (isQuestionsLeft()) {
            processRemainingQuestions()
            updateViewOnQuestionChanged()
        } else {
            submitHearingTest(score = totalPoints, questionsAnswersList.toMutableList())
        }
    }

    /**
     * previousQuestion
     * returns to a previous question.
     */
    private fun previousQuestion() {
        if (isNotFirstQuestion()) {
            decrementQuestion()
            updateViewQuestionDifficulty()
            updateViewOnQuestionChanged()
        }
    }

    /**
     * processRemainingQuestions
     * as a requirement the questionnaire starts at the fif entry in the list.
     * if a user answers questions from 5-10 for example 1-4 would left unanswered
     * this method aims to take unanswered and shift it to the upper half of the list so that it maybe answered.
     */
    private fun processRemainingQuestions() {
        val lowerBoundList =
            questions.subList(
                LOWER_BOUND_INDEX_START,
                questions.size - questionsAnswersList.size
            ).toMutableList()


        val upperBoundList =
            questions.subList(
                questions.size - questionsAnswersList.size,
                UPPER_BOUND_INDEX_END
            ).toMutableList()


        questions = (upperBoundList + lowerBoundList).toMutableList()
        currentQuestionIndex = questionsAnswersList.size
    }

    /**
     * isQuestionsLeft
     * checks to see if there are any questions unanswered.
     * @return Boolean
     */
    private fun isQuestionsLeft() =
        questionsAnswersList.size < questions.lastIndex && currentQuestionIndex == questions.lastIndex

    /**
     * decrementQuestion
     * increment the questions list by a factor of 1.
     */
    private fun incrementQuestion() {
        questions[currentQuestionIndex++]
    }

    /**
     * hasMoreQuestions
     * checks to see if theres more questions in the list.
     * @return Boolean
     */
    private fun hasMoreQuestions() = currentQuestionIndex < questions.lastIndex

    /**
     * isNotFirstQuestion
     * checks if the current index is not on the first question.
     */
    private fun isNotFirstQuestion() = currentQuestionIndex > 0

    /**
     * decrementQuestion
     * decrements the questions list by a factor of 1.
     */
    private fun decrementQuestion() {
        questions[currentQuestionIndex--]
    }

    /**
     * updateViewOnQuestionChanged
     * updates view with new a question.
     */
    private fun updateViewOnQuestionChanged() {
        questionMutableLiveData.postValue(questions[currentQuestionIndex])
    }

    /**
     * updateViewQuestionDifficulty
     * updates text view with new question difficulty.
     */

    private fun updateViewQuestionDifficulty() {
        difficultyMutableLiveData.postValue(getCurrentQuestion().noise.difficulty)
    }

    /**
     * updateViewWithTotalQuestionsAnswered
     * updates text view count of questions answered.
     */
    private fun updateViewWithTotalQuestionsAnswered() {
        totalQuestionsAnsweredMutableLiveData.postValue(questionsAnswersList.size)
    }

    /**
     * calculateTotalScore
     * calculates total score.
     * live data then post the score to the view.
     * returns true if userAnswer is correct.
     * returns false if userAnswer is incorrect.
     * @param currentQuestion [Question]
     * @return [Boolean]
     */
    private fun calculateTotalScore(currentQuestion: Question) {
        val points = currentQuestion.pointValue
        totalPoints += points
        scoreMutableLiveData.postValue(totalPoints)
    }


    /**
     * isUserAnswerCorrect
     * checks if user answer is correct.
     * returns true if userAnswer is correct.
     * returns false if userAnswer is incorrect.
     * @param [Question]
     * @return [Boolean]
     */
    private fun isUserAnswerCorrect(currentQuestion: Question): Boolean {
        return currentQuestion.triplet.tripletValue == userAnswers.value
    }

    /**
     * captureCurrentQuestionsAnswer
     * captures user inputted answer for the current question.
     * @param [Question]
     */
    private fun captureCurrentQuestionsAnswer(currentQuestion: Question) {
        questionsAnswersList.add(
            QuestionnaireAnswers(
                difficulty = currentQuestion.noise.difficulty,
                tripletPlayed = currentQuestion.triplet.tripletValue,
                tripletAnswered = userAnswers.value.orEmpty()
            )
        )
    }

    /**
     * getCurrentQuestion
     * returns the current question.
     * @return [Question]
     */
    private fun getCurrentQuestion(): Question {
        return questions[currentQuestionIndex]
    }

    /**
     * storeHearingTestLocally
     * stores  test data to the local database.
     * @param hearingTest [HearingTest] test taken.
     */
    private fun storeHearingTestLocally(hearingTest: HearingTest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.storeHearingTest(hearingTest)
        }
    }

    /**
     * submitHearingTest
     * sends test data to the api for processing.
     * Response Success -> it post live data to the view to show an alert dialog and also store the hearing test the database.
     * Response Error -> it post live data to the view to show an error layout.
     * Response Loading  it post live data to the view to show an loading layout.
     * @param score [Int] sum of score
     * @param questionnaireAnswers [List][QuestionnaireAnswers] list of question the user has answered.
     */
    private fun submitHearingTest(score: Int, questionnaireAnswers: List<QuestionnaireAnswers>) {
        viewModelScope.launch(Dispatchers.IO) {
            val hearingTest =
                HearingTest(
                    score = score,
                    questionnaireAnswers = questionnaireAnswers,
                    userDetails = userDetails
                )
            val response = repository.publishHearingTest(hearingTest)

            when (response) {
                is DataState.Error -> {
                    storeHearingTestLocally(hearingTest)
                    showErrorMutableLiveData.postValue(true)
                }

                is DataState.Loading -> {
                    showLoadingMutableLiveData.postValue(true)
                }

                is DataState.Success -> {
                    storeHearingTestLocally(hearingTest)
                    showAlertDialogMutableLiveData.postValue(DialogData(totalPoints,""))
                }
            }
        }
    }

    /**
     * setupAudioPlayer
     * initialises the audio player.
     * @param context Noise
     * @param mediaPlayer [MediaPlayer]
     */
    fun setupAudioPlayer(context: Context, mediaPlayer: MediaPlayer) {
        audioPlayerImpl = AudioPlayerImpl(context, mediaPlayer)
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerImpl.init()
        }
    }

    /**
     * playAudio
     * Plays the a series audio clips of the current question.
     * @param noise Noise
     * @param digits [List][Digit]
     */
    fun playAudio(noise: Noise, digits: List<Digit>) {
        Timber.v("Triplet:${getCurrentQuestion().triplet.tripletValue}")
        viewModelScope.launch(Dispatchers.IO) {
            delay(AUDIO_NOISE_DELAY)
            audioPlayerImpl.loadAndPlay(noise.audioResource)
            delay(AUDIO_DIGIT_DELAY)
            for (i in 0..2) {
                audioPlayerImpl.loadAndPlay(digits[i].audioResource)
                delay(AUDIO_DIGIT_DELAY)
            }
            audioPlayerImpl.reset()
        }
    }


    /**
     * destroyAudioPlayer
     * method used to remove the audio player when its not being used.
     */
    fun destroyAudioPlayer() {
        audioPlayerImpl.destroy()
    }

    companion object {
        const val AUDIO_NOISE_DELAY = 3000L
        const val AUDIO_DIGIT_DELAY = 2000L
        const val LOWER_BOUND_INDEX_START = 0
        const val UPPER_BOUND_INDEX_END = 10
        const val CURRENT_QUESTION_START_INDEX = 4
    }
}
