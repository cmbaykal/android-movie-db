package com.baykal.moviedb.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.baykal.moviedb.base.DialogUtil
import com.baykal.moviedb.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels()

    private var binding: FragmentMovieListBinding? = null
    private var adapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)

        setupLayout()
        setupViewObservers()
        lifecycleScope.launch {
            viewModel.userIntent.send(MovieListIntent.FetchMovieList)
        }

        return binding?.root ?: View(context)
    }

    private fun setupLayout() {
        adapter = MovieAdapter { id ->
            val direction = MovieListFragmentDirections.navigateToDetail(id)
            findNavController().navigate(direction)
        }
        binding?.apply {
            listMovie.adapter = adapter
            refreshLayout.setOnRefreshListener {
                adapter?.submitList(emptyList())
                lifecycleScope.launch {
                    viewModel.userIntent.send(MovieListIntent.RefreshList)
                }
            }
            listMovie.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        lifecycleScope.launch {
                            viewModel.userIntent.send(MovieListIntent.FetchMovieList)
                        }
                    }
                }
            })
        }
    }

    private fun setupViewObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    MovieListState.Idle -> {}
                    MovieListState.Loading -> DialogUtil.showLoading(context)
                    is MovieListState.Error -> DialogUtil.showError(context, it.message)
                    is MovieListState.MovieList -> {
                        DialogUtil.closeDialog()
                        binding?.refreshLayout?.isRefreshing = false
                        adapter?.submitList(it.list)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding = null
    }
}