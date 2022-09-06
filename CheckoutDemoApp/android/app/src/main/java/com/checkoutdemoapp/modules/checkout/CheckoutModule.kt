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
import com.verygoodsecurity.vgscheckout.config.networking.request.core.VGSCheckoutDataMergePolicy
import com.verygoodsecurity.vgscheckout.config.ui.view.address.VGSCheckoutBillingAddressVisibility
import com.verygoodsecurity.vgscheckout.model.VGSCheckoutResult
import com.verygoodsecurity.vgscheckout.model.VGSCheckoutResultBundle.Keys.ADD_CARD_RESPONSE
import com.verygoodsecurity.vgscheckout.model.response.VGSCheckoutCardResponse

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
                present(
                    VGSCheckoutCustomConfig.Builder(VAULT_ID)
                        .setCardHolderOptions("cardholder_name")
                        .setCardNumberOptions("card_number")
                        .setExpirationDateOptions("exp_date")
                        .setCVCOptions("card_cvc")
                        .setBillingAddressVisibility(VGSCheckoutBillingAddressVisibility.VISIBLE)
                        .setCountryOptions("billing_address.country")
                        .setCityOptions("billing_address.city")
                        .setAddressOptions("billing_address.addressLine1")
                        .setOptionalAddressOptions("billing_address.addressLine2")
                        .setPostalCodeOptions("billing_address.postal_code")
                        .setMergePolicy(VGSCheckoutDataMergePolicy.NESTED_JSON)
                        .setPath("post")
                        .build()
                )
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
        val response: MutableMap<String, Any?> = HashMap()
        response["status"] = when (result) {
            is VGSCheckoutResult.Success -> "success"
            is VGSCheckoutResult.Failed -> "failed"
            is VGSCheckoutResult.Canceled -> "canceled"
        }
        val cardResult = result.data.getParcelable<VGSCheckoutCardResponse>(ADD_CARD_RESPONSE)
        response["statusCode"] = cardResult?.code
        response["data"] = cardResult?.body
        response["error"] = cardResult?.message
        callback.invoke(response.toString())
    }
}