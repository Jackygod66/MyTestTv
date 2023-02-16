package com.example.mytesttv.BaseMvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.reflect.ParameterizedType

abstract class BaseMvpFragment<V, P : BaseMvpPresenter<V>> : Fragment() {

    /**
     * MVP模式中Presenter对象
     */
    private var mPresenter: P? = null
    fun getPresenter(): P? {
        if (mPresenter == null) {
            throw RuntimeException("Presenter is Null, You can't get it at this time!")
        }
        return mPresenter
    }

    /*************分界线以上为外部使用的方法，以下为私有或生命周期方法不应主动调用*****************/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        @Suppress("UNCHECKED_CAST")
        mPresenter?.attach(this as V)
    }

    override fun onDestroy() {
        mPresenter?.detach()
        mPresenter = null
        super.onDestroy()
    }

    /**
     * 此方法自动生成Presenter
     */
    private fun <P : BaseMvpPresenter<V>> createPresenter(): P {
        val type = javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments = type.actualTypeArguments
        @Suppress("UNCHECKED_CAST")
        val presenterClass = actualTypeArguments[1] as Class<P>
        val presenter = presenterClass.newInstance() as BaseMvpPresenter<V>
        presenter.onCreate(context)
        @Suppress("UNCHECKED_CAST")
        return presenter as P
    }
}