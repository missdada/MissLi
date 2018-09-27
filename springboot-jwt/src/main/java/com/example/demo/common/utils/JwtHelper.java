package com.example.demo.common.utils;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.bo.TokenObject;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;

public class JwtHelper {
	private static Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    /**
     * 校验Token
     * @param jwt
     * @param httpRequest
     * @return
     */
    public static int checkToken(String jwt, HttpServletRequest httpRequest){
        if (!StringUtils.isBlank(jwt)){
            if (jwt.split("\\.").length==3) {
                logger.info("jwt:" + jwt);
                String[] split = jwt.split("\\.");
                String content = split[1];//负荷
                String s = Base64Codec.BASE64URL.decodeToString(content);
                logger.info("s:" + s);
                String sign = split[2];//签名
                logger.info("sign:" + sign);
                JSONObject jsonObject1 = JSONObject.parseObject(s);

                long nowMillis = System.currentTimeMillis();
                Date now = new Date(nowMillis);
                long expiresSecond = (long) jsonObject1.get("expiresSecond");

                //判断是否过期
                if(now.getTime()>expiresSecond)
                     return 2;


                TokenObject o = (TokenObject) JSONObject.toJavaObject(jsonObject1, TokenObject.class);
//                 if (o!=null){
//                    String project = o.getProject();
//                   // if (!StaticInfo.PROJECT.equals(project))
//                        return 0;
//                }
                String jwtByStr = createJWTByObj(o);
                String s2 = jwtByStr.split("\\.")[2];
                logger.info("s2:" + s2);
                if (sign.equals(s2)) {
                	logger.info("验证成功");
                    return 1;
                } else
                    return 0;
            }
        }
        return 0;
    }

    /**
     * 获取用户id
     * @param jwt
     * @return
     */
    public static int  getIdByJWT(String jwt){
        if (!StringUtils.isBlank(jwt)) {
            if (jwt.split("\\.").length == 3) {
                logger.info("jwt:" + jwt);
                String[] split = jwt.split("\\.");
                String content = split[1];
                String s = Base64Codec.BASE64URL.decodeToString(content);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                TokenObject o = (TokenObject) JSONObject.toJavaObject(jsonObject1, TokenObject.class);
                return o.getaId();
            }
        }
        return 0;
    }
    
    /**
     * 获取用户clinetId
     * @param jwt
     * @return
     */
    public static String  getClinetIdByJWT(String jwt){
        if (!StringUtils.isBlank(jwt)) {
            if (jwt.split("\\.").length == 3) {
                logger.info("jwt:" + jwt);
                String[] split = jwt.split("\\.");
                String content = split[1];
                String s = Base64Codec.BASE64URL.decodeToString(content);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                TokenObject o = (TokenObject) JSONObject.toJavaObject(jsonObject1, TokenObject.class);
                return o.getClientId();
            }
        }
        return "";
    }

    /**
     * 获取客户信息
     * @param request
     * @return
     * @throws CustomException
     */
    public static int getIdByRequest(HttpServletRequest request) {
        int i = 0;
        String auth = request.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 6)) {
            String HeadStr = auth.substring(0, 5).toLowerCase();
            if (HeadStr.compareTo("basic") == 0) {
                auth = auth.substring(6, auth.length());
                i = JwtHelper.getIdByJWT(auth);
            }
        }
        if (i==0)
           // throw new CustomException(ResultEnum.PERMISSION_DENIED);
		return i;
		return i;
    }

    public static String createJWTByObj(TokenObject tokenObject) {
        Object jsonObject = JSONObject.toJSON(tokenObject);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //long nowMillis = System.currentTimeMillis();
       // Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenObject.getBase64Secret());//服务器自己定义的秘钥
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setPayload(jsonObject.toString())
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }

}
