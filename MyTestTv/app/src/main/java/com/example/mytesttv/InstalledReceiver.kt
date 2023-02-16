package com.example.mytesttv

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class InstalledReceiver : BroadcastReceiver() {
    var mPackageAddListener : PackageAddListener? = null
    var mPackageRemoveListener : PackageRemoveListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_PACKAGE_ADDED) {
            mPackageAddListener?.onPackageAdd(intent.dataString!!)
        } else if (intent?.action == Intent.ACTION_PACKAGE_REMOVED) {
            mPackageRemoveListener?.onPackageRemove(intent.dataString!!)
        }
    }

    interface PackageAddListener {
        fun onPackageAdd(packageName : String)
    }

    interface PackageRemoveListener {
        fun onPackageRemove(packageName: String)
    }
}