package com.example.roomexplore

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MyViewModel(private val myRepository: MyRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val allWords: LiveData<List<Word>> = myRepository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(word: Word) = viewModelScope.launch {
        myRepository.insert(word)
    }
}

class MyViewModelFactory(private val myRepository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(myRepository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }

}