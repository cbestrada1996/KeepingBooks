package com.cestrada.keepingbooks.ui


import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel()  {

}


sealed class MainStateEvent {
    object  GetBlogEvents: MainStateEvent()
    object None: MainStateEvent()
}