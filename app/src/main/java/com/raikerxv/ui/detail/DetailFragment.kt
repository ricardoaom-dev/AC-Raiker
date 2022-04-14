package com.raikerxv.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raikerxv.R
import com.raikerxv.databinding.FragmentDetailBinding
import com.raikerxv.ui.loadUrl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(safeArgs.movie) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { binding.updateUI(it) }
            }
        }
    }

    private fun FragmentDetailBinding.updateUI(state: DetailViewModel.DetailUIState) {
        val movie = state.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}