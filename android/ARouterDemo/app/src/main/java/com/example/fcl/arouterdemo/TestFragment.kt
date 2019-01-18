package com.example.fcl.arouterdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * Created by galio.fang on 19-1-4
 */
@Route(path = "/com/TestFragment")
public class TestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_test_fragment, container, false)
        return view
    }

}
