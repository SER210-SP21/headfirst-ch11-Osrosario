package edu.quinnipiac.ser210.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import edu.quinnipiac.ser210.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment()
{
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    //Link to the View Model file
    //Syntax: lateinit var <variable name>: <name of View Model Class>
    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        //Linkage code to View Model
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.secretWordDisplay = viewModel.deriveSecretWordDisplay()
        updateScreen()

        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()

            if (viewModel.isWon () || viewModel.isLost())
            {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    fun updateScreen()
    {
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text = "You have ${viewModel.livesLeft} lives left"
        binding.incorrectGuesses.text = "Incorrect guesses: ${viewModel.incorrectGuesses}"
    }
}