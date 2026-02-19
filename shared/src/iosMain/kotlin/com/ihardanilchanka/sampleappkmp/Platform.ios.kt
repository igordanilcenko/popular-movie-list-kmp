package com.ihardanilchanka.sampleappkmp

import platform.UIKit.UIDevice

actual class Platform {
    actual val name: String = UIDevice.currentDevice.systemName()
}
