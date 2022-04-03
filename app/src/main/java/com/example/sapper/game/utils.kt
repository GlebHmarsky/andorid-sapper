package com.example.sapper.game

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import com.example.sapper.R
import com.example.sapper.sapper.field.SapperCell

fun udpView(button: Button, sapperCell: SapperCell, context: Context) {
  button.textSize = button.layoutParams.width * 0.17F
  if (sapperCell.isOpen) {
    button.setBackgroundColor(Color.TRANSPARENT)

    val gd = GradientDrawable()
    gd.setStroke(6, Color.LTGRAY)
    button.background = gd
    if (sapperCell.isBomb) {
      val img = context.resources.getDrawable(R.drawable.ic_mine)
      img.setBounds(
        (button.layoutParams.width * 0.15F).toInt(),
        0,
        (button.layoutParams.width * 0.85F).toInt(),
        (button.layoutParams.width * 0.70F).toInt()
      )
      button.setCompoundDrawables(img, null, null, null)
    } else if (sapperCell.bombsAroundCount != 0) {
      button.text = sapperCell.bombsAroundCount.toString()
    }
  }
  if (sapperCell.isFlagged) {
    val img = context.resources.getDrawable(R.drawable.ic_flag)
    img.setBounds(
      (button.layoutParams.width * 0.15F).toInt(),
      0,
      (button.layoutParams.width * 0.85F).toInt(),
      (button.layoutParams.width * 0.85F).toInt()
    )
    button.setCompoundDrawables(img, null, null, null)
    button.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL


  }
}
