package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SectionController {

    @Inject
    private SectionRequest sectionRequest;

    @Inject
    private TestRepository testRepository;

    private static Map<Long, Boolean> map;

    public void addSection() {
        testRepository.addSection(sectionRequest.getName());
        sectionRequest.setName(null);
    }

    public Map<Long, String> getSections() {
        return testRepository.getSections();
    }

    public String delete(Long id) {
        testRepository.deleteSection(id);
        return "adminSection.xhtml";
    }

    public String edit() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        testRepository.editSection((Long) session.getAttribute("editId"), sectionRequest.getName());
        sectionRequest.setName(null);
        return "adminSection.xhtml";
    }

   /* public void editField(Long id, String name) {

        if(map == null) map = new HashMap<Long, Boolean>();

        if(map.get(id) == null) {
            map.put(id, false);
        } else if(canEdit(id)) {
            map.put(id, false);
            edit(id, name);
        } else {
            map.put(id, true);
        }
    }

    public boolean canEdit(Long id) {
        if(map == null) {
            map = new HashMap<Long, Boolean>();
            map.put(id, false);
        }
        if(map.get(id) == true) {
            return true;
        } else {
            return false;
        }
    }*/
}
