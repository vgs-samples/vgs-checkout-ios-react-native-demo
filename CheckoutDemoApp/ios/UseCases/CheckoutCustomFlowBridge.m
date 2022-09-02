//
//  CheckoutCustomFlowManager.m
//  CheckoutDemoApp
//


#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(CheckoutCustomFlowManager, RCTViewManager)

RCT_EXTERN_METHOD(presentCheckout: (RCTResponseSenderBlock)callback);

@end
