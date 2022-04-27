package cn.felord.idserver.repository;

import cn.felord.idserver.entity.SysPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysPowerRepository extends JpaRepository<SysPower, String>, JpaSpecificationExecutor<SysPower> {


}