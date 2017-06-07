package com.alibaba.cobarclient.transaction;
import com.alibaba.cobarclient.Shard;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.*;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class MyBestEffortMultiDataSourceTransactionManager extends AbstractPlatformTransactionManager implements InitializingBean {

    private Set<Shard> shards;

    protected List<AbstractPlatformTransactionManager> transactionManagers;

    public MyBestEffortMultiDataSourceTransactionManager() {
    }

    public MyBestEffortMultiDataSourceTransactionManager(Set<Shard> shards) {
        this.shards = shards;
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        return new ArrayList<DefaultTransactionStatus>();
    }

    @Override
    protected void doBegin(Object o, TransactionDefinition transactionDefinition) throws TransactionException {
        List<TransactionStatus> statusList = (List<TransactionStatus>) o;
        for (AbstractPlatformTransactionManager transactionManager : transactionManagers) {
        	//其实最好做法是调用各个实际数据源的doBegin方法，但是因为这个方法是protected的，
        	//无法在外部调用，只能退而求其次，调用getTransacation方法。 
            statusList.add(transactionManager.getTransaction(transactionDefinition));
        }
    }

    @Override
    protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        MultipleCauseException ex = new MultipleCauseException();
        List<TransactionStatus> statusList = (List<TransactionStatus>) defaultTransactionStatus.getTransaction();
        for (int i = transactionManagers.size() - 1; i >= 0; i--) {
            AbstractPlatformTransactionManager transactionManager = transactionManagers.get(i);
            TransactionStatus status = statusList.get(i);
            try {
                transactionManager.commit(status);
            } catch (TransactionException e) {
                ex.add(e);
            }
        }
        if (!ex.getCauses().isEmpty())
            throw new HeuristicCompletionException(HeuristicCompletionException.STATE_UNKNOWN, ex);

    }

    @Override
    protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        MultipleCauseException ex = new MultipleCauseException();
        List<TransactionStatus> statusList = (List<TransactionStatus>) defaultTransactionStatus.getTransaction();
        for (int i = transactionManagers.size() - 1; i >= 0; i--) {
            AbstractPlatformTransactionManager transactionManager = transactionManagers.get(i);
            TransactionStatus status = statusList.get(i);
            try {
                transactionManager.rollback(status);
            } catch (TransactionException e) {
                e.printStackTrace();
                ex.add(e);
            }
        }
        if (!ex.getCauses().isEmpty())
            throw new UnexpectedRollbackException("one or more error on rolling back the transaction", ex);
    }

    public Set<Shard> getShards() {
        return shards;
    }

    public void setShards(Set<Shard> shards) {
        this.shards = shards;
    }

    public void afterPropertiesSet() throws Exception {
        if (shards == null || shards.isEmpty()) throw new IllegalArgumentException("'shards' is required.");
        transactionManagers = new ArrayList<AbstractPlatformTransactionManager>();
        for (Shard shard : shards) {
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(shard.getDataSource());
            transactionManager.setDefaultTimeout(getDefaultTimeout());
            transactionManager.setTransactionSynchronization(getTransactionSynchronization());
            transactionManagers.add(transactionManager);
        }
    }
}
