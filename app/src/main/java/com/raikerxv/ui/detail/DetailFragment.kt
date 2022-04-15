package com.raikerxv.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raikerxv.R
import com.raikerxv.databinding.FragmentDetailBinding
import com.raikerxv.data.AndroidPermissionChecker
import com.raikerxv.data.MoviesRepository
import com.raikerxv.data.PlayServicesLocationDataSource
import com.raikerxv.data.RegionRepository
import com.raikerxv.data.database.MovieRoomDataSource
import com.raikerxv.data.server.MovieServerDataSource
import com.raikerxv.ui.app
import com.raikerxv.ui.launchAndCollect
import com.raikerxv.usecases.FindMovieUseCase
import com.raikerxv.usecases.SwitchMovieFavoriteUseCase

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        val application = requireActivity().app
        val repository = MoviesRepository(
            RegionRepository(
                PlayServicesLocationDataSource(application),
                AndroidPermissionChecker(application)
            ),
            MovieRoomDataSource(application.db.movieDao()),
            MovieServerDataSource(getString(R.string.api_key))
        )
        DetailViewModelFactory(
            safeArgs.movieId,
            FindMovieUseCase(repository),
            SwitchMovieFavoriteUseCase(repository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            state.movie?.let { binding.movie = it }
        }
    }
}