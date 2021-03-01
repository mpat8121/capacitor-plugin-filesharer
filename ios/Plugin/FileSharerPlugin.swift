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
            call.resolve([
             "result": false,
             "message": "Filename parameter not provided"
            ])
            return
        }
        guard let base64Data = call.getString("base64Data") else {
           call.resolve([
            "result": false,
            "message": "Base64Data parameter not provided"
           ])
           return
        }
        guard let fileData = Data(base64Encoded: base64Data) else {
            call.resolve([
              "result": false,
              "message": "The base64 data provided is invalid"
            ])
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
                        call.resolve(["result": true, "message": "Share Sheet opened successfully"])
                        })
                } else if(deviceType.range(of: "iPhone") != nil) {
                    self.bridge?.viewController?.present(viewCtrl, animated: true, completion: {
                        call.resolve(["result": true, "message": "Share Sheet opened successfully"])
                        })
                } else {
                    call.resolve([
                     "result": false,
                     "message": "Unknown device type to present share"
                    ])
                }             
            }
        } catch {
            call.resolve([
              "result": false,
              "message": "Unable to write file correctly"
            ])
        }
    }

    @objc func shareMultiple(_ call: CAPPluginCall) {
        let files = call.getArray("files", [String:String].self)
        if(files?.isEmpty == nil) {
            call.resolve([
             "result": false,
             "message": "Incorrect files parameter provided"
            ])
            return
        }
        var header = "Share your files"
        if(call.getString("header") != nil) {
            header = call.getString("header")!
        }
        var fileUrls: [Any] = []
        files?.forEach({ (file) in
            let filename = file["filename"]
            if(filename?.isEmpty == nil || filename?.count == 0) {
                call.resolve([
                 "result": false,
                 "message": "Filename parameter not provided"
                ])
                return
            }
            let base64Data = file["base64Data"]
            guard let fileData = Data(base64Encoded: base64Data!) else {
                call.resolve([
                  "result": false,
                  "message": "The base64 data provided is invalid"
                ])
                return
            }
            let tempFilePathUrl = FileManager.default.temporaryDirectory.appendingPathComponent(filename!)
            fileUrls.append(tempFilePathUrl)
            do {
                try fileData.write(to: tempFilePathUrl)
            } catch {
                call.resolve([
                    "result": false,
                    "message": "Unable to write file"
                  ])
            }
        })

        let viewCtrl = UIActivityViewController(activityItems: fileUrls, applicationActivities: nil)
        DispatchQueue.main.async {
            viewCtrl.setValue(header, forKey: "Subject")
            let deviceType = UIDevice.current.model
            if(deviceType.range(of: "iPad") != nil) {
                self.setCenteredPopover(viewCtrl)
                self.bridge?.viewController?.present(viewCtrl, animated: true, completion: {
                    call.resolve([
                        "result": true,
                        "message": "Share Sheet opened successfully"
                      ])
                    })
            } else if(deviceType.range(of: "iPhone") != nil) {
                self.bridge?.viewController?.present(viewCtrl, animated: true, completion: {
                        call.resolve([
                            "result": true,
                            "message": "Share Sheet opened successfully"
                          ])
                    })
            } else {
                call.resolve([
                    "result": false,
                    "message": "Unknown device type to present share"
                   ])
            }
        }
    }
    typealias fileType = (contentType: String, filename: String, base64Data: String)
}
