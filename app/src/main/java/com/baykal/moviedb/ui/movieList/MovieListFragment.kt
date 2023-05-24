package com.baykal.moviedb.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.baykal.moviedb.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

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
                viewModel.refreshData()
            }
            listMovie.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.fetchData()
                    }
                }
            })
        }
    }

    private fun setupViewObservers() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding?.refreshLayout?.apply {
                isRefreshing = it
                isEnabled = !it
            }
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding = null
    }
}