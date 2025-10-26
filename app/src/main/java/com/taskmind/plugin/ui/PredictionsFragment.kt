package com.taskmind.plugin.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.taskmind.plugin.R
import com.taskmind.plugin.db.AppDatabase
import kotlinx.coroutines.launch

class PredictionsFragment : Fragment(R.layout.fragment_predictions) {

    private val viewModel: PredictionsViewModel by viewModels {
        ViewModelFactory(AppDatabase.getDatabase(requireContext()).logEventDao())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.predictions_text_view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.suggestions.collect { suggestions ->
                    if (suggestions.isNotEmpty()) {
                        textView.text = suggestions.joinToString("\n") { it.action }
                    } else {
                        textView.text = "No suggestions yet."
                    }
                }
            }
        }
    }
}
