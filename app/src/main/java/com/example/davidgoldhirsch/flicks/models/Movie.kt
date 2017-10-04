package com.example.davidgoldhirsch.flicks.models

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Movie() {
    var originalTitle: String = ""
    var overview: String = ""

    var posterPath: String = ""
        get() = String.format("https://image.tmdb.org/t/p/w342/%s", field)


    companion object {
        fun fromJSONArray(array: JSONArray): ArrayList<Movie> {
            val results = ArrayList<Movie>(array.length())

            for(i in 0.until(array.length())) {
                results.add(Movie(array.getJSONObject(i)))
            }

            return results
        }
    }

    constructor(jsonObject: JSONObject): this() {
        this.posterPath = extractStringFrom(jsonObject, "poster_path")
        this.originalTitle = extractStringFrom(jsonObject, "original_title")
        this.overview = extractStringFrom(jsonObject, "overview")
    }

    private fun extractStringFrom(jsonObject: JSONObject, key: String): String {
        return try {
            jsonObject.getString(key)
        } catch(e: JSONException) {
            e.printStackTrace() // TODO Toast
            ""
        }
    }
}
