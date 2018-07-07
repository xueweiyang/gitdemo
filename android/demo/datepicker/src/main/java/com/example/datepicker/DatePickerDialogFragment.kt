package com.example.datepicker

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import com.example.datepicker.adapter.NumericWheelAdapter
import kotlinx.android.synthetic.main.layout_date_picker_fragment.cancelView
import kotlinx.android.synthetic.main.layout_date_picker_fragment.ensureView
import kotlinx.android.synthetic.main.layout_date_picker_fragment.timerView

class DatePickerDialogFragment : DialogFragment() {

    var onClickListener : ClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.PopupAdDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.layout_date_picker_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setGravity(Gravity.BOTTOM)
        dialog.window.setLayout(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        cancelView.setOnClickListener {
            dismiss()
        }
        ensureView.setOnClickListener {
            Toast.makeText(context, "点击确认", Toast.LENGTH_SHORT).show()
            onClickListener?.ensure(timerView.getTime())
            dismiss()
        }
        timerView.setScale(1900,2018,1,7,1,31)
        timerView.setTime(2018, 1, 31)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val transaction = manager.beginTransaction()
        transaction.add(this, tag)
        transaction.commitAllowingStateLoss()
    }

    interface ClickListener{
        fun ensure(date : String)
    }
}