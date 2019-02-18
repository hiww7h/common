package com.ww7h.ww.common.bases.activity

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import com.ww7h.ww.common.R
import com.ww7h.ww.common.utils.Constant
import com.ww7h.ww.common.utils.ConstantCode
import kotlinx.android.synthetic.main.toolbar.*


abstract class BaseActivity<T: BaseActivity<T>>: AppCompatActivity() {

    lateinit var activity:T

    lateinit var TAG:String

    private val MY_PERMISSION_REQUEST_CODE = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        activity = this as T

        TAG = activity.javaClass.name
        setSupportActionBar(toolbar)
        if(intent.getStringExtra("title")!=null){
            title = intent.getStringExtra("title")
        }
        toolbar?.setNavigationIcon(R.mipmap.icon_back)
        toolbar?.setNavigationOnClickListener {
            onLeftClick()
        }
        getPermission()
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle("")
        title_tv.text=title
    }

    private fun getPermission() {
        val isAllGranted = checkPermissionAllGranted(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)
        )

        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            initView()

            initAction()
            return
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_CODE
        )
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false
            }
        }
        return true
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            var isAllGranted = true

            // 判断是否所有的权限都已经授予了
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false
                    break
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                initView()
                initAction()

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails()
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private fun openAppDetails() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("备份通讯录需要访问 “相机” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！")
        builder.setPositiveButton("去手动授权") { _, _ ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:$packageName")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(intent)
        }
        builder.setNegativeButton("取消", null)
        builder.show()
    }

    /**
     * 返回界面显示视图
     */
    abstract fun getContentView():Int

    /**
     * 初始化视图
     */
    abstract fun initView()

    /**
     * 初始化事件
     */
    abstract fun initAction()

    open fun onLeftClick(){
        finish()
    }

    /**
     * 隐藏软键盘
     */
    protected fun hideSoftInput(){
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus.windowToken
            , InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

