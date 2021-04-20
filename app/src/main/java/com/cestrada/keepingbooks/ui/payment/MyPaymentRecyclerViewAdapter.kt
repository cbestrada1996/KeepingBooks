package com.cestrada.keepingbooks.ui.payment

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.model.Payment

class MyPaymentRecyclerViewAdapter(
        private val values: List<Payment>,
        private val listener: OnPaymentOptionsClickListener
        )
    : RecyclerView.Adapter<PaymentItemViewHolder>() {

    companion object {
        const val TAG = "MyPaymentRecyclerViewAdapter"
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_payment, parent, false)
        return PaymentItemViewHolder(view)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: PaymentItemViewHolder, position: Int) {
        val item = values[position]

        holder.setUpView(item, listener)
    }

    override fun getItemCount(): Int = values.size
}

interface OnPaymentOptionsClickListener {
    fun onDelete(payment: Payment)
    fun onEdit(payment: Payment)
}