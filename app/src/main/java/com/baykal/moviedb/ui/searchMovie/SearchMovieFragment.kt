package com.baykal.moviedb.ui.searchMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.baykal.moviedb.databinding.FragmentSearchMoviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieFragment : Fragment() {
    private val viewModel: SearchMoviewViewModel by viewModels()
    private lateinit var binding: FragmentSearchMoviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMoviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}