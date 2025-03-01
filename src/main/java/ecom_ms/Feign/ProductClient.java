package ecom_ms.Feign;

import ecom_ms.Config.FeignConfig;
import ecom_ms.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "https://21f2d361-965d-44be-8205-0e6318bb5fcb.mock.pstmn.io", configuration = FeignConfig.class)
public interface ProductClient {
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);
}

