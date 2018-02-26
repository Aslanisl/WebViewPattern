package aslanisl.mail.ru.webviewpattern.launch

import android.arch.lifecycle.*
import android.os.Handler

const val LOADING_TIME = 2 * 1000L

class LaunchViewModel() : ViewModel(), LifecycleObserver{

    private val statusLiveData = MutableLiveData<Boolean>()
    private var handler: Handler
    private var runnable: Runnable

    init {
        handler = Handler()
        runnable = Runnable {
            statusLiveData.postValue(true)
        }
    }

    fun init(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun getStatusData() = statusLiveData

    fun load(){
        handler.postDelayed(runnable, LOADING_TIME)
    }

    fun stopLoad(){
        handler.removeCallbacks(runnable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun stopCalls(){
        handler.removeCallbacks(runnable)
    }
}