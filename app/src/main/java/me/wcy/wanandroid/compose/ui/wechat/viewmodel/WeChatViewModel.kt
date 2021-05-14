package me.wcy.wanandroid.compose.ui.wechat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.wcy.wanandroid.compose.api.Api
import me.wcy.wanandroid.compose.api.apiCall
import me.wcy.wanandroid.compose.ui.wechat.model.WeChatAuthor
import me.wcy.wanandroid.compose.widget.LoadState
import me.wcy.wanandroid.compose.widget.PagerState

class WeChatViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    val pagerState by mutableStateOf(PagerState())
    val authorList by mutableStateOf(mutableListOf<WeChatAuthor>())

    init {
        getAuthorList()
    }

    fun getAuthorList() {
        viewModelScope.launch {
            pageState = LoadState.LOADING
            val authorListRes = apiCall { Api.get().getWeChatAuthorList() }
            if (authorListRes.isSuccess()) {
                pageState = LoadState.SUCCESS
                authorList.clear()
                authorList.addAll(authorListRes.data!!)
                pagerState.maxPage = authorList.size - 1
            } else {
                pageState = LoadState.FAIL
            }
        }
    }
}