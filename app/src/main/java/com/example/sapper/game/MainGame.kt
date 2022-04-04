package com.example.sapper.game

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.format.DateUtils
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sapper.R
import com.example.sapper.databinding.FragmentMainGameBinding
import com.example.sapper.enums.ModeClick
import com.example.sapper.sapper.field.GameStatus
import com.example.sapper.sapper.field.SapperCell


class MainGame : Fragment() {
  private lateinit var binding: FragmentMainGameBinding
  private lateinit var viewModel: SapperViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_game, container, false)

    val application = requireNotNull(this.activity).application

    val width = getScreenSizes()
    val viewModelFactory = SapperViewModelFactory(width, application)
    viewModel = ViewModelProvider(this, viewModelFactory)[SapperViewModel::class.java]

    val mainGameProps by navArgs<MainGameArgs>()

    if (viewModel.isGameShouldInit.value == true) {
      viewModel.initField(mainGameProps.size, mainGameProps.bombs)
    }
    addAllButtons()

    viewModel.sapperField.value?.flagsCount?.observe(viewLifecycleOwner) { flagsCount ->
      binding.bombsText.text = (viewModel.sapperField.value!!.bombsCount - flagsCount).toString()
    }

    binding.switch1.isChecked = viewModel.modeClick.value == ModeClick.OPEN
    binding.switch1.setOnClickListener {
      viewModel.modeClick.value = when (viewModel.modeClick.value) {
        ModeClick.OPEN -> ModeClick.FLAG
        else -> ModeClick.OPEN
      }
    }

    viewModel.secondsPass.observe(viewLifecycleOwner) { timePass ->
      binding.timeText.text = DateUtils.formatElapsedTime(timePass.toLong())
    }

    viewModel.sapperField.value?.gameStatus?.observe(viewLifecycleOwner) { gameStatus ->
      if (gameStatus == GameStatus.LOSE) {
        redirectWithDelayLose()
      }
      if (gameStatus == GameStatus.WIN) {
        redirectWithDelayWin()
      }
    }

    return binding.root
  }

  private fun getScreenSizes(): Int {
    val display: Display = requireActivity().windowManager.defaultDisplay
    return display.width
  }

  private fun addAllButtons() {
    val view = binding.mainRl
    view.removeAllViews()
    val field = viewModel.sapperField.value?.field
    if (field != null) {
      for (i in field.indices) {
        for (g in field[i].indices) {
          val cell = field[i][g]
          addButtonToView(cell, i, g)
        }
      }
    }
  }

  private fun basicHandlerButton(modeClick: ModeClick, i: Int, g: Int) {
    if (viewModel.isGameShouldInit.value!! && modeClick != ModeClick.FLAG) {
      viewModel.sapperField.value!!.startGame(i, g)
      viewModel.isGameShouldInit.value = false
      viewModel.timerGo = true
      viewModel.addSecond()
    }
  }

  private fun redirectWithDelayLose() {
    viewModel.timerGo = false
    Toast.makeText(
      requireNotNull(this.activity).application,
      requireContext().resources.getString(R.string.lose_text),
      Toast.LENGTH_LONG
    ).show()
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      findNavController().navigate(
        MainGameDirections.actionMainGameToLoseFragment(
          viewModel.secondsPass.value!!,
          viewModel.sapperField.value!!.getCountFlags(),
          viewModel.sapperField.value!!.getCountCorrectFlags()
        )
      )
    }, 5000)
  }

  private fun redirectWithDelayWin() {
    viewModel.timerGo = false
    Toast.makeText(
      requireNotNull(this.activity).application,
      requireContext().resources.getString(R.string.win_text),
      Toast.LENGTH_LONG
    ).show()
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      findNavController().navigate(
        MainGameDirections.actionMainGameToVictoryFragment(viewModel.secondsPass.value!!)
      )
    }, 5000)
  }

  private fun addButtonToView(
    sapperCell: SapperCell,
    i: Int,
    g: Int
  ) {
    val view = binding.mainRl

    val button = Button(context)
    button.id = sapperCell.id

    button.setBackgroundColor(Color.LTGRAY)
    button.layoutParams = RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )


    button.textSize = button.layoutParams.width * 0.17F
    button.layoutParams.width = sapperCell.cellSize
    button.layoutParams.height = sapperCell.cellSize
    val param = button.layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(
      sapperCell.topMargin,
      sapperCell.leftMargin,
      0,
      0
    )
    udpView(button, sapperCell, requireContext())

    viewModel.sapperField.value!!.gameStatus.observe(viewLifecycleOwner) { gameStatus ->
      if (gameStatus == GameStatus.PLAYING)
        viewModel.modeClick.observe(viewLifecycleOwner) { modeClick ->
          button.setOnClickListener {
            basicHandlerButton(modeClick, i, g)
            viewModel.sapperField.value?.openCells(modeClick, i, g)
            addAllButtons()
          }
          button.isClickable =
            ((modeClick == ModeClick.OPEN && !sapperCell.isFlagged) || modeClick == ModeClick.FLAG) &&
                    !sapperCell.isOpen &&
                    !(viewModel.isGameShouldInit.value!! && modeClick == ModeClick.FLAG)
        }
    }
    view.addView(button)

  }
}
