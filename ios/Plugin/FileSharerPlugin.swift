import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileSharerPlugin)
public class FileSharerPlugin: CAPPlugin {
    private let implementation = FileSharer()

}
