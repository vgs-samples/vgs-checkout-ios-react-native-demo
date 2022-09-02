//
//  CheckoutCustomFlowManager.swift
//  CheckoutDemoApp
//


import Foundation
import VGSCheckoutSDK

struct DemoAppConfiguration {
  static let vaultId = "vaultId"
  static let environment = "sandbox"
}

@objc(CheckoutCustomFlowManager)
class CheckoutCustomFlowManager: NSObject {
  let rootVC = UIApplication.shared.keyWindow?.rootViewController

  var vgsCheckout: VGSCheckout?
  
  @objc var resultCallback: RCTResponseSenderBlock?

  @objc
  func presentCheckout(_ callback: @escaping RCTResponseSenderBlock) {
    // Create custom configuration.
    var checkoutConfiguration = VGSCheckoutCustomConfiguration(vaultID: DemoAppConfiguration.vaultId, environment: DemoAppConfiguration.environment)

    checkoutConfiguration.cardHolderFieldOptions.fieldNameType = .single("cardHolder_name")
    checkoutConfiguration.cardNumberFieldOptions.fieldName = "card_number"
    checkoutConfiguration.expirationDateFieldOptions.fieldName = "exp_data"
    checkoutConfiguration.cvcFieldOptions.fieldName = "card_cvc"

    checkoutConfiguration.billingAddressVisibility = .visible
        
    checkoutConfiguration.billingAddressCountryFieldOptions.fieldName = "billing_address.country"
    checkoutConfiguration.billingAddressCityFieldOptions.fieldName = "billing_address.city"
    checkoutConfiguration.billingAddressLine1FieldOptions.fieldName = "billing_address.addressLine1"
    checkoutConfiguration.billingAddressLine2FieldOptions.fieldName = "billing_address.addressLine2"
    checkoutConfiguration.billingAddressPostalCodeFieldOptions.fieldName = "billing_address.postal_code"

    // Produce nested json for fields with `.` notation.
    checkoutConfiguration.routeConfiguration.requestOptions.mergePolicy = .nestedJSON

    checkoutConfiguration.routeConfiguration.path = "post"

    /* Set custom date user input/output JSON format.

    checkoutConfiguration.expirationDateFieldOptions.inputDateFormat = .shortYearThenMonth
    checkoutConfiguration.expirationDateFieldOptions.outputDateFormat = .longYearThenMonth

    let expDateSerializer = VGSCheckoutExpDateSeparateSerializer(monthFieldName: "card_date.month", yearFieldName: "card_date.year")
    checkoutConfiguration.expirationDateFieldOptions.serializers = [expDateSerializer]
    */

    // Init Checkout with vault and ID.
    vgsCheckout = VGSCheckout(configuration: checkoutConfiguration)

    // Set callback reference
    self.resultCallback = callback
    // Present checkout configuration.
    DispatchQueue.main.async { [weak self] in
      guard let strongSelf = self else {return}
      strongSelf.vgsCheckout?.present(from: strongSelf.rootVC!)
      strongSelf.vgsCheckout?.delegate = strongSelf
    }
  }
  
  
  private func showPopUp(title: String, message: String) {
    //Show popup with result status
    let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertController.Style.alert)

    alert.addAction(UIAlertAction(title: "OK", style: .default))

    if let popoverController = alert.popoverPresentationController, let rootView = self.rootVC?.view {
      popoverController.sourceView = rootView //to set the source of your alert
      popoverController.sourceRect = CGRect(x: rootView.bounds.midX, y: rootView.bounds.midY, width: 0, height: 0) // you can set this as per your requirement.
      popoverController.permittedArrowDirections = [] //to hide the arrow of any particular direction
    }
    self.rootVC?.present(alert, animated: true, completion: nil)
  }
}

// MARK: - VGSCheckoutDelegate

extension CheckoutCustomFlowManager: VGSCheckoutDelegate {
  func checkoutDidCancel() {
    //send result callback
    let result = ["status": "canceled"]
    self.resultCallback?([result])
    //show result popup with status
    self.showPopUp(title: "Checkout status: .cancelled", message: "User cancelled checkout.")
  }

  func checkoutDidFinish(with requestResult: VGSCheckoutRequestResult) {
    let title: String
    let message: String
    
    switch requestResult {
    case .success(let statusCode, let data, let response, _):
      title = "Checkout status: Success!"
      message = "status code is: \(statusCode)"

      let result: [String : AnyHashable] = ["status": "success",
                                            "statusCode": statusCode,
                                            "response": response,
                                            "data": data]
      self.resultCallback?([result])
    case .failure(let statusCode, let data, let response, let error, _):
      title = "Checkout status: Failed!"
      message = "status code is: \(statusCode) error: \(error?.localizedDescription ?? "Uknown error!")"
      let error = "\(error?.localizedDescription ?? "Uknown error!")"
      let result: [String : AnyHashable] = ["status": "failed",
                                            "statusCode": statusCode,
                                            "response": response,
                                            "data": data,
                                            "error": error]
      self.resultCallback?([result])
    }
    self.showPopUp(title: title, message: message)
  }
}
