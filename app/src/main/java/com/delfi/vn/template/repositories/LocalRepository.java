package com.delfi.vn.template.repositories;

import com.delfi.core.controls.editableview.interfaces.BaseItem;
import com.delfi.core.sqlite.DbHelper;
import com.delfi.core.sqlite.QueryOption;
import com.delfi.core.sqlite.SQLiteBatchInsert;
import com.delfi.core.sqlite.Value;
import com.delfi.vn.template.MyApplication;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.utils.AppException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

@SuppressWarnings({"rawtypes", "unchecked"})
public class LocalRepository {
    private DbHelper dbHelper;

    @Inject
    public LocalRepository(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private <T> Observable<T> callObservable(final Callable<T> func) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(func.call());
                emitter.onComplete();
            } catch (Exception ex) {
                emitter.onError(ex);
            }
        });
    }

    private <T> Completable callCompletable(final Callable<T> func) {
        return Completable.create(emitter -> {
            try {
                func.call();
                emitter.onComplete();
            } catch (Exception ex) {
                emitter.onError(ex);
            }
        });
    }

    public Observable<Long> insertBase(BaseItem item) {
        return callObservable(() -> {
            long value = 0;
            try {
                value = dbHelper.insert(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        });
    }

    public <T> Observable<Long> update(Class classOfT, QueryOption queryOption, Value values){
        return callObservable(() -> {
            long value = 0;
            try {
                value = dbHelper.update(classOfT, queryOption, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        });
    }

    public Observable<Long> updateBase(BaseItem item) {
        return callObservable(() -> {
            long value = 0;
            try {
                value = dbHelper.update(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        });
    }


    public <T> Observable<List<T>> getListByQueryOption(Class classOfT, QueryOption queryOption) {
        return callObservable(() -> {
            List<T> receiptList = new ArrayList<>();
            try {
                receiptList = dbHelper.getListAsObject(classOfT, queryOption);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiptList;
        });
    }

    public <T> Observable<T>  getItem(Class classOfT, QueryOption queryOption) {
        return callObservable(() -> {
            try {
                T object = (T) dbHelper.getObject(classOfT, queryOption);
                if (object != null) {
                    return object;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new AppException(ErrorCode.DB_RETURN_NULL);
        });
    }


    public Observable<Long> updateInventory(BaseItem inventory) {
        return callObservable(() -> {
            long value = 0;
            try {
                value = dbHelper.update(inventory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        });
    }

    public Observable<Float> getSumOfQuantity(Class classOfT, String columnName, QueryOption queryOption) {
        return callObservable(() -> {
            float result = 0;
            try {
                result = dbHelper.getSum(classOfT, columnName, queryOption);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    public Observable<Integer> countAllItems(Class classOfT) {
        return callObservable(() -> {
            int count = 0;
            try {
                count = dbHelper.getCount(classOfT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count;
        });
    }

    public <T> Observable<Integer> count(Class classOfT, QueryOption queryOption) {
        return callObservable(() -> {
            int count = 0;
            try {
                List<T> result = new ArrayList<>();
                result = dbHelper.getListAsObject(classOfT, queryOption);
                if (result != null && !result.isEmpty()) {
                    return result.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public Observable<Long> deleteItems(Class classOfT, QueryOption queryOption) {
        return callObservable(() -> {
            long value = 0;
            try {
                value = dbHelper.delete(classOfT, queryOption);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        });
    }

    public Observable<Long> deleteItemById(Class classOfT, int id) {
        return callObservable(() -> {
            long value = 0;
            try {
                value = dbHelper.delete(classOfT, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        });
    }

    public <T> Observable<Boolean> insertItemList(List<T> list, Class classOfT, int maxRecords){
        return callObservable(() ->  {
            SQLiteBatchInsert batchInsert = new SQLiteBatchInsert(MyApplication.getContext(), classOfT);
            batchInsert.setMaxRecords(maxRecords); // Maximum size 999, ex: 10 columns x 90 = 900 (OK)
            batchInsert.execute(list);
            return true;
        });
    }

    public <T> Observable<List<T>> getListByQueryOption(Class classOfT, String query) {
        return callObservable(() -> {
            List<T> receiptList = new ArrayList<>();
            try {
                receiptList = dbHelper.getListAsObject(classOfT, query);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return receiptList;
        });
    }
}

