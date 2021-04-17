package com.cestrada.keepingbooks.ui.payment

import android.animation.ObjectAnimator
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.cestrada.keepingbooks.R
import com.cestrada.keepingbooks.model.Payment

private const val TAG = "MyPaymentRecyclerViewAdapter";

class MyPaymentRecyclerViewAdapter(
        private val values: List<Payment>)
    : RecyclerView.Adapter<MyPaymentRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_payment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.textPaymentDescription.text = item.description
        holder.textPaymentTotal.text = "$${item.total}"
        holder.textPaymentDate.text = item.date
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnTouchListener {
        val maxMovementPerTick = 25f
        var maxMovement: Float = 100 * view.context.resources.displayMetrics.density

        var startTouchPaymentViewX: Float = 0f
        var startTouchPaymentTranslationX: Float = 0f
        var touchPaymentViewX: Float = 0f
        var isSwipe = false


        val constraintPaymentRoot: ConstraintLayout =  view.findViewById(R.id.constraint_payment_root)
        val constraintPaymentInfo: ConstraintLayout =  view.findViewById(R.id.constraint_payment_info)
        val imagePaymentType: ImageView = view.findViewById(R.id.image_type_payment)
        val textPaymentDescription: TextView = view.findViewById(R.id.text_payment_description)
        val textPaymentTotal: TextView = view.findViewById(R.id.text_payment_total)
        val textPaymentDate: TextView = view.findViewById(R.id.text_payment_date)

        init {
            constraintPaymentRoot.setOnTouchListener(this)
        }

        override fun toString(): String {
            return super.toString() + " '" + textPaymentDescription.text + "'"
        }

        override fun onTouch(view: View?, event: MotionEvent?): Boolean {
            if(event != null){
                when(event.action){
                    MotionEvent.ACTION_DOWN -> {
                        Log.d(TAG, "onTouch DOWN")
                        startTouchPaymentViewX = event.x
                        touchPaymentViewX = event.x
                        startTouchPaymentTranslationX = constraintPaymentInfo.translationX
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.d(TAG, "onTouch MOVE")
                        var movement = (touchPaymentViewX - event.x)
                        touchPaymentViewX = event.x
                        if(movement < 0) return true //Swiping Right
                        movement = if(movement > maxMovementPerTick) maxMovementPerTick else movement;
                        val realMovement = constraintPaymentInfo.translationX - movement
                        if (isSwipe) return true

                        if(-realMovement <= maxMovement + 80){
                            ObjectAnimator.ofFloat(constraintPaymentInfo, "translationX", realMovement).apply {
                                duration = 0
                                start()
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        val movement = constraintPaymentInfo.translationX
                        Log.d(TAG, "onTouch UP")
                        if (isSwipe) {
                            ObjectAnimator.ofFloat(constraintPaymentInfo, "translationX", 0f).apply {
                                duration = 500
                                start()
                            }
                            isSwipe = false
                            return true
                        }

                        if (!isSwipe) {
                            Log.d(TAG, "onTouch !isSwipe: movement: ${-movement} maxMovement: $maxMovement")
                            if (-movement < maxMovement) {
                                Log.d(TAG, "onTouch !isSwipe: return")
                                val calDuration = (500 * ((-movement) / maxMovement)).toLong()
                                ObjectAnimator.ofFloat(constraintPaymentInfo, "translationX", 0f).apply {
                                    duration = calDuration
                                    start()
                                }
                            } else {
                                Log.d(TAG, "onTouch !isSwipe: stay")
                                ObjectAnimator.ofFloat(constraintPaymentInfo, "translationX", -maxMovement).apply {
                                    duration = (250 * (constraintPaymentInfo.translationX / (-maxMovement))).toLong()
                                    start()
                                }
                                isSwipe = true
                            }
                        }
                    }
                }
            }

            return true;
        }


    }
}