package fastdex.build.util

/**
 * Created by tong on 17/4/18.
 */
class FastdexRuntimeException extends RuntimeException {

    FastdexRuntimeException() {
    }

    FastdexRuntimeException(String var1) {
        super(var1)
    }

    FastdexRuntimeException(String var1, Throwable var2) {
        super(var1, var2)
    }

    FastdexRuntimeException(Throwable var1) {
        super(var1)
    }

    FastdexRuntimeException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
