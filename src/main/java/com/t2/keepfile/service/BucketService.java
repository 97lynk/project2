package com.t2.keepfile.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BucketService {

    private static final String PROJECT_ID = "t2cloud-1997";
    private static final String BUCKET_NAME = "cloud-project2";
    private static final String AUTH_FILE = "/cloud.json";

    private static Bucket bucket;

    static {
        try {
            StorageOptions storage = StorageOptions.newBuilder()
                    .setProjectId(PROJECT_ID)
                    .setCredentials(GoogleCredentials.fromStream(BucketService.class.getResourceAsStream(AUTH_FILE)))
                    .build();

            // get bucket
            bucket = storage.getService().get(BUCKET_NAME, Storage.BucketGetOption.userProject(PROJECT_ID));
            System.out.println(bucket.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        blobs = bucket.list(Storage.BlobListOption.currentDirectory());
//        for (Blob b : blobs.getValues()) {
//            System.out.println(b.getName());
//        }
//        return new ModelAndView("index", "blobs", blobs.getValues() );
    }

    public Iterable<Blob> getAllBlob() {
        return bucket.list(Storage.BlobListOption.currentDirectory(), Storage.BlobListOption.prefix("")).getValues();
    }

    public Iterable<Blob> getAllBlob(String dir) {
        //Iterable<Blob> blobIterable =
        return bucket.list(Storage.BlobListOption.prefix(dir), Storage.BlobListOption.currentDirectory()).getValues();
//        StreamSupport.stream(blobIterable.spliterator(), false)
        //return blobIterable;
    }


    public Blob getBlobByBucketAndObject(String objectName) {
        System.out.println("download name = " + objectName);
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        return bucket.getStorage().get(blobId);
    }

    public String getLinkShare(String objectName) {
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        bucket.getStorage().createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, objectName);
    }
//    public Iterable<Blob> getAllBlob2() {
//        bucket.list(Storage.BlobListOption.currentDirectory()).getValues().forEach(b -> {
//            if (b.isDirectory()) {
//                b.
//            }
//        });
//    }

}
