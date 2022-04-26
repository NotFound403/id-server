package cn.felord.idserver.repository;

import cn.felord.idserver.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    /**
     * 根据父级 id 找到所有菜单
     *
     * @param parentId 父id
     * @return {@link List}<{@link Menu}>
     */
    @EntityGraph(attributePaths = {"children"})
    List<Menu> findAllByParentId(String parentId);

}
