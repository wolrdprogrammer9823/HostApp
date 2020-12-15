package com.wolfsea.hostapp
import android.Manifest
import android.os.Bundle
import com.permissionx.guolindev.PermissionX
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class EntranceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        PermissionX.init(this)
                   .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                   .request { allGranted, _, _ ->
                       run {
                           if (allGranted) {
                               addDisposable(Observable.timer(1000, TimeUnit.MILLISECONDS)
                                   .subscribeOn(Schedulers.single())
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe {

                                      gotoActivity(this, MainActivity::class.java)
                                      this.finish()
                               })
                           } else {
                               this.finish()
                               exitProcess(0)
                           }
                       }
                   }
    }

}