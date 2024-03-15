var exec = require('cordova/exec');

var myBarcodeScanner = {
    scanBarcode: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'MyBarcodeScanner', 'scanBarcode', []);
    }
};

module.exports = myBarcodeScanner;
