package com.cestrada.keepingbooks.listener

import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.absoluteValue

abstract class OnTouchExtendsListener: View.OnTouchListener {

    companion object {
        private const val TAG = "OnTouchExtendsListener"
        private const val DOUBLE_TAP_DELTA = 300
        private const val LONG_TAP_DELTA = 500
    }

    private var isSwipingAlreadyStart: Boolean = false
    private var stateLastTick: Int = 0
    private var timeLastTap: Long = 0L
    private var timeStartTap: Long = 0L

    private var positionLastTick: Position = Position(0f,0f)

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if(event != null){
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "onTouch DOWN")
                    positionLastTick.x = event.x
                    positionLastTick.y = event.y
                    isSwipingAlreadyStart = false
                    stateLastTick = MotionEvent.ACTION_DOWN
                    timeStartTap =  System.currentTimeMillis()
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d(TAG, "onTouch MOVE")
                    stateLastTick = MotionEvent.ACTION_MOVE

                    //Calculate movement from the las tick
                    val xMovement = (event.x - positionLastTick.x)
                    val yMovement = (event.y - positionLastTick.y)

                    //Updating the las Move event
                    positionLastTick.x = event.x
                    positionLastTick.y = event.y


                    //First check for left or rigth movement
                    if(xMovement != 0f){
                        val direction = if (xMovement < 0) SwipeDirection.LEFT else SwipeDirection.RIGHT
                        onSwipingHorizontal(xMovement, direction)
                    }

                    //Second check for up or down movement
                    if(yMovement != 0f){
                        val direction = if (yMovement < 0) SwipeDirection.UP else SwipeDirection.DOWN
                        onSwipingVertical(yMovement, direction)
                    }

                }
                MotionEvent.ACTION_UP -> {
                    Log.d(TAG, "onTouch UP")
                    if(stateLastTick == MotionEvent.ACTION_DOWN){
                        checkForTap()
                        return true
                    }

                    onFinishSwiping()
                }
                MotionEvent.ACTION_CANCEL  -> {
                    onFinishSwiping()
                }
            }
        }

        return true
    }

    abstract fun onDoubleTap()
    abstract fun onLongTap()
    abstract fun onSwipingHorizontal(movement: Float, direction: SwipeDirection)
    abstract fun onSwipingVertical(movement: Float, direction: SwipeDirection)
    abstract fun onFinishSwiping()

    private fun checkForTap(){
        val diffTimeLongTab = (timeStartTap-System.currentTimeMillis()).absoluteValue
        val diffTimeLastTap = (timeLastTap-System.currentTimeMillis()).absoluteValue
        timeLastTap = System.currentTimeMillis()

        if(timeStartTap != 0L && diffTimeLongTab >= LONG_TAP_DELTA){
            timeStartTap = 0L
            onLongTap()
            return
        }

        if(diffTimeLastTap <= DOUBLE_TAP_DELTA){
            timeLastTap = 0
            onDoubleTap()
            return
        }

    }

    enum class SwipeDirection {
        UP, RIGHT,DOWN, LEFT, NO_DIRECTION
    }

    data class Position(var x: Float, var y: Float)
}