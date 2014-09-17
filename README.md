i18nWidgets
===========

Android Widgets with multi-lingual support (especially Indic)

This Project is a Free Software and is released under GPLv3 License.

The project tree is already made suitable for using with eclipse and hence is readily testable from within eclipse. There are three dependencies to take care of while building:
1. Andoroid SDK 14/15: You may have to configure your development environment for Android SDK 14/15 
2. Android NDK 8: Make sure to have NDK 8 support enabled. 
3. Also have the appcompat_v7_2 library added properly to the project dependencies. 

Please make sure to have exact sdk and ndk versions setup, as the project includes working with native C/C++ libraries along with android platform and hence may run into tedious binary incompatibility issues if not built with proper resources. I have made best attempt to keep things simple for working within eclipse, but do go through the documentation of above development environment thoroughly if you are new to developing native android apps, in case you are struck at some compilation issue.

The project tree has following important components:
1. /jni : This contains the complex-script-rendering library that integrates Android Java widget with the C/C++ libraries of Harfbuzz and Freetype. The entire source tree of particular working snapshot of HB and FT are given in the repo to avoid any incompatibilities during the compilation. Also their makefiles are tuned to work with the given setup.
2. /src/gujaratirendering : This package contains a widget IndicTextView, an extension to the TextView widget which can render Gujarati text. Currently 
3. /src/com/example/i18nwidgetdemo: This is a demo package for testing the the IndicTextView widget with sample text on a simple app screen with Lohit-Gujarati font.
4. /assets: It contains the Lohit-Gujarati font to be used in the demo app

The library complex-script-rendering is based on the original work from Shiva Kumar, and is enhanced and made more generic. Indeed the attempt is to make it further generic and extendable. 

As of now, the project supports text rendering on app being developed for ICS platform and Gujarati language, but the language is not a necessarily limitation. Its just that I tried it with one language to start with, and this can be easily extended and made flexible for auto-supporting any language on the fly. Currently, one will have to make a small change in the complex-script-rendering.c to support language other than Gujarati and add the font of the respective language.

The objectives of the project are:
1. To further improve the library and the widgets code (may involve a lot of code cleaning to fit the norms)
2. Add support for all the languages, by detecting the language of the running text
3. Add more extended widgets to support complete android SDK
4. Create and maintain different branches supporting different android platforms


© 2014 GnowTantra Pvt Ltd
