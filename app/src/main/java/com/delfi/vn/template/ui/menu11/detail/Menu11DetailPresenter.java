package com.delfi.vn.template.ui.menu11.detail;

import android.annotation.SuppressLint;

import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.Menu11Repo;
import com.delfi.vn.template.repositories.Receipt11Repo;
import com.delfi.vn.template.ui.base.screen.BaseFeaturePresenter;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Menu11DetailPresenter extends BaseFeaturePresenter<Menu11DetailContract.View>
        implements Menu11DetailContract.Presenter {
    private Receipt11Repo receipt11Repo;
    private Menu11Repo menu11Repo;

    protected Warehouse selectedWarehouse;
    public Receipt11 receipt;
    private ProductMenu11 selectedProduct;
    protected String barcode;

    private List<ProductMenu11> receiptList;

    private Menu11DetailContract.View view;

    @Inject
    public Menu11DetailPresenter(
            Menu11DetailContract.View view,
            SchedulerListener schedulerListener,
            Menu11Repo menu11Repo,
            Receipt11Repo receipt11Repo,
            AppRepository appRepo) {
        super(view, schedulerListener, appRepo);
        this.menu11Repo = menu11Repo;
        this.receipt11Repo = receipt11Repo;
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void receiptData(Warehouse fromDate, Receipt11 receiptPO1) {
        this.selectedWarehouse = fromDate;
        this.receipt = receiptPO1;
    }


    @SuppressLint("CheckResult")
    @Override
    public void onCheckLocalDataForSync() {
        if (receipt == null)
            view.canNotSyncedData();
        menu11Repo.counterSaved(receipt.soCT)
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(this::addToDispose)
                .subscribe(
                        new Consumer<Integer>() {
                            @Override
                            public void accept(Integer counter) throws Exception {
                                if (counter > 0)
                                    view.canSyncedData();
                                else
                                    view.canNotSyncedData();
                            }
                        },
                        throwable -> {
                            view.canNotSyncedData();
                        }
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void initDataToStartScreen() {
        receipt11Repo.getDetail(receipt.soCT)
                .flatMap(result -> menu11Repo.getProductList(receipt.soCT))
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(response -> {
                            this.receiptList = response;
                            view.showProductList(receipt.soCT, receiptList);
                        },
                        throwable -> {
                            boolean result = handleException(throwable);
                            if (!result)
                                if (throwable instanceof AppException) {
                                    view.showErrorMessage((AppException) throwable);
                                } else {
                                    view.showErrorMessage(new AppException(ErrorCode.GET_RECEIPT_PO_1_ERROR));
                                }
                        }
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void refresh() {
        menu11Repo.getProductList(receipt.soCT)
                .flatMap(new Function<List<ProductMenu11>, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(@NotNull List<ProductMenu11> list) throws Exception {
                        Menu11DetailPresenter.this.receiptList = list;
                        return menu11Repo.counterSaved(receipt.soCT);
                    }
                })
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(new Consumer<Integer>() {
                               @Override
                               public void accept(Integer counter) throws Exception {
                                   view.showProductList(receipt.soCT, receiptList);
                                   if (counter > 0)
                                       view.canSyncedData();
                                   else
                                       view.canNotSyncedData();
//                            if(!barcode.isEmpty()){
//                                view.scanBarcode(barcode);
//                            }


                               }
                           },
                        throwable -> {
                            boolean result = handleException(throwable);
                            if (!result)
                                if (throwable instanceof AppException) {
                                    view.showErrorMessage((AppException) throwable);
                                } else {
                                    view.showErrorMessage(new AppException(ErrorCode.GET_RECEIPT_PO_1_ERROR));
                                }
                        }
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void setSelectedProduct(int position, ProductMenu11 product) {
        menu11Repo.sumSaved(product.soCT, product.maVT)
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(response -> {
                            this.selectedProduct = product;
                            this.selectedProduct.soLuongDaQuet = response;
                            view.showEditQuantity(position, this.selectedProduct);
                        },
                        throwable -> {
                            this.selectedProduct = product;
                            view.showEditQuantity(position, this.selectedProduct);
                        }
                );
    }

    @Override
    public ProductMenu11 getSelectedProduct() {
        return this.selectedProduct;
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveQuantity(int position, float soLuong, ProductMenu11 selectedProduct, String barcode) {
        menu11Repo.sumSaved(selectedProduct.soCT, selectedProduct.maVT)
                .map(new Function<Float, SavedMenu11>() {
                    @Override
                    public SavedMenu11 apply(@NotNull Float result) throws Exception {
                        if ((result + soLuong) <= selectedProduct.soLuongYeuCau) {
                            SavedMenu11 item = new SavedMenu11();
                            item.soCT = selectedProduct.soCT;
                            item.maVT = selectedProduct.maVT;
                            item.productId = selectedProduct.productId;
                            item.missingQty = selectedProduct.missingQty;
                            item.barcode = selectedProduct.barcode;
                            item.configId = selectedProduct.configId;
                            item.sizeId = selectedProduct.sizeId;
                            item.colorId = selectedProduct.colorId;
                            item.styleId = selectedProduct.styleId;
                            item.unit = selectedProduct.unit;
                            item.note = selectedProduct.note;
                            item.status = selectedProduct.status;
                            item.tenVT = selectedProduct.tenVT;
                            item.soLuongYeuCau = selectedProduct.soLuongYeuCau;
                            item.fromWH = receipt.fromWH;
                            item.toWH = receipt.toWH;
                            item.createDate = receipt.createDate;
                            item.shipDate = receipt.shipDate;
                            item.receiveDate = receipt.receiveDate;
                            item.soLuongDaQuet = soLuong;
                            return item;
                        }
                        throw new AppException(ErrorCode.ERROR_GET_ALL_OUT_OF_STOCK);

                    }
                })
                .flatMap(new Function<SavedMenu11, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(@NotNull SavedMenu11 item) throws Exception {
                        return menu11Repo.save(item);
                    }
                })
                .flatMap(new Function<Boolean, ObservableSource<? extends Float>>() {
                    @Override
                    public ObservableSource<? extends Float> apply(@NotNull Boolean item) throws Exception {
                        return menu11Repo.sumSaved(selectedProduct.soCT, selectedProduct.maVT);
                    }
                })
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(new Consumer<Float>() {
                               @Override
                               public void accept(Float response) throws Exception {
                                   Menu11DetailPresenter.this.selectedProduct.soLuongDaQuet = response;
                                   view.savedDataSuccess(position, barcode, Menu11DetailPresenter.this.selectedProduct);
                                   Menu11DetailPresenter.this.selectedProduct = null;
                               }
                           },
                        throwable -> {
                            boolean result = handleException(throwable);
                            if (!result)
                                if (throwable instanceof AppException) {
                                    view.showErrorMessage((AppException) throwable);
                                } else {
                                    view.showErrorMessage(new AppException(ErrorCode.SAVE_MENU_11_ERROR));
                                }

                        }
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSyncData() {
        menu11Repo.postMenu11(receipt.soCT)
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(__ -> view.showLoadingDialog())
                .doOnTerminate(() -> view.hideLoadingDialog())
                .doOnSubscribe(this::addToDispose)
                .subscribe(
                        new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean result) throws Exception {
                                if (result) view.onSyncDataSuccess();
                                else view.onSyncDataError();
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                boolean result = Menu11DetailPresenter.this.handleException(throwable);
                                if (!result)
                                    view.onSyncDataError();
                            }
                        }
                );
    }

}
