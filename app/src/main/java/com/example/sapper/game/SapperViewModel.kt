package com.example.sapper.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sapper.sapper.field.SapperField


class SapperViewModel(width: Int, application: Application) :
  AndroidViewModel(application) {

  val sapperField = MutableLiveData<SapperField>()
  var modeClick = MutableLiveData(ModeClick.OPEN)
  var isGameShouldInit: Boolean = true

  init {
    sapperField.value = SapperField(width, 10, 15)
  }
}
