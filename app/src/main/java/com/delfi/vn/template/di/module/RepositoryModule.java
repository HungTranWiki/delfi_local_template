package com.delfi.vn.template.di.module;

import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.AppRepositoryImpl;
import com.delfi.vn.template.repositories.EmailLocalRepo;
import com.delfi.vn.template.repositories.EmailLocalRepoImpl;
import com.delfi.vn.template.repositories.EmailRepo;
import com.delfi.vn.template.repositories.EmailRepoImpl;
import com.delfi.vn.template.repositories.GeneralRepo;
import com.delfi.vn.template.repositories.GeneralRepoImpl;
import com.delfi.vn.template.repositories.MainMenuRepository;
import com.delfi.vn.template.repositories.MainMenuRepositoryImpl;
import com.delfi.vn.template.repositories.Menu11LocalRepo;
import com.delfi.vn.template.repositories.Menu11LocalRepoImpl;
import com.delfi.vn.template.repositories.Menu11Repo;
import com.delfi.vn.template.repositories.Menu11RepoImpl;
import com.delfi.vn.template.repositories.PhoneLocalRepo;
import com.delfi.vn.template.repositories.PhoneLocalRepoImpl;
import com.delfi.vn.template.repositories.PhoneRepo;
import com.delfi.vn.template.repositories.PhoneRepoImpl;
import com.delfi.vn.template.repositories.ProductLocalRepo;
import com.delfi.vn.template.repositories.ProductLocalRepoImpl;
import com.delfi.vn.template.repositories.Receipt11LocalRepo;
import com.delfi.vn.template.repositories.Receipt11LocalRepoImpl;
import com.delfi.vn.template.repositories.Receipt11Repo;
import com.delfi.vn.template.repositories.Receipt11RepoImpl;
import com.delfi.vn.template.repositories.UserRepository;
import com.delfi.vn.template.repositories.UserRepositoryImpl;
import com.delfi.vn.template.repositories.WarehouseLocalRepo;
import com.delfi.vn.template.repositories.WarehouseLocalRepoImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    abstract GeneralRepo bindGeneralRepo(GeneralRepoImpl impl);

    @Binds
    abstract WarehouseLocalRepo bindWarehouseLocalRepo(WarehouseLocalRepoImpl impl);

    @Binds
    abstract AppRepository bindAppRepositoryImpl(AppRepositoryImpl impl);

    @Binds
    abstract UserRepository bindLoginRepository(UserRepositoryImpl impl);

    @Binds
    abstract MainMenuRepository bindMainMenuRepository(MainMenuRepositoryImpl impl);

    @Binds
    abstract ProductLocalRepo bindProductLocalRepo(ProductLocalRepoImpl impl);

    @Binds
    abstract Receipt11LocalRepo bindReceipt11LocalRepo(Receipt11LocalRepoImpl impl);

    @Binds
    abstract Receipt11Repo bindReceipt11Repo(Receipt11RepoImpl impl);

    @Binds
    abstract Menu11LocalRepo bindMenu11LocalRepo(Menu11LocalRepoImpl impl);

    @Binds
    abstract Menu11Repo bindMenu11Repo(Menu11RepoImpl impl);

    @Binds
    abstract EmailLocalRepo bindEmailLocalRepo(EmailLocalRepoImpl impl);

    @Binds
    abstract EmailRepo bindEmailRepo(EmailRepoImpl impl);

    @Binds
    abstract PhoneLocalRepo bindPhoneLocalRepo(PhoneLocalRepoImpl impl);

    @Binds
    abstract PhoneRepo bindPhoneRepo(PhoneRepoImpl impl);
}


