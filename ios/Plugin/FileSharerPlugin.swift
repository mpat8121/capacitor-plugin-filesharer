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
            return
        }
        guard let base64Data = call.getString("base64Data") else {
           call.reject("Base64Data parameter not provided")
            return
        }
        guard let fileData = Data(base64Encoded: base64Data) else {
            call.reject("The base64 data provided is invalid")
            return
        }

        let tempFilePathUrl = FileManager.default.temporaryDirectory.appendingPathComponent(filename)
        let viewCtrl = UIActivityViewController(activityItems: [tempFilePathUrl], applicationActivities: nil)
        do{
            try fileData.write(to: tempFilePathUrl)

            DispatchQueue.main.async {
                let deviceType = UIDevice.current.model
                if(deviceType.range(of: "iPad") != nil) {
                    self.setCenteredPopover(viewCtrl)
                    self.bridge?.viewController?.present(viewCtrl, animated: true, completion: {
                            call.resolve()
                        })
                } else if(deviceType.range(of: "iPhone") != nil) {
                    self.bridge?.viewController?.present(viewCtrl, animated: true, completion: {
                            call.resolve()
                        })
                } else {
                    call.reject("Unknow device type to present share")
                }             
            }
        } catch {
            call.reject("Unable to write file correctly")
        }
    }
}
