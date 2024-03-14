package com.example.barcodeScanner;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.util.Log;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeScanner extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("scan")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IntentIntegrator integrator = new IntentIntegrator(cordova.getActivity());
                    integrator.setPrompt("Scan a barcode");
                    integrator.setOrientationLocked(false);
                    integrator.setBeepEnabled(false);
                    integrator.initiateScan();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            if (result.getContents() != null) {
                String scannedData = result.getContents();
                this.callbackContext.success(scannedData);
            } else {
                this.callbackContext.error("Scan canceled");
            }
        }
    }
}