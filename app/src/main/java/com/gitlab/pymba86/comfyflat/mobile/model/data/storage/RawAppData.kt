package com.gitlab.pymba86.comfyflat.mobile.model.data.storage

import android.content.res.AssetManager
import com.gitlab.pymba86.comfyflat.mobile.entity.app.develop.AppDeveloper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.InputStreamReader
import javax.inject.Inject

class RawAppData @Inject constructor(
    private val assets: AssetManager,
    private val gson: Gson
) {

    fun getAppDevelopers(): Single<List<AppDeveloper>> = fromAsset("app/app_developers.json")

    private inline fun <reified T> fromAsset(pathToAsset: String) = Single.defer {
        assets.open(pathToAsset).use { stream ->
            Single.just<T>(gson.fromJson(InputStreamReader(stream), object : TypeToken<T>() {}.type))
        }
    }

}