package com.raikerxv.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raikerxv.R
import com.raikerxv.data.MoviesRepository
import com.raikerxv.data.RegionRepository
import com.raikerxv.databinding.FragmentMainBinding
import com.raikerxv.framework.AndroidPermissionChecker
import com.raikerxv.framework.PlayServicesLocationDataSource
import com.raikerxv.framework.database.MovieRoomDataSource
import com.raikerxv.framework.server.MovieServerDataSource
import com.raikerxv.ui.app
import com.raikerxv.ui.launchAndCollect
import com.raikerxv.usecases.GetPopularMoviesUseCase
import com.raikerxv.usecases.RequestPopularMoviesUseCase

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        val application = requireActivity().app
        val repository = MoviesRepository(
            RegionRepository(
                PlayServicesLocationDataSource(application),
                AndroidPermissionChecker(application)
            ),
            MovieRoomDataSource(application.db.movieDao()),
            MovieServerDataSource(getString(R.string.api_key))
        )
        MainViewModelFactory(
            GetPopularMoviesUseCase(repository),
            RequestPopularMoviesUseCase(repository)
        )
    }
    private lateinit var mainState: MainState
    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        val binding = FragmentMainBinding.bind(view).apply { recycler.adapter = adapter }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.movies = it.movies
            binding.error = it.error?.let(mainState::errorToString)
        }

        mainState.requestLocationPermission { viewModel.onUIReady() }
    }

}

