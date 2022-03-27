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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sapper.R
import com.example.sapper.databinding.FragmentMainGameBinding
import com.example.sapper.enums.ModeClick
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
    addAllButtons()

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

  private fun basicHandlerButton(i: Int, g: Int) {
    if (viewModel.isGameShouldInit) {
      viewModel.sapperField.value!!.startGame(i, g)
      viewModel.isGameShouldInit = false
      viewModel.timerGo = true
      viewModel.addSecond()
    }
  }

  private fun redirectWithDelay() {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      findNavController().navigate(R.id.action_mainGame_to_loseFragment)
    }, 2000)

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

    val param = button.layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(
      sapperCell.topMargin,
      sapperCell.leftMargin,
      0,
      0
    )
    button.textSize = button.layoutParams.width * 0.17F
    button.layoutParams.width = sapperCell.cellSize
    button.layoutParams.height = sapperCell.cellSize

    udpView(button, sapperCell, requireContext())

    viewModel.modeClick.observe(viewLifecycleOwner) { modeClick ->
      button.setOnClickListener {
        basicHandlerButton(i, g)
        viewModel.sapperField.value?.openCells(modeClick, i, g)
        if (viewModel.sapperField.value!!.field[i][g].isBomb && modeClick == ModeClick.OPEN) {
          redirectWithDelay()
        }
        addAllButtons()

        button.isClickable =
          ((modeClick == ModeClick.OPEN && !sapperCell.isFlagged) || modeClick == ModeClick.FLAG) && !sapperCell.isOpen
      }
    }
    view.addView(button)

  }
}
