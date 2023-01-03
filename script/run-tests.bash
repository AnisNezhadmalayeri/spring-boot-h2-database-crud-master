#!/usr/bin/env bash

xcodebuild \
  -workspace MovizySwiftUI/MovizySwiftUI.xcworkspace \
  -scheme MovizySwiftUI \
  -sdk iphonesimulator \
  -destination 'platform=iOS Simulator,name=iPhone 11 Pro' \
  test | xcpretty --test --color