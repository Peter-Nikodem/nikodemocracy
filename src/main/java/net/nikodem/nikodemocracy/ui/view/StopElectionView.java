package net.nikodem.nikodemocracy.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import net.nikodem.nikodemocracy.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;

/**
 * @author Peter Nikodem
 */
@Secured("ROLE_ELECTION_ADMIN")
@SpringView(name = StopElectionView.NAME)
public class StopElectionView extends AbstractView {
    public static final String NAME = "stop";

    @Autowired
    private ElectionService electionService;

    private String electionShortUrl;

    public static String getPath(String shortUrl) {
        return NAME + "/" + shortUrl;
    }

    @PostConstruct
    private void initView(){
        electionService.findElectionByShortUrl(electionShortUrl);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        electionShortUrl = event.getParameters();
    }
}
