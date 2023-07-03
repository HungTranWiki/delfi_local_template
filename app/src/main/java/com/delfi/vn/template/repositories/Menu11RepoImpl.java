package com.delfi.vn.template.repositories;

import android.content.Context;
import com.delfi.vn.template.mappers.PostMenu11Mapper;
import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.models.servermodels.PostMenu11Request;
import com.delfi.vn.template.models.servermodels.PostMenu11Request.PHRequest;
import com.delfi.vn.template.services.api.ApiRepositoryImpl;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class Menu11RepoImpl implements Menu11Repo {
    private ApiRepositoryImpl apiRepositoryImpl;
    private Menu11LocalRepo menu11LocalRepo;
    private Context context;

    @Inject
    public Menu11RepoImpl(Context context, ApiRepositoryImpl apiRepositoryImpl,
                          Menu11LocalRepo menu11LocalRepo) {
        this.context = context;
        this.apiRepositoryImpl = apiRepositoryImpl;
        this.menu11LocalRepo = menu11LocalRepo;
    }

    @Override
    public Observable<List<ProductMenu11>> getProductList(String soCT) {
        return menu11LocalRepo.getProductList(soCT);
    }


    @Override
    public Observable<Boolean> postMenu11(String soCt) {
        return menu11LocalRepo.getProductListGroupByMaVT(soCt)
                .map(new Function<List<SavedMenu11>, PostMenu11Request>() {
                    @Override
                    public PostMenu11Request apply(@NotNull List<SavedMenu11> ctList) throws Exception {
                        if (ctList.size() == 0)
                            throw new AppException(ErrorCode.SYNC_MENU_11, 1, "get the saved data is empty");
                        SavedMenu11 savedMenu11 = ctList.get(0);
                        List<PostMenu11Request.CTRequest> phListRequest = (new PostMenu11Mapper()).mapList(ctList);
                        PostMenu11Request request = new PostMenu11Request();
                        request.data = new PHRequest();
                        request.data.line_items = phListRequest;
                        request.data.order_id = savedMenu11.soCT;
                        request.data.from_warehouse = savedMenu11.fromWH;
                        request.data.to_warehouse = savedMenu11.toWH;
                        request.data.created_date = savedMenu11.createDate;
                        request.data.ship_date = savedMenu11.shipDate;
                        request.data.received_date = savedMenu11.receiveDate;
                        return request;
                    }
                })
                .flatMap(new Function<PostMenu11Request, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(@NotNull PostMenu11Request request) throws Exception {
                        return apiRepositoryImpl.postMenu11(request);
                    }
                })
                .flatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(@NotNull Boolean isSuccess) throws Exception {
                        if (!isSuccess) {
                            AppException exception = new AppException(ErrorCode.SYNC_MENU_11, 1, "postMenu11 error");
                            exception.writeAppLog(Menu11RepoImpl.this.getClass().getName());
                            throw exception;
                        }
                        return menu11LocalRepo.delete(soCt)
                                .map(new Function<Long, Boolean>() {
                                    @Override
                                    public Boolean apply(@NotNull Long result) throws Exception {
                                        return result > 0;
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<Boolean> save(SavedMenu11 ct) {
        return menu11LocalRepo.save(ct);
    }

    @Override
    public Observable<Integer> counterSaved(String soCt) {
        return menu11LocalRepo.count(soCt);
    }

    @Override
    public Observable<Integer> counterSaved() {
        return menu11LocalRepo.count();
    }

    @Override
    public Observable<Float> sumSaved(String soCT, String maVT) {
        return menu11LocalRepo.sumSaved(soCT, maVT);
    }

    @Override
    public Observable<Boolean> delete(String soCt) {
        return menu11LocalRepo.delete(soCt)
                .map(result -> result > 0);
    }

    @Override
    public Observable<Boolean> delete() {
        return menu11LocalRepo.delete()
                .map(new Function<Long, Boolean>() {
                    @Override
                    public Boolean apply(@NotNull Long result) throws Exception {
                        return result > 0;
                    }
                });
    }

    @Override
    public Observable<List<SavedMenu11>> exportOutput() {
        return menu11LocalRepo.getSaved();
    }

    @Override
    public Observable<Integer> prepareMasterData() {
        List<Warehouse> records = readItems();
        if (records.isEmpty())
            return Observable.just(0);
        return Observable.just(records.size());
//        return delete()
//                .flatMap(result -> {
//                    Log.e("Warehouse.size", records.size() + "");
//                    return addNewData(records)
//                            .map(inserted -> {
//                                if (inserted) {
//                                    File f = new File(Constants.IMPORT_FILE_PATH);
//                                    f.delete();
//                                    return records.size();
//                                }
//                                return 0;
//                            });
//                });
    }

    private List<Warehouse> readItems() {
        List<Warehouse> items = new ArrayList<>();
        File f = new File(Constants.IMPORT_FILE_PATH);
        if (!f.exists()) {
            return items;
        }
        String[] arr;
        try {
            arr = FileUtil.getInstance(context).read(f);
            for (String s : arr) {
                Warehouse model = new Warehouse();
                model = model.buildItemFromFile(s);
                if (model != null)
                    items.add(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

}
