./gradlew check -x lint
./gradlew assembleDebug
adb install -r app/build/outputs/apk/app-debug.apk
adb shell am start -n "com.github.dilyar85.violetdroid/.MainActivity"