package com.delfi.vn.template.models.appmodels;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.delfi.vn.template.models.enums.MenuCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Serializable {

    private int id;
    private int nameResource;
    private MenuCode idInServer;
    private int icon;
    private int countDocument = 0;
    private int position = 1;
    private Class cClass;
    private int menuType;
    private int subMenuMessage;
    private List<MainMenu> subMenus;

    public MainMenu(int id, @DrawableRes int icon,
                    @StringRes int nameResource,
                    MenuCode idInServer,
                    int countDocument, int position, Class cClass,
                    int menuType, @StringRes int subMenuMessage, List<MainMenu> subMenus) {
        this.id = id;
        this.icon = icon;
        this.nameResource = nameResource;
        this.idInServer = idInServer;
        this.countDocument = countDocument;
        this.position = position;
        this.cClass = cClass;
        this.menuType = menuType;
        this.subMenuMessage = subMenuMessage;
        this.subMenus = subMenus;
    }

    public MainMenu(int id,
                    @DrawableRes int icon,
                    @StringRes int nameResource,
                    MenuCode idInServer,
                    int countDocument, int position,
                    Class cClass) {
        this.id = id;
        this.icon = icon;
        this.nameResource = nameResource;
        this.idInServer = idInServer;
        this.countDocument = countDocument;
        this.position = position;
        this.cClass = cClass;
        this.menuType = 0;
        this.subMenuMessage = 0;
        this.subMenus = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNameResource() {
        return nameResource;
    }

    public void setNameResource(int nameResource) {
        this.nameResource = nameResource;
    }

    public MenuCode getIdInServer() {
        return idInServer;
    }

    public void setIdInServer(MenuCode idInServer) {
        this.idInServer = idInServer;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCountDocument() {
        return countDocument;
    }

    public void setCountDocument(int countDocument) {
        this.countDocument = countDocument;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Class getcClass() {
        return cClass;
    }

    public void setcClass(Class cClass) {
        this.cClass = cClass;
    }

    public List<MainMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MainMenu> subMenus) {
        this.subMenus = subMenus;
    }

    public int getSubMenuMessage() {
        return subMenuMessage;
    }

    public int getMenuType() {
        return menuType;
    }

    @Override
    public String toString() {
        return "MainMenu{" +
                "id=" + id +
                ", nameResource=" + nameResource +
                ", idInServer=" + idInServer +
                ", icon=" + icon +
                ", countDocument=" + countDocument +
                ", position=" + position +
                ", cClass=" + cClass +
                ", menuType=" + menuType +
                ", subMenuMessage=" + subMenuMessage +
                ", subMenus=" + subMenus +
                '}';
    }
}
