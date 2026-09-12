# Watcha Cookin V2

Watcha Cookin V2 is a mobile-first recipe app that helps you decide what to cook from the ingredients you have.

## V2 features
- Add and remove ingredients
- Weighted recipe matching with a visible match percentage
- Missing-ingredient breakdown
- Search and Discover pages
- Difficulty and cooking-time filtering/sorting
- Save recipes locally
- Recipe details and step-by-step Cooking Mode
- Shopping-list placeholder action
- Photo upload / camera-capable ingredient scanning flow
- Editable locally simulated scan results
- Android APK packaging through WebView
- Offline-first app shell with localStorage

## Android build
GitHub Actions builds a debug APK on pushes to `main` or from the Actions tab using **Build Watcha Cookin V2 APK**.

The Android wrapper loads the bundled app from `app/src/main/assets/index.html`, so the APK does not depend on a hosted website.

## Important
The ingredient-photo recognition in this V2 build is a local demo. The `fakeScan()` flow is the integration point for a real computer-vision API later.
