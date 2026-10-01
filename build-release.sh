#!/usr/bin/env bash
# Builds a signed release APK (direct install) and AAB (Play Store) without Android Studio.
# Usage:  ./build-release.sh /path/to/your-release-keystore.jks [key-alias]
# Passwords are asked for interactively and never saved.
set -euo pipefail
cd "$(dirname "$0")"

KEYSTORE="${1:?usage: ./build-release.sh /path/to/keystore.jks [key-alias]}"
ALIAS="${2:-}"
[ -f "$KEYSTORE" ] || { echo "Keystore not found: $KEYSTORE"; exit 1; }

# This project (Gradle 5.6.4 / AGP 3.6.1) needs Java 8 or 11
if JH=$(/usr/libexec/java_home -v 11 2>/dev/null); then export JAVA_HOME="$JH"; fi
echo "Using Java: $("${JAVA_HOME:-/usr}/bin/java" -version 2>&1 | head -1)"

SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-/opt/homebrew/share/android-commandlinetools}}"
BT="$SDK/build-tools/29.0.3"
[ -x "$BT/apksigner" ] || { echo "Android build-tools 29.0.3 not found in $SDK (see setup steps)"; exit 1; }
echo "sdk.dir=$SDK" > local.properties

read -r -s -p "Keystore password: " KS_PASS; echo
read -r -s -p "Key password (press Enter if same): " KEY_PASS; echo
KEY_PASS="${KEY_PASS:-$KS_PASS}"
export KS_PASS KEY_PASS

if [ -z "$ALIAS" ]; then
  echo "Aliases in keystore:"
  keytool -list -v -keystore "$KEYSTORE" -storepass:env KS_PASS | grep -i '^Alias name' || echo "  (could not list - check the keystore password)"
  read -r -p "Key alias: " ALIAS
fi
[ -n "$ALIAS" ] || { echo "Key alias is required."; exit 1; }

./gradlew --no-daemon clean assembleRelease bundleRelease

V=$(grep versionName app/build.gradle | sed -E 's/.*"(.*)".*/\1/')
OUT=release-out; mkdir -p "$OUT"

# APK: zipalign + apksigner
"$BT/zipalign" -f -p 4 app/build/outputs/apk/release/app-release-unsigned.apk "$OUT/.aligned.apk"
"$BT/apksigner" sign --ks "$KEYSTORE" --ks-key-alias "$ALIAS" \
  --ks-pass env:KS_PASS --key-pass env:KEY_PASS \
  --out "$OUT/splash-$V.apk" "$OUT/.aligned.apk"
rm -f "$OUT/.aligned.apk" "$OUT/splash-$V.apk.idsig"
"$BT/apksigner" verify "$OUT/splash-$V.apk" && echo "APK signed OK"

# AAB: jarsigner
cp app/build/outputs/bundle/release/app-release.aab "$OUT/splash-$V.aab"
jarsigner -keystore "$KEYSTORE" -storepass:env KS_PASS -keypass:env KEY_PASS \
  -sigalg SHA256withRSA -digestalg SHA-256 "$OUT/splash-$V.aab" "$ALIAS" >/dev/null
jarsigner -verify "$OUT/splash-$V.aab" | tail -1

echo; echo "Done:"; ls -lh "$OUT"
