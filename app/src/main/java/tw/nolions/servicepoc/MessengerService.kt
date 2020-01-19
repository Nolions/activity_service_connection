package tw.nolions.servicepoc

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

class MessengerService : Service() {
    private val mMessenger = Messenger(MyHandler())

    override fun onBind(intent: Intent): IBinder {
        return mMessenger.binder
    }

    private class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            Log.e("test", "MyHandler::handleMessage, " + msg.what.toString())
            Log.e("test", "MyHandler::handleMessage, " + msg.obj.toString())
        }
    }
}
