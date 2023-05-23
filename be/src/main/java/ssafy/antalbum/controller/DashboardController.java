package ssafy.antalbum.controller;

import com.amazonaws.services.s3.model.S3Object;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ssafy.antalbum.service.MetadataService;

@Controller
@AllArgsConstructor
public class DashboardController {

    private MetadataService metadataService;

    @GetMapping("dashboard")
    public String dashboard(Model model) {

        var files = metadataService.list();
        model.addAttribute("files", files);
        return "dashboard";
    }

    @PostMapping("upload")
    public String upload(
            @RequestParam("file") MultipartFile file) throws IOException {
        metadataService.upload(file);
        return "redirect:/dashboard";
    }

    @GetMapping("download/{id}")
    @ResponseBody
    public HttpEntity<byte[]> download(Model model, @PathVariable int id, HttpServletResponse response) throws
            IOException {

        S3Object s3Object = metadataService.download(id);
        String contentType = s3Object.getObjectMetadata().getContentType();
        var bytes = s3Object.getObjectContent().readAllBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);

        return new HttpEntity<byte[]>(bytes, header);
    }
}
