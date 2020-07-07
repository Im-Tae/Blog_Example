package com.leaf.rxandroid.square

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.util.concurrent.Future

interface GithubServiceApi {

    @GET("repos/{owner}/{repo}/contributors")
    fun getCallContributors(@Path("owner") owner: String, @Path("repo") repo: String) : Call<List<Contributor>>

    @GET("repos/{owner}/{repo}/contributors")
    fun getObContributors(@Path("owner") owner: String, @Path("repo") repo: String) : Observable<List<Contributor>>

    @Headers("Accept: application/vnd.github.v3.full+json")
    @GET("repos/{owner}/{repo}/contributors")
    fun getFutureContributors(@Path("owner") owner: String, @Path("repo") repo: String) : Future<List<Contributor>>
}