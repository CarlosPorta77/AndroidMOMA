package innovationlab.cc.momart.controller;

import innovationlab.cc.momart.model.DAO.MomaDAO;
import innovationlab.cc.momart.model.POJO.PaintContainer;
import innovationlab.cc.momart.utils.ResultListener;

public class PaintController {

    public void getPaints(final ResultListener<PaintContainer> listener){

        MomaDAO momaDAO = new MomaDAO();
        momaDAO.getPaints(new ResultListener<PaintContainer>() {
            @Override
            public void finish(PaintContainer result) {
                listener.finish(result);
            }
        });
    }
}
