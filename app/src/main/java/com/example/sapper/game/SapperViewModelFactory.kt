package com.example.sapper.game

import android.app.Application
import android.content.Context
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SapperViewModelFactory(
  private val width: Int,
  private val application: Application
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SapperViewModel::class.java)) {
      return SapperViewModel(width,application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
