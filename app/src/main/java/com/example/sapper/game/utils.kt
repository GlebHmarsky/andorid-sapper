package com.example.sapper.game

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import com.example.sapper.sapper.field.SapperCell

fun udpView(button: Button, sapperCell: SapperCell) {

  if (sapperCell.isOpen) {
    button.isClickable = false
    button.setBackgroundColor(Color.TRANSPARENT)

    val gd = GradientDrawable()
    gd.setStroke(6, Color.LTGRAY)
    button.background = gd
    if (sapperCell.isBomb) {
      button.text = "B"
      button.setTextColor(Color.RED)
    } else if (sapperCell.bombsAroundCount != 0) {
      button.text = sapperCell.bombsAroundCount.toString()
    }
  }
  if (sapperCell.isFlagged) {
    button.text = "F"
    button.setTextColor(Color.RED)
  }
}


fun setListener(button: Button, sapperCell: SapperCell) {
  button.setOnClickListener() {
    button.setBackgroundColor(Color.TRANSPARENT)
    val gd = GradientDrawable()
    gd.setStroke(6, Color.LTGRAY)
    button.background = gd
    button.isClickable = false
    button.textSize = button.layoutParams.width * 0.17F

    if (sapperCell.isBomb) {
      button.text = "B"
      button.setTextColor(Color.RED)
    } else if (sapperCell.bombsAroundCount != 0) {
      button.text = sapperCell.bombsAroundCount.toString()
    }
  }
}