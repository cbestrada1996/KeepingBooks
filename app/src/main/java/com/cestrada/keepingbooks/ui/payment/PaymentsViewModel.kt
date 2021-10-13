package com.cestrada.keepingbooks.ui.payment

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
class PaymentsViewModel
@Inject constructor(
        private val paymentRepository: PaymentRepository,
        private val savedStateHandle: SavedStateHandle
) : ViewModel()  {
    private val _dataState: MutableLiveData<DataState<List<Payment>, PaymentRepository.Type>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Payment>, PaymentRepository.Type>>
        get() = _dataState

    fun setStateEvent(paymentStateEvent: PaymentStateEvent){
        viewModelScope.launch {
            when(paymentStateEvent){
                is PaymentStateEvent.GetPaymentsEvents -> {
                    paymentRepository.getPayments()
                            .onEach { dataState ->
                                _dataState.value = dataState
                            }
                            .launchIn(viewModelScope)
                }
                is PaymentStateEvent.RemovePayment -> {
                    paymentRepository.removePayment(paymentStateEvent.payment)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)

                }
            }
        }
    }
}

sealed class PaymentStateEvent {
    object  GetPaymentsEvents: PaymentStateEvent()
    data class RemovePayment(val payment: Payment): PaymentStateEvent()
    object None: PaymentStateEvent()
}