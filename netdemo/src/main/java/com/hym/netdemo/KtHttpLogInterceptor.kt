package com.hym.netdemo

import android.util.Log
import okhttp3.*
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作者： mika
 * 主页：
 * 日期： 2021年06月10日 8:14 上午
 */
class KtHttpLogInterceptor(block: (KtHttpLogInterceptor.() -> Unit)? = null) : Interceptor {

    private var logLevel: LogLevel = LogLevel.NONE//打印日志的标记
    private var colorLevel: ColorLevel = ColorLevel.DEBUG //默认是debug级别的logcat
    private var logTag = "KtHttpLogInterceptor" //日志的logcat的tag
    private val MILLIS_PATTERN:String = "yyyy-MM-dd HH:mm:ss"

    init {
        block?.invoke(this)
    }


    /**
     * 设置logLevel
     */
    fun logLevel(level: LogLevel): KtHttpLogInterceptor {
        logLevel = level
        return this
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        //请求
        val request = chain.request()
        //响应
        return kotlin.runCatching { chain.proceed(request) }
            .onFailure {
                it.printStackTrace()
                logIt(
                    it.message.toString(),
                    ColorLevel.ERROR
                )
            }.onSuccess { respones ->
                if (logLevel == LogLevel.NONE) {
                    return respones
                }
                //记录请求日志
                chain.connection()?.let { logRequest(request, it) }
                //记录响应日志
                logResponse(respones)

            }.getOrThrow()
    }

    /**
     * 记录请求日志
     */
    private fun logRequest(request: Request, connection: Connection) {
        val sb = StringBuilder()
        sb.appendLine("->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->")
        when (logLevel) {
            LogLevel.NONE -> {
//                do nothing
            }
            LogLevel.BASIC -> {
                logBasicReq(sb, request, connection)
            }
            LogLevel.HEADERS -> {
                logHeadersReq(sb, request, connection)
            }
            LogLevel.BODY -> {
                logBodyReq(sb, request, connection)
            }

        }

        sb.appendLine("->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->")

        logIt(sb)
    }


    /**
     * 打印全部日志
     */
    private fun logBodyReq(sb: StringBuilder, request: Request, connection: Connection) {
        logHeadersReq(sb, request, connection)
        sb.appendLine("RequestBody: ${request.body.toString()}")
    }

    /**
     * 打印请求头的日志
     */
    private fun logHeadersReq(sb: StringBuilder, request: Request, connection: Connection) {
        logBasicReq(sb, request, connection)
        val headerStr = request.headers.joinToString("") { header ->
            "请求 Header:{${header.first} = ${header.second}}\n"
        }
        sb.appendLine(headerStr)
    }

    /**
     * 打印请求基础日志
     */
    private fun logBasicReq(sb: StringBuilder, request: Request, connection: Connection) {
        sb.appendLine(
            "请求 method: ${request.method} url: ${decodeUrlStr(request.url.toString())} tag: ${request.tag()} protocol: ${connection.protocol() ?: okhttp3.Protocol.HTTP_1_1}"
        )
    }

    /**
     * 记录响应日志
     */
    private fun logResponse(response:Response){
        val sb = StringBuilder();
        sb.appendLine("->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->")
        when (logLevel) {
            LogLevel.NONE -> {
//                do nothing
            }
            LogLevel.BASIC -> {
                logBasicRep(sb, response)

            }
            LogLevel.HEADERS -> {
                logHeadersRep(sb, response)
            }
            LogLevel.BODY -> {
                logHeadersRep(sb, response)
                //body.string 会抛IO异常
                kotlin.runCatching {
                    //peek类似于clone数据流，监视，窥探，不能直接用原来的body的string流数据作为日志，会消费掉io，所有这里是peek，监测
                    val peekBody:ResponseBody = response.peekBody(1024 * 1024)
                    sb.appendLine(peekBody.string())
                }.getOrNull()
            }

        }

        sb.appendLine("->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->")

        logIt(sb,ColorLevel.INFO)
    }

    /**
     * 打印响应头的日志
     */
    private fun logHeadersRep(sb: StringBuilder, response: Response) {
        logBasicRep(sb,response)
        val headerStr:String = response.headers.joinToString {
            header -> "响应 Header:{${header.first}=${header.second}}\n"
        }
        sb.appendLine(headerStr)
    }

    /**
     * 打印基础信息日志
     */
    private fun logBasicRep(sb: StringBuilder, response: Response) {
        sb.appendLine("响应 protocol：${response.protocol} code:${response.cacheControl} message:${response.message}")
            .appendLine("响应 request Url:${decodeUrlStr(response.request.url.toString())}")
            .appendLine("响应 sentRequestTime：${
                toDateTimeStr(
                    response.sentRequestAtMillis,
                    MILLIS_PATTERN
                )
            } ")
            .appendLine("响应 receivedResponseTime:${
                toDateTimeStr(
                    response.receivedResponseAtMillis,
                    MILLIS_PATTERN
                )
            }")

    }


    /**
     * 对于url编码的string解码
     */
    private fun decodeUrlStr(url: String): String? {
        return kotlin.runCatching {
            URLDecoder.decode(url, "utf-8")
        }.onFailure { it.printStackTrace() }.getOrNull()
    }


    /**
     * 打印日志
     */
    private fun logIt(any: Any, tempLevel: ColorLevel? = null) {
        when (tempLevel ?: colorLevel) {
            ColorLevel.VERBOSE -> Log.v(logTag, any.toString())
            ColorLevel.DEBUG -> Log.d(logTag, any.toString())
            ColorLevel.INFO -> Log.i(logTag, any.toString())
            ColorLevel.WARN -> Log.w(logTag, any.toString())
            ColorLevel.ERROR -> Log.e(logTag, any.toString())
        }
    }


    companion object {
        private const val TAG = "<KtHttp>" //默认的TAG

        //时间格式化
        fun toDateTimeStr(millis: Long, pattern: String): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
        }
    }

    /**
     * 打印日志的范围
     */
    enum class LogLevel {
        NONE,//不打印
        BASIC,//只打印行首，请求/响应
        HEADERS,//打印请求和响应的所有 header
        BODY,//打印所有
    }


    /**
     * log颜色等级，应用于Android logcat 分为v\d\i\w\e
     */
    enum class ColorLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR

    }
}