package com.raikerxv.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raikerxv.R
import com.raikerxv.databinding.FragmentMainBinding
import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import com.raikerxv.ui.common.PermissionRequester
import com.raikerxv.ui.launchAndCollect
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
    }
    private lateinit var mainState: MainState
    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        val binding = FragmentMainBinding.bind(view).apply { recycler.adapter = adapter }
        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
        mainState.requestLocationPermission { viewModel.onUIReady() }
    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.MainUIState) {
        progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
    }

}

