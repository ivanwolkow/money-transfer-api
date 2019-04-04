package org.example.dto;

import java.math.BigDecimal;

public class TransferRequestDto {
    private Long srcAccount;
    private Long dstAccount;
    private BigDecimal amount;

    public TransferRequestDto() {
    }

    public TransferRequestDto(Long srcAccount, Long dstAccount, BigDecimal amount) {
        this.srcAccount = srcAccount;
        this.dstAccount = dstAccount;
        this.amount = amount;
    }

    public Long getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(Long srcAccount) {
        this.srcAccount = srcAccount;
    }

    public Long getDstAccount() {
        return dstAccount;
    }

    public void setDstAccount(Long dstAccount) {
        this.dstAccount = dstAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferRequestDto{" +
                "srcAccount=" + srcAccount +
                ", dstAccount=" + dstAccount +
                ", amount=" + amount +
                '}';
    }
}
