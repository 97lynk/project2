package com.t2.keepfile;

//
//import com.t2.keepfile.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.Calendar;

//@Component
//public class BootStrap implements CommandLineRunner {

//    AccountResponsitory accountResponsitory;
//
//    FileResponsitory fileResponsitory;
//
//    FolderResponsitory folderResponsitory;
//
//    ProfileResponsitory profileResponsitory;
//
//    @Autowired
//    public BootStrap(AccountResponsitory accountResponsitory, FileResponsitory fileResponsitory, FolderResponsitory folderResponsitory,
//                     ProfileResponsitory profileResponsitory) {
//        this.accountResponsitory = accountResponsitory;
//        this.fileResponsitory = fileResponsitory;
//        this.folderResponsitory = folderResponsitory;
//        this.profileResponsitory = profileResponsitory;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Profile savedProfile = profileResponsitory.save(new Profile("Tuan", "Nguyen Anh", Date.valueOf(LocalDate.now()), true, "530"));
//
//        Account newAccount = new Account("tuan", "111", true, Calendar.getInstance().getTime());
//        newAccount.setProfile(savedProfile);
//        Account savedAccount = accountResponsitory.save(newAccount);
//
//        Folder newFolder = new Folder("tuan", "no", "c://tuan", "tuan", 222.2, Calendar.getInstance().getTime(),
//                Calendar.getInstance().getTime(), false);
//        newFolder.setOwner(savedAccount);
//
//        Folder savedFolder = folderResponsitory.save(newFolder);
//
//        fileResponsitory.save(new File("tuan.txt", "no", "c://tuan/tuan.txt", "tuan/tuan.xt", 222.2, Calendar.getInstance().getTime(),
//                Calendar.getInstance().getTime(), false, newAccount, savedFolder));
//    }
//}
