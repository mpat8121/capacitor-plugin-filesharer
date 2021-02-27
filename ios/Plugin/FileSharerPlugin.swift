import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileSharerPlugin)
public class FileSharerPlugin: CAPPlugin {
    private let implementation = FileSharer()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    @objc func share(_ call: CAPPluginCall) {
        guard let filename = call.getString("filename") else {
           call.reject("Filename parameter not provided")
        }
        guard let base64Data = call.getString("base64Data") else {
           call.reject("Base64Data parameter not provided")
        }
        guard let contentType = call.getString("contentType") else {
           call.reject("ContentType parameter not provided")
        }
        guard let fileData = Data(base64Encoded: base64Data) else {
            call.reject("The base64 data provided is invalid")
        }

        let tempFilePathUrl = FileManager.default.temporaryDirectory.appendingPathComponent(filename)

        
    }
}
