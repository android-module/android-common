package com.caldremch.android.common.sample

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.BaseDataBindingActivity
import com.caldremch.android.common.databinding.BaseMVVMActivity
import com.caldremch.android.common.databinding.adapter.IBindingClick
import com.caldremch.android.common.sample.databinding.ActivityMain2Binding
import com.caldremch.common.ext.BaseActivity

class Main2ViewModel: ViewModel() {
     val start = MutableLiveData<Any?>()
    val click = object : IBindingClick{
        override fun click() {
            start.postValue(null)
        }
    }
}
class MainActivity2 : BaseMVVMActivity<ActivityMain2Binding, Main2ViewModel>() {
    override fun getVariableId(): Int {
        return BR.viewModel
    }

    override val statusBarColorRes: Int?
        get() = android.R.color.white

    override val titleBarTitle: String
        get() = "super.titleBarTitle"

//    override val titleColor: Int?
//        get() = Color.RED

    override val layoutId: Int
        get() = R.layout.activity_main2

    override fun initView() {
        super.initView()
        viewModel.start.observe(this){
            startActivity(Intent(this, SampleMVVMActivity::class.java))
        }
    }
}