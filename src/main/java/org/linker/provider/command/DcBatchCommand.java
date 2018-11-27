package org.linker.provider.command;


import com.netflix.hystrix.HystrixCommand;
import org.linker.provider.service.DcService;

import java.util.List;

import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

/**
 * @author RWM
 * @date 2018/11/27
 */
public class DcBatchCommand extends HystrixCommand<List<String>> {

    DcService dcService;
    List<Long> ids;

    public DcBatchCommand(DcService dcService, List<Long> ids) {
        super(Setter.withGroupKey(asKey("dcServiceCommand")));
        this.dcService = dcService;
        this.ids = ids;
    }

    @Override
    protected List<String> run() throws Exception {
        return dcService.findAll(ids);
    }
}
