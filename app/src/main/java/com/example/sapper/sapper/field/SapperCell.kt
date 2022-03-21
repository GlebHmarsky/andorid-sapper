package com.example.sapper.sapper.field

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.Button

class SapperCell() {
  var id: Int = 0;
  var bombsAroundCount: Int = 0;
  var isBomb: Boolean = false
  var isOpen: Boolean = false
  var isFlagged: Boolean = false


  var cellSize: Int = 10
  var leftMargin: Int = 0
  var topMargin: Int = 0

}