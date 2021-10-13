package com.cestrada.keepingbooks.ui.custom

import android.animation.ValueAnimator
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.animation.doOnEnd
import androidx.fragment.app.DialogFragment
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.ui.payment.list.PaymentListFragment.Companion.TAG
import kotlinx.android.synthetic.main.custom_progress_dialog.*


class CustomProgressDialog : DialogFragment(R.layout.custom_progress_dialog) {

    private var maxHeight: Int = -1
    private lateinit var mVA: ValueAnimator
    private var mTimer: CountDownTimer? = null

    private fun setDialogGravity(gravity: Int) {
        if (dialog != null) {
            if (dialog!!.window != null) {
                val params = dialog!!.window!!.attributes
                params.width = WindowManager.LayoutParams.WRAP_CONTENT
                params.height = (160*requireView().context.resources.displayMetrics.density).toInt()
                params.horizontalMargin = 0f
                params.gravity = gravity
                params.dimAmount = 0f
                dialog!!.window!!.attributes = params
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogGravity(Gravity.CENTER)
        maxHeight = (160*requireView().context.resources.displayMetrics.density).toInt()
        mVA =  ValueAnimator.ofInt(image_logo_progress.layoutParams.width, maxHeight)
        mTimer = object : CountDownTimer(250,500) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                image_logo_progress.layoutParams.height = 0
                image_logo_progress.requestLayout()
                growLogo()
            }

        }
        growLogo()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mTimer?.cancel()
        mTimer = null
        mVA.cancel()
    }


    private fun growLogo(){
        Log.d(TAG, "growLogo: growing")
        mVA.duration = 1500
        mVA.addUpdateListener {
            image_logo_progress.layoutParams.height = it.animatedValue as Int
            image_logo_progress.requestLayout()
        }
        mVA.doOnEnd {
           mTimer?.start()
        }
        mVA.start()
    }

}