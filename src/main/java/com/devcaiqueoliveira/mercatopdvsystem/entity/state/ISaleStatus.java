package com.devcaiqueoliveira.mercatopdvsystem.entity.state;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Sale;
import com.devcaiqueoliveira.mercatopdvsystem.entity.SaleItem;

public interface ISaleStatus {
    void addItem(Sale sale, SaleItem item);
    void complete(Sale sale);
    void cancel(Sale sale);
}
