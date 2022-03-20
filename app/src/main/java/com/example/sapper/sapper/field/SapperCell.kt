package com.example.sapper.sapper.field

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.Button

class SapperCell(button: Button) {
  var button: Button = button;
  var bombsAroundCount: Int = 0;
  var isBomb: Boolean = false

  init {
    setListener()
  }

  private fun setListener() {
    button.setOnClickListener() {
      button.setBackgroundColor(Color.TRANSPARENT)
      val gd = GradientDrawable()
      gd.cornerRadius = 15f
      gd.setStroke(6, Color.LTGRAY)
      button.background = gd
      button.isClickable = false
      button.textSize = button.layoutParams.width * 0.17F

      if (isBomb) {
        button.text = "B"
        button.setTextColor(Color.RED)
      } else if (bombsAroundCount != 0) {
        button.text = bombsAroundCount.toString()
      }
    }
  }
}