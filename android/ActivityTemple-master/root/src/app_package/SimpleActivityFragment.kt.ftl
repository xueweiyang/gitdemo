package ${escapeKotlinIdentifiers(packageName)}

import com.dadaabc.zhuozan.dadaabcstudent.base.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<#if applicationPackage??>
import ${applicationPackage}.R
</#if>

class ${fragmentClass} : BaseFragment() , ${contactClass}.View {

    private lateinit var presenter: ${contactClass}.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.${fragmentLayoutName}, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPresenter()
    }

    private fun setupPresenter(){
        this.presenter = ${presenterClass}(this)
    }

}
