package dao;

import entities.UserEntity;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Stateless
public class UserDao extends AbstractDao<UserEntity>{
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public UserDao() {
        super(UserEntity.class);
    }
    public UserEntity findUserByToken(String token) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByToken").setParameter("token", token)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    public void updateToken(UserEntity userEntity) {
        em.createNamedQuery("User.updateToken").setParameter("token", userEntity.getToken()).setParameter("token",userEntity.getToken()).executeUpdate();
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
}