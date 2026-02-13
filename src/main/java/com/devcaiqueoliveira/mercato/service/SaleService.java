package com.devcaiqueoliveira.mercato.service;

import com.devcaiqueoliveira.mercato.entity.Product;
import com.devcaiqueoliveira.mercato.entity.Sale;
import com.devcaiqueoliveira.mercato.entity.SaleItem;
import com.devcaiqueoliveira.mercato.enums.SaleStatus;
import com.devcaiqueoliveira.mercato.exception.EntityNotFoundException;
import com.devcaiqueoliveira.mercato.repository.ProductRepository;
import com.devcaiqueoliveira.mercato.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Sale create(Sale saleInput) {
        saleInput.setStatus(SaleStatus.PENDING);

        List<SaleItem> rawItems = new ArrayList<>(saleInput.getItems());
        saleInput.getItems().clear();

        for (SaleItem rawItem : rawItems) {

            Long productId = rawItem.getProduct().getId();

            Product realProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado ID: " + productId));

            realProduct.decreaseStock(rawItem.getQuantity());

            saleInput.addItem(realProduct, rawItem.getQuantity());
        }

        saleInput.finalizeSale();

        return saleRepository.save(saleInput);
    }

    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda com ID: " + id + " não encontrada."));
    }

    @Transactional(readOnly = true)
    public Sale findByCode(String code) {
        return saleRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o código: " + code));
    }

}
