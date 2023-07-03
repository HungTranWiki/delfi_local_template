package com.delfi.vn.template.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.delfi.core.common.SoundManager;
import com.delfi.core.controls.DialogCreator;
import com.delfi.core.controls.styledinput.SimpleStyledAutoCompleteView;
import com.delfi.core.controls.styledinput.SimpleStyledEditText;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.services.api.NetworkStatusReceiver;
import com.delfi.vn.template.ui.customview.SimpleEditedText;
import com.delfi.vn.template.ui.login.LoginActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.CommonUtil;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@SuppressWarnings("rawtypes")
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements NetworkStatusReceiver.ConnectivityReceiverListener, BaseView {
    @Inject
    SchedulerListener schedulerListener;

    private NetworkStatusReceiver mNetworkReceiver = new NetworkStatusReceiver();

    private P mPresenter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ArrayList<EditText> editableViews = new ArrayList<>();

    private ProgressDialog progressDialog;

    protected boolean isKeyboardShow = false;

    private boolean isShowingNetworkError;

    protected AppDialog appDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        registerNetworkCheckReceiver();
        this.mPresenter = getPresenter();
        progressDialog = new ProgressDialog(this);
        setContentView(getLayoutId());
        initViews();
    }

    @Override
    public void showErrorMessage(AppException exception) {
        createAppDialog();
        switch (exception.getErrorCode()) {
            case GET_TON_VT_LIST_ERROR:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.ton_kho_error_title, R.string.ton_kho_error_message, null);
                break;
            case GET_TON_VT_LIST_EMPTY:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.ton_kho_empty_error_title, R.string.ton_kho_empty_error_message, null);
                break;
            case GET_RECEIPT_PO_1_ERROR:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.product_received_error_title, R.string.product_received_error_message, null);
                break;
            case BARCODE_NOT_FOUND:
                SoundManager.getInstance().PlayError(this);
                appDialog.showErrorDialog(R.drawable.ic_barcode_error_circle_grey,
                        R.string.barcode_error_title, R.string.barcode_error_msg, null);
                break;
            case ERROR_GET_ALL_OUT_OF_STOCK:
                appDialog.showErrorDialog(
                        R.drawable.ic_over_quantity, R.string.enough_quantity_error_title,
                        R.string.enough_quantity_error_message, null);
                break;
            case ERROR_PICK_MORE_ITEM:
                appDialog.showErrorDialog(
                        R.drawable.ic_over_quantity, R.string.enough_quantity_error_title,
                        R.string.need_enough_quantity_error_message, null);
                break;
            case SAVE_MENU_21_ERROR:
                appDialog.showErrorDialog(
                        R.drawable.ic_warning_orange_circle_grey, R.string.saved_error_title,
                        R.string.saved_error_message, null);
                break;
            case UPDATE_STATUS_PROCESSIONG:
                appDialog.showErrorDialog(
                        R.drawable.ic_warning_orange_circle_grey, R.string.update_processing_error_title,
                        R.string.update_processing_error_message, null);
                break;
            case NO_INTERNET_CONNECTION:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.no_internet_title, R.string.no_internet_message, new DialogCreator.IOKListener() {
                            @Override
                            public void onOk() {
                                isShowingNetworkError = false;
                            }
                        });
                break;
            case UNAUTHORIZED:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.token_expired_title, R.string.token_expired_message, () -> {
                            Intent intent = LoginActivity.initActivity(this);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finishAffinity();
                        });
                break;
            case ERROR_INTERNAL_SERVER:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.internal_error_title, R.string.internal_error_message, this::finish);
                break;
            default:
                clearAppDialog();
        }
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initViews();

    public abstract P getPresenter();

    public void addEditableView(EditText editText) {
        editableViews.add(editText);
    }

    public void clearEditableView() {
        if(getCurrentFocus() instanceof SimpleStyledEditText){
            ((SimpleStyledEditText) getCurrentFocus()).success();
        }
        editableViews.clear();
    }

    @org.jetbrains.annotations.Nullable
    private EditText findViewByTag(Object tabIndex) {
        for (int i = 0; i < editableViews.size(); i++) {
            if (editableViews.get(i).getTag().equals(tabIndex))
                return checkViewVisibility(editableViews.get(i)) ? editableViews.get(i) : findViewByTag((int) editableViews.get(i).getTag() - 1);
        }
        return null;
    }

    private boolean checkViewVisibility(@NotNull View view) {
        return view.getVisibility() == View.VISIBLE && view.isShown();
    }

    protected void setEditTextEnable(EditText view, boolean editable) {
        if (editable) {
            view.setEnabled(true);
            view.setFocusable(true);

            Completable.complete().delay(100, TimeUnit.MILLISECONDS)
                    .observeOn(schedulerListener.ui())
                    .subscribeOn(schedulerListener.io())
                    .doOnComplete(() -> {
                        view.requestFocus();
                        view.selectAll();
                    })
                    .subscribe();
        } else {
            view.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (editableViews.size() > 0) {
                EditText v;
                try {
                    v = getCurrentFocus() != null ? (EditText) getCurrentFocus() : editableViews.get(editableViews.size() - 1);
                } catch (Exception ex) {
                    v = editableViews.get(editableViews.size() - 1);
                }
                if (v.getText().length() > 0) {
                    v.getText().clear();
                    v.requestFocus();
                    return;
                }
                int tabIndex = (Integer) v.getTag(); // get tag as tab index
                tabIndex = tabIndex - 1;
                EditText prevEdit = findViewByTag(tabIndex);
                if (prevEdit != null) {
                    if (prevEdit instanceof SimpleEditedText) {
                        ((SimpleEditedText) prevEdit).focus();
                    }else if (prevEdit instanceof SimpleStyledAutoCompleteView) {
                        ((SimpleStyledAutoCompleteView) prevEdit).focus();
                    } else {
                        setEditTextEnable(prevEdit, true);
                        prevEdit.requestFocus();
                        prevEdit.selectAll();
                    }
                    if (v instanceof SimpleEditedText)
                        ((SimpleEditedText) v).success();
                    else  if (v instanceof SimpleStyledAutoCompleteView)
                        ((SimpleStyledAutoCompleteView) v).success();
                    else
                        setEditTextEnable(v, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.clearDispose();
        clearDispose();
        unregisterReceiver(mNetworkReceiver);
    }

    private void registerNetworkCheckReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    public void finish() {
        CommonUtil.hideKeyboard(getCurrentFocus(), this);
        Completable.complete().delay(100, TimeUnit.MILLISECONDS)
                .observeOn(schedulerListener.ui())
                .subscribeOn(schedulerListener.io())
                .doOnComplete(super::finish)
                .subscribe();
    }

    public void addToDispose(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void clearDispose() {
        compositeDisposable.dispose();
    }


    protected void showSoftKeyboard(final View v) {
        View view = null;
        if (v instanceof EditText && v.isEnabled())
            view = v;
        else {
            for (EditText edt : editableViews)
                if (edt.isEnabled())
                    view = edt;
        }
        if (view != null) {
            View finalView = view;
            finalView.postDelayed(() -> {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(finalView, 0);
            }, 100);
        }

    }

    protected void hideSoftKeyboard(final View v) {
        if (v != null) {
            v.postDelayed(() -> {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }, 100);
        }
    }

    protected void selectAllEditText(EditText editText) {
        editText.postDelayed(() -> {
            editText.setSelectAllOnFocus(true);
            editText.clearFocus();
            editText.requestFocus();
            editText.selectAll();
        }, 250);
    }

    protected void setFocus(EditText editText) {
        editText.setSelectAllOnFocus(true);
        editText.clearFocus();
        editText.requestFocus();
    }

    protected void stepMoveInput(EditText curr, EditText move) {
        curr.setEnabled(false);
        move.setEnabled(true);
        move.requestFocus();
    }

    protected void detectInput(final EditText editText, final int length, final boolean isNumber) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (isKeyboardShow) {
                    if (isNumber) {
                        if (s.length() > 0) {
                            if (s.substring(0, 1).equals("-") && s.length() > length + 1) {
                                editText.setText(s.substring(0, length + 1));
                            }
                            if (!s.substring(0, 1).equals("-") && s.length() > length) {
                                editText.setText(s.substring(0, length));
                            }
                            /**
                             * detect numberDecimal with . and -
                             */
                            if (s.length() >= 2 && s.indexOf(".") == 0) {
                                editText.setText("0" + s);
                            }
                            if (isContains(s, '.') || isContains(s, '-')) {
                                editText.setText(s.substring(0, s.length() - 1));
                            }
                            if (s.length() > 1 && s.indexOf("-") != -1 && s.indexOf("-") != 0)
                                editText.setText(s.substring(0, s.length() - 1));
                            if (s.indexOf("-") != -1 && s.indexOf("-") == 0 && s.indexOf(".") != -1 && s.indexOf(".") == 1)
                                editText.setText(s.substring(0, s.length() - 1));
                            editText.setSelection(editText.getText().toString().length());
                        }
                    } else {
                        if (s.length() > length) {
                            editText.setText(s.substring(0, length));
                            editText.setSelection(editText.getText().toString().length());
                        }
                    }
                }
                if (editText.getId() != R.id.edtQuantity)
                    editText.setBackgroundResource(R.drawable.drawable_edittext_common);
                else
                    editText.setBackgroundResource(R.color.white);
            }

            private boolean isContains(String value, Character ch) {
                int count = 0;
                for (int i = 0; i < value.length(); i++)
                    if (value.charAt(i) == ch)
                        count++;
                if (count > 1)
                    return true;
                else
                    return false;
            }
        });
    }


    protected void detectInput(final EditText editText, final int length, final boolean isNumber, TextView errorView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (errorView != null) {
                    errorView.setVisibility(View.GONE);
                    editText.setBackgroundResource(R.drawable.drawable_edittext_common);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (isKeyboardShow) {
                    if (s.length() > length) {
                        editText.setText(s.substring(0, length));
                        editText.setSelection(editText.getText().toString().length());
                    }
                }

            }
        });
    }

    protected void detectInput(final EditText editText, final int length, TextView errorView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (errorView != null) {
                    errorView.setVisibility(View.GONE);
                    editText.setBackgroundResource(R.drawable.drawable_edittext_common);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (isKeyboardShow) {
                    if (s.length() > length) {
                        editText.setText(s.substring(0, length));
                        editText.setSelection(editText.getText().toString().length());
                    }
                }

            }
        });
    }

    @Override
    public void onNetworkConnectionChanged(boolean isDataNetworkAvailable) {
        if (!isDataNetworkAvailable && !isShowingNetworkError) {
            isShowingNetworkError = true;
            showErrorMessage(new AppException(ErrorCode.NO_INTERNET_CONNECTION));
        }
    }

    public void showLoadingDialog() {
        if (progressDialog.isShowing()) {
            return;
        }
        hideSoftKeyboard(getCurrentFocus());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void showMessage(int resId) {
    }

    @Override
    public void showError(int errCode) {
        if (Constants.HTTP_STATUS_UNAUTHORIZED == errCode) {
            showErrorMessage(new AppException(ErrorCode.UNAUTHORIZED));
        } else if (Constants.HTTP_STATUS_INTERNAL_SERVER_ERROR == errCode) {
            showErrorMessage(new AppException(ErrorCode.ERROR_INTERNAL_SERVER));
        }
    }

    protected AppDialog createAppDialog() {
        clearAppDialog();
        appDialog = new AppDialog(this);
        return appDialog;
    }

    protected void clearAppDialog(){
        if (appDialog != null ){
            if( appDialog.isShowing()){
                appDialog.dismiss();
            }
            appDialog = null;
        }
    }
}