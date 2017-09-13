package org.rapidpm.vaadin.server;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.rapidpm.ddi.Produces;
import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.vaadin.trainer.api.security.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;

/**
 *
 */
@Theme("valo")
//@Push
@PreserveOnRefresh
public class MyUI extends JumpstartUI {


  @Produces(value = JumpstartUI.class)
  public static class MyUIProducer implements Producer<JumpstartUI> {
    @Override
    public JumpstartUI create() {
      return new MyUI();
    }
  }


  private static final Logger LOGGER = LoggerFactory.getLogger(MyUI.class);

  @Inject JumpstartUIComponentFactory jumpstartUIComponentFactory;

  @Override
  protected void init(VaadinRequest vaadinRequest) {

    LOGGER.debug("init - request = " + vaadinRequest);
    boolean remembered = isRemembered();

    if (! (user().isPresent() && remembered)) setContent(login(vaadinRequest));

    setSizeFull();
  }

  private boolean isRemembered() {
    return SecurityUtils.getSubject().isRemembered();
  }

  private Optional<User> user() {
    return Optional
        .ofNullable(
            getCurrent()
                .getSession()
                .getAttribute(User.class));
  }

  private Component login(VaadinRequest vaadinRequest) {
    return jumpstartUIComponentFactory.createComponentToSetAsContent(vaadinRequest);
  }

  @Override
  protected void refresh(VaadinRequest request) {
    super.refresh(request);
    LOGGER.debug("refresh - request = " + request);
  }

}
