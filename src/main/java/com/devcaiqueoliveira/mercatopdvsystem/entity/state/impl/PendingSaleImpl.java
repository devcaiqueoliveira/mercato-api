package com.devcaiqueoliveira.mercatopdvsystem.entity.state.impl;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Sale;
import com.devcaiqueoliveira.mercatopdvsystem.entity.SaleItem;
import com.devcaiqueoliveira.mercatopdvsystem.entity.state.ISaleStatus;
import com.devcaiqueoliveira.mercatopdvsystem.enums.SaleStatus;

public class PendingSaleImpl implements ISaleStatus {

    @Override
    public void addItem(Sale sale, SaleItem item) {
        sale.getItems().add(item);
        item.setSale(sale);
    }

    @Override
    public void complete(Sale sale) {
        sale.setStatus(SaleStatus.COMPLETE);
    }

    @Override
    public void cancel(Sale sale) {
        sale.setStatus(SaleStatus.CANCELED);
    }
}
