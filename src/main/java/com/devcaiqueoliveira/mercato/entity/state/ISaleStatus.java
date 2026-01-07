package com.devcaiqueoliveira.mercato.entity.state;

import com.devcaiqueoliveira.mercato.entity.Sale;
import com.devcaiqueoliveira.mercato.entity.SaleItem;

public interface ISaleStatus {
    void addItem(Sale sale, SaleItem item);
    void complete(Sale sale);
    void cancel(Sale sale);
}
