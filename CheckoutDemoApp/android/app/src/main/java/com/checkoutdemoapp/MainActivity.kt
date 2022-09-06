package com.checkoutdemoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.checkoutdemoapp.modules.checkout.CheckoutPackages
import com.facebook.react.BuildConfig
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.common.LifecycleState
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.soloader.SoLoader

class MainActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {

    private lateinit var reactRootView: ReactRootView
    private lateinit var reactInstanceManager: ReactInstanceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoLoader.init(this, false)
        reactInstanceManager = ReactInstanceManager.builder()
            .setApplication(application)
            .setCurrentActivity(this)
            .setBundleAssetName("index.android.bundle")
            .setJSMainModulePath("index")
            .addPackages(PackageList(application).packages)
            .addPackage(CheckoutPackages(this)) // <-- Important: CheckoutPackages should be created before Activity RESUMED state.
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build()
        reactRootView = ReactRootView(this)
        reactRootView.startReactApplication(reactInstanceManager, "MainActivity", null)
        setContentView(reactRootView)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        reactInstanceManager.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        reactInstanceManager.onHostResume(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        reactInstanceManager.onHostDestroy(this)
        reactRootView.unmountReactApplication()
    }

    override fun onBackPressed() {
        reactInstanceManager.onBackPressed()
    }
}