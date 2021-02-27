import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileSharerPlugin)
public class FileSharerPlugin: CAPPlugin {
    private let implementation = FileSharer()

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

    @objc func shareMultiple(_ call: CAPPluginCall) {
        let files = call.getArray("files", [String:String].self)
        var fileUrls: [URL] = []
        files?.forEach({ (file) in
            let filename = file["filename"]
            let base64Data = file["base64Data"]
            let fileData = Data(base64Encoded: base64Data!)
            let tempFilePathUrl = FileManager.default.temporaryDirectory.appendingPathComponent(filename!)
            fileUrls.append(tempFilePathUrl)
            do {
            try fileData?.write(to: tempFilePathUrl)
            } catch {
                call.resolve()
            }
        })
        let viewCtrl = UIActivityViewController(activityItems: [fileUrls], applicationActivities: nil)
        
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
        
        call.resolve();

    }
    typealias fileType = (contentType: String, filename: String, base64Data: String)
}
