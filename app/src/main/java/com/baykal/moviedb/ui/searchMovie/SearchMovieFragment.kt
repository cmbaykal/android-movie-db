package com.baykal.moviedb.ui.searchMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.baykal.moviedb.databinding.FragmentSearchMoviewBinding
import com.baykal.moviedb.ui.movieList.MovieAdapter
import com.baykal.moviedb.ui.movieList.MovieListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieFragment : Fragment() {

    private val viewModel: SearchMoviewViewModel by viewModels()
    private lateinit var binding: FragmentSearchMoviewBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMoviewBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        setupViewObservers()
    }

    private fun initLayout() {
        adapter = MovieAdapter { id ->
            val direction = MovieListFragmentDirections.navigateToDetail(id)
            findNavController().navigate(direction)
        }
        binding.listMovie.adapter = adapter
        with(binding) {
            buttonSearch.setOnClickListener {
                viewModel.searchMovie(inputMovieName.text.toString())
            }
        }
    }

    private fun setupViewObservers() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}