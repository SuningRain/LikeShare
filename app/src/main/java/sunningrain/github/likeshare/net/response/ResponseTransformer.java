package sunningrain.github.likeshare.net.response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.exception.CustomException;

/**
 * Created by 27837 on  2019/4/18.
 */
public class ResponseTransformer {
    public static <T>ObservableTransformer<BaseResponse<T>,T> handleResult(){
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>>{
        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }

//        @Override
//        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
//            return Observable.error(CustomException.handleException(throwable));
//        }
    }

    private static class ResponseFunction<T> implements Function<BaseResponse<T>,ObservableSource<T>>{

        @Override
        public ObservableSource<T> apply(BaseResponse<T> response) throws Exception {
            int code  = response.getCode();
            String message = response.getMessage();
            if (code == 200){
                return Observable.just(response.getData());
            }else {
                return Observable.error(new ApiException(code,message));
            }
        }
    }

}
