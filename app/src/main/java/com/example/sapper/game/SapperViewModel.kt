package com.example.sapper.game

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.Display
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBindings
import com.example.sapper.R
import com.example.sapper.databinding.FragmentMainGameBinding
import com.example.sapper.sapper.field.SapperField


class SapperViewModel(width: Int, application: Application) :
  AndroidViewModel(application) {
  lateinit var sapperField: SapperField
  var width = width

  var modeOpen = MutableLiveData<Boolean>(true)


  init {
    initial()
  }

  private fun initial() {
    sapperField = SapperField(width, 5, 10)
  }


}