package com.example.sapper.sapper.field

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import kotlin.random.Random

class SapperField(width: Int, size: Int, bombs: Int) {
  private lateinit var field: Array<Array<SapperCell>>
  private var width = width
  private val size = size
  private val bombsCount = bombs

  init {
    startGame()
  }

  private fun startGame() {
    initField()
    updField()
    setUpBombs()
    setUpNumericCells()
  }

  private fun initField() {
    val blockSize = width / size
    field = Array(size) {
      Array(size) {
        SapperCell()
      }
    }
  }

  private fun updField() {
    val blockSize = width / size
    var counter = 1
    for (i in 0 until size) {
      for (g in 0 until size) {
        updSapperCell(field[i][g], i, g, counter++, blockSize)
      }
    }
  }

  private fun setUpBombs() {
    var bombsOnField = 0
    while (bombsOnField < bombsCount) {
      val rndRowIndex = Random.nextInt(0, size)
      val rndColumnIndex = Random.nextInt(0, size)

      val rndCell = field[rndRowIndex][rndColumnIndex]
      if (rndCell.isBomb) {
        continue
      }
      rndCell.isBomb = true

      bombsOnField++
    }
  }

  private fun setUpNumericCells() {
    for (i in field.indices) {
      for (g in field[i].indices) {
        field[i][g].bombsAroundCount = countBombAround(i, g)
      }
    }
  }


  private fun updSapperCell(
    sapperCell: SapperCell,
    row: Int,
    column: Int,
    id: Int,
    blockSize: Int
  ) {
    val pseudoMargin = 15
    val size = blockSize - pseudoMargin

    sapperCell.id = id
    sapperCell.topMargin = ((size + pseudoMargin) * row + pseudoMargin / 2)
    sapperCell.leftMargin = ((size + pseudoMargin) * column + pseudoMargin / 2)
    sapperCell.cellSize = size
  }

  private fun countBombAround(i: Int, g: Int): Int {
    var bombCounter = 0
    val up = i - 1
    val down = i + 1
    val left = g - 1
    val right = g + 1

    if (up >= 0 && left >= 0) {
      if (field[up][left].isBomb) bombCounter++
    }

    if (up >= 0) {
      if (field[up][g].isBomb) bombCounter++
    }

    if (up >= 0 && right < size) {
      if (field[up][right].isBomb) bombCounter++
    }

    if (left >= 0) {
      if (field[i][left].isBomb) bombCounter++
    }

    if (right < size) {
      if (field[i][right].isBomb) bombCounter++
    }

    if (down < size && left >= 0) {
      if (field[down][left].isBomb) bombCounter++
    }

    if (down < size) {
      if (field[down][g].isBomb) bombCounter++
    }

    if (down < size && right < size) {
      if (field[down][right].isBomb) bombCounter++
    }

    return bombCounter
  }

  // TODO: Change it (Separate logic?)
//  private fun createButton(
//    countHorizontal: Int,
//    countVertical: Int,
//    id: Int,
//    blockSize: Int
//  ): Button {
//    val pseudoMargin = 15
//    val buttonSize = blockSize - pseudoMargin
//
//    val buttonDynamic = Button(context)
//    buttonDynamic.id = id
//
//    buttonDynamic.setBackgroundColor(Color.LTGRAY)
//    buttonDynamic.layoutParams = RelativeLayout.LayoutParams(
//      RelativeLayout.LayoutParams.WRAP_CONTENT,
//      ViewGroup.LayoutParams.WRAP_CONTENT
//    )
//
//    val param = buttonDynamic.layoutParams as ViewGroup.MarginLayoutParams
//    param.setMargins(
//      ((buttonSize + pseudoMargin) * countVertical + pseudoMargin / 2),
//      ((buttonSize + pseudoMargin) * countHorizontal + pseudoMargin / 2),
//      0,
//      0
//    )
//    buttonDynamic.layoutParams.width = buttonSize
//    buttonDynamic.layoutParams.height = buttonSize
//    buttonDynamic.height = buttonSize
//
//
//    return buttonDynamic
//  }
}