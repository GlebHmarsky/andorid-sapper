package com.example.sapper.game

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.Display
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBindings
import com.example.sapper.R
import com.example.sapper.databinding.FragmentMainGameBinding
import com.example.sapper.sapper.field.SapperField

class SapperViewModel(application: Application) :
  AndroidViewModel(application) {
  lateinit var sapperField: SapperField
  var width = 0
  var height = 0

  init {
    initial()
  }

  private fun initial() {
    sapperField = SapperField(width, 5, 10)
  }


}