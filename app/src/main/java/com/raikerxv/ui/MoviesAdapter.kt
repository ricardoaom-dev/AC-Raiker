package com.raikerxv.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raikerxv.R
import com.raikerxv.databinding.ViewMovieBinding
import com.raikerxv.model.Movie
import kotlin.properties.Delegates

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(basicDiffUtil {old, new -> old.id == new.id}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewMovieBinding.bind(view)
        fun bind(movie: Movie) = with(binding) {
            movieTitle.text = movie.title
            movieCover.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}