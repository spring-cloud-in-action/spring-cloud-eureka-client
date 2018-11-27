package org.linker.provider.collapser;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.linker.provider.command.DcBatchCommand;
import org.linker.provider.service.DcService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RWM
 * @date 2018/11/27
 */
public class DcCollapseCommand extends HystrixCollapser<List<String>, String, Long> {

    private DcService dcService;
    private Long id;

    public DcCollapseCommand(DcService dcService, Long id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("dcCollapseCommand")).andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.dcService = dcService;
        this.id = id;
    }

    @Override
    public Long getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Long>> collection) {
        List<Long> ids = new ArrayList<>(collection.size());
        ids.addAll(collection.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new DcBatchCommand(dcService, ids);
    }

    @Override
    protected void mapResponseToRequests(List<String> strings, Collection<CollapsedRequest<String, Long>> collection) {
        int count = 0;
        for (CollapsedRequest<String, Long> request : collection) {
            String response = strings.get(count++);
            request.setResponse(response);
        }
    }
}
