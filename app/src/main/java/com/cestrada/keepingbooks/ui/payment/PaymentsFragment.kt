package com.cestrada.keepingbooks.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.model.PaymentType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payments_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyPaymentRecyclerViewAdapter(
                        listOf(
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021"),
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021"),
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021"),
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021"),
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021"),
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021"),
                                Payment(1,"Pruebas", 12f, PaymentType.CREDIT, "12/15/2021")
                        )
                )
            }
        }
        return view
    }


}