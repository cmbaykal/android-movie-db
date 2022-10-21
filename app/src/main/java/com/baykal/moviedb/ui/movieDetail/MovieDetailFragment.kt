package com.baykal.moviedb.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.baykal.moviedb.databinding.FragmentMovieDetailBinding
import com.baykal.moviedb.di.IMG_BASE_URL
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)

        viewModel.getMovieDetail(args.movieId)

        setupViewObservers()

        return binding.root
    }

    private fun setupViewObservers() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { movie ->
            with(binding) {
                Glide.with(requireContext()).load(IMG_BASE_URL + movie.posterPath).into(imageMovie)
                textTitle.text = movie.name ?: movie.title ?: movie.originalTitle
                textGenre.text = buildString { movie.genres?.forEach { append("${it.name} ") } }
                textReleaseDate.text = movie.releaseDate
                textDescription.text = movie.overview
            }
        }
    }
}