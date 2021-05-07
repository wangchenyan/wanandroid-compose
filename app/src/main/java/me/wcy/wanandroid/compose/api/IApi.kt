package me.wcy.wanandroid.compose.api

import me.wcy.wanandroid.compose.ui.home.model.Article
import me.wcy.wanandroid.compose.ui.home.model.ArticleList
import me.wcy.wanandroid.compose.ui.home.model.HomeBannerData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by wcy on 2021/4/1.
 */
interface IApi {
    @GET("banner/json")
    suspend fun getHomeBanner(): Response<List<HomeBannerData>>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int = 0): Response<ArticleList>

    @GET("article/top/json")
    suspend fun getStickyArticle(): Response<List<Article>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int = 0): Response<ArticleList>
}