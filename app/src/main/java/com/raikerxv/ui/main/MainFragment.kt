package com.raikerxv.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.raikerxv.R
import com.raikerxv.databinding.FragmentMainBinding
import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity() as AppCompatActivity))
    }
    private val adapter = MoviesAdapter { viewModel.onMovieClick(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
        viewLifecycleOwner.launchAndCollect(viewModel.events) { event ->
            when (event) {
                is MainViewModel.DetailUIEvent.NavigateTo -> navigateTo(event.movie)
            }
        }
    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.MainUIState) {
        progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
    }

    private fun navigateTo(movie: Movie) {
        val navAction = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(navAction)
    }
}

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}