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
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(TokenBean.class);

public TokenEntity findTokenByToken(String token) {
    logger.info("Finding token by token {}", token);
    return tokenDao.findTokenByToken(token);
    }
    public UserEntity findUserByToken(String token) {
    logger.info("Finding user by token {}", token);
    return tokenDao.findUserByToken(token);
    }
    public void removeToken(String token) {
    logger.info("Removing token {}", token);
        TokenEntity tokenEntity = tokenDao.findTokenByToken(token);
        tokenEntity.setUsed(true);
        tokenDao.updateToken(tokenEntity);
    }

    public List<TokenEntity> findTimedOutTokens(LocalDateTime time) {
    logger.info("Finding timed out tokens");
        return tokenDao.findTimedOutTokens(time);
    }

    public void updateToken(TokenEntity tokenEntity) {
    logger.info("Updating token {}", tokenEntity.getToken());
        tokenDao.updateToken(tokenEntity);
    }
    public TokenEntity createLoginToken(String Token, UserEntity userEntity) {
    logger.info("Creating login token for user {}", userEntity.getEmail());
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
    logger.info("Creating register token {} for user {}",Token, userEntity.getEmail());
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(Token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setCreationDate(LocalDateTime.now());
        tokenEntity.setType(TokenEntity.TokenType.REGISTER);
        tokenEntity.setUsed(false);
        tokenEntity.setLastActivity(LocalDateTime.now());
        tokenDao.createToken(tokenEntity);
        logger.info("Register token created");
        return tokenEntity;
    }
    public TokenEntity createPasswordToken(String Token, UserEntity userEntity) {
    logger.info("Creating password token {} for user {}",Token, userEntity.getEmail());
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(Token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setCreationDate(LocalDateTime.now());
        tokenEntity.setType(TokenEntity.TokenType.PASSWORD_RESET);
        tokenEntity.setUsed(false);
        tokenEntity.setLastActivity(LocalDateTime.now());
        tokenDao.createToken(tokenEntity);
        logger.info("Password token created");
        return tokenEntity;
    }
    public TokenEntity findTokenByUserAndType(UserEntity userEntity, String type) {
    logger.info("Finding token by user {} and type {}", userEntity.getEmail(), type);
        return tokenDao.findTokenByUserAndType(userEntity, type);
    }
    public List<TokenEntity> findTokensByUser(UserEntity userEntity) {
    logger.info("Finding tokens by user {}", userEntity.getEmail());
    return tokenDao.findTokensByUser(userEntity);
    }
    public boolean isTokenValid(String token) {
    logger.info("Checking if token is valid {}", token);
        TokenEntity tokenEntity = tokenDao.findTokenByToken(token);
        if (tokenEntity == null) {
            logger.error("Token not found");
            return false;
        }
        if (tokenEntity.isUsed()) {
            logger.error("Token is used");
            return false;
        }
        logger.info("Token is valid");
        return true;
    }
    public List<TokenEntity> findActiveTokensByUser(UserEntity userEntity) {
    logger.info("Finding active tokens by user {}", userEntity.getEmail());
        return tokenDao.findActiveTokensByUser(userEntity);
    }
}
