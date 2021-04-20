package com.cestrada.keepingbooks.ui.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payments_list.*

@AndroidEntryPoint
class PaymentsFragment : Fragment(R.layout.fragment_payments_list) {

    private val viewModel: PaymentsViewModel by viewModels()
    private val payments: MutableList<Payment> = mutableListOf()
    companion object {
        const val TAG = "PaymentsFragment"
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
                    viewModel.onPaymentOptionsClickListener
            )
        }
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<Payment>> -> {
                    Log.d(TAG, "subscribeObservers: Success")
                    payments.clear()
                    payments.addAll(dataState.data)
                    recycler_view_payments.adapter!!.notifyDataSetChanged()
                }
                is DataState.Error -> {
                    Log.d(TAG, "subscribeObservers: Error")
                }
                is DataState.Loading -> {
                    Log.d(TAG, "subscribeObservers: Loading")
                }
            }
        })
    }

}