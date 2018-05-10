package com.workspace.server.exception

/**
 * 服务器运行时异常信息
 * @author Burgess Li
 * @date 2017/12/27
 */
class ServerException extends RuntimeException {

    static final class ServerExceptionCode {

        /**
         * 接口停用
         */
        static final Integer INTERFACE_DISABLE = 1001

        /**
         * 系统维护
         */
        static final Integer INTERFACE_MAINTENANCE = 1002

        /**
         * 未知请求
         */
        static final Integer UNKNOWN_REQUEST = 1003

        /**
         * 系统未知异常
         */
        static final Integer SYSTEM_UNKNOWN_ABNORMALITY = 1004

        /**
         * 禁止访问
         */
        static final Integer ACCESS_DENIED = 1005

        /**
         * 访问受限
         */
        static final Integer ACCESS_RESTRICTED = 1006

    }

    /**
     * 异常码
     */
    Integer exceptionCode

    ServerException(){
        this.exceptionCode = ServerExceptionCode.SYSTEM_UNKNOWN_ABNORMALITY
    }

    ServerException(Integer exceptionCode){
        this.exceptionCode = exceptionCode
    }

    @Override
    String toString() {
        switch (exceptionCode){
            case ServerExceptionCode.INTERFACE_DISABLE:
                return 'server.exception.interface-disable'
            case ServerExceptionCode.INTERFACE_MAINTENANCE:
                return 'server.exception.interface-maintenance'
            case ServerExceptionCode.UNKNOWN_REQUEST:
                return 'server.exception.unknown-request'
            case ServerExceptionCode.SYSTEM_UNKNOWN_ABNORMALITY:
                return 'server.exception.system-unknown-abnormality'
            case ServerExceptionCode.ACCESS_DENIED:
                return 'server.exception.access-denied'
            case ServerExceptionCode.ACCESS_RESTRICTED:
                return 'server.exception.access-restricted'
            default:
                return 'server.exception.system-unknown-abnormality'
        }
    }

}