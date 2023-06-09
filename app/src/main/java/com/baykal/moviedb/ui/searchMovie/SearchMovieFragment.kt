package com.baykal.moviedb.ui.searchMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baykal.moviedb.databinding.FragmentSearchMovieBinding
import com.baykal.moviedb.ui.movieList.MovieAdapter
import com.baykal.moviedb.ui.movieList.MovieListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovieFragment : Fragment() {

    private val viewModel: SearchMovieViewModel by viewModels()
    private lateinit var binding: FragmentSearchMovieBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(layoutInflater, container, false)

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
        binding.buttonSearch.setOnClickListener {
            lifecycleScope.launch {
                val query = binding.inputMovieName.editText?.text.toString()
                viewModel.userIntent.send(SearchMovieIntent.SearchMovie(query))
            }
        }
    }

    private fun setupViewObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    SearchMovieState.Idle -> {}
                    SearchMovieState.Loading -> DialogUtil.showLoading(context)
                    is SearchMovieState.Error -> DialogUtil.showError(context, it.message)
                    is SearchMovieState.SearchList -> {
                        adapter.submitList(it.list)
                    }
                }
            }
        }
    }
}