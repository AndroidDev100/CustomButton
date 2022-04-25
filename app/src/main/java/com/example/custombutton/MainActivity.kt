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
        binding!!.customButton.setButtonType(CustomButton.ButtonType.rounded)
        binding!!.customButton.setStroke(5,R.color.black)
        binding!!.customButton.setButtonBackgroundColor(R.color.blue)
        binding!!.customButton.setButtonElevation(10)
        binding!!.customButton.setButtonPadding(5)
    }

    fun click(v: View) {
        Toast.makeText(this, "Button clicked!", Toast.LENGTH_LONG).show()
    }
}