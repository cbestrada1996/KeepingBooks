package com.cestrada.keepingbooks.ui.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PaymentsViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel()  {

}
