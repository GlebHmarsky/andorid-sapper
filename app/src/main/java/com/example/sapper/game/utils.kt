package com.example.sapper.game

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.Button
import com.example.sapper.R
import com.example.sapper.enums.ModeClick
import com.example.sapper.sapper.field.SapperCell

fun udpView(button: Button, sapperCell: SapperCell, context: Context) {
  button.textSize = button.layoutParams.width * 0.17F
  if (sapperCell.isOpen) {
    button.isClickable = false
    button.setBackgroundColor(Color.TRANSPARENT)

    val gd = GradientDrawable()
    gd.setStroke(6, Color.LTGRAY)
    button.background = gd
    if (sapperCell.isBomb) {
      val img = context.resources.getDrawable(R.drawable.ic_mine)
      img.setBounds(15, 0, 85, 70)
      button.setCompoundDrawables(img, null, null, null)
//      button.text = "B"
//      button.setTextColor(Color.RED)
    } else if (sapperCell.bombsAroundCount != 0) {
      button.text = sapperCell.bombsAroundCount.toString()
    }
  }
  if (sapperCell.isFlagged) {
    val img = context.resources.getDrawable(R.drawable.ic_flag)
    img.setBounds(15, 0, 85, 90)
    button.setCompoundDrawables(img, null, null, null)
//    button.isClickable = false
//    button.text = "F"
//    button.setTextColor(Color.BLUE)

  }
}
