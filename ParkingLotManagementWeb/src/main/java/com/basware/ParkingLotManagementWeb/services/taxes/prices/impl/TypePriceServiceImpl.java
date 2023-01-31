package com.basware.ParkingLotManagementWeb.services.taxes.prices.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.taxes.TypePriceDao;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.TypePriceService;
import org.springframework.stereotype.Service;

@Service
public class TypePriceServiceImpl implements TypePriceService {
    private final TypePriceDao typePriceDao;

    public TypePriceServiceImpl(TypePriceDao typePriceDao) {
        this.typePriceDao = typePriceDao;
    }

    @Override
    public Price getPrice(TypeInfo typeInfo) throws ResourceNotFoundException {
        return typePriceDao.getPriceByTypeInfo(typeInfo).orElseThrow(() -> new ResourceNotFoundException("Price could not be found"));
    }
}
