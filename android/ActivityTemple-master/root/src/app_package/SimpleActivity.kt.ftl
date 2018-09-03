package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
<#if applicationPackage??>
import ${applicationPackage}.R
</#if>
import com.dadaabc.zhuozan.dadaabcstudent.base.BaseActivity



class ${activityClass} : BaseActivity() <#if useFragment>{ <#else>, ${contactClass}.View{</#if>

    <#if !useFragment>
     private lateinit var presenter: ${contactClass}.Presenter
    </#if>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.${layoutName})
        <#if !useFragment>
        setupPresenter()
        </#if>
    }

    <#if !useFragment>
    private fun setupPresenter(){
        this.presenter = ${presenterClass}(this)
    }
    </#if>
}
