package com.checkoutdemoapp.modules.checkout

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class CheckoutPackages(activity: AppCompatActivity) : ReactPackage {

    private val module: CheckoutModule

    init {
        module = CheckoutModule(activity)
    }

    override fun createNativeModules(p0: ReactApplicationContext): MutableList<NativeModule> {
        val modules: MutableList<NativeModule> = ArrayList()
        modules.add(module)
        return modules
    }

    override fun createViewManagers(p0: ReactApplicationContext): MutableList<ViewManager<View, ReactShadowNode<*>>> {
        return mutableListOf()
    }
}