# Diversity Android App

## Common Tasks

### Generate a Play Store Build

- Obtain the `diversity.jks` key store from the shared Google Drive folder and copy it to your machine;
- Obtain the password for the `diversity.jks` key store (shared via email);
- Invoke the following Gradle command from the root of this repository:
    ```
    ./gradlew clean app:assembleRelease -PpathToKeyStore='fill-me-in' -PsigningPassword='fill-me-in'
    ```

This generates a signed APK in the `app/build/outputs/apk` directory.
