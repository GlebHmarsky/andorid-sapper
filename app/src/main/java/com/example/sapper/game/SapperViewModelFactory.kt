package com.example.sapper.game

import android.app.Application
import android.content.Context
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SapperViewModelFactory(
  private val context: Context,
  private val layout: RelativeLayout,
  private val application: Application
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SapperViewModel::class.java)) {
      return SapperViewModel(context, layout, application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
