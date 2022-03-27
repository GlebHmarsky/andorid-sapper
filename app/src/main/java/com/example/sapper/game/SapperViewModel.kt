package com.example.sapper.game

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sapper.enums.ModeClick
import com.example.sapper.sapper.field.SapperField


class SapperViewModel(width: Int, application: Application) :
  AndroidViewModel(application) {

  val sapperField = MutableLiveData<SapperField>()
  var modeClick = MutableLiveData(ModeClick.OPEN)
  var isGameShouldInit: Boolean = true
  val secondsPass = MutableLiveData(0)
  var timerGo = false

  companion object {
    val ONE_SECON = 1000
  }

  init {


    sapperField.value = SapperField(width, 10, 15)
  }

  fun addSecond() {
    if (!timerGo) return

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      secondsPass.value = secondsPass.value?.plus(1)
      addSecond()
    }, ONE_SECON.toLong())
  }
}
