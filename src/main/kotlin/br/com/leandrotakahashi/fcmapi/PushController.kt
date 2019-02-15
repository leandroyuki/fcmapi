package br.com.leandrotakahashi.fcmapi

import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@RestController
@RequestMapping()
class NoteController {

    val userDeviceID = "cBO9HGinTnk:APA91bHdcFNJAhWVWYi6nZxC8qxqJb77KfdnbAKDWSgVN2MEtXuB_y5E_eGdFp6mO7zPb_EscHrY-wx0DUnx_-e5xSe4PeeV0KqPMTTIvplKY8PvCrsQcY580KGdTyMPAbCylwuQS1Em"
    @PostMapping("/push")
    fun list(): String {
        pushFCMNotification(userDeviceID)
        return "OK"
    }


    val AUTH_KEY_FCM = "AIzaSyBH4MdDjuxt6eBcr3-g8XIuybzLVHxbnoY"
    val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"

// userDeviceIdKey is the device id you will query from your database

    @Throws(Exception::class)
    fun pushFCMNotification(userDeviceIdKey: String) {

        val authKey = AUTH_KEY_FCM // You FCM AUTH key
        val FMCurl = API_URL_FCM

        val url = URL(FMCurl)
        val conn = url.openConnection() as HttpURLConnection

        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true

        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "key=$authKey")
        conn.setRequestProperty("Content-Type", "application/json")

        val data = JSONObject()
        data.put("id", "123")
        data.put("outroparam", "teste")


        val json = JSONObject()
        json.put("to", userDeviceIdKey.trim { it <= ' ' })
        val info = JSONObject()
        info.put("title", "Notificatoin Title") // Notification title
        info.put("body", "Hello Test notification") // Notification body
        info.put("click_action", "br.com.leandrotakahashi.detalhe")

        json.put("notification", info)
        json.put("data", data)

        val wr = OutputStreamWriter(conn.outputStream)
        wr.write(json.toString())
        wr.flush()
        conn.inputStream
    }
}
