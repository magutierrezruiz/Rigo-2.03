# AlgebrAventura 2.0 — FIX6

Esta versión está preparada específicamente para Codemagic y elimina las dos causas de error anteriores:

1. No usa Gradle ni Gradle Wrapper.
2. No depende de `app/src/main/res`; los recursos mínimos se generan automáticamente durante la compilación.

## Contenido del repositorio

Todos los archivos importantes están en la raíz:

- `codemagic.yaml`
- `build-apk.sh`
- `AndroidManifest.xml`
- `MainActivity.java`

No debes crear manualmente carpetas `app/src/main/...`.

## Compilar

1. Crea un repositorio NUEVO en GitHub.
2. Sube todos los archivos de esta carpeta directamente a la raíz.
3. Conecta el repositorio a Codemagic.
4. Ejecuta el workflow **AlgebrAventura 2.0 FIX6 - repositorio plano**.
5. Descarga `app-debug.apk` desde Artifacts.
