package com.ww7h.ww.common.bases.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by ww on 2018/6/27.
 */
abstract class BaseFragment<T : BaseFragment<T>>:Fragment() {
    var TAG:String
    var fragment:T

    init {
        fragment = this as T
        TAG = fragment.javaClass.name
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(getResourceId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView();
        initAction();
    }

    /**
     * 返回界面显示视图
     */
    abstract fun getResourceId():Int

    /**
     * 初始化视图
     */
    abstract fun initView()

    /**
     * 初始化事件
     */
    abstract fun initAction()
}