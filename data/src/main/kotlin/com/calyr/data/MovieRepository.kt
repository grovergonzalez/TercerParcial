package com.calyr.data

import com.calyr.domain.Movie

class MovieRepository(
    val remoteDataSource: IRemoteDataSource,
    val localDataSource: ILocalDataSource
) {

    suspend fun obtainMovies(): List<Movie> {
        // Consultar al servicio web
        val moviesRemote = remoteDataSource.fetchData()

        // Verificar el estado del consumo de API
        when (moviesRemote) {
            is NetworkResult.Success -> {
                // Si la consulta es exitosa, actualizamos la base de datos local
                localDataSource.deleteAll()
                localDataSource.insertMovies(moviesRemote.data)
            }
            is NetworkResult.Error -> {
                // Registrar un log en Sentry o manejo de errores
            }
        }

        // Retornar los datos de la base de datos local
        val moviesLocal = localDataSource.getList()
        return when (moviesLocal) {
            is NetworkResult.Success -> moviesLocal.data
            is NetworkResult.Error -> {
                // Registrar un log en Sentry o manejo de errores
                emptyList()
            }
        }
    }

    // Método para obtener datos exclusivamente desde LocalDataSource
    suspend fun obtainMoviesFromLocal(): List<Movie> {
        val moviesLocal = localDataSource.getList()
        return when (moviesLocal) {
            is NetworkResult.Success -> moviesLocal.data
            is NetworkResult.Error -> {
                // Registrar un log en Sentry o manejo de errores
                emptyList()
            }
        }
    }

    fun findById(id: String): Movie? {
        val movieLocal = localDataSource.findById(id)
        return when (movieLocal) {
            is NetworkResult.Success -> movieLocal.data
            is NetworkResult.Error -> {
                // Registrar un log en Sentry o manejo de errores
                null
            }
        }
    }
}