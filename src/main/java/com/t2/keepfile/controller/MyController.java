package com.t2.keepfile.controller;

import com.t2.keepfile.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class MyController {

    @Autowired
    private BucketService bucketService;


    @GetMapping({"", "/"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index", "blobs", bucketService.getAllBlob());
        modelAndView.addObject("currentDirectory", "");
        return modelAndView;
    }

    @GetMapping("/file")
    public String file(@RequestParam("b") String b, Model model) {
        System.out.println("=========CLICK===========");
        System.out.println(b);
//        String reqURI = new StringBuilder(rebq.getRequestURI()).reverse().toString();
        if (b.lastIndexOf("/") == b.length() - 1) {
            System.out.println("is directtory: " + b);

            // build breadcrumb
            StringBuilder builder = new StringBuilder();
            Map<String, String> breadcrumb = Arrays.stream(b.split("/"))
                    .collect(Collectors.toMap(str ->
                            builder.append(str).append("/").toString(), str -> str));

            model.addAttribute("currentDirectory", b);
            model.addAttribute("listBreadcrumb", breadcrumb);
            model.addAttribute("blobs", bucketService.getAllBlob(b));
            return "index";
        } else {
//            name = name.substring(name.indexOf("/"));
            System.out.println("is file : " + b);
            if (bucketService.getBlobByBucketAndObject(b) == null) {
                System.out.println(" =>>> error");
                return "redirect:/";
            } else {
                System.out.println(" =>>> download");
                return String.format("redirect:/download?b=%s", b);
            }
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


}
