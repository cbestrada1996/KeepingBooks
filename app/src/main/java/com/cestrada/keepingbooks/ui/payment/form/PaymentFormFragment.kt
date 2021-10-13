package com.cestrada.keepingbooks.ui.payment.form

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.ui.custom.SpinnerPaymentTypeAdapter
import com.cestrada.keepingbooks.ui.payment.PaymentsViewModel
import com.cestrada.keepingbooks.util.SimpleStringDate
import kotlinx.android.synthetic.main.fragment_payment_form.*
import java.text.SimpleDateFormat
import java.util.*


class PaymentFormFragment: Fragment(R.layout.fragment_payment_form) {

    private val COUNTRIES = arrayOf(
            "Belgium", "France", "Italy", "Germany", "Spain"
    )

    private val viewModel: PaymentsViewModel by viewModels()

    private var mPaymentDate: SimpleStringDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES)

        auto_complete_description.setAdapter(adapter)
        spinner_type.adapter = SpinnerPaymentTypeAdapter(requireContext())

        setDatePicker()
    }

    private fun setDatePicker(){
        val myCalendar = Calendar.getInstance()
        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }

        edit_date.setOnClickListener {
            DatePickerDialog(requireContext(), date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        image_calendar.setOnClickListener {
            DatePickerDialog(requireContext(), date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]).show()
        }
    }
    private fun updateDate() {
        val myFormat = "MMM dd, y"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edit_date.setText(sdf.format(Calendar.getInstance().getTime()))

        val datePaymentFormat = "yyyy-MM-dd HH:mm:SS"
        val sdf2 = SimpleDateFormat(datePaymentFormat, Locale.US)
        mPaymentDate = SimpleStringDate(sdf2.format(Calendar.getInstance().getTime()))
    }

    private fun setUpTags(){
        image_add_tags.setOnClickListener {
            
        }
    }

}