//
//  DemoAppParser.swift
//  CheckoutDemoApp
//
//  Created by Dmytro on 31.08.2022.
//

import Foundation


class DemoAppParser {
  static func stringifySuccessResponse(from data: Data?, rootJsonKey: String = "json") -> String? {
    if let data = data, let jsonData = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
      // swiftlint:disable force_try
      let response = (String(data: try! JSONSerialization.data(withJSONObject: jsonData[rootJsonKey]!, options: .prettyPrinted), encoding: .utf8)!)
      return response
      // swiftlint:enable force_try
    }
    return nil
  }
}
