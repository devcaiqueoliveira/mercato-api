package com.devcaiqueoliveira.mercatopdvsystem.entity.state.impl;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Sale;
import com.devcaiqueoliveira.mercatopdvsystem.entity.SaleItem;
import com.devcaiqueoliveira.mercatopdvsystem.entity.state.ISaleStatus;

public class CanceledSaleImpl implements ISaleStatus {
    @Override
    public void addItem(Sale sale, SaleItem item) {
        throw new RuntimeException("Não é possível alterar uma venda cancelada.");
    }

    @Override
    public void complete(Sale sale) {
        throw new RuntimeException("Não é possível cancelar uma venda finalizada.");
    }

    @Override
    public void cancel(Sale sale) {
        throw new RuntimeException("Esta venda já foi cancelada.");
    }
}
