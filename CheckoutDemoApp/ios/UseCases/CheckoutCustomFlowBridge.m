//
//  CheckoutCustomFlowManager.m
//  CheckoutDemoApp
//


#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(CheckoutCustomFlowManager, RCTViewManager)

RCT_EXTERN_METHOD(checkoutButtonDidTap: (RCTResponseSenderBlock)callback);
RCT_EXPORT_VIEW_PROPERTY(onPressLeftButton, RCTDirectEventBlock)

@end
