package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.net.SSLCertificateSocketFactory
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.sdk.android.httpdns.HttpDns
import com.alibaba.sdk.android.httpdns.HttpDnsService

import javax.net.ssl.*
import java.io.IOException
import java.net.*
import java.util.*

class WebviewActivity2 : Activity() {
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_webview)

        // 初始化httpdns
        httpdns = HttpDns.getService(applicationContext, MainActivity.accountID)
        // 预解析热点域名
        httpdns!!.setPreResolveHosts(ArrayList(Arrays.asList("www.apple.com")))

        webView = this.findViewById<View>(R.id.wv_container)
        webView!!.webViewClient = object : WebViewClient() {
            @SuppressLint("NewApi")
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                val scheme = request.url.scheme!!.trim { it <= ' ' }
                val method = request.method
                val headerFields = request.requestHeaders
                val url = request.url.toString()
                Log.e(TAG, "url:$url")
                // 无法拦截body，拦截方案只能正常处理不带body的请求；
                if ((scheme.equals("http", ignoreCase = true) || scheme.equals(
                        "https",
                        ignoreCase = true
                    )) && method.equals("get", ignoreCase = true)
                ) {
                    try {
                        val connection = recursiveRequest(url, headerFields, null)

                        if (connection == null) {
                            Log.e(TAG, "connection null")
                            return super.shouldInterceptRequest(view, request)
                        }

                        // 注*：对于POST请求的Body数据，WebResourceRequest接口中并没有提供，这里无法处理
                        val contentType = connection.contentType
                        val mime = getMime(contentType)
                        val charset = getCharset(contentType)
                        val httpURLConnection = connection as HttpURLConnection?
                        val statusCode = httpURLConnection!!.responseCode
                        val response = httpURLConnection.responseMessage
                        val headers = httpURLConnection.headerFields
                        val headerKeySet = headers.keys
                        Log.e(TAG, "code:" + httpURLConnection.responseCode)
                        Log.e(TAG, "mime:$mime; charset:$charset")


                        // 无mime类型的请求不拦截
                        if (TextUtils.isEmpty(mime)) {
                            Log.e(TAG, "no MIME")
                            return super.shouldInterceptRequest(view, request)
                        } else {
                            // 二进制资源无需编码信息
                            if (!TextUtils.isEmpty(charset) || isBinaryRes(mime!!)) {
                                val resourceResponse = WebResourceResponse(mime, charset, httpURLConnection.inputStream)
                                resourceResponse.setStatusCodeAndReasonPhrase(statusCode, response)
                                val responseHeader = HashMap<String, String>()
                                for (key in headerKeySet) {
                                    // HttpUrlConnection可能包含key为null的报头，指向该http请求状态码
                                    responseHeader[key] = httpURLConnection.getHeaderField(key)
                                }
                                resourceResponse.responseHeaders = responseHeader
                                return resourceResponse
                            } else {
                                Log.e(TAG, "non binary resource for $mime")
                                return super.shouldInterceptRequest(view, request)
                            }
                        }
                    } catch (e: MalformedURLException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                // API < 21 只能拦截URL参数
                return super.shouldInterceptRequest(view, url)
            }
        }
        webView!!.loadUrl(targetUrl)
    }

    /**
     * 从contentType中获取MIME类型
     * @param contentType
     * @return
     */
    private fun getMime(contentType: String?): String? {
        return if (contentType == null) {
            null
        } else contentType.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }

    /**
     * 从contentType中获取编码信息
     * @param contentType
     * @return
     */
    private fun getCharset(contentType: String?): String? {
        if (contentType == null) {
            return null
        }

        val fields = contentType.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (fields.size <= 1) {
            return null
        }

        var charset = fields[1]
        if (!charset.contains("=")) {
            return null
        }
        charset = charset.substring(charset.indexOf("=") + 1)
        return charset
    }

    /**
     * 是否是二进制资源，二进制资源可以不需要编码信息
     * @param mime
     * @return
     */
    private fun isBinaryRes(mime: String): Boolean {
        return if (mime.startsWith("image")
            || mime.startsWith("audio")
            || mime.startsWith("video")
        ) {
            true
        } else {
            false
        }
    }

    /**
     * header中是否含有cookie
     * @param headers
     */
    private fun containCookie(headers: Map<String, String>): Boolean {
        for ((key) in headers) {
            if (key.contains("Cookie")) {
                return true
            }
        }
        return false
    }

    fun recursiveRequest(path: String, headers: Map<String, String>?, reffer: String?): URLConnection? {
        var conn: HttpURLConnection
        var url: URL? = null
        try {
            url = URL(path)
            conn = url.openConnection() as HttpURLConnection
            // 异步接口获取IP
            val ip = httpdns!!.getIpByHostAsync(url.host)
            if (ip != null) {
                // 通过HTTPDNS获取IP成功，进行URL替换和HOST头设置
                Log.d(TAG, "Get IP: " + ip + " for host: " + url.host + " from HTTPDNS successfully!")
                val newUrl = path.replaceFirst(url.host.toRegex(), ip)
                conn = URL(newUrl).openConnection() as HttpURLConnection

                if (headers != null) {
                    for ((key, value) in headers) {
                        conn.setRequestProperty(key, value)
                    }
                }
                // 设置HTTP请求头Host域
                conn.setRequestProperty("Host", url.host)
            } else {
                return null
            }
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            conn.instanceFollowRedirects = false
            if (conn is HttpsURLConnection) {
                val httpsURLConnection = conn
                val sslSocketFactory = WebviewTlsSniSocketFactory(conn)

                // sni场景，创建SSLScocket
                httpsURLConnection.sslSocketFactory = sslSocketFactory
                // https场景，证书校验
                httpsURLConnection.hostnameVerifier = HostnameVerifier { hostname, session ->
                    var host: String? = httpsURLConnection.getRequestProperty("Host")
                    if (null == host) {
                        host = httpsURLConnection.url.host
                    }
                    HttpsURLConnection.getDefaultHostnameVerifier().verify(host, session)
                }
            }
            val code = conn.responseCode// Network block
            if (needRedirect(code)) {
                // 原有报头中含有cookie，放弃拦截
                if (containCookie(headers!!)) {
                    return null
                }

                var location: String? = conn.getHeaderField("Location")
                if (location == null) {
                    location = conn.getHeaderField("location")
                }

                if (location != null) {
                    if (!(location.startsWith("http://") || location
                            .startsWith("https://"))
                    ) {
                        //某些时候会省略host，只返回后面的path，所以需要补全url
                        val originalUrl = URL(path)
                        location = (originalUrl.protocol + "://"
                                + originalUrl.host + location)
                    }
                    Log.e(TAG, "code:$code; location:$location; path$path")
                    return recursiveRequest(location, headers, path)
                } else {
                    // 无法获取location信息，让浏览器获取
                    return null
                }
            } else {
                // redirect finish.
                Log.e(TAG, "redirect finish")
                return conn
            }
        } catch (e: MalformedURLException) {
            Log.w(TAG, "recursiveRequest MalformedURLException")
        } catch (e: IOException) {
            Log.w(TAG, "recursiveRequest IOException")
        } catch (e: Exception) {
            Log.w(TAG, "unknow exception")
        }

        return null
    }

    private fun needRedirect(code: Int): Boolean {
        return code >= 300 && code < 400
    }

    internal inner class WebviewTlsSniSocketFactory(private val conn: HttpsURLConnection) : SSLSocketFactory() {
        private val TAG = WebviewTlsSniSocketFactory::class.java.simpleName
        var hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()

        @Throws(IOException::class)
        override fun createSocket(): Socket? {
            return null
        }

        @Throws(IOException::class, UnknownHostException::class)
        override fun createSocket(host: String, port: Int): Socket? {
            return null
        }

        @Throws(IOException::class, UnknownHostException::class)
        override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket? {
            return null
        }

        @Throws(IOException::class)
        override fun createSocket(host: InetAddress, port: Int): Socket? {
            return null
        }

        @Throws(IOException::class)
        override fun createSocket(address: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket? {
            return null
        }

        // TLS layer

        override fun getDefaultCipherSuites(): Array<String> {
            return arrayOfNulls(0)
        }

        override fun getSupportedCipherSuites(): Array<String> {
            return arrayOfNulls(0)
        }

        @Throws(IOException::class)
        override fun createSocket(plainSocket: Socket, host: String, port: Int, autoClose: Boolean): Socket {
            var peerHost: String? = this.conn.getRequestProperty("Host")
            if (peerHost == null)
                peerHost = host
            Log.i(TAG, "customized createSocket. host: $peerHost")
            val address = plainSocket.inetAddress
            if (autoClose) {
                // we don't need the plainSocket
                plainSocket.close()
            }
            // create and connect SSL socket, but don't do hostname/certificate verification yet
            val sslSocketFactory = SSLCertificateSocketFactory.getDefault(0) as SSLCertificateSocketFactory
            val ssl = sslSocketFactory.createSocket(address, port) as SSLSocket

            // enable TLSv1.1/1.2 if available
            ssl.enabledProtocols = ssl.supportedProtocols

            // set up SNI before the handshake
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.i(TAG, "Setting SNI hostname")
                sslSocketFactory.setHostname(ssl, peerHost)
            } else {
                Log.d(TAG, "No documented SNI support on Android <4.2, trying with reflection")
                try {
                    val setHostnameMethod = ssl.javaClass.getMethod("setHostname", String::class.java)
                    setHostnameMethod.invoke(ssl, peerHost)
                } catch (e: Exception) {
                    Log.w(TAG, "SNI not useable", e)
                }

            }

            // verify hostname and certificate
            val session = ssl.session

            if (!hostnameVerifier.verify(peerHost, session))
                throw SSLPeerUnverifiedException("Cannot verify hostname: $peerHost")

            Log.i(
                TAG, "Established " + session.protocol + " connection with " + session.peerHost +
                        " using " + session.cipherSuite
            )

            return ssl
        }
    }

    companion object {
        private val targetUrl = "http://www.apple.com"
        private val TAG = "WebviewScene"
        private var httpdns: HttpDnsService? = null
    }
}
