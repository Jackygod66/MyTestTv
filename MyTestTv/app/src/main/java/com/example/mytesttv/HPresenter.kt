package com.example.mytesttv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.Presenter
import com.example.mytesttv.appinfo.AppInfo

class HPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val inflate : View = LayoutInflater.from(parent?.context).inflate(R.layout.hgv_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any?) {
        if (item is AppInfo) {
            var viewHolder = viewHolder as ViewHolder
            item.apply {
                viewHolder.imageView?.setImageDrawable(appIcon)
                viewHolder.textView?.text = appName
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
    }

    inner class ViewHolder(view: View?) : Presenter.ViewHolder(view) {
        var imageView : ImageView? = null
        var textView : TextView? = null
        init {
            imageView = view?.findViewById(R.id.iv_item_img)
            textView = view?.findViewById(R.id.tv_item_name)
        }
    }
}