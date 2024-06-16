package dao;

import entities.TokenEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TokenDao {
    @PersistenceContext
    private EntityManager em;

    public TokenDao() {
    }

    public void createToken(TokenEntity token) {
        em.persist(token);
    }

    public TokenEntity findTokenByToken(String token) {
        return em.createNamedQuery("TokenEntity.findTokenByToken", TokenEntity.class)
                .setParameter("token", token)
                .getSingleResult();
    }

    public UserEntity findUserByToken(String token) {
          return em.createNamedQuery("TokenEntity.findUSerByToken", UserEntity.class)
                 .setParameter("token", token)
                 .getSingleResult();
    }
    public List<TokenEntity> findTimedOutTokens(LocalDateTime time
    ) {
        return em.createNamedQuery("TokenEntity.findTimedOutTokens", TokenEntity.class)
                .setParameter("time", time)
                .getResultList();
    }
    public void updateToken(TokenEntity tokenEntity) {
        em.merge(tokenEntity);
    }
    public TokenEntity findTokenByUserAndType(UserEntity userEntity, String type) {
        TokenEntity.TokenType tokenType = TokenEntity.TokenType.valueOf(type);
        return em.createNamedQuery("TokenEntity.findTokenByUserAndType", TokenEntity.class)
                .setParameter("user", userEntity)
                .setParameter("type", tokenType)
                .getSingleResult();
    }
    public List<TokenEntity> findTokensByUser(UserEntity userEntity) {
        return em.createNamedQuery("TokenEntity.findTokensByUser", TokenEntity.class)
                .setParameter("user", userEntity)
                .getResultList();
    }


}
