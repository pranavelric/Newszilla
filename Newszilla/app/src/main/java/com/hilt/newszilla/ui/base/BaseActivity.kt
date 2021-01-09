package com.hilt.newszilla.ui.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hilt.newszilla.ui.home.HomeViewModel
import com.hilt.newszilla.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.ParameterizedType
import javax.inject.Inject


abstract class BaseActivity<MyViewModel : BaseViewModel, Binding : ViewDataBinding> :
    AppCompatActivity() {


    lateinit var binding: Binding
    private var viewModel: MyViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setFullScreenForNotch()

        performViewModelBinding()


    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getViewModel(): MyViewModel
    abstract fun getBindingVariable(): Int

    private fun performViewModelBinding() {

        binding = DataBindingUtil.setContentView(this, layoutId)
        this.viewModel = viewModel ?: getViewModel()
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()

    }

    private fun getViewModelClass(): Class<MyViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<MyViewModel>
    }


    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            setFullScreen()
        }
    }


}