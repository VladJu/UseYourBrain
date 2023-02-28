package com.example.useyourbrain.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.useyourbrain.R
import com.example.useyourbrain.databinding.FragmentGameBinding
import com.example.useyourbrain.databinding.FragmentWelcomeBinding
import com.example.useyourbrain.domain.entity.GameResult
import com.example.useyourbrain.domain.entity.GameSettings
import com.example.useyourbrain.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding== null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener {
            launchGameFinishFragment(
                GameResult(
                    true,
                    0,
                    0,
                    GameSettings(
                        0,
                        0,
                        0,
                        0
                    )
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // получим данный объет level из аргументов
    private fun parsArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level=it
        }
    }

    // метод который запускает экран окончания игры
    private fun launchGameFinishFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }


    companion object {

        const val NAME = "GameFragment"

        private const val KEY_LEVEL = "level"
        //Чтобы положить объект Level , то данный класс должен реализовывать интерфейс Serializable
        //И если класс реализует интерфейс Serializable, то объект этого класса можно превратить в набор байтов
        //и обратно из набора байтов можно получить объект, но Enum Классы не явно его реализуют

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }

    }
}