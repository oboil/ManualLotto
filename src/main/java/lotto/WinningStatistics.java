package lotto;

public class WinningStatistics {
    LottoStatus lottoStatus;
    long totalPrice;

    WinningStatistics(LottoStatus lottoStatus, int unitPrice, long count) {
        this.lottoStatus = lottoStatus;
        this.totalPrice = count * unitPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }
}

