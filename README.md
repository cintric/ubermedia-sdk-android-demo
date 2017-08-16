# Android demo app for the UberMedia Header Bidding SDK (v0.1.2)

The UberMedia Header Bidding SDK for Android allows you to optimize ad revenue by creating an open auction for your ad space instead of using the traditional waterfall method like other mediation SDKs. It is lightweight and optimized to minimize impact on your application.

## Demo App

Import this project into Android Studio and press run. You may be prompted to install the necessary platform versions.

## Installation

Installing the UberMedia Header Bidding SDK is done in 3 steps.

1. **Import SDK into project** and add it as a dependency
2. **Add Google Play Services** to your build.gradle file
3. **Initialize the SDK** with code

### Import SDK into your project

After downloading the SDK from [here](https://github.com/cintric/ubermedia-sdk-android-demo/blob/master/ubermedia/ubermedia.aar), click `File > Project Structure` in Android Studio. Click the green plus icon in the top left, click `Import AAR`, select the ubermedia SDK file, press `OK` and wait for Gradle to finish syncing.

Once Gradle is finished (only a few seconds), click `File > Project Structure` again. Click on `app`, then `Depenencies` tab, then the green icon (top right), select `Module dependency`, click on ubermedia, then press Ok and wait for Gradle to sync again.

![](https://raw.githubusercontent.com/cintric/ubermedia-sdk-android-demo/master/doc_assets/import-module.gif?token=AFPMMD2dBxdKU6n-EwUlOQ_P9yajmYTeks5Zne4GwA%3D%3D)

### Add Google Play Services to your build.gradle file

Open up your build.gradle file and locate the dependencies section at the bottom of the file.

Add this line:

```
compile 'com.google.android.gms:play-services:10.2.6'
```

Your dependencies should now have **Google Play Services** and **ubermedia**.

![](https://raw.githubusercontent.com/cintric/ubermedia-sdk-android-demo/master/doc_assets/build-gradle.png?token=AFPMMDvAm2-5mVvpU6ykarhwV4Qw9N5fks5ZnfNMwA%3D%3D)

### Initalize the SDK

To initialize the SDK, you must call the initializeUberMediaSDK when your app is started.

```java
UberMedia.initializeUberMediaSDK(this, "test-1-api-key", "test-1-secret-key");
```

**You should call this method in your Main Activity's onCreate method.
Put in your own sdk key and secret here.**

![](https://raw.githubusercontent.com/cintric/ubermedia-sdk-android-demo/master/doc_assets/sdk-init.png?token=AFPMMFqbu2fmxYRonIt5lONBw4DB78Y1ks5ZngeawA%3D%3D)

### Android Manifest

**Make sure you have internet permissions in your AndroidManifest.xml**.

The required internet permissions are:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Displaying Banner Ad


In your parent layout, add the following:

```xml
xmlns:ubermedia="http://schemas.android.com/apk/res-auto"
```

And add the banner view:

```xml
    <ubermedia.com.ubermedia.UMBannerView
        android:id="@+id/adview"
        ubermedia:ad_unit="test_ad_placement_id"
        android:layout_width="wrap_content"
        android:layout_height="50dp" />
```


To center the banner view, change the above example to:

```xml
    <ubermedia.com.ubermedia.UMBannerView
        android:id="@+id/adview"
        ubermedia:ad_unit="test_ad_placement_id"
        android:layout_width="wrap_content"
        android:layout_height="50dp"
        android:layout_weight="1"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent" />
```

### Ad Units

Inside the UMBannerView xml is an `ubermedia:ad_unit` attribute.

Test Ad Unit: `test_ad_placement_id` will return a test 320x50 banner.

Please contact us for production ad units.
