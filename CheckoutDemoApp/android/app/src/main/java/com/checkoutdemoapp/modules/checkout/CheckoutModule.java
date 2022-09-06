package com.checkoutdemoapp.modules.checkout;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.verygoodsecurity.vgscheckout.VGSCheckout;
import com.verygoodsecurity.vgscheckout.VGSCheckoutCallback;
import com.verygoodsecurity.vgscheckout.config.VGSCheckoutCustomConfig;
import com.verygoodsecurity.vgscheckout.model.VGSCheckoutResult;

public class CheckoutModule extends ReactContextBaseJavaModule implements VGSCheckoutCallback {

    @Nullable
    private VGSCheckout checkout;

    @Nullable
    private Callback callback;

    public CheckoutModule(@NonNull AppCompatActivity activity) {
        super();
        if (activityInCorrectState(activity)) {
            this.checkout = new VGSCheckout(activity, this);
        } else {
            Log.d("Checkout", "Activity in incorrect state");
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "CheckoutCustomFlowManager";
    }

    @Override
    public void onCheckoutResult(@NonNull VGSCheckoutResult vgsCheckoutResult) {
        Log.d("Checkout", "onCheckoutResult[" + vgsCheckoutResult + "]");
        if (callback == null) {
            return;
        }
        if (vgsCheckoutResult instanceof VGSCheckoutResult.Success) {
            callback.invoke("Success");
        } else if (vgsCheckoutResult instanceof VGSCheckoutResult.Failed) {
            callback.invoke("Failed");
        } else if (vgsCheckoutResult instanceof VGSCheckoutResult.Canceled) {
            callback.invoke("Cancelled");
        }
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void presentCheckout(Callback callback) {
        String id = "";
        Log.d("Checkout", "checkout[" + id + "]");
        this.callback = callback;
        if (checkout != null) {
            checkout.present(new VGSCheckoutCustomConfig.Builder(id).build());
        } else {
            Log.d("Checkout", "VGSCheckout is null");
        }
    }

    /**
     * Check if activity is already INITIALIZED but not RESUMED.
     */
    private boolean activityInCorrectState(AppCompatActivity activity) {
        Lifecycle.State state = activity.getLifecycle().getCurrentState();
        return state.isAtLeast(Lifecycle.State.INITIALIZED) && state.compareTo(Lifecycle.State.RESUMED) < 0;
    }
}
