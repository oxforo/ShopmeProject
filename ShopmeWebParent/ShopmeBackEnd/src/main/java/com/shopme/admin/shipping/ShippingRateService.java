package com.shopme.admin.shipping;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.ShippingRate;
import org.hibernate.DuplicateMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ShippingRateService {

    private static final int SHIPPING_PER_PAGE = 10;
    @Autowired private ShippingRateRepository shippingRateRepository;
    @Autowired private CountryRepository countryRepository;

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, SHIPPING_PER_PAGE, shippingRateRepository);
    }

    public void updateUserEnabledStatus(Integer id, boolean codSupported) {
        shippingRateRepository.updateEnabledStatus(id, codSupported);
    }

    public List<Country> listAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    public void save(ShippingRate shippingRateInForm) throws ShippingRateDuplicateException {

        System.out.println("shippingRateInForm = " + shippingRateInForm);

        ShippingRate shippingRateInDB = null;
        if (shippingRateInForm.getId() != null) {
            shippingRateInDB = shippingRateRepository.findById(shippingRateInForm.getId()).get();
        } else {
            shippingRateInDB = new ShippingRate();

            Long countByCountryAndState = shippingRateRepository.countByCountryAndState(
                    shippingRateInForm.getCountry(),
                    shippingRateInForm.getState());

            if (countByCountryAndState != 0) {
                throw new ShippingRateDuplicateException("There's already a rate for the destination "
                        + shippingRateInForm.getCountry().getName() + ", " + shippingRateInForm.getState());
            }
        }

        shippingRateInDB.setCountry(shippingRateInForm.getCountry());
        shippingRateInDB.setRate(shippingRateInForm.getRate());
        shippingRateInDB.setDays(shippingRateInForm.getDays());
        shippingRateInDB.setCodSupported(shippingRateInForm.getCodSupported());
        shippingRateInDB.setState(shippingRateInForm.getState());

        shippingRateRepository.save(shippingRateInDB);
    }

    public ShippingRate get(Integer id) throws ShippingRateNotFoundException {

        try {
            return shippingRateRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ShippingRateNotFoundException("Could not find any shipping rate with ID " + id);
        }

    }

    public void delete(Integer id) throws ShippingRateNotFoundException {

        Long countById = shippingRateRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new ShippingRateNotFoundException("Could not found any shipping rate with ID " + id);
        }
        shippingRateRepository.deleteById(id);
    }
}
