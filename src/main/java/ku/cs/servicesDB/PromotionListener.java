package ku.cs.servicesDB;

import ku.cs.models.Promotion;

import java.io.IOException;

public interface PromotionListener {

    public void onClickProListener(Promotion promotion) throws IOException;
}
