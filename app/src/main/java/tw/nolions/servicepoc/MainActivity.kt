package tw.nolions.servicepoc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    internal var mMyService: MyService? = null
    private var mMessengerService: Messenger? = null

    private var mMyServiceBound = false
    private var mMessengerBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService()
    }

    private fun startService() {
        bindService(Intent(this,
            MyService::class.java),
            MyServiceConnection(),
            Context.BIND_AUTO_CREATE)

        bindService(Intent(this,
            MessengerService::class.java),
            MessengerServiceConnection(),
            Context.BIND_AUTO_CREATE)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.mySevice_btn -> {
                Log.e("test", mMyService?.getServiceName())
            }
            R.id.msgService_btn -> {
                var msg = Message()
                msg.what = 0
                msg.obj = "sss"
                mMessengerService?.send(msg)
            }
        }
    }

    internal inner class MyServiceConnection : ServiceConnection {
        /**
         * Called when a connection to the Service has been lost.  This typically
         * happens when the process hosting the service has crashed or been killed.
         * This does *not* remove the ServiceConnection itself -- this
         * binding to the service will remain active, and you will receive a call
         * to [.onServiceConnected] when the Service is next running.
         *
         * @param name
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            mMyService = null
            mMyServiceBound = false
        }

        /**
         * Called when a connection to the Service has been established, with
         * the [android.os.IBinder] of the communication channel to the
         * Service.
         *
         * @param name
         * @param service
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyService.LocalBinder
            mMyService = binder.getService()
            mMyServiceBound = true
        }

    }

    internal inner class MessengerServiceConnection : ServiceConnection {
        /**
         * Called when a connection to the Service has been lost.  This typically
         * happens when the process hosting the service has crashed or been killed.
         * This does *not* remove the ServiceConnection itself -- this
         * binding to the service will remain active, and you will receive a call
         * to [.onServiceConnected] when the Service is next running.
         *
         * @param name The concrete component name of the service whose
         * connection has been lost.
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            mMessengerService = null
            mMessengerBound = false
        }

        /**
         * Called when a connection to the Service has been established, with
         * the [android.os.IBinder] of the communication channel to the
         * Service.
         *
         *
         * @param name The concrete component name of the service that has
         * been connected.
         * @param service The IBinder of the Service's communication channel,
         * which you can now make calls on.
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mMessengerService = Messenger(service)
            mMessengerBound = true
        }

    }
}
