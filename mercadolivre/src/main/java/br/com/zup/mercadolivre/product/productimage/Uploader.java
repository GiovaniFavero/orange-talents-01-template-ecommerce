package br.com.zup.mercadolivre.product.productimage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface Uploader {

    Set<String> send(List<MultipartFile> images);

}
