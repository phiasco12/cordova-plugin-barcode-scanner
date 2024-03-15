var exec = require('cordova/exec');

exports.scanBarcode = function(success, error) {
    exec(success, error, "MyBarcodeScanner", "scanBarcode", []);
};

