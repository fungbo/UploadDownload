package com.tw;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class FileController {

    @RequestMapping(value = "/")
    @ResponseBody
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public FileSystemResource download(HttpServletResponse response) throws IOException {
        String fileName = "test.jar";
        String filePath = "/Users/bfeng/H5/" + fileName;
        String contentDisposition = "attachment; filename*=UTF-8''" + URLEncoder.encode("知乎", "UTF-8");
        response.setHeader("Content-disposition", contentDisposition);
        return new FileSystemResource(filePath);
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("/Users/bfeng/H5", name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
