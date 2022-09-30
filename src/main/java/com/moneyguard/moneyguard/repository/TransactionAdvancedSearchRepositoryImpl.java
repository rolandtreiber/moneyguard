package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Transaction;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.service.AuthService;
import org.hibernate.query.internal.QueryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TransactionAdvancedSearchRepositoryImpl implements TransactionAdvancedSearchRepository {

    private class QueryResult {
        private QueryImpl queryImpl;
        private String query;

        public QueryResult() {
        }

        public QueryResult(QueryImpl queryImpl, String query) {
            this.queryImpl = queryImpl;
            this.query = query;
        }

        public QueryImpl getQueryImpl() {
            return queryImpl;
        }

        public void setQueryImpl(QueryImpl queryImpl) {
            this.queryImpl = queryImpl;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }

    @Autowired
    AuthService authService;

    @PersistenceContext
    private EntityManager entityManager;

    private QueryResult constructQuery(String query, RetrieveTransactionsRequest request) {
        QueryResult result = new QueryResult();

        query += "LEFT JOIN t.categories categories " +
                "LEFT JOIN t.tags tags " +
                "WHERE " +
                "t.user.id = :userId " +
                "AND t.name LIKE CONCAT('%',:search,'%') " +
                "AND t.createdAt >= :startDate " +
                "AND t.createdAt <= :endDate ";
        Set<UUID> categoryIds = new HashSet<>();
        Set<UUID> tagIds = new HashSet<>();
        Set<UUID> importanceLevelIds = new HashSet<>();
        Set<Short> types = new HashSet<>();

        if (request.getCategories() != null && request.getCategories().length > 0) {
            query += "AND categories.id IN :categoryIds ";
            for (String c : request.getCategories()) {
                categoryIds.add(UUID.fromString(c));
            }
        }
        if (request.getTags() != null && request.getTags().length > 0) {
            query += "AND tags.id IN :tagIds ";

            for (String t : request.getTags()) {
                tagIds.add(UUID.fromString(t));
            }
        }
        if (request.getImportanceLevels() != null && request.getImportanceLevels().length > 0) {
            query += "AND t.importanceLevel.id IN :importanceLevels ";
            for (String i : request.getImportanceLevels()) {
                importanceLevelIds.add(UUID.fromString(i));
            }
        }
        if (request.getTypes() != null && request.getTypes().length > 0) {
            query += "AND t.type IN :types ";
            for (String t : request.getTypes()) {
                types.add(Short.parseShort(t));
            }
        }

        switch (request.getOrderBy()) {
            case "name":
                query += "ORDER BY t.name ";
                break;
            case "date":
                query += "ORDER BY t.createdAt ";
                break;
            case "amount":
                query += "ORDER BY t.amount ";
                break;
            case "importance":
                query += "ORDER BY t.importanceLevel.level ";
        }
        query += request.getOrder();
        System.out.println(query);

        QueryImpl q = (QueryImpl) entityManager.createQuery(query);
        q.setParameter("search", request.getSearch());
        q.setParameter("userId", authService.getAuthUser().getId());
        q.setParameter("startDate", request.getStartDate());
        q.setParameter("endDate", request.getEndDate());

        if (!categoryIds.isEmpty()) {
            q.setParameter("categoryIds", categoryIds);
        }
        if (!tagIds.isEmpty()) {
            q.setParameter("tagIds", tagIds);
        }
        if (!importanceLevelIds.isEmpty()) {
            q.setParameter("importanceLevels", importanceLevelIds);
        }
        if (!types.isEmpty()) {
            q.setParameter("types", types);
        }

        result.setQuery(query);
        result.setQueryImpl(q);
        return result;
    }

    @Override
    public List<Transaction> advancedSearch(RetrieveTransactionsRequest request) {
        String query = "SELECT DISTINCT t from Transaction t ";
        QueryResult result = constructQuery(query, request);

        return result.queryImpl.setFirstResult(request.getPage() * 10).setMaxResults(10).getResultList();
    }

    @Override
    public Double getTotal(RetrieveTransactionsRequest request) {
        String query = "SELECT DISTINCT t from Transaction t ";
        QueryResult result = constructQuery(query, request);
        List<Transaction> res = (List<Transaction>) result.queryImpl.getResultList();
        Double sum = 0.0;

        for (Transaction t:res) {
            sum += t.getAmount();
        }

        return (Double) sum;
    }

}
