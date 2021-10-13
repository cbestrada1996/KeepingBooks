package com.cestrada.keepingbooks.ui.payment.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.model.PaymentType
import com.cestrada.keepingbooks.repository.PaymentRepository
import com.cestrada.keepingbooks.ui.custom.CustomProgressDialog
import com.cestrada.keepingbooks.ui.payment.MyPaymentRecyclerViewAdapter
import com.cestrada.keepingbooks.ui.payment.OnPaymentOptionsClickListener
import com.cestrada.keepingbooks.ui.payment.PaymentStateEvent
import com.cestrada.keepingbooks.ui.payment.PaymentsViewModel
import com.cestrada.keepingbooks.util.DataState
import com.cestrada.keepingbooks.util.SimpleStringDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payments_list.*

@AndroidEntryPoint
class PaymentListFragment : Fragment(R.layout.fragment_payments_list) {

    private val viewModel: PaymentsViewModel by viewModels()
    private val payments: MutableList<Payment> = mutableListOf()
    private val loadingDialog = CustomProgressDialog()

    companion object {
        const val TAG = "PaymentsFragment"
    }

    private val onPaymentOptionsClickListener = object : OnPaymentOptionsClickListener {
        override fun onDelete(payment: Payment, position: Int) {
            removePaymentFromList(payment, position)
        }

        override fun onEdit(payment: Payment, position: Int) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(PaymentStateEvent.GetPaymentsEvents)


    }

    private fun setupRecyclerView(){
        recycler_view_payments.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = MyPaymentRecyclerViewAdapter(
                    payments,
                    onPaymentOptionsClickListener
            )
        }
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<Payment>, PaymentRepository.Type> -> {
                    Log.d(TAG, "subscribeObservers: Success")
                    if(!(payments.containsAll(dataState.data))){
                        payments.clear()
                        payments.addAll(dataState.data)
                        recycler_view_payments.adapter!!.notifyDataSetChanged()
                    }
                    loadingDialog.dismiss()
                }
                is DataState.Error<PaymentRepository.Type> -> {
                    Log.d(TAG, "subscribeObservers: Error")
                    loadingDialog.dismiss()
                    if(dataState.type == PaymentRepository.Type.REMOVE){
                        viewModel.setStateEvent(PaymentStateEvent.GetPaymentsEvents)
                        Snackbar.make(requireView(), "Error trying to remove the payment", Snackbar.LENGTH_LONG).show()
                    }
                }
                is DataState.Loading -> {
                    Log.d(TAG, "subscribeObservers: Loading")
                    loadingDialog.show(parentFragmentManager, "TEST")
                }
            }
        })
    }

    private fun removePaymentFromList(payment: Payment, position: Int){
        payments.removeAt(position)
        recycler_view_payments.adapter?.notifyItemRemoved(position)
        viewModel.setStateEvent(PaymentStateEvent.RemovePayment(Payment(458, "", 12f, PaymentType.CREDIT, SimpleStringDate("2021-02-14 11:11:00"))))
    }

}