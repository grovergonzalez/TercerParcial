package com.calyr.framework.network

import com.calyr.data.IRemoteDataSource
import com.calyr.data.NetworkResult
import com.calyr.domain.Movie
import com.calyr.framework.toMovie

class RemoteDataSource(
    val retrofit: RetrofitBuilder
): IRemoteDataSource {
    override suspend fun fetchData(): NetworkResult<List<Movie>> {
        val response = retrofit.apiService.fetchData()

        if (response.isSuccessful) {
            val networkResponse = response.body()
            return NetworkResult.Success(
                networkResponse!!.results.withIndex().map {
                        (index, value) -> value.toMovie(index)
                }
            )

        } else {
            return NetworkResult.Error(response.errorBody()!!.string())
        }
    }
}