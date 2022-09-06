## VGS Collect iOS SDK - React Native Demo

> **_NOTE:_** This demo is just an example of how [VGS Checkout iOS SDK](https://github.com/verygoodsecurity/vgs-checkout-ios) can be integrated into your's RN application. VGS don't provide RN wrapper for Checkout SDK.

## How to run it?

### Requirements

- Installed latest <a href="https://apps.apple.com/us/app/xcode/id497799835?mt=12" target="_blank">Xcode</a>
- Installed latest <a href="https://developer.android.com/studio" target="_blank">Android Studio</a>
- Installed <a href="https://guides.cocoapods.org/using/getting-started.html#installation" target="_blank">CocoaPods</a>
- Organization with <a href="https://www.verygoodsecurity.com/">VGS</a>

#### Step 1

Go to your <a href="https://dashboard.verygoodsecurity.com/" target="_blank">VGS organization</a> and establish <a href="https://www.verygoodsecurity.com/docs/getting-started/quick-integration#securing-inbound-connection" target="_blank">Inbound connection</a>. For this demo you can import pre-built route configuration:

<p align="center">
<img src="images/dashboard_routs.png" width="600">
</p>

- Find the **configuration.yaml** file inside the app repository and download it.
- Go to the **Routes** section on the <a href="https://dashboard.verygoodsecurity.com/" target="_blank">Dashboard</a> page and select the **Inbound** tab.
- Press **Manage** button at the right corner and select **Import YAML file**.
- Choose **configuration.yaml** file that you just downloaded and tap on **Save** button to save the route.

#### Step 2

Clone the application repository.

#### Step 3

Install npm:

`npm install`

Open Terminal and change working directory to `ios` folder that is inside:

`cd ~/CheckoutDemoApp/ios`

Install pods:

`pod install`

#### Step 4

**iOS**

In `vgs-collect-show-ios-react-native-demo` folder find and open `CheckoutDemoApp.xcworkspace` file.
In the app go to `CheckoutCustomFlowManager.swift` file, find `DemoAppConfiguration` class and `vaultId` attribute there:

`let vaultId = "vaultId"`

and replace `vaultId` with your organization
<a href="https://www.verygoodsecurity.com/docs/terminology/nomenclature#vault" target="_blank">vault id</a>.

**Android**

Replace `private const val VAULT_ID = ""` in `android/app/src/main/java/com/checkoutdemoapp/modules/checkout/CheckoutModule.kt`  with your organization
<a href="https://www.verygoodsecurity.com/docs/terminology/nomenclature#vault" target="_blank">vault id</a>.

### Step 5

**iOS**: Run the application and submit the checkout form.
Then go to the Logs tab on <a href="http://dashboard.verygoodsecurity.com" target="_blank">Dashboard</a>, find request and secure a payload.
Instruction for this step you can find <a href="https://www.verygoodsecurity.com/docs/getting-started/quick-integration#securing-inbound-connection" target="_blank">here</a>.

**Android**: `npx react-native run-android`

### Useful links

- <a href="https://www.verygoodsecurity.com/docs/payment-optimization/checkout/ios-sdk/send-data-to-your-server-ios" target="_blank">VGS Checkout SDK Documentation</a>
- <a href="https://github.com/verygoodsecurity/vgs-checkout-ios/tree/main/VGSCheckoutDemoApp" target="_blank">Checkout Demo App in native iOS</a>
- <a href="https://facebook.github.io/react-native/docs/native-modules-ios#exporting-swift" target="_blank">Exporting Swift into React Native</a>
