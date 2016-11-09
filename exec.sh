./gradlew check
read -n1 -rsp "Press any key to continue..."
./gradlew assembleDebug
adb install -r app/build/outputs/apk/app-debug.apk
adb shell am start -n "com.github.dilyar85.violetdroid/.MainActivity"