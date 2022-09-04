#!/bin/bash
APP_UUID=01gc254e4n55xgvw8m2pvp4r0y
bootstrap $APP_UUID

echo "Adding secrets for the application use"
writesecret $APP_UUID oracle password123
