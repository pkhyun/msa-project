package com.spring_cloud.eureka.client.product;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        QProduct product = QProduct.product;

        BooleanExpression conditionExpression = buildConditionExpression(keyword);

        List<Product> result = queryFactory.selectFrom(product)
                .where(conditionExpression)
                .orderBy(getOrderBy(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = getTotalCount(product, conditionExpression);

        return new PageImpl<>(result, pageable, totalCount);
    }

    // keyword 를 이용한 검색 기능
    private BooleanExpression buildConditionExpression(String keyword) {
        if (keyword == null) {
            return null;
        }
            return QProduct.product.name.containsIgnoreCase(keyword);
    }

    private long getTotalCount(QProduct product, BooleanExpression conditionExpression) {
        return queryFactory.selectFrom(product)
                .where(conditionExpression)
                .fetch().size();
    }

    private OrderSpecifier<?> getOrderBy(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            Sort.Order sortOrder = pageable.getSort().iterator().next();
            boolean ascending = sortOrder.getDirection() == Sort.Direction.ASC;

            return ascending ? QProduct.product.price.asc() : QProduct.product.price.desc();
        }
        return QProduct.product.price.asc();  // 기본적으로 가격으로 오름차순 정렬
    }
}
