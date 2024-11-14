package com.kodiiiofc.urbanuniversity.daggerhilt.presetation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.daggerhilt.data.api.NewsRepository
import com.kodiiiofc.urbanuniversity.daggerhilt.domain.Resources
import com.kodiiiofc.urbanuniversity.daggerhilt.domain.models.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val newsLiveData: MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    init {
        getNews("us")
    }

    private fun getNews(countryCode: String) =
        viewModelScope.launch {
            newsLiveData.postValue(Resources.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    newsLiveData.postValue(Resources.Success(res))
                }
            } else {
                newsLiveData.postValue(Resources.Error(message = response.message()))
            }
        }

}