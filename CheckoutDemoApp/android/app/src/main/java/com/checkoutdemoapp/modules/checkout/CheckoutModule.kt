package com.checkoutdemoapp.modules.checkout

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.verygoodsecurity.vgscheckout.VGSCheckout
import com.verygoodsecurity.vgscheckout.VGSCheckoutCallback
import com.verygoodsecurity.vgscheckout.config.VGSCheckoutCustomConfig
import com.verygoodsecurity.vgscheckout.model.VGSCheckoutResult

private const val VAULT_ID = ""

class CheckoutModule(
    activity: AppCompatActivity
) : ReactContextBaseJavaModule(), VGSCheckoutCallback {

    private var checkout: VGSCheckout? = null
    private var callback: Callback? = null

    init {
        if (activityInCorrectState(activity)) {
            checkout = VGSCheckout(activity, this)
        } else {
            Log.d("Checkout", "Activity in incorrect state")
        }
    }

    override fun getName(): String = "CheckoutCustomFlowManager"

    override fun onCheckoutResult(result: VGSCheckoutResult) {
        callback?.let { handleResult(it, result) }
    }

    @Suppress("unused")
    @ReactMethod
    fun presentCheckout(callback: Callback?) {
        this.callback = callback
        with(checkout) {
            if (this == null) {
                Log.d("Checkout", "VGSCheckout is null")
            } else {
                present(VGSCheckoutCustomConfig.Builder(VAULT_ID).build())
            }
        }
    }

    /**
     * Check if activity is already INITIALIZED but not RESUMED.
     */
    private fun activityInCorrectState(activity: AppCompatActivity): Boolean {
        val state = activity.lifecycle.currentState
        return state.isAtLeast(Lifecycle.State.INITIALIZED) && state < Lifecycle.State.RESUMED
    }

    private fun handleResult(callback: Callback, result: VGSCheckoutResult) {
        val response: MutableMap<String, Any> = HashMap()
        response["status"] = when (result) {
            is VGSCheckoutResult.Success -> "success"
            is VGSCheckoutResult.Failed -> "failed"
            is VGSCheckoutResult.Canceled -> "canceled"
        }
        callback.invoke(response.toString())
    }
}