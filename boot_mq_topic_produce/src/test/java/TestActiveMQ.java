import org.bootmq.MainBootMQ;
import org.bootmq.produce.TopicProduce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @author lijichen
 * @date 2021/2/8 - 14:42
 */
@SpringBootTest(classes = MainBootMQ.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {

    @Resource    //  这个是java 的注解，而Autowried  是 spring 的
    private TopicProduce queue_produce ;

    @Test
    public  void testSend() throws Exception{
        queue_produce.produceSendMessage();
    }
}