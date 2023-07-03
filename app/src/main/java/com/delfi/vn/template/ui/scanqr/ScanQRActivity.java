package com.delfi.vn.template.ui.scanqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delfi.core.zxing.ui.DelfiScannerView;
import com.delfi.vn.template.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class ScanQRActivity extends AppCompatActivity implements DelfiScannerView.ResultHandler {
    private DelfiScannerView mScannerView;
    private ViewGroup cameraFrameView;
    private ProgressDialog progressDialog;

    public ScanQRActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.getWindow().setFlags(1024, 1024);
        this.setContentView(R.layout.activity_scanner);
        progressDialog = new ProgressDialog(this);
        this.cameraFrameView = (ViewGroup) this.findViewById(R.id.qr_scanner_frame);
        this.mScannerView = new DelfiScannerView(this);
        addBarcodeFormats();
        this.mScannerView.setAutoFocus(true);
        this.cameraFrameView.addView(this.mScannerView);
        ((TextView) findViewById(R.id.tvMainTitle)).setText(R.string.scan_barcode);
        findViewById(R.id.icBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void requestPermission() {
        int hasWriteCameraPermission = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        if (hasWriteCameraPermission != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 1);
        }

    }

    public void login(String key) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("BARCODE_RESULT", key);
        this.setResult(-1, resultIntent);
        this.finish();
    }

    public void onResume() {
        super.onResume();
        showLoadingDialog();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPermission();
                mScannerView.setResultHandler(ScanQRActivity.this);
                mScannerView.startCamera();
                mScannerView.setFlash(false);
                hideLoadingDialog();
            }
        }, 500);

    }

    //
    public void onPause() {
        super.onPause();
        this.mScannerView.stopCamera();
        hideLoadingDialog();
    }

    public void handleResult(Result rawResult) {
        String barcode = rawResult.getText();
        this.login(barcode);
    }


    public void showLoadingDialog() {
        if (progressDialog.isShowing()) {
            return;
        }
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void addBarcodeFormats() {
        this.mScannerView.ALL_FORMATS.clear();
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.AZTEC);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.CODABAR);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.CODE_39);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.CODE_93);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.CODE_128);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.EAN_8);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.EAN_13);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.ITF);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.MAXICODE);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.PDF_417);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.QR_CODE);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.RSS_14);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.RSS_EXPANDED);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.UPC_A);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.UPC_E);
        this.mScannerView.ALL_FORMATS.add(BarcodeFormat.UPC_EAN_EXTENSION);
    }

}
