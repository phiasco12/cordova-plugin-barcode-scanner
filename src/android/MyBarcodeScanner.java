package com.example.myBarcodeScanner;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MyBarcodeScanner extends CordovaPlugin {
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        
        if ("scanBarcode".equals(action)) {
            this.scanBarcode();
            return true;
        }
        return false;
    }

    private void scanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this.cordova.getActivity());
        integrator.setOrientationLocked(false); // Scans in all orientations
        integrator.setBeepEnabled(true); // Beep after scanning
        integrator.setBarcodeImageEnabled(true); // Capture image of barcode
        integrator.initiateScan(); // Start scanning
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                this.callbackContext.error("Cancelled");
            } else {
                this.callbackContext.success(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
