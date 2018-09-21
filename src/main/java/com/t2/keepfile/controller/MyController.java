package com.t2.keepfile.controller;

import com.google.cloud.storage.Blob;
import com.t2.keepfile.service.BucketService;
import com.t2.registrationlogin.entity.User;
import com.t2.registrationlogin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Controller
public class MyController {

    @Autowired
    private BucketService bucketService;

    private static final Logger logger = Logger.getLogger(MyController.class.getName());

    @Autowired
    IUserService userService;

    @ModelAttribute("user")
    public User login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return userService.getUserByEmail(authentication.getName());
        }
        return null;
    }

    @ModelAttribute("currentDirectory")
    public String currentDirectory() {
        return "";
    }

    @GetMapping({"", "/"})
    public ModelAndView index(HttpServletRequest req) {
        req.getSession().setAttribute("currentDirectory", "/");

        ModelAndView modelAndView = new ModelAndView("index", "blobs", bucketService.getAllBlob());
        modelAndView.addObject("auth", SecurityContextHolder.getContext().getAuthentication());
        return modelAndView;
    }


    @GetMapping({"/owner"})
    public String ownerFolder(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!bucketService.existObject(String.format("%s/", auth))) {
                bucketService.createFolder(auth.getName());
            }
            return String.format("redirect:/file?b=%s/", auth.getName());
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/file")
    public String file(@RequestParam("b") String b, Model model, HttpServletRequest req) {
        System.out.println("=========CLICK===========");
        System.out.println(b + ": " + bucketService.getBlobByBucketAndObject(b));
        model.asMap().forEach((k, v) -> {
            logger.info(k + " - " + v);
        });

        if (!bucketService.existObject(b)) {
            System.out.println(" =>>> error");
            return "redirect:/";
        }
        if (b.lastIndexOf("/") == b.length() - 1) {
            System.out.println("is directtory: " + b);
            req.getSession().setAttribute("currentDirectory", b);
            // build breadcrumb

            StringBuilder builder = new StringBuilder();
            Map<String, String> breadcrumb = Arrays.stream(b.split("/"))
                    .collect(Collectors.toMap(str -> builder.append(str).append("/").toString(), str -> str,
                            (x, y) -> x, LinkedHashMap::new));
            model.addAttribute("currentDirectory", b);
            model.addAttribute("listBreadcrumb", breadcrumb);
            model.addAttribute("blobs", bucketService.getAllBlob(b));
            model.addAttribute("auth", SecurityContextHolder.getContext().getAuthentication());
            return "index";
        } else {
//            name = name.substring(name.indexOf("/"));
            System.out.println("is file : " + b);
            System.out.println(" =>>> download");
            return String.format("redirect:/download?b=%s", b);
        }
    }

    @GetMapping("/share")
    @ResponseBody
    public String shareFile(@RequestParam("b") String b, Model model) {
        System.out.println("=========SHARE===========");
        System.out.println(b);
        if (!(b.lastIndexOf("/") == b.length() - 1)) {
            System.out.println("share file: " + b);
            return bucketService.getLinkShare(b);
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/up")
    public String upFileSubmit(@RequestParam("files") MultipartFile uploadedFile, Model model,
                               HttpServletRequest req) throws IOException {
//
//        String fileName = uploadedFile.getOriginalFilename();
//        String mimeType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);

        String dir = req.getSession().getAttribute("currentDirectory").toString();

        if ("/".equals(dir))
            dir = "/";
        Blob blob = bucketService.uploadFile(uploadedFile, dir);
        logger.info("upload success " + blob.getName());
        return "redirect:/file?b=" + dir;
    }


}
