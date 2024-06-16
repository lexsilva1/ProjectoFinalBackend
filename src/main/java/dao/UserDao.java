package dao;

import entities.TokenEntity;
import entities.UserEntity;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Stateless
public class UserDao extends AbstractDao<UserEntity>{
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public UserDao() {
        super(UserEntity.class);
    }

    public void updateToken(UserEntity userEntity, TokenEntity tokenEntity) {

        userEntity.setTokens(tokenEntity);
        em.merge(userEntity);
    }
    public void updateUser(UserEntity userEntity) {
        em.merge(userEntity);
    }
    public UserEntity findUserByEmail(String email) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByEmail").setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    public UserEntity findUserByNickname(String nickname) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByNickname").setParameter("nickname", nickname)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    public UserEntity findUserById(int id) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserById").setParameter("id", id)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }



}