package ui.anwesome.com.oscillatedpendulumview

/**
 * Created by anweshmishra on 22/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class OscillatedPendulumView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}