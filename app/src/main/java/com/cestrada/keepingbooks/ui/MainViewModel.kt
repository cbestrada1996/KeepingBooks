package com.cestrada.keepingbooks.ui


import androidx.lifecycle.*
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.repository.PaymentRepository
import com.cestrada.keepingbooks.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
        private val savedStateHandle: SavedStateHandle
) : ViewModel()  {



}


