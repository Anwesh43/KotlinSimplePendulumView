package ui.anwesome.com.oscillatedpendulumview

/**
 * Created by anweshmishra on 22/01/18.
 */
import android.app.Activity
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
    data class OscillatedPendulum(var x:Float,var y:Float,var l:Float,var r:Float) {
        val state = OscillatedPendulumState()
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor("#4527A0")
            canvas.save()
            canvas.translate(x,y)
            state.executeFn {
                canvas.rotate(it)
            }
            paint.strokeWidth = r/7
            canvas.drawLine(0f,0f,0f,l,paint)
            canvas.drawCircle(0f,l+r,r,paint)
            canvas.restore()
        }
        fun update(stopcb:()->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
        }
    }
    data class OscillatedPendulumState(var deg:Float = 0f,var scale:Float = 0f,var dir:Float = 0f) {
        fun update(stopcb:()->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale) > 1) {
                scale = dir
                dir *= -1
            }
            if(deg == 0f && scale > 0) {
                dir = 0f
                scale = 0f
                deg = 0f
                stopcb()
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                deg = 90f
                dir = 1 - 2 * scale
                startcb()
            }
        }
        fun executeFn(cb:(Float)->Unit) {
            cb(deg*scale)
        }
    }
    class OscillatedAnimator(var view:OscillatedPendulumView,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(!animated) {
                animated = true
            }
        }
    }
    class OscillatedPendulumRenderer(var view:OscillatedPendulumView,var time:Int = 0) {
        var oscillatedPendulum:OscillatedPendulum?=null
        val animator = OscillatedAnimator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                oscillatedPendulum = OscillatedPendulum(w/2,h/2,w/3,w/20)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            oscillatedPendulum?.draw(canvas,paint)
            time++
            animator.animate {
                oscillatedPendulum?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            oscillatedPendulum?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity:Activity):OscillatedPendulumView {
            val view = OscillatedPendulumView(activity)
            activity.setContentView(view)
            return view
        }
    }
}