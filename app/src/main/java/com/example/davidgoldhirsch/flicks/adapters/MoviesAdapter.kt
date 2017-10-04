package com.example.davidgoldhirsch.flicks.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.davidgoldhirsch.flicks.R
import com.example.davidgoldhirsch.flicks.models.Movie

class MoviesAdapter(private var mMovies: MutableList<Movie> = ArrayList())
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun getItemCount() = mMovies.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = mMovies.get(position)
        holder.originalTitle.text = movie.originalTitle
        holder.overview.text = movie.overview
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val context = parent!!.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_movie, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder internal constructor(view: View?): RecyclerView.ViewHolder(view) {
        var originalTitle: TextView = view!!.findViewById(R.id.originalTitle)
        var overview: TextView = view!!.findViewById(R.id.overview)
    }
}
