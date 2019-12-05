package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SectionController {

    private SectionRequest sectionRequest;

    @Inject
    private TestRepository testRepository;

    @Inject
    private HttpServletRequest request;

    public SectionRequest getSectionRequest() {
        if(sectionRequest == null) {
            sectionRequest = createSectionRequest();
        }
        return sectionRequest;
    }

    private SectionRequest createSectionRequest() {
        if (request.getParameter("id") != null) {
            var id = request.getParameter("id");
            var auction = testRepository.getSection(Long.parseLong(id));
            return new SectionRequest(auction.getId(), auction.getName());
        }
        return new SectionRequest();
    }

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
        //HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //Long id = Long.parseLong(sectionRequest.getId());
        testRepository.editSection((Long) sectionRequest.getId(), sectionRequest.getName());
        sectionRequest.setName(null);
        return "adminSection.xhtml";
    }
}
