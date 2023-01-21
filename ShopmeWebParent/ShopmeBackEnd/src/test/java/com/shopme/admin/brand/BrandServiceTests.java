package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class BrandServiceTests {

    @InjectMocks
    BrandService brandService;

    @MockBean
    BrandRepository brandRepository;

    @Test
    public void testCheckUniqueNewModeReturnDuplicate() {
        Integer id = null;
        String name = "Samsung";
        Brand brand = new Brand(id, name);

        Mockito.when(brandRepository.findByName(name)).thenReturn(brand);
        String result = brandService.checkUnique(id, name);

        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheckUniqueNewModeReturnOk() {
        Integer id = null;
        String name = "Samsung";

        Mockito.when(brandRepository.findByName(name)).thenReturn(null);
        String result = brandService.checkUnique(id, name);

        assertThat(result).isEqualTo("OK");
    }
}