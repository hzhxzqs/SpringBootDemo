package zam.hzh.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zam.hzh.jpa.common.result.Msg;
import zam.hzh.jpa.common.result.MsgEnum;
import zam.hzh.jpa.dao.DemoDAO;
import zam.hzh.jpa.model.Demo;
import zam.hzh.jpa.service.DemoService;

import java.util.List;
import java.util.Optional;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDAO demoDAO;

    @Override
    public Msg addDemo(Demo addDemo) {
        demoDAO.save(addDemo);
        return Msg.success();
    }

    @Override
    public Msg deleteDemo(String deleteId) {
        Optional<Demo> optional = demoDAO.findById(deleteId);
        if (!optional.isPresent()) {
            return Msg.error(MsgEnum.DATA_NOT_EXIST);
        }
        demoDAO.deleteById(deleteId);
        return Msg.success();
    }

    @Override
    public Msg findAllDemo() {
        List<Demo> demoList = demoDAO.findAll();
        return Msg.success(demoList);
    }

    @Override
    public Msg updateDemo(Demo updateDemo) {
        Optional<Demo> optional = demoDAO.findById(updateDemo.getId());
        if (!optional.isPresent()) {
            return Msg.error(MsgEnum.DATA_NOT_EXIST);
        }
        Demo demo = optional.get();
        demo.setDemoDesc(updateDemo.getDemoDesc());
        demoDAO.save(demo);
        return Msg.success();
    }
}
