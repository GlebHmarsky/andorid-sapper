package com.example.sapper.game

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.Button
import com.example.sapper.sapper.field.SapperCell

fun udpView(button: Button, sapperCell: SapperCell) {
  button.textSize = button.layoutParams.width * 0.17F
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
    button.isClickable = false
    button.text = "F"
    button.setTextColor(Color.RED)
  }
}

fun setListener(
  button: Button,
  sapperCell: SapperCell,
  modeOpen: Boolean,
  callback: () -> Unit
) {
  button.setOnClickListener() {
    button.isClickable = false
    button.textSize = button.layoutParams.width * 0.17F
    if (modeOpen) {
      button.setBackgroundColor(Color.TRANSPARENT)
      sapperCell.isOpen = true

      val gd = GradientDrawable()
      gd.setStroke(6, Color.LTGRAY)
      button.background = gd
      if (sapperCell.isBomb) {
        button.text = "B"
        button.setTextColor(Color.RED)
      } else if (sapperCell.bombsAroundCount != 0) {
        button.text = sapperCell.bombsAroundCount.toString()
      }
    } else {
      sapperCell.isFlagged = true
      button.text = "F"
    }

    callback()
  }
}