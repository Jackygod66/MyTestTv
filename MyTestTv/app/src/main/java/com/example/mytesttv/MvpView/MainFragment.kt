package com.example.mytesttv.MvpView

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.leanback.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mytesttv.*
import com.example.mytesttv.BaseMvp.BaseMvpFragment
import com.example.mytesttv.MvpPresenter.MainFragmentPresenter
import com.example.mytesttv.appinfo.AppInfo
import com.example.mytesttv.highlight.MyFocusHighlightHelper

class MainFragment : BaseMvpFragment<IMainContract.IMainView, MainFragmentPresenter>(),
    IMainContract.IMainView {
    private val TAG = "MainFragment"
    private var mBrowseItemFocusHighlight : MyFocusHighlightHelper.BrowseItemFocusHighlight? = null
    private var mHorizontalGridView : HorizontalGridView? = null
    private lateinit var mBridgeAdapter : ItemBridgeAdapter
    private lateinit var mObjectAdapter: ArrayObjectAdapter
    private lateinit var mDialog : Dialog
    private var mInstalledReceiver : InstalledReceiver? = null
    private var selectPosition : Int? = null
    private var deletePosition : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    //广播监听
    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    //广播取消监听
    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadCastReceiver()
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mHorizontalGridView = view?.findViewById(R.id.hgv_recently)
        val presenter = HPresenter()
        mObjectAdapter = ArrayObjectAdapter(presenter)
        mBridgeAdapter = ItemBridgeAdapter(mObjectAdapter)
        mBridgeAdapter.setAdapterListener(object : ItemBridgeAdapter.AdapterListener() {
            override fun onCreate(viewHolder: ItemBridgeAdapter.ViewHolder?) {
                super.onCreate(viewHolder)
                viewHolder?.itemView?.apply {
                    setOnClickListener {
                        startApp(context, (mObjectAdapter[selectPosition!!] as AppInfo).packageName)
                    }
                    setOnLongClickListener {
                        showChooseDialog()
                        true
                    }
                    setOnFocusChangeListener { v, hasFocus ->
                        mBrowseItemFocusHighlight?.apply {
                            onItemFocused(v.findViewById(R.id.rl_item_img), hasFocus)
                            onItemFocused(v.findViewById(R.id.tv_item_name), hasFocus)
                        }
                        showView(v.findViewById(R.id.tv_item_name), hasFocus)
                    }
                }
            }
        })
        mHorizontalGridView?.apply {
            setNumRows(1)
            horizontalSpacing = 5
            setOnChildViewHolderSelectedListener(object : OnChildViewHolderSelectedListener(){
                override fun onChildViewHolderSelected(
                    parent: RecyclerView?,
                    child: RecyclerView.ViewHolder?,
                    position: Int,
                    subposition: Int
                ) {
                    super.onChildViewHolderSelected(parent, child, position, subposition)
                    selectPosition = position
                    Log.d(TAG, position.toString())
                }

                override fun onChildViewHolderSelectedAndPositioned(
                    parent: RecyclerView?,
                    child: RecyclerView.ViewHolder?,
                    position: Int,
                    subposition: Int
                ) {
                    super.onChildViewHolderSelectedAndPositioned(
                        parent,
                        child,
                        position,
                        subposition
                    )
                }
            })
            adapter = mBridgeAdapter
        }

        //初始化高亮组件
        val typedArray = requireContext().obtainStyledAttributes(R.styleable.SelectConstraintLayout)
        val zoomIndex = typedArray.getInteger(
            R.styleable.SelectConstraintLayout_scale_mode,
            MyFocusHighlightHelper.ZOOM_FACTOR_XXSMALL
        )
        mBrowseItemFocusHighlight =
            MyFocusHighlightHelper.BrowseItemFocusHighlight(zoomIndex, false)
    }

    private fun initData() {
        getPresenter()?.getAllApp()
    }

    private fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    private fun showChooseDialog() {
        context?.let {
            mDialog = AlertDialog.Builder(it)
                .setTitle("置顶或删除")
                .setPositiveButton("删除"
                ) { _, _ ->
                    deletePosition = selectPosition
                    mDialog.dismiss()
                    showDeleteDialog()
                }
                .setNegativeButton("置顶"
                ) { _, _ ->
                    mDialog.dismiss()
                    getPresenter()?.moveAppToTargetPosition(selectPosition!!, 0)
                }.create()
            mDialog.show()
        }
    }

    private fun showDeleteDialog() {
        if (!(mObjectAdapter[selectPosition!!] as AppInfo).isUserApp) {
            showToast("系统应用无法删除")
            return
        }
        context?.let { uninstallApp((mObjectAdapter[selectPosition!!] as AppInfo).packageName) }
    }

    /**
     * 卸载应用
     */
    private fun uninstallApp(pkg: String) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:" + pkg)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    //启动应用
    private fun startApp(context: Context?, pkg : String) {
        val intent = context?.packageManager?.getLaunchIntentForPackage(pkg)
        startActivity(intent)
    }

    //注册广播监听
    private fun registerBroadCastReceiver() {
        mInstalledReceiver = InstalledReceiver().apply {
            mPackageRemoveListener = object : InstalledReceiver.PackageRemoveListener {
                override fun onPackageRemove(packageName: String) {
                    if ("package:" + (mObjectAdapter[deletePosition!!] as AppInfo).packageName == packageName) {
                        getPresenter()?.deleteApp(deletePosition!!)
                    }
                }
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
        activity?.registerReceiver(mInstalledReceiver, intentFilter)
    }

    //取消广播监听
    private fun unregisterBroadCastReceiver() {
        mInstalledReceiver?.apply {
            activity?.unregisterReceiver(this)
        }
        mInstalledReceiver = null
    }

    /**
     * 根据焦点展示view
     */
    private fun showView(view : View, hasFocus : Boolean) {
        if (hasFocus) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE
    }

    override fun showAllApp(appInfoList: MutableList<AppInfo>?) {
        mObjectAdapter.addAll(0, appInfoList)
    }

    override fun deleteAppInView(position: Int) {
        mObjectAdapter.removeItems(position, 1)
    }

    override fun moveAppInView(fromPosition: Int, toPosition: Int) {
        mObjectAdapter.move(fromPosition, toPosition)
    }
}