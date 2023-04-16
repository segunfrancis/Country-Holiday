package com.segunfrancis.home.usecase

import com.segunfrancis.home.model.CountryHome
import com.segunfrancis.home.source.DatasourceFactory
import com.segunfrancis.local.db.entities.CountryLocal
import com.segunfrancis.remote.models.CountryRemote
import com.segunfrancis.shared.extension.handleThrowable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.job

class CountryListUseCase @Inject constructor(
    private val datasource: DatasourceFactory,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Flow<CountryListScenario> {
        return channelFlow {
            val dao = datasource.local()
            if (dao == null) {
                send(CountryListScenario.Error("Database schema has been changed. Kindly clear app data"))
                return@channelFlow
            } else {
                try {
                    val localList = getCountriesLocal()
                    if (localList.isNotEmpty()) {
                        send(CountryListScenario.Database(localList))
                        // Update the cache while cache is being displayed
                        saveCountriesToCache(countries = getCountriesRemote())
                        this.coroutineContext.job.cancel()
                    } else {
                        send(CountryListScenario.Loading)
                        saveCountriesToCache(countries = getCountriesRemote())
                        send(CountryListScenario.Database(getCountriesLocal()))
                        this.coroutineContext.job.cancel()
                    }
                } catch (t: Throwable) {
                    send(CountryListScenario.Error(t.handleThrowable()))
                }
            }
        }.flowOn(dispatcher)
    }

    suspend fun temp(): Flow<CountryListScenario> {
        val dao = datasource.local()
            ?: return flowOf(CountryListScenario.Error("Database schema has been changed. Kindly clear app data"))
        return  flow {
            val localList = getCountriesLocal()
            if (localList.isNotEmpty()) {
                emit(CountryListScenario.Database(localList))
            } else {
                val remoteList = try {
                    getCountriesRemote()
                } catch (e: Throwable) {
                    emit(CountryListScenario.Error(e.handleThrowable()))
                    emptyList()
                }
                if (remoteList.isNotEmpty()) {
                    saveCountriesToCache(remoteList)
                } else {
                    //return flowOf(CountryListScenario.NetworkErrorDatabaseSuccess())
                }
            }
        }
    }

    private suspend fun getCountriesRemote(): List<CountryHome> {
        return datasource.remote().getCountries().countries.map { it.mapToHome() }
    }

    private suspend fun saveCountriesToCache(countries: List<CountryHome>) {
        datasource.local()?.saveCountries(*countries.map { it.mapToLocal() }.toTypedArray())
    }

    private fun getCountriesLocal(): List<CountryHome> {
        return datasource.local()!!.getCountries().map { local -> local.mapToHome() }
    }

    private fun CountryRemote.mapToHome(): CountryHome {
        return CountryHome(code, name)
    }

    private fun CountryHome.mapToLocal(): CountryLocal {
        return CountryLocal(code, name)
    }

    private fun CountryLocal.mapToHome(): CountryHome {
        return CountryHome(code, name)
    }

    sealed class CountryListScenario {
        data class NetworkErrorDatabaseSuccess(
            val countries: List<CountryHome>,
            val errorMessage: String?
        ) : CountryListScenario()

        data class Database(val countries: List<CountryHome>) : CountryListScenario()
        data class Error(val errorMessage: String?) : CountryListScenario()
        object Loading : CountryListScenario()
    }
}
