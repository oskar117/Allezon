package pl.edu.pjwstk.jazapp.admin.section;

import pl.edu.pjwstk.jazapp.admin.section.SectionEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SectionRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addSection(String name) {
        SectionEntity se = new SectionEntity(name);
        em.persist(se);
    }

    public Map<Long, String> getSections() {
        Map<Long, String> res = new HashMap<Long, String>();
        List<SectionEntity> list = em.createQuery("from SectionEntity ", SectionEntity.class).getResultList();
        for(SectionEntity x : list) {
            res.put(x.getId(), x.getName());
        }
        return res;
    }

    public SectionEntity getSection(long parseLong) {
        var auction = em.find(SectionEntity.class, parseLong);
        return auction;
    }

    @Transactional
    public void editSection(Long id, String name) {
        var section = em.find(SectionEntity.class, id);
        em.detach(section);
        section.setName(name);
        em.merge(section);
    }

    @Transactional
    public void deleteSection(Long id) {
        var section = em.find(SectionEntity.class, id);
        em.remove(section);
    }

}
