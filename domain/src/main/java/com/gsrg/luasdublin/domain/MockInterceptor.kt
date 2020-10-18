package com.gsrg.luasdublin.domain

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.MOCK_RESPONSE) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.contains("stop=mar") -> loadStringFromAssets("MarlboroughResponse.xml")
                uri.contains("stop=sti") -> loadStringFromAssets("StillorganResponse.xml")
                else -> "{}"
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("text/html".toMediaTypeOrNull())
                )
                .addHeader("content-type", "text/html")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    private fun loadStringFromAssets(fileName: String): String {
        return try {
            val inputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            "{}"
        }
    }
}