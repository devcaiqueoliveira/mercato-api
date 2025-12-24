package com.devcaiqueoliveira.mercatopdvsystem.entity.state.impl;

import com.devcaiqueoliveira.mercatopdvsystem.entity.Sale;
import com.devcaiqueoliveira.mercatopdvsystem.entity.SaleItem;
import com.devcaiqueoliveira.mercatopdvsystem.entity.state.ISaleStatus;

public class CompletedSaleImpl implements ISaleStatus {
    @Override
    public void addItem(Sale sale, SaleItem item) {
        throw new RuntimeException("Não é possível adicionar itens a uma venda já finalizada.");
    }

    @Override
    public void complete(Sale sale) {
        throw new RuntimeException("Esta venda já foi finalizada");
    }

    @Override
    public void cancel(Sale sale) {
        throw new RuntimeException("Vendas finalizadas não podem ser canceladas diretamente.");
    }
}
