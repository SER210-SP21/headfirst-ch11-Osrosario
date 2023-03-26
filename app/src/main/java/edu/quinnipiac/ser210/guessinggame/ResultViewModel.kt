package edu.quinnipiac.ser210.guessinggame

import androidx.lifecycle.ViewModel

class ResultViewModel(finalResult: String) : ViewModel()
{
    val result = finalResult
}