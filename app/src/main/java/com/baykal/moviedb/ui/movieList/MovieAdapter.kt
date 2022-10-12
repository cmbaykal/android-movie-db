package com.baykal.moviedb.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baykal.moviedb.databinding.ItemViewMovieBinding
import com.baykal.moviedb.di.IMG_BASE_URL
import com.baykal.moviedb.network.data.response.MovieItem
import com.bumptech.glide.Glide

class MovieAdapter(
    private val onClick: (id: Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var list: MutableList<MovieItem> = mutableListOf()

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun addItem(movies: MutableList<MovieItem>) {
        list.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewMovieBinding.inflate(inflater, null, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount() = list.size

    class ViewHolder(
        private val binding: ItemViewMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movieItem: MovieItem,
            onClick: (id: Int) -> Unit
        ) {
            movieItem.posterPath?.let {
                Glide.with(binding.root.context).load(IMG_BASE_URL + it).into(binding.imageMoview)
            }

            val name = movieItem.name ?: movieItem.title ?: movieItem.originalTitle
            binding.textMovie.text = name

            movieItem.id?.let { id ->
                binding.root.setOnClickListener { onClick.invoke(id) }
            }
        }
    }
}