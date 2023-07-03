package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.appmodels.MainMenu;

import java.util.List;

import io.reactivex.Observable;

public interface MainMenuRepository {
    Observable<List<MainMenu>> getManagerMainMenuList();
}
