package org.example.dto;

import java.math.BigDecimal;

public class AccountDto {

    private Long id;
    private BigDecimal amount;

    public AccountDto(Long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
