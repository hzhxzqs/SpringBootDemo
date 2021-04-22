package zam.hzh.shiro.common.shiro.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import zam.hzh.shiro.common.exception.MyException;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.model.SysUser;

/**
 * 密码操作类
 */
public class PasswordHelper {

    // 加密算法
    public static final String ALGORITHM_NAME = "md5";

    // 加密次数
    public static final int HASH_ITERATIONS = 2;

    /**
     * 加密密码
     *
     * @param password 密码
     * @param salt     盐值
     * @return 已加密密码
     */
    public static String encryptPassword(String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    /**
     * 设置用户密码
     *
     * @param user 用户
     */
    public static void setPassword(SysUser user) {
        RandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        // 使用随机值做盐值
        user.setSalt(secureRandomNumberGenerator.nextBytes().toHex());
        String newPassword = encryptPassword(user.getPassword(), user.getCredentialSalt());
        user.setPassword(newPassword);
    }

    /**
     * 检查用户密码是否匹配
     *
     * @param user          用户
     * @param checkPassword 检查密码
     * @return 是否匹配
     */
    public static boolean checkPassword(SysUser user, String checkPassword) {
        String encryptPassword = encryptPassword(checkPassword, user.getCredentialSalt());
        return encryptPassword.equals(user.getPassword());
    }

    /**
     * 更新用户密码
     *
     * @param user            用户
     * @param oldPassword     原密码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @throws MyException 原密码错误或密码不一致异常
     */
    public static void updatePassword(SysUser user, String oldPassword, String newPassword, String confirmPassword)
            throws MyException {
        if (checkPassword(user, oldPassword)) {
            throw new MyException(MsgEnum.OLD_PASSWORD_ERROR);
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new MyException(MsgEnum.PASSWORD_CONFIRM_ERROR);
        }
        user.setPassword(newPassword);
        setPassword(user);
    }
}
