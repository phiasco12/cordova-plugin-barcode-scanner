var BarcodeScanner = {
    scan: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "BarcodeScanner", "scan", []);
    }
};

module.exports = BarcodeScanner;