package ckDao;

public interface AccountDao {
    // 汇款
    void out(String outUser, int money);

    // 收款
    void in(String inUser, int money);
}
