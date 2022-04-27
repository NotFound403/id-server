package cn.felord.idserver.service;

import cn.felord.idserver.entity.SysPower;
import cn.felord.idserver.entity.SysPowerQueryVO;
import cn.felord.idserver.entity.SysPowerUpdateVO;
import cn.felord.idserver.entity.SysPowerVO;
import cn.felord.idserver.entity.SysPowerDTO;
import cn.felord.idserver.repository.SysPowerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SysPowerService {

    @Autowired
    private SysPowerRepository sysPowerRepository;

    public String save(SysPowerVO vO) {
        SysPower bean = new SysPower();
        BeanUtils.copyProperties(vO, bean);
        bean = sysPowerRepository.save(bean);
        return bean.getPowerId();
    }

    public void delete(String id) {
        sysPowerRepository.deleteById(id);
    }

    public void update(String id, SysPowerUpdateVO vO) {
        SysPower bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        sysPowerRepository.save(bean);
    }

    public SysPowerDTO getById(String id) {
        SysPower original = requireOne(id);
        return toDTO(original);
    }

    public Page<SysPowerDTO> query(SysPowerQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SysPowerDTO toDTO(SysPower original) {
        SysPowerDTO bean = new SysPowerDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private SysPower requireOne(String id) {
        return sysPowerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
