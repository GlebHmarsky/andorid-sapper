package com.example.sapper.enums

enum class LevelDifficulty(val size: Int, val bombs: Int) {
  EASY(5, 5),
  MEDIUM(10, 13),
  HARD(20, 40)
}
