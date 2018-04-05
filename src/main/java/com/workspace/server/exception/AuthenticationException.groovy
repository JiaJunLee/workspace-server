package com.workspace.server.exception

import com.workspace.server.model.UserEntity

/**
 * Created by 51998 on 2018/3/5.
 */
class AuthenticationException extends RuntimeException {

    static final class AuthenticationExceptionCode {

        /**
         * 用户不存在
         */
        static final Integer USER_NOT_FOUND = 1021

        /**
         * 用户禁用
         */
        static final Integer USER_DISABLE = 1022

        /**
         * 用户验证失败
         */
        static final Integer USER_VALIDATE_FAIL = 1023

    }

    /**
     * 异常码
     */
    Integer exceptionCode

    /**
     * 异常用户
     */
    UserEntity userEntity

    AuthenticationException (Integer exceptionCode, UserEntity userEntity) {
        this.exceptionCode = exceptionCode
        this.userEntity = userEntity
    }

    @Override
    String toString() {
        switch (exceptionCode){
            case AuthenticationExceptionCode.USER_NOT_FOUND:
                return 'server.exception.authentication.user-not-found'
            case AuthenticationExceptionCode.USER_DISABLE:
                return 'server.exception.authentication.user-disable'
            case AuthenticationExceptionCode.USER_VALIDATE_FAIL:
                return 'server.exception.authentication.user-validate-fail'
            default:
                return null
        }
    }

}
