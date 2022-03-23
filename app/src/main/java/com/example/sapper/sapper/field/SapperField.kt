package com.example.sapper.sapper.field

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.lifecycle.LiveData
import kotlin.random.Random

class SapperField(width: Int, size: Int, bombs: Int) {
  lateinit var field: Array<Array<SapperCell>>
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

  fun checkField(): Boolean {
    return field.all { row -> row.all { it.isFlagged || it.isOpen } }
  }

  private fun initField() {
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

  fun openNeighborCells(i: Int, g: Int) {
    val up = i - 1
    val down = i + 1
    val left = g - 1
    val right = g + 1

    if (up >= 0) {
      if (!field[up][g].isBomb) field[up][g].isOpen = true
    }

    if (down < size) {
      if (!field[down][g].isBomb) field[down][g].isOpen = true
    }

    if (left >= 0) {
      if (!field[i][left].isBomb) field[i][left].isOpen = true
    }

    if (right < size) {
      if (!field[i][right].isBomb) field[i][right].isOpen = true
    }
  }
}
