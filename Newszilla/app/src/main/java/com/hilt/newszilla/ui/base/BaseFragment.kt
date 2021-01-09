package com.hilt.newszilla.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<MyViewModel : BaseViewModel, Binding : ViewDataBinding> : Fragment() {


    protected lateinit var binding: Binding
    protected lateinit var viewModel: MyViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, getFragmentView(), container, false)
        viewModel = ViewModelProvider(this).get(getViewModel())
        binding.setVariable(getBindingVariableId(), viewModel)
        return binding.root

    }

    abstract fun getBindingVariableId(): Int

    abstract fun getViewModel(): Class<MyViewModel>

    abstract fun getFragmentView(): Int


}