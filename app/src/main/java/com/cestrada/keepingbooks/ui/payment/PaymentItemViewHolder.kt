package com.cestrada.keepingbooks.ui.payment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.listener.OnTouchExtendsListener
import com.cestrada.keepingbooks.model.Payment
import com.cestrada.keepingbooks.model.PaymentType
import kotlin.math.absoluteValue

@SuppressLint("ClickableViewAccessibility")
class PaymentItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private val TAG = "PaymentItemViewHolder"
    }

    private val swipedPosition = (-100 * view.context.resources.displayMetrics.density)
    private val maxSwipePosition: Float = swipedPosition - 80
    private val maxMovementPerTick = 25f
    private val maxScaleSizePaymentOptions = 1.286f

    private var isSwipingAlreadyStart: Boolean = false
    private var isSwipe = false



    val constraintPaymentRoot: ConstraintLayout =  view.findViewById(R.id.constraint_payment_root)
    val constraintPaymentInfo: ConstraintLayout =  view.findViewById(R.id.constraint_payment_info)
    val imagePaymentType: ImageView = view.findViewById(R.id.image_type_payment)
    val imagePaymentDelete: ImageView = view.findViewById(R.id.image_delete)
    val imagePaymentEdit: ImageView = view.findViewById(R.id.image_edit)
    val textPaymentDescription: TextView = view.findViewById(R.id.text_payment_description)
    val textPaymentTotal: TextView = view.findViewById(R.id.text_payment_total)
    val textPaymentDate: TextView = view.findViewById(R.id.text_payment_date)

    val mContext = view.context
    private lateinit var mPayment: Payment

    private val onTouchExtendsListener = object : OnTouchExtendsListener() {
        override fun onDoubleTap() {
            if(isSwipe) {
                moveToInitPosPaymentInfoView()
                resizeToInitPaymentsOptions()
                return
            }

            moveToSwipedPosPaymentInfoView()
            resizeToSwipedPaymentsOptions()
        }
        override fun onLongTap() {

        }
        override fun onSwipingHorizontal(movement: Float, direction: SwipeDirection) {
            val isSwipingLeft = direction == SwipeDirection.LEFT

            //Don't care if start by swiping right
            if (!isSwipingLeft && !isSwipingAlreadyStart) return

            if (!isSwipingAlreadyStart && isSwipingLeft) {
                isSwipingAlreadyStart = true
            }

            moveBySwipePaymentInfoView(movement)
            resizeBySwipePaymentOptions(movement)
        }
        override fun onSwipingVertical(movement: Float, direction: SwipeDirection) {

        }
        override fun onFinishSwiping() {
            checkDistanceOfSwipe()
        }
    }

    init {
        constraintPaymentRoot.setOnTouchListener(onTouchExtendsListener)
    }

    fun setUpView(payment: Payment, listener: OnPaymentOptionsClickListener){
        mPayment =  payment

        this.textPaymentDescription.text = mPayment.description
        this.textPaymentTotal.text = mContext.resources.getString(R.string.list_payment_info_cost, mPayment.total)
        this.textPaymentDate.text = mPayment.date.getTimePassedUntilToday()

        imagePaymentEdit.setOnClickListener { listener.onEdit(mPayment) }
        imagePaymentDelete.setOnClickListener { listener.onDelete(mPayment) }
        imagePaymentDelete.isClickable = false
        imagePaymentEdit.isClickable = false

        setUpPaymentTypeIcon(mPayment.payment_type)
    }

    private fun setUpPaymentTypeIcon(paymentType: PaymentType){
        when (paymentType) {
            PaymentType.CREDIT -> {
                imagePaymentType.setImageResource(R.drawable.ic_payment_credit)
            }
            PaymentType.DEBIT -> {
                imagePaymentType.setImageResource(R.drawable.ic_payment_debit)
            }
            PaymentType.CASH -> {
                imagePaymentType.setImageResource(R.drawable.ic_payment_cash)
            }
        }
    }

    private fun calculateAnimDurationByDistance(distance: Float): Long{
        val ratio = 500 / (100 * view.context.resources.displayMetrics.density)
        return (distance.absoluteValue*ratio).toLong()
    }
    private fun calculateAnimDurationByScale(scale: Float): Long{
        val ratio = 500 / (0.28)
        return (scale.absoluteValue*ratio).toLong()
    }
    private fun calculateAlphaByScale(scale: Float): Float {
        val ratio = 1 / (maxScaleSizePaymentOptions-1)

        return (scale-1) * ratio
    }

    private fun checkDistanceOfSwipe(){
        if (constraintPaymentInfo.translationX > swipedPosition/2) {
            moveToInitPosPaymentInfoView()
            resizeToInitPaymentsOptions()
        } else {
            moveToSwipedPosPaymentInfoView()
            resizeToSwipedPaymentsOptions()
        }
    }

    private fun resizeBySwipePaymentOptions(swipeMovement: Float){
        val movement = if(swipeMovement > maxMovementPerTick) maxMovementPerTick else swipeMovement
        var finalPos= constraintPaymentInfo.translationX + movement
        finalPos = if(finalPos > 0) 0f else finalPos
        val scale: Float = ((finalPos*(maxScaleSizePaymentOptions-1)) / maxSwipePosition)+1f
        Log.d(TAG, "resizeBySwipePaymentOptions: Scale: $scale maxScaleSizePaymentOptions: $maxScaleSizePaymentOptions")
        if(scale <= maxScaleSizePaymentOptions){
            resizePaymentsOptions(scale, 0)
        }
    }
    private fun resizeToInitPaymentsOptions(){
        Log.d(TAG, "resizeToInitPaymentsOptions")
        imagePaymentDelete.isClickable = false
        imagePaymentEdit.isClickable = false
        resizePaymentsOptions(1f)
    }
    private fun resizeToSwipedPaymentsOptions(){
        Log.d(TAG, "resizeToSwipedPaymentsOptions")
        imagePaymentDelete.isClickable = true
        imagePaymentEdit.isClickable = true
        resizePaymentsOptions(maxScaleSizePaymentOptions)
    }
    private fun resizePaymentsOptions(scale: Float, animDuration: Long? = null){
        val actualScale = imagePaymentDelete.scaleX
        val realDuration = animDuration ?: calculateAnimDurationByScale((actualScale-scale).absoluteValue)
        val alpha = calculateAlphaByScale(scale)

        scalePaymentOption(imagePaymentDelete, scale, realDuration, alpha)
        scalePaymentOption(imagePaymentEdit, scale, realDuration, alpha)
    }
    private fun scalePaymentOption(option: View, scale: Float, realDuration: Long, alpha: Float){
        Log.d(TAG, "scalePaymentOption: alpha $alpha Scale $scale")

        ObjectAnimator.ofFloat(option, "scaleX", scale).apply {
            duration = realDuration
            start()
        }
        ObjectAnimator.ofFloat(option, "scaleY", scale).apply {
            duration = realDuration
            start()
        }
        ObjectAnimator.ofFloat(option, "alpha", alpha).apply {
            duration = realDuration
            start()
        }
    }

    private fun moveBySwipePaymentInfoView(swipeMovement: Float){
        val movement = if(swipeMovement > maxMovementPerTick) maxMovementPerTick else swipeMovement
        var finalPos= constraintPaymentInfo.translationX + movement
        finalPos = if(finalPos > 0) 0f else finalPos
        if(finalPos >= maxSwipePosition){
            movePaymentInfoView(finalPos, constraintPaymentInfo.translationX, reqDuration = 0)
        }
    }
    private fun moveToInitPosPaymentInfoView(){
        Log.d(TAG, "moveToInitPosPaymentInfoView")
        movePaymentInfoView(0f, constraintPaymentInfo.translationX, false)
    }
    private fun moveToSwipedPosPaymentInfoView(){
        Log.d(TAG, "moveToSwipedPosPaymentInfoView")
        movePaymentInfoView(swipedPosition, constraintPaymentInfo.translationX, true)
    }
    private fun movePaymentInfoView(finalPos:Float, currentPos: Float, isSwipe: Boolean? = null, reqDuration: Long? = null){
        val durationByDistance = reqDuration ?: calculateAnimDurationByDistance(finalPos - currentPos)
        Log.d(TAG, "movePaymentInfoView: durationByDistance $durationByDistance Distance ${finalPos - currentPos}")
        val anim = ObjectAnimator.ofFloat(constraintPaymentInfo, "translationX", finalPos)
                .apply {
                    duration = durationByDistance
                }
        if(isSwipe != null) anim.doOnEnd {this.isSwipe = isSwipe}

        anim.start()
    }

}