package com.example.myBarcodeScanner;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

// Importing necessary classes
import android.content.Intent;
import com.journeyapps.barcodescanner.CaptureActivity;
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
        // If you have a custom CaptureActivity to handle various conditions
        // integrator.setCaptureActivity(CustomScannerActivity.class); 
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0); // Use the back camera
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.addExtra("TRY_HARDER", true);
        integrator.addExtra("PRESERVE_ORIENTATION", true);
        // Enabling the following can help with blurry images, but might make scanning slower
        integrator.addExtra("ALLOWED_LENGTHS", new int[]{8, 13});
        integrator.addExtra("ASSUME_CODE_39_CHECK_DIGIT", false);
        integrator.addExtra("ASSUME_GS1", true);

        this.cordova.setActivityResultCallback(this);
        integrator.initiateScan();
    }

    @Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {
        if (result.getContents() == null) {
            callbackContext.error("Cancelled");
        } else {
            callbackContext.success(result.getContents());
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

}
