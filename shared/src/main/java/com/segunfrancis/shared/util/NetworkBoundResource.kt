package com.segunfrancis.shared.util

import com.segunfrancis.shared.extension.handleThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <T, RequestType>networkBoundResource(
    crossinline query: () -> CountryListScenario<T>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (CountryListScenario<T>) -> Boolean = { true }
): Flow<CountryListScenario<T>> {
    return flow {
        val data = query()
        if (shouldFetch(data)) {
            emit(CountryListScenario.Loading)
            try {
                saveFetchResult(fetch())
                val newData = query()
                if (newData is CountryListScenario.Database) {
                    emit(CountryListScenario.Database(newData.countries))
                }
            } catch (t: Throwable) {
                emit(CountryListScenario.Error(errorMessage = t.handleThrowable()))
            }
        } else {
            //emit(CountryListScenario.Database(query()))
        }
    }
}

sealed class CountryListScenario<out T> {
    data class NetworkErrorDatabaseSuccess<T>(
        val countries: List<T>,
        val errorMessage: String?
    ) : CountryListScenario<T>()

    data class Database<T>(val countries: List<T>) : CountryListScenario<T>()
    data class Error(val errorMessage: String?) : CountryListScenario<Nothing>()
    object Loading : CountryListScenario<Nothing>()
}
