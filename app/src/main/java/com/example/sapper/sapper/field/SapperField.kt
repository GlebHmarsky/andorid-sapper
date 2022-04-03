package com.example.sapper.sapper.field

import androidx.lifecycle.MutableLiveData
import com.example.sapper.enums.ModeClick
import kotlin.random.Random

enum class GameStatus {
  WIN, PLAYING, LOSE
}

class SapperField(width: Int, size: Int, bombs: Int) {
  lateinit var field: Array<Array<SapperCell>>
  private var width = width
  private val size = size
  val bombsCount = bombs
  val flagsCount = MutableLiveData(0)
  val gameStatus = MutableLiveData(GameStatus.PLAYING)

  init {
    initField()
    updField()
//    startGame(0, 0)
  }

  fun startGame(
    exceptRow: Int,
    exceptColumn: Int
  ) {
    updField()
    field[exceptRow][exceptColumn].isOpen = true
    setUpBombs(
      exceptRow,
      exceptColumn
    )
    setUpNumericCells()
  }


  private fun initField() {
    field = Array(size) {
      Array(size) {
        SapperCell()
      }
    }
  }

  private fun updField() {
    var blockSize = width / size
    if (blockSize < 150) {
      blockSize = 150
    }

    var counter = 1
    for (i in 0 until size) {
      for (g in 0 until size) {
        updSapperCell(field[i][g], i, g, counter++, blockSize)

      }
    }
  }

  private fun checkAround(
    originRow: Int,
    originColumn: Int,
    targetRow: Int,
    targetColumn: Int
  ): Boolean {
    val up = originRow - 1
    val down = originRow + 1
    val left = originColumn - 1
    val right = originColumn + 1

    return ((up == targetRow && left == targetColumn) ||
            (up == targetRow && originColumn == targetColumn) ||
            (up == targetRow && right == targetColumn) ||

            (originRow == targetRow && left == targetColumn) ||
            (originRow == targetRow && originColumn == targetColumn) ||
            (originRow == targetRow && right == targetColumn) ||


            (down == targetRow && left == targetColumn) ||
            (down == targetRow && originColumn == targetColumn) ||
            (down == targetRow && right == targetColumn)
            )
  }

  private fun setUpBombs(
    exceptRow: Int,
    exceptColumn: Int
  ) {
    var bombsOnField = 0
    while (bombsOnField < bombsCount) {
      val rndRowIndex = Random.nextInt(0, size)
      val rndColumnIndex = Random.nextInt(0, size)

      val rndCell = field[rndRowIndex][rndColumnIndex]
      if (rndCell.isBomb ||
        checkAround(
          rndRowIndex,
          rndColumnIndex,
          exceptRow,
          exceptColumn
        )
      ) {
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

  fun openCells(modeClick: ModeClick, i: Int, g: Int) {
    if (modeClick == ModeClick.FLAG) {
      field[i][g].isFlagged = !field[i][g].isFlagged

      if (field[i][g].isFlagged) {
        flagsCount.value = flagsCount.value?.plus(1)
      } else {
        flagsCount.value = flagsCount.value?.minus(1)
      }

      return
    }

    if (field[i][g].isFlagged) {
      return
    }

    field[i][g].isOpen = true
    if (field[i][g].isBomb) {
      openAllBombs()
      gameStatus.value = GameStatus.LOSE
    } else {
      if (modeClick == ModeClick.OPEN) {
        openNeighborCells(i, g)
      }
    }
    if (checkField())
      gameStatus.value = GameStatus.WIN
  }


  /**
   * Returns true if game is finished
   */
  fun checkField(): Boolean {
    var openCellsCount = 0
    field.forEach { row -> row.forEach { cell -> if (cell.isOpen) openCellsCount++ } }
    return openCellsCount == this.size * this.size - this.bombsCount
  }


  private fun openAllBombs() {
    for (i in field.indices) {
      for (g in field[i].indices) {
        if (field[i][g].isBomb) {
          field[i][g].isOpen = true
        }
      }
    }

//    field.map { row ->
//      row.map {
//        if (it.isBomb) {
//          it.isOpen = true
//        }
//      }
//    }
  }

  private fun openNeighborCells(i: Int, g: Int) {
    val up = i - 1
    val down = i + 1
    val left = g - 1
    val right = g + 1
    field[i][g].isOpen = true
    if (field[i][g].bombsAroundCount != 0) {
      return
    }

    if (up >= 0 && left >= 0) {
      if (!field[up][left].isOpen) {
        openNeighborCells(up, left)
      }
    }

    if (up >= 0) {
      if (!field[up][g].isOpen) {
        openNeighborCells(up, g)
      }
    }

    if (up >= 0 && right < size) {
      if (!field[up][right].isOpen) {
        openNeighborCells(up, right)
      }
    }

    if (left >= 0) {
      if (!field[i][left].isOpen) {
        openNeighborCells(i, left)
      }
    }

    if (right < size) {
      if (!field[i][right].isOpen) {
        openNeighborCells(i, right)
      }
    }

    if (down < size && left >= 0) {
      if (!field[down][left].isOpen) {
        openNeighborCells(down, left)
      }
    }

    if (down < size) {
      if (!field[down][g].isOpen) {
        openNeighborCells(down, g)
      }
    }

    if (down < size && right < size) {
      if (!field[down][right].isOpen) {
        openNeighborCells(down, right)
      }
    }

  }
}
