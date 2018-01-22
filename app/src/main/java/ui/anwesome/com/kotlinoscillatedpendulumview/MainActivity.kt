package ui.anwesome.com.kotlinoscillatedpendulumview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.oscillatedpendulumview.OscillatedPendulumView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = OscillatedPendulumView.create(this)
    }
}
