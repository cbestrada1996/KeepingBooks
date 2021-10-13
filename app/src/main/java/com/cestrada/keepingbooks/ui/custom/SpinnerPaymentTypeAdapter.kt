package com.cestrada.keepingbooks.ui.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.model.PaymentType

class SpinnerPaymentTypeAdapter constructor(context: Context): BaseAdapter() {

    private val list = PaymentType.getAll()
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): PaymentType {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = inflater.inflate(R.layout.custom_spinner_item, null)
        val imageType = view.findViewById<ImageView>(R.id.image_type)
        val textType = view.findViewById<TextView>(R.id.text_type)

        imageType.setImageResource(
                when(list[p0]){
                    PaymentType.CASH -> R.drawable.ic_payment_cash
                    PaymentType.DEBIT -> R.drawable.ic_payment_debit
                    PaymentType.CREDIT -> R.drawable.ic_payment_credit
                }
        )
        textType.text = PaymentType.getString(list[p0])

        return view
    }

}