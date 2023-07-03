package com.delfi.vn.template.repositories;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.MainMenu;
import com.delfi.vn.template.ui.menu11.input.Menu11InputActivity;
import com.delfi.vn.template.ui.menu21.Menu21InputActivity;
import com.delfi.vn.template.ui.menu22.Menu22InputActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.delfi.vn.template.models.enums.MenuCode.MENU_11;
import static com.delfi.vn.template.models.enums.MenuCode.MENU_21;
import static com.delfi.vn.template.models.enums.MenuCode.MENU_22;

public class MainMenuRepositoryImpl implements MainMenuRepository {

    private AppRepository appRepository;

    @Inject
    public MainMenuRepositoryImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public Observable<List<MainMenu>> getManagerMainMenuList() {
        List subMenu2 = new ArrayList<MainMenu>();
        subMenu2.add(new MainMenu(MENU_21.ordinal(),
                R.drawable.ic_menu12,
                R.string.manager_menu12_title,
                MENU_21,
                0,
                0, Menu21InputActivity.class));
        subMenu2.add(new MainMenu(MENU_22.ordinal(),
                R.drawable.ic_menu12,
                R.string.manager_menu13_title,
                MENU_22,
                0,
                0, Menu22InputActivity.class));


        return Observable.just(new ArrayList() {
            {
                add(new MainMenu(MENU_11.ordinal(), R.drawable.ic_menu11, R.string.manager_menu11_title,
                        MENU_11, 0, 0, Menu11InputActivity.class));
                add(new MainMenu(MENU_21.ordinal(), R.drawable.ic_menu12, R.string.manager_menu2_title,
                        MENU_21, 0, 0, null, 0, R.string.please_choose_action,
                        subMenu2));
            }
        });
    }

    private List<MainMenu> addSubMenu(List<MainMenu> mainMenuList, List<MainMenu> subMenuList,
                                      MainMenu currentMainMenu) {
        List<MainMenu> subCopy = new ArrayList<>(subMenuList);
        currentMainMenu.setSubMenus(subCopy);
        mainMenuList.add(currentMainMenu);
        return mainMenuList;
    }
}
