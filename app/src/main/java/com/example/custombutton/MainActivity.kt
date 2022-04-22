package com.example.custombutton

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.custombutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var clickListner: ((View) -> (Unit))? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        clickListner = ::click
        binding!!.customButton.setClickListner(clickListner as ((View) -> (Unit)))
    }

    fun click(v: View) {
        Toast.makeText(this, "Button clicked!", Toast.LENGTH_LONG).show()
    }
}