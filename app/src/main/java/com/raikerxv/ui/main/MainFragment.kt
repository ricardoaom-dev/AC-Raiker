package com.raikerxv.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raikerxv.R
import com.raikerxv.databinding.FragmentMainBinding
import com.raikerxv.model.MoviesRepository
import com.raikerxv.ui.launchAndCollect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

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

        with(viewModel.state) {
            diff({ it.movies }, adapter::submitList)
            diff({ it.loading }) { isVisible -> binding.progress.isVisible = isVisible }
        }

        mainState.requestLocationPermission { viewModel.onUIReady() }
    }

    private fun <T, U> Flow<T>.diff(mapf: (T) -> U, body: (U) -> Unit) {
        viewLifecycleOwner.launchAndCollect(
            flow = map(mapf).distinctUntilChanged(),
            body = body
        )
    }

}

