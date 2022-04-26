package org.leo.energy.auth.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.leo.energy.auth.base.TokenAuthInfo;
import org.leo.energy.common.constant.TokenConstant;
import org.leo.energy.common.utils.Func;
import org.leo.energy.common.utils.JsonUtil;
import org.leo.energy.service.boot.auth.TokenInfo;
import org.leo.energy.service.boot.security.SecureUtil;
import org.leo.energy.user.entity.User;
import org.leo.energy.user.vo.UserInfo;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @Author:gonglong
 * @Date:2022/4/13 17:33
 */
public class TokenGenerator {
    public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
    public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
    public final static String CAPTCHA_NOT_CORRECT = "验证码不正确";
    public final static String TENANT_HEADER_KEY = "Tenant-Id";
    public final static String DEFAULT_TENANT_ID = "000000";
    public final static String USER_TYPE_HEADER_KEY = "User-Type";
    public final static String WEB_USER_TYPE = "web";
    public final static String APP_USER_TYPE = "app";
    public final static String USER_NOT_FOUND = "用户名或密码错误";
    public final static String ILLEGAL_USER = "非法用户";
    public final static String HEADER_KEY = "Authorization";
    public final static String HEADER_PREFIX = "Basic ";
    public final static String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

    public static TokenAuthInfo createToken(@NonNull UserInfo userInfo) {
        TokenInfo tokenInfo = createTokenInfo(userInfo,TokenConstant.ACCESS_TOKEN);
        String accessToken = tokenInfo.getToken();
        int expire = tokenInfo.getExpire();
        String refreshToken = createTokenInfo(userInfo,TokenConstant.REFRESH_TOKEN).getToken();
        User user = userInfo.getUser();
        TokenAuthInfo tokenAuthInfo = new TokenAuthInfo();
        tokenAuthInfo.setUserId(user.getId());
        tokenAuthInfo.setTenantId(user.getTenantId());
        tokenAuthInfo.setOauthId(userInfo.getOauthId());
        tokenAuthInfo.setAccount(user.getAccount());
        tokenAuthInfo.setUserName(user.getRealName());
        tokenAuthInfo.setAuthority(Func.join(userInfo.getRoles()));
        tokenAuthInfo.setAccessToken(accessToken);
        tokenAuthInfo.setExpiresIn(expire);
        tokenAuthInfo.setRefreshToken(refreshToken);
        tokenAuthInfo.setTokenType(TokenConstant.BEARER);
        tokenAuthInfo.setLicense(TokenConstant.LICENSE_NAME);
        return tokenAuthInfo;
    }

    /**
     * @param tokenType
     * @param userInfo
     * @return
     */
    private static TokenInfo createTokenInfo(UserInfo userInfo,String tokenType) {
        Map<String, String> userMap = getUserMap(userInfo, tokenType);
        return SecureUtil.createJWT(userMap,
                "audience", "issuser",tokenType);
    }

    private static Map<String, String> getUserMap(UserInfo userInfo,String tokenType) {
        User user = userInfo.getUser();
        Map<String, String> param = new HashMap<>(16);
        param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
        param.put(TokenConstant.TENANT_ID, user.getTenantId());
        param.put(TokenConstant.OAUTH_ID, userInfo.getOauthId());
        param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
        param.put(TokenConstant.ROLE_ID, user.getRoleId());
        param.put(TokenConstant.ACCOUNT, user.getAccount());
        param.put(TokenConstant.USER_NAME, user.getRealName());
        param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));
        return param;
    }
}
