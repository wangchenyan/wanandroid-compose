package me.wcy.wanandroid.compose.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.api.Api
import me.wcy.wanandroid.compose.api.apiCall
import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.widget.LoadState
import me.wcy.wanandroid.compose.widget.Toaster

/**
 * Created by wcy on 2021/4/1.
 */
class CollectViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    val list by mutableStateOf(mutableListOf<Article>())
    var refreshingState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var page = 0

    init {
        firstLoad()
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val articleList = apiCall { Api.get().getCollectArticleList() }
            if (articleList.isSuccess()) {
                pageState = LoadState.SUCCESS
                list.apply {
                    clear()
                    addAll(articleList.data!!.datas)
                }
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshingState = true
            val articleList = apiCall { Api.get().getCollectArticleList() }
            if (articleList.isSuccess()) {
                list.apply {
                    clear()
                    addAll(articleList.data!!.datas)
                }
                refreshingState = false
            } else {
                refreshingState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun onLoad() {
        viewModelScope.launch {
            loadState = true
            val articleList = apiCall { Api.get().getSquareArticleList(page + 1) }
            if (articleList.isSuccess()) {
                page++
                list.addAll(articleList.data!!.datas)
                loadState = false
            } else {
                loadState = false
                Toaster.show("加载失败")
            }
        }
    }
}