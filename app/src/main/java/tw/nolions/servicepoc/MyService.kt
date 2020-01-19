package tw.nolions.servicepoc

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {
    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    fun getServiceName(): String {
        return "MyService"
    }


    inner class LocalBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }
}
