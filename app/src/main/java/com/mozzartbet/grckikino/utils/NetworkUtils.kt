package com.mozzartbet.grckikino.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity

class NetworkUtils(val context: Context) {

    var broadcastReceiver: BroadcastReceiver? = null

    fun hasConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun setConnectionListener(listener: ConnectionListener) {
        listener.isConnected(hasConnection())

        val connectivityManager = context.getSystemService(
            AppCompatActivity.CONNECTIVITY_SERVICE
        ) as? ConnectivityManager
        connectivityManager?.registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    listener.isConnected(true)
                }

                override fun onLost(network: Network) {
                    listener.isConnected(false)
                }
            }
        )
    }

    fun removeConnectionListener() {
        broadcastReceiver?.let {
            context.unregisterReceiver(it)
        }
    }

    interface ConnectionListener {
        fun isConnected(value: Boolean)
    }
}