#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"

SDK_ROOT="${ANDROID_SDK_ROOT:-/usr/local/share/android-sdk}"
API="${ANDROID_API:-35}"
BUILD_TOOLS_VERSION="${ANDROID_BUILD_TOOLS:-35.0.0}"
BT="$SDK_ROOT/build-tools/$BUILD_TOOLS_VERSION"
ANDROID_JAR="$SDK_ROOT/platforms/android-$API/android.jar"

AAPT2="$BT/aapt2"
D8="$BT/d8"
ZIPALIGN="$BT/zipalign"
APKSIGNER="$BT/apksigner"

printf '=== AlgebrAventura 2.0 FIX6 ===\n'
printf 'Directorio del repositorio: %s\n' "$ROOT"
printf 'SDK: %s\n' "$SDK_ROOT"
printf 'API: %s\n' "$API"
printf 'Build Tools: %s\n\n' "$BUILD_TOOLS_VERSION"

for f in "$AAPT2" "$D8" "$ZIPALIGN" "$APKSIGNER" "$ANDROID_JAR"; do
  if [ ! -e "$f" ]; then
    echo "ERROR: No se encontró $f"
    exit 2
  fi
done

for f in "$ROOT/MainActivity.java" "$ROOT/AndroidManifest.xml"; do
  if [ ! -f "$f" ]; then
    echo "ERROR: Falta el archivo obligatorio $f"
    echo "Contenido de la raíz del repositorio:"
    ls -la "$ROOT"
    exit 3
  fi
done

WORK="$ROOT/.manual-build"
OUT="$ROOT/app/build/outputs/apk/debug"
rm -rf "$WORK"
mkdir -p "$WORK/res/values" "$WORK/compiled-res" "$WORK/classes" "$WORK/dex" "$OUT"

# El recurso se crea DURANTE el build. Así el repositorio no depende de app/src/main/res.
cat > "$WORK/res/values/build_info.xml" <<'XML'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="build_info">AlgebrAventura 2.0</string>
</resources>
XML

echo "== 1/6 Compilar recurso mínimo generado en tiempo de build =="
"$AAPT2" compile "$WORK/res/values/build_info.xml" -o "$WORK/compiled-res"

RESOURCE_FLAT="$(find "$WORK/compiled-res" -type f -name '*.flat' -print -quit)"
if [ -z "$RESOURCE_FLAT" ] || [ ! -f "$RESOURCE_FLAT" ]; then
  echo "ERROR: aapt2 no generó el recurso compilado"
  find "$WORK" -maxdepth 4 -type f -print || true
  exit 4
fi

echo "== 2/6 Enlazar AndroidManifest y recursos =="
"$AAPT2" link \
  -o "$WORK/app-resources.apk" \
  -I "$ANDROID_JAR" \
  --manifest "$ROOT/AndroidManifest.xml" \
  --min-sdk-version 21 \
  --target-sdk-version "$API" \
  --version-code 2 \
  --version-name 2.0 \
  "$RESOURCE_FLAT"

echo "== 3/6 Compilar Java =="
javac \
  -encoding UTF-8 \
  -source 8 \
  -target 8 \
  -classpath "$ANDROID_JAR" \
  -d "$WORK/classes" \
  "$ROOT/MainActivity.java"

if ! find "$WORK/classes" -type f -name '*.class' -print -quit | grep -q .; then
  echo "ERROR: javac no generó archivos .class"
  exit 5
fi

# Empaquetar las clases evita pasar una lista de archivos dependiente de funciones modernas de Bash.
jar cf "$WORK/classes.jar" -C "$WORK/classes" .

echo "== 4/6 Convertir bytecode a DEX =="
"$D8" \
  --lib "$ANDROID_JAR" \
  --min-api 21 \
  --output "$WORK/dex" \
  "$WORK/classes.jar"

if [ ! -f "$WORK/dex/classes.dex" ]; then
  echo "ERROR: d8 no generó classes.dex"
  exit 6
fi

echo "== 5/6 Insertar DEX y alinear APK =="
cp "$WORK/app-resources.apk" "$WORK/app-with-dex.apk"
(
  cd "$WORK/dex"
  zip -q -u "$WORK/app-with-dex.apk" classes.dex
)
"$ZIPALIGN" -f 4 "$WORK/app-with-dex.apk" "$WORK/app-aligned.apk"

echo "== 6/6 Firmar y verificar APK =="
KEYSTORE="$WORK/debug.keystore"
keytool -genkeypair \
  -keystore "$KEYSTORE" \
  -storepass android \
  -alias androiddebugkey \
  -keypass android \
  -dname "CN=Android Debug,O=Android,C=US" \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -noprompt >/dev/null 2>&1

FINAL_APK="$OUT/app-debug.apk"
"$APKSIGNER" sign \
  --ks "$KEYSTORE" \
  --ks-pass pass:android \
  --key-pass pass:android \
  --out "$FINAL_APK" \
  "$WORK/app-aligned.apk"

"$APKSIGNER" verify --verbose "$FINAL_APK"

echo ""
echo "=========================================="
echo "APK CREADO CORRECTAMENTE"
echo "$FINAL_APK"
ls -lh "$FINAL_APK"
echo "=========================================="
