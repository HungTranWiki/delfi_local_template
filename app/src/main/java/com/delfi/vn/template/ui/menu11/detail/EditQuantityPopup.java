package com.delfi.vn.template.ui.menu11.detail;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delfi.core.controls.styledinput.basicrules.BlankRule;
import com.delfi.core.controls.styledinput.basicrules.NumberRule;
import com.delfi.core.scanner.DelfiScannerHandler;
import com.delfi.core.scanner.LaserEventArgs;
import com.delfi.core.scanner.OnScanListener;
import com.delfi.core.utils.InputQuantityEvent;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.ui.customview.SimpleEditedText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class EditQuantityPopup extends BottomSheetDialogFragment implements TextView.OnEditorActionListener {
    private View lnEditNoteBottom;
    private SimpleEditedText edtQuantityTab2;
    Button btnOk;

    public interface Listener {
        void cancel();

        void complete(int quantity, ProductMenu11 productMenu11, String barcode);

        void stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.menu11_edit_quantity_dialog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        lnEditNoteBottom = view.findViewById(R.id.lnEditNoteBottom);
        ((TextView) lnEditNoteBottom.findViewById(R.id.tvProductName)).setText(product.tenVT);

        edtQuantityTab2 = lnEditNoteBottom.findViewById(R.id.edtQuantity);
        edtQuantityTab2.addRule(new BlankRule(getString(R.string.empty_field)));
        edtQuantityTab2.addRule(new NumberRule(getString(R.string.please_input_quantity)));
        edtQuantityTab2.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtQuantityTab2.setOnEditorActionListener(this);
        InputQuantityEvent.getInstance().attach(edtQuantityTab2);

        edtQuantityTab2.setOnEditorActionListener(this);

        edtQuantityTab2.setTag(1);
        edtQuantityTab2.focus();
        edtQuantityTab2.setText("1");
        edtQuantityTab2.postDelayed(new Runnable() {
            @Override
            public void run() {
                EditQuantityPopup.this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(edtQuantityTab2.getWindowToken(), 0);
            }
        }, 100);

        ImageButton buttonCancel = lnEditNoteBottom.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> {
            listener.cancel();
            dismiss();
        });

        btnOk = lnEditNoteBottom.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> {
            handleInput(false, "");
        });

        ((TextView) view.findViewById(R.id.tvProductName)).setText(product.productId);
        ((TextView) view.findViewById(R.id.tvItemNo)).setText(product.maVT);
        ((TextView) view.findViewById(R.id.tvScannedQuantity)).setText(product.soLuongDaQuet + "");
        ((TextView) view.findViewById(R.id.tvExpectedQuantity)).setText(product.soLuongYeuCau + "");
        ((TextView) view.findViewById(R.id.tvUnit)).setText(product.unit);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(listener -> {
            FrameLayout bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
            @SuppressWarnings("rawtypes")
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return dialog;
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.stop();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            handleInput(false, "");
            return true;
        }
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();
        registerScanBarcodeListener();
    }


    private void registerScanBarcodeListener() {
        DelfiScannerHandler.getInstance(getContext()).enableScanner();
        DelfiScannerHandler.getInstance(getContext()).setDecodeResultType(0); //user msg
        DelfiScannerHandler.getInstance(getContext()).setOnScanListener(new OnScanListener() {
            @Override
            public void onRead(LaserEventArgs args) {
                if (args == null || args.Barcode == null)
                    return;
                args.Barcode = args.Barcode.replace("\n", "");
                handleInput(true, args.Barcode);
            }
        });
    }

    public void show(FragmentManager supportFragmentManager, String tag,
                     ProductMenu11 product, Listener listener) {
        super.show(supportFragmentManager, tag);
        this.listener = listener;
        this.product = product;
    }

    private void handleInput(boolean isLaserScan, String barcode) {
        if (edtQuantityTab2.hasFocus()) {
            if (isLaserScan) {
                save(barcode);
                return;
            }
            String validate = edtQuantityTab2.validate();
            if (validate != null && !validate.isEmpty()) {
                edtQuantityTab2.error(validate);
                return;
            }
            edtQuantityTab2.success();
            save("");

            return;
        }
    }

    private void save(String barcode) {
        try {
            if (barcode != null && !barcode.isEmpty()) {
                listener.complete(1, product, barcode);
            } else {
                listener.complete(Integer.parseInt(edtQuantityTab2.getText().toString()), product, barcode);
            }
        } catch (Exception ex) {
            edtQuantityTab2.error(getString(R.string.input_again));
            edtQuantityTab2.focus();
            edtQuantityTab2.selectAll();
        }
    }

    private ProductMenu11 product;
    private Listener listener;
}
