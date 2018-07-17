package com.example.fcl.dadademo.widget.dialog

import android.graphics.*
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.fcl.dadademo.api.ApiService
import com.example.fcl.dadademo.util.RxSchedulerUtils
import com.example.fcl.dadademo.util.extension.subscribeRemoteData
import com.example.fcl.kotlindemo.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_verify_dialog.*

class VerifyDialogFragment:DialogFragment() {

    private lateinit var disposable: CompositeDisposable
    private var imageToken=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable= CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.layout_verify_dialog,container,false)
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initData() {
        requestImage()
    }

    private fun requestImage() {
        ApiService.getImageCaptcha(82,36)
                .compose(RxSchedulerUtils.ioToMainSchedulers())
                .subscribeRemoteData({imageVerify->
                    imageVerify?.let {
                        val roundBitmap=getRoundBitmap(base64ToBitmap(it.image))
                        dialogVerifyImage.setImageBitmap(roundBitmap)
                    }
                },{e->
                    e?.printStackTrace()
                })
    }

    fun base64ToBitmap(base64Data:String):Bitmap{
        val bytes= Base64.decode(base64Data,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun getRoundBitmap(bitmap: Bitmap):Bitmap{
        try {
            val output=Bitmap.createBitmap(bitmap.width,bitmap.height,Bitmap.Config.ARGB_8888)
            val canvas=Canvas(output)
            val paint=Paint()
            val rect=Rect(0,0,bitmap.width,bitmap.height)
            val rectF=RectF(rect)
            val roundPx=3f
            paint.isAntiAlias=true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = Color.WHITE
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            canvas.drawRect(0F, 0F, roundPx, roundPx, paint)
            canvas.drawRect(0F, rectF.bottom - roundPx, roundPx, rectF.bottom, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        } catch (e:Exception) {
            return bitmap
        }
    }

    fun show(manager: FragmentManager) {
        val beginTransaction = manager.beginTransaction()
        beginTransaction.add(this, "VerifyDialogFragment")
        beginTransaction.commitAllowingStateLoss()
    }

    override fun onDetach() {
        super.onDetach()
        disposable.clear()
    }

    override fun dismiss() {
        disposable.clear()
        dismissAllowingStateLoss()
    }

    override fun dismissAllowingStateLoss() {
        if (isAdded){
            super.dismissAllowingStateLoss()
        }
    }

}