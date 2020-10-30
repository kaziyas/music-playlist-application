package com.mondiamedia.app.io.repository;

import com.mondiamedia.app.io.entity.TokenEntity;
import com.mondiamedia.app.service.shared.TokenDTO;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
  List<TokenEntity> findByIdOrderByIdDesc(long id);
}
