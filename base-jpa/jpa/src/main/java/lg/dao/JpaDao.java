package lg.dao;

import lg.domain.User;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * author: LG
 * date: 2019-09-04 13:38
 * desc:
 */
@Repository
public class JpaDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Page<User> queryUser() {
        int currentPage = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(new Sort.Order(Sort.Direction.DESC, "user_id")));

        StringBuilder dataSql = new StringBuilder("SELECT * FROM t_user");
        StringBuilder countSql = new StringBuilder("SELECT count(1) FROM t_user ");

        //getOffset 启始页
       // System.out.println("(int) pageable.getOffset()--->" + (int) pageable.getOffset());
       // System.out.println("pageable.getPageSize()--->" + pageable.getPageSize());

        //创建本地sql查询实例
        Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), User.class);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());

        //dataQuery.getResultList() 执行这句话的时候才开始去查库，所以分页是正确的
       // List<User> content22 = dataQuery.getResultList();

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        Long total = count.longValue();
        List<User> content = dataQuery.getResultList();
        return new PageImpl(content, pageable, total);

    }

}
