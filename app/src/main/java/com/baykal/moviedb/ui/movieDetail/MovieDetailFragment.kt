package com.baykal.moviedb.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.baykal.moviedb.databinding.FragmentMovieDetailBinding
import com.baykal.moviedb.di.IMG_BASE_URL
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    MovieDetailState.Idle -> {}
                    MovieDetailState.Loading -> DialogUtil.showLoading(context)
                    is MovieDetailState.Error -> DialogUtil.showError(it.message)
                    is MovieDetailState.MovieDetail -> {
                        it.detail?.apply {
                            Glide.with(requireContext()).load(IMG_BASE_URL + posterPath).into(binding.imageMovie)
                            binding.textTitle.text = name ?: title ?: originalTitle
                            binding.textGenre.text = buildString { genres?.forEach { append("${name} ") } }
                            binding.textReleaseDate.text = releaseDate
                            binding.textDescription.text = overview
                        }
                    }
                }

            }
        }
    }
}