package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.SectionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Named
@RequestScoped
public class SectionController {

    private SectionRequest sectionRequest;

    @Inject
    private SectionRepository sectionRepository;

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
            var auction = sectionRepository.getSection(Long.parseLong(id));
            return new SectionRequest(auction.getId(), auction.getName());
        }
        return new SectionRequest();
    }

    public void addSection() {
        sectionRepository.addSection(sectionRequest.getName());
        sectionRequest.setName(null);
    }

    public Map<Long, String> getSections() {
        return sectionRepository.getSections();
    }

    public String delete(Long id) {
        sectionRepository.deleteSection(id);
        return "adminSection.xhtml";
    }

    public String edit() {
        //HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //Long id = Long.parseLong(sectionRequest.getId());
        sectionRepository.editSection((Long) sectionRequest.getId(), sectionRequest.getName());
        sectionRequest.setName(null);
        return "adminSection.xhtml";
    }
}
