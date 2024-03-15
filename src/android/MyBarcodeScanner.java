package com.example.mybarcodescanner;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MyBarcodeScanner extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("scanBarcode".equals(action)) {
            this.scanBarcode(callbackContext);
            return true;
        }
        return false;
    }

    private void scanBarcode(CallbackContext callbackContext) {
        IntentIntegrator integrator = new IntentIntegrator(cordova.getActivity());
        integrator.setOrientationLocked(false); // Allow scanning in any orientation
        integrator.setBeepEnabled(false); // Optional beep on scan
        integrator.setBarcodeImageEnabled(true); // Capture barcode image
        // Start scan
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String result = scanResult.getContents();
            this.callbackContext.success(result);
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
