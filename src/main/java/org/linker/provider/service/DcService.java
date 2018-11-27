package org.linker.provider.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RWM
 * @date 2018/11/27
 */
@Service
public class DcService {


    public List<String> findAll(List<Long> ids) {
        System.out.println("finaAll request:---------" + ids + "  Thread.currentThread().getName():-------" + Thread.currentThread().getName());
        return ids.stream().map(e -> "dc" + e).collect(Collectors.toList());
    }

    public String findOne(Long id) {
        return "dc" + id;
    }
}
