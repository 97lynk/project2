package com.t2.keepfile.controller;

import com.google.cloud.storage.Blob;
import com.t2.keepfile.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;


@Controller
public class DownloadController {

    @Autowired
    private BucketService bucketService;

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestParam("b") String b) {
        Blob blob = bucketService.getBlobByBucketAndObject(b);
        Resource resource = new InputStreamResource(
                new ByteArrayInputStream(blob.getContent(Blob.BlobSourceOption.userProject("t2cloud-1997"))));
        System.out.println("here");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + blob.getName() + "\"").body(resource);
    }
}
