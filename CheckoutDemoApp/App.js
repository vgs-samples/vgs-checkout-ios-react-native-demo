import {Node, useState} from 'react';
import React from 'react';
import {Button, NativeModules} from 'react-native';

import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  AppRegistry
} from 'react-native';

import {Colors} from 'react-native/Libraries/NewAppScreen';

const VGSCheckoutCustomFlowManager = NativeModules.CheckoutCustomFlowManager;

const Section = ({children, title}): Node => {
  const isDarkMode = useColorScheme() === 'dark';

  const [checkoutResult, setCheckoutResult] = useState('...');

  return (
    <View style={styles.sectionContainer}>
      <Button
        title="Start Checkout Custom Flow"
        onPress={() =>
          VGSCheckoutCustomFlowManager.presentCheckout(result => {
            /// handle checkout result
            setCheckoutResult(JSON.stringify(result));
          })
        }
      />
      <Text style={styles.sectionDescription}>{checkoutResult}</Text>
    </View>
  );
};

const App: () => Node = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>
          <Section title="Step One">
            Edit <Text style={styles.highlight}>App.js</Text> to change this
            screen and then come back to see your edits.
          </Section>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 100,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 50,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;

AppRegistry.registerComponent(
    'MainActivity',
    () => App
);