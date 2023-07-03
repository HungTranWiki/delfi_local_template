package com.delfi.vn.template.ui.main;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.delfi.core.log.LogEventArgs;
import com.delfi.core.log.LogLevel;
import com.delfi.core.log.Logger;
import com.delfi.vn.template.MyApplication;
import com.delfi.vn.template.models.appmodels.MainMenu;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.GeneralRepo;
import com.delfi.vn.template.repositories.MainMenuRepository;
import com.delfi.vn.template.repositories.Menu11Repo;
import com.delfi.vn.template.repositories.UserRepository;
import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.FileUtil;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    private SchedulerListener schedulerListener;
    private MainContract.View view;
    private MainMenuRepository mainMenuRepository;
    private UserRepository userRepository;
    private Menu11Repo menu11Repo;
    private AppRepository appRepository;
    private GeneralRepo generalRepo;
    private String oldURLConfig = "";

    @Inject
    public MainPresenter(MainContract.View view, SchedulerListener schedulerListener,
                         MainMenuRepository mainMenuRepository,
                         UserRepository userRepository,
                         AppRepository appRepository,
                         Menu11Repo menu11Repo,
                         GeneralRepo generalRepo

    ) {
        super(view);
        this.view = view;
        this.schedulerListener = schedulerListener;
        this.mainMenuRepository = mainMenuRepository;
        this.userRepository = userRepository;
        this.appRepository = appRepository;
        this.menu11Repo = menu11Repo;
        this.generalRepo = generalRepo;

    }


    @Override
    public boolean isActivation() {
        return appRepository.getActivation();
    }


    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void showMenuList(boolean isManager) {
        showManagerMenu();
    }

    @SuppressLint("CheckResult")
    @Override
    public void logOut() {
        //noinspection ResultOfMethodCallIgnored
        userRepository.logout()
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable __) throws Exception {
                        view.showLoadingDialog();
                    }
                })
                .doFinally(view::hideLoadingDialog)
                .doOnSubscribe(this::addToDispose)
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.logoutSuccess();
                    }
                });
    }

    @Override
    public void checkURLConfigIsChanged() {
        if (!appRepository.getURLOdoo().equals(oldURLConfig)) {
            view.forceLogout();
        }
    }

    @Override
    public void setURLConfigBeforeUpdate() {
        oldURLConfig = appRepository.getURLOdoo();
    }


    @SuppressLint("CheckResult")
    @Override
    public void syncData() {
        generalRepo.getProductListFromSoanHang()
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(__ -> view.showLoadingDialog())
                .doFinally(view::hideLoadingDialog)
                .doOnSubscribe(this::addToDispose)
                .subscribe(result -> {
                    if (result)
                        view.syncDataSuccess();
                    else view.syncDataError("Sync Product Error");
                }, throwable -> {
                    view.syncDataError(throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void showManagerMenu() {
        Observable.zip(
                mainMenuRepository.getManagerMainMenuList(),
                menu11Repo.counterSaved(),
                new BiFunction<List<MainMenu>, Integer, List<MainMenu>>() {
                    @NotNull
                    @Override
                    public List<MainMenu> apply(@NotNull List<MainMenu> mainMenus, @NotNull Integer countMenu11) throws Exception {
                        if (mainMenus != null && mainMenus.size() > 0) {
                            for (MainMenu menu : mainMenus) {
                                switch (menu.getIdInServer()) {
                                    case MENU_11:
                                        menu.setCountDocument(countMenu11);
                                        break;
                                }
                            }
                        }
                        return mainMenus;
                    }
                })
                .subscribeOn(this.schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        MainPresenter.this.addToDispose(disposable);
                    }
                })
                .subscribe(response -> view.onRefreshMenuList(response),
                        Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    @Override
    public void exportDB() {
        menu11Repo.exportOutput()
                .map(new Function<List<SavedMenu11>, Integer>() {
                    @Override
                    public Integer apply(@NotNull List<SavedMenu11> items) throws Exception {
                        if (items.isEmpty())
                            return 0;
                        File fileNewItem = new File(Constants.EXPORT_SCAN_DATA_FILE);
                        try {
                            FileUtil.getInstance(MyApplication.getContext()).clear(fileNewItem);
                            for (SavedMenu11 item : items)
                                FileUtil.getInstance(MyApplication.getContext())
                                        .write(fileNewItem, item.getRecordStringOutputFile(MyApplication.getContext()), FileUtil.MODE.APPEND);
                            return items.size();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Logger.getInstance().logMessage(new LogEventArgs(LogLevel.ERROR, e.getMessage(), e));
                            throw new AppException(ErrorCode.ERROR_GET_SAVED_NHAP_KHO);
                        }
                    }
                })
                .subscribeOn(this.schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        MainPresenter.this.addToDispose(disposable);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer response) throws Exception {
                        if (response >= 0) {
                            view.goToSyncCommunicationAfterExported();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showErrorMessage(new AppException(ErrorCode.ERROR_GET_SAVED_NHAP_KHO));
                    }
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void importDB() {
        menu11Repo.prepareMasterData()
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        MainPresenter.this.addToDispose(disposable);
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable __) throws Exception {
                        view.showLoadingDialog();
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.hideLoadingDialog();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                               @Override
                               public void accept(Integer dataImportCounter) throws Exception {
                                   Log.d("SYNC_DATA", "Items synced: " + dataImportCounter);
                                   view.saveDataSuccess();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                view.showErrorMessage(new AppException(ErrorCode.NO_DATA_IMPORT));
//                            boolean result = handleException(throwable);
//                            if (!result) {
//                                if (throwable instanceof AppException)
//                                    view.showErrorMessage(new AppException(ErrorCode.NO_DATA_IMPORT));
//                            } else {
//                                view.showErrorMessage(new AppException(ErrorCode.NO_DATA_IMPORT));
//                            }
                            }
                        });
    }

    @SuppressLint("CheckResult")
    public void cleanUpData() {
        File qcFile = new File(Constants.EXPORT_SCAN_DATA_FILE);
        if (!qcFile.exists()) {
            menu11Repo.delete()
                    .subscribeOn(schedulerListener.io())
                    .doOnSubscribe(this::addToDispose)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean result) throws Exception {
                            Log.d("SYNC_DATA", "Output items deleted count: " + result);
                        }
                        }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Logger.e("Failed to delete data after synced", (Exception) throwable);
                                }
                    });
        }
    }
}
