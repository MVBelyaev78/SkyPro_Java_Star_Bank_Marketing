package org.skypro.starbank.marketing.result;

public class SearchResult {
    private final Boolean result;
    private final Boolean has_debit_product;
    private final Boolean debit_deposits_ge_50000;
    private final Boolean saving_deposits_ge_50000;
    private final Boolean deposits_gt_withdrawals;
    private final Boolean user_exists;

    public SearchResult(Boolean result, Boolean has_debit_product, Boolean debit_deposits_ge_50000, Boolean saving_deposits_ge_50000, Boolean deposits_gt_withdrawals, Boolean user_exists) {
        this.result = result;
        this.has_debit_product = has_debit_product;
        this.debit_deposits_ge_50000 = debit_deposits_ge_50000;
        this.saving_deposits_ge_50000 = saving_deposits_ge_50000;
        this.deposits_gt_withdrawals = deposits_gt_withdrawals;
        this.user_exists = user_exists;
    }

    public Boolean getResult() {
        return result;
    }

    public Boolean getHas_debit_product() {
        return has_debit_product;
    }

    public Boolean getDebit_deposits_ge_50000() {
        return debit_deposits_ge_50000;
    }

    public Boolean getSaving_deposits_ge_50000() {
        return saving_deposits_ge_50000;
    }

    public Boolean getDeposits_gt_withdrawals() {
        return deposits_gt_withdrawals;
    }

    public Boolean getUser_exists() {
        return user_exists;
    }
}
