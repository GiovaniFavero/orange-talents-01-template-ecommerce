package br.com.zup.mercadolivre.product.productimage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader {

    public Set<String> send(List<MultipartFile> images) {
        return images.stream().map(image -> {
            if(image.getOriginalFilename().isEmpty()) {
                throw new IllegalStateException("File name can't be empty!");
            }
            return "http://bucket.io/" + image.getOriginalFilename();
        }).collect(Collectors.toSet());
    }
}
