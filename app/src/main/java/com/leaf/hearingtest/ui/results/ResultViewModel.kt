package com.leaf.hearingtest.ui.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leaf.repository.HearingTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val repository: HearingTestRepository) :
    ViewModel() {

    private val resultsMutableLiveData = MutableLiveData<List<Result>>()
    val resultsLiveData: LiveData<List<Result>> = resultsMutableLiveData

    private val showErrorMutableLiveData = MutableLiveData<Boolean>()
    val showErrorLiveData: LiveData<Boolean> = showErrorMutableLiveData

    private val handler = CoroutineExceptionHandler { _, throwable ->
        showErrorMutableLiveData.postValue(true)
        throwable.printStackTrace()
    }

    /**
     * getResults
     * makes a call to the HearingTestRepository to return all tests stored in the database
     */
    fun getResults() {
        viewModelScope.launch(Dispatchers.IO + handler) {

           val results = repository.getAllHearingTests()?.map {
                Result(
                    firstName = it.userDetails?.firstName.orEmpty(),
                    lastName = it.userDetails?.lastName.orEmpty(),
                    emailAddress = it.userDetails?.emailAddress.orEmpty(),
                    score = it.score
                )
            }

            if(results?.size != 0)
                resultsMutableLiveData.postValue(results!!)
            else
                showErrorMutableLiveData.postValue(true)
        }
    }
}