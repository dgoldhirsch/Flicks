package com.example.davidgoldhirsch.flicks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.davidgoldhirsch.flicks.adapters.MoviesAdapter
import com.example.davidgoldhirsch.flicks.models.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_movie.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MovieActivity : AppCompatActivity() {

    private lateinit var mAdapter: MoviesAdapter
    private var mMovies: MutableList<Movie> = ArrayList()

    companion object {
        private const val THEMOVIEDB_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        mAdapter = MoviesAdapter(mMovies)
        recyclerMovies.layoutManager = LinearLayoutManager(this)
        recyclerMovies.setHasFixedSize(false)
        recyclerMovies.adapter = mAdapter
        refreshMovies()
    }

    private fun refreshMovies() {
        val client = AsyncHttpClient()
        client.get(THEMOVIEDB_URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?,
                                   response: JSONObject?) {
                try {
                    val movieJsonResults = response!!.getJSONArray("results")
                    mMovies.addAll(Movie.fromJSONArray(movieJsonResults))
                    Collections.sort(mMovies) { movie, anotherMovie ->
                        movie.originalTitle.toLowerCase().compareTo(anotherMovie.originalTitle.toLowerCase()) }
                    mAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace() // TODO Snackbar or Toast
                }

                super.onSuccess(statusCode, headers, response)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?,
                                   responseString: String?, throwable: Throwable?) {

                // TODO Snackbar or Toast, instead of the following
                super.onFailure(statusCode, headers, responseString, throwable)
            }
        })
    }
}
