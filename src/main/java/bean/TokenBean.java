package bean;

import dao.TokenDao;
import entities.TokenEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TokenBean {
    @EJB
    private UserBean userBean;
    @Inject
    private TokenDao tokenDao;

public TokenEntity findTokenByToken(String token) {
        return tokenDao.findTokenByToken(token);
    }
    public UserEntity findUserByToken(String token) {
        return tokenDao.findUserByToken(token);
    }
    public void removeToken(String token) {
        TokenEntity tokenEntity = tokenDao.findTokenByToken(token);
        tokenEntity.setUsed(true);
        tokenDao.updateToken(tokenEntity);
    }

    public List<TokenEntity> findTimedOutTokens(LocalDateTime time) {
        return tokenDao.findTimedOutTokens(time);
    }

    public void updateToken(TokenEntity tokenEntity) {
        tokenDao.updateToken(tokenEntity);
    }
    public TokenEntity createLoginToken(String Token, UserEntity userEntity) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(Token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setCreationDate(LocalDateTime.now());
        tokenEntity.setType(TokenEntity.TokenType.LOGIN);
        tokenEntity.setUsed(false);
        tokenEntity.setLastActivity(LocalDateTime.now());
        tokenDao.createToken(tokenEntity);
        return tokenEntity;

    }
    public TokenEntity createRegisterToken(String Token, UserEntity userEntity) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(Token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setCreationDate(LocalDateTime.now());
        tokenEntity.setType(TokenEntity.TokenType.REGISTER);
        tokenEntity.setUsed(false);
        tokenEntity.setLastActivity(LocalDateTime.now());
        tokenDao.createToken(tokenEntity);
        return tokenEntity;
    }
    public TokenEntity createPasswordToken(String Token, UserEntity userEntity) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(Token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setCreationDate(LocalDateTime.now());
        tokenEntity.setType(TokenEntity.TokenType.PASSWORD_RESET);
        tokenEntity.setUsed(false);
        tokenEntity.setLastActivity(LocalDateTime.now());
        tokenDao.createToken(tokenEntity);
        return tokenEntity;
    }
    public TokenEntity findTokenByUserAndType(UserEntity userEntity, String type) {
        return tokenDao.findTokenByUserAndType(userEntity, type);
    }
    public List<TokenEntity> findTokensByUser(UserEntity userEntity) {
        return tokenDao.findTokensByUser(userEntity);
    }
    public boolean isTokenValid(String token) {
        TokenEntity tokenEntity = tokenDao.findTokenByToken(token);
        if (tokenEntity == null) {
            return false;
        }
        if (tokenEntity.isUsed()) {
            return false;
        }
        return true;
    }
    public List<TokenEntity> findActiveTokensByUser(UserEntity userEntity) {
        return tokenDao.findActiveTokensByUser(userEntity);
    }
}
