package net.watertao.springsock.infras.concurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class ConcurrentCaller {

    @Autowired
    private Environment env;

    private ExecutorService executorService;


    @PostConstruct
    public void init() {

        executorService  = Executors.newFixedThreadPool(Integer.parseInt(env.getProperty("concurrent.pool-size")));

    }


    public List<CallResult> invokeAll(List<Call> calls) {

        List<CallResult> callResults = new ArrayList<CallResult>();
        List<Future<CallResult>> futures = new ArrayList<Future<CallResult>>();
        for (Call call : calls) {
            Future<CallResult> future = executorService.submit(call);
            futures.add(future);
        }
        for (Future<CallResult> future : futures) {
            try {
                CallResult callResult = future.get();
                callResults.add(callResult);
            } catch (InterruptedException e) {
                CallResult callResult = new CallResult();
                callResult.setSuccess(false);
                callResult.setErrMsg("interrupted");
                callResults.add(callResult);
            } catch (ExecutionException e) {
                CallResult callResult = new CallResult();
                callResult.setSuccess(false);
                callResult.setErrMsg(e.getCause().getMessage());
                callResults.add(callResult);
            } catch (Throwable e) {
                CallResult callResult = new CallResult();
                callResult.setSuccess(false);
                callResult.setErrMsg(e.getMessage());
                callResults.add(callResult);
            }
        }

        return callResults;

    }


}
